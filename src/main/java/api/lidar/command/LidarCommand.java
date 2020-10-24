package api.lidar.command;

import java.io.IOException;

import api.lidar.command.exception.LidarCommandFailedException;
import api.lidar.link.RpLidarLink;
import api.lidar.utils.LidarHelper;
import api.lidar.LidarException;

public abstract class LidarCommand extends Thread {

	public enum COMMAND_STATE {
		READY, // Pr�te � �tre lanc�e
		RUNNING, // En cours d'ex�cution
		FINISHED // Finie
	};
	
	public static final byte REQUEST_START_BYTE = (byte) 0xA5;
	
	protected final static byte[] EMPTY_BYTE_ARRAY = new byte[0];
	
	private COMMAND_STATE cs;
	private boolean canContinue;
	protected RpLidarLink link;
	private byte[] requestBuffer;
	protected Exception executionException;
	
	public LidarCommand(RpLidarLink link) {
		this.cs = COMMAND_STATE.READY;
		this.canContinue = true;
		this.link = link;
		this.requestBuffer = new byte[259]; // taille max d'une requ�te : 1 + 1 + 1 + 255 + 1
		this.requestBuffer[0] = (byte) REQUEST_START_BYTE; // Valeur de d�but d'une requ�te
		this.executionException = null;
	}
	
	/**
	 * Renvoie le status de la commande
	 * @return
	 */
	public COMMAND_STATE getCommandState() {
		return this.cs;
	}
	
	public final void run() {
		this.cs = COMMAND_STATE.RUNNING;
		try {
			this.executePreRequest();
			this.executeRequest();
			this.executeCustomPostRequest();
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof IOException) {
				this.executionException = new LidarCommandFailedException("Une erreur de communication avec le Lidar est survenue", e);
			} else {
				this.executionException = e;	
			}
		} finally {
			this.cs = COMMAND_STATE.FINISHED;
			this.endCommand();
		}
	}
	
	protected final boolean canContinue() {
		return canContinue;
	}
	
	/**
	 * Le byte identifiant la commande
	 * @return
	 */
	protected abstract byte getCommandCode();
	
	/**
	 * La payload de la commande
	 * @return peut �tre vide pour calculer un checksum, sinon null pour envoyer la commande brut sans checksum
	 */
	protected abstract byte[] getCommandPayload();
	
	/**
	 * Nom de la commande
	 * @return
	 */
	public abstract String getCommandName();
	
	/**
	 * Le descripteur de r�ponse attendu apr�s l'envoi de la commande
	 * Renvoyer un tableau vide ou null si rien n'est attendu
	 * 
	 * @return
	 */
	protected abstract byte[] getExpectedResponseDescriptor();
	
	/**
	 * Ex�cute du code avant de r�aliser la requ�te
	 * 
	 * @throws LidarCommandException
	 * @throws IOException
	 */
	protected abstract void executePreRequest() throws LidarCommandException, IOException;
	
	/**
	 * Ex�cute du code apr�s avoir effectu� la requ�te
	 * Le code ex�cut� doit �tre sensible � une demande d'arr�t et donc appeler r�guli�rement {@link LidarCommand#canContinue()}
	 * 
	 * @param e l'�ventuelle exception g�n�r�e lors de la requ�te, null si aucune exception n'a �t� g�n�r�e
	 * @throws LidarCommandException
	 * @throws  
	 */
	protected abstract void executeCustomPostRequest() throws LidarCommandException, IOException;
	
	/**
	 * Appel� � la toute fin de la commande
	 */
	protected void endCommand() {
	}
	
	/**
	 * Termine proprement la commande
	 */
	public final void terminate() {
		this.canContinue = false;
	}
	
	/**
	 * Ex�cute la requ�te associ�e � la commande
	 * 
	 * @param req
	 * @param payload ne doit jamais �tre null, peut causer un null pointer exception. Utili�e seulement si la requ�te le permet, sinon elle est simplement ignor�e.
	 * @return
	 * @throws LidarException
	 */
	protected void executeRequest() throws LidarCommandException {
		// Forger une requ�te avec payload (d'apr�s la doc) :
		//
		// | StartFlag    | Command | Payload Size | Payload Data | Checksum |
		// | 1byte (0xA5) | 1 byte  |    1 byte    | 0-255 bytes  | 1 byte   |
		//
		// Checksum = 0xA5 XOR Command XOR Payload Size XOR Paylod[0] XOR ... XOR Payload[n]
		// La requ�te doit �tre transmise enti�rement en moins de 5 secondes, sinon le lidar l'ignore
		//
		// Forger une requ�te sans payload revient  � n'envoyer que le StartFlag et la Command seuls
		
		// le requestBuffer[0] ne change jamais : 0xA5
		requestBuffer[1] = this.getCommandCode();
		
		int checksumoffset = 1;
		byte[] payload = this.getCommandPayload();
		if (payload != null) {
			checksumoffset += 1 + Math.min(payload.length, 255); // La doc dit que la payload ne peut d�passer 255 bytes// taille max d'une requ�te : 1 + 1 + 1 + 255 + 1
			requestBuffer[checksumoffset] = requestBuffer[0]; // On commence le checksum par 0xA5
			requestBuffer[checksumoffset] ^= requestBuffer[1];
			requestBuffer[checksumoffset] ^= requestBuffer[2];
			
			for (int i=3; i<checksumoffset; i++) {
				requestBuffer[i] = payload[i-3];
				requestBuffer[checksumoffset] ^= payload[i-3]; // XOR
			}		
		}		
		
		try {
			System.out.print("commande "+this.getCommandName()+" >");
			for (int i=0; i<=checksumoffset; i++) {
				System.out.print(" "+LidarHelper.unsignedBytesToHex(requestBuffer[i]));
			}
			System.out.println();
			this.link.write(requestBuffer, 0, checksumoffset+1);
			this.link.flush();
		} catch (IOException e) {
			throw new LidarCommandException("Erreur d'envoi d'une commande", e);
		}
		
		// Si un descripteur de r�ponse est attendu, on l'attend et on le v�rifie
		byte[] rd = this.getExpectedResponseDescriptor();
		if (rd.length == 0) {
			return;
		}
		byte[] response = new byte[rd.length]; 
		try {
			int qte = this.link.tryToReadNByteWith1Retry(response, response.length);
			if (qte < rd.length) {
				throw new LidarCommandException("Le lidar n'a pas r�pondu correctement � la commande. Re�u "+qte+" byte(s), "+rd.length+" attendu(s)");
			}
			for (int i=0; i<qte; i++) {
				if (rd[i] != response[i]) {
					throw new LidarCommandException("Le lidar n'a pas r�pondu correctement � la commande");
				}
			}
		} catch (IOException e) {
			throw new LidarCommandException("Erreur de v�rification du r�sultat d'une commande", e);
		}
	}
	
	protected void startRotation() throws IOException {
		this.link.enableRotation(true);
	}
	
	protected void stopRotation() throws IOException {
		this.link.enableRotation(false);
	}
	
	protected static byte[] toByteArray(int ...values) {
		byte[] r = new byte[values.length];
		for (int i=0; i<values.length; i++) {
			r[i] = (byte) (values[i] & 0xFF);
		}
		return r;
	}
	
	/**
	 * Lit le flux d'entr�e duy Lidar pour remplir compl�tement le buffer de lecture donn�
	 * @param buf
	 */
	protected void readToFillBufferFully(byte[] buf) throws LidarCommandFailedException, IOException {
		int lu = this.link.tryToReadNByteWith1Retry(buf, buf.length);
		if (lu != buf.length) {
			throw new LidarCommandFailedException("Le Lidar a produit une r�ponse plus courte qu'attendu : "+lu+"/"+buf.length+" bytes re�u(s)");
		}
	}
}
