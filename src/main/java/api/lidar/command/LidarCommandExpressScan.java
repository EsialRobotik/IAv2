package api.lidar.command;

import java.io.IOException;

import api.lidar.command.exception.LidarCommandFailedException;
import api.lidar.link.RpLidarLink;
import api.lidar.RpLidarScanHandlerInterface;

public class LidarCommandExpressScan extends LidarCommand {

	protected RpLidarScanHandlerInterface scanHandler;
	
	/**
	 * Nombre de bytes d'une Frame, header inclus
	 * 4 bytes de header puis 16 groupes de Cabin
	 */
	protected final static int FRAME_LENGTH = 84;
	
	/**
	 * Nombre de bytes d'une Cabin
	 * Une Cabin contient 2 mesures
	 */
	protected final static int CABIN_LENGTH = 5;
	
	/**
	 * Le nombre de tentatives de resynchro autoris�es avant de renvoyer une erreur
	 */
	protected final static int RESYNCHRO_THRESHOLD = 10;
	
	/**
	 * Le nombre de mauvais checksum autoris�s avant de lancer une erreur
	 */
	protected final static int CHECKSUM_ERROR_THRESHOLD = 10;
	
	protected byte[] frameWithHeader;
	protected byte[] frameN;
	protected byte[] frameNplus1;
	protected boolean frameNplus1IsFollowingFrameN;
	
	public LidarCommandExpressScan(RpLidarLink link, RpLidarScanHandlerInterface sh) {
		super(link);
		this.scanHandler = sh;
		this.frameWithHeader = new byte[FRAME_LENGTH];
		this.frameN = new byte[FRAME_LENGTH];
		this.frameNplus1 = new byte[FRAME_LENGTH];
		this.frameNplus1IsFollowingFrameN = false;
	}

	@Override
	protected byte getCommandCode() {
		return (byte) 0x82;
	}

	@Override
	protected byte[] getCommandPayload() {
		return toByteArray(0x00, 0x00, 0x00, 0x00, 0x00) ;
	}

	@Override
	protected byte[] getExpectedResponseDescriptor() {
		return toByteArray(0xA5, 0x5A, 0x54, 0x00, 0x00, 0x40, 0x82) ;
	}
	
	@Override
	protected void executePreRequest() throws LidarCommandException, IOException {
		this.startRotation();
	}
	
	@Override
	protected void executeCustomPostRequest() throws LidarCommandException {
		if (this.executionException != null) {
			return;
		}
		
		// TODO lancer le Thread de lecture de la r�ponse
		Thread lecture = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					LidarCommandExpressScan.this.acquireFrame(); // Il faut 2 frames d'affil� pour pouvoir exploiter des donn�es
					while (LidarCommandExpressScan.this.canContinue()) {
						LidarCommandExpressScan.this.acquireFrame();	
						LidarCommandExpressScan.this.processFrame();
					}
					LidarCommandExpressScan.this.stopRotation();
					LidarCommandExpressScan.this.link.cleanInput();
				} catch (IOException e) {
					LidarCommandExpressScan.this.executionException = e;
				} catch (LidarCommandException e) {
					LidarCommandExpressScan.this.executionException = e;
				}
				try {
					LidarCommandExpressScan.this.stopRotation();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		lecture.start();
	}
	static int frame = 0;
	/**
	 * Traite une Frame valide
	 * 
	 * @param frame
	 */
	protected void processFrame() {
		boolean startFlagN = this.frameN[3] >> 7 == 0x1;
		// L'angle est cod� sur 15 bits et il faut le diviser par 64.0 pour avoir la valeur d�cimale en degr�s
		double startAngleN = ((double) ((0xFF & this.frameN[3] << 7) | this.frameN[2])) / 64.0;
		double startAngleNplus1 = ((double) ((0xFF & this.frameNplus1[3] << 7) | this.frameNplus1[2])) / 64.0;;
	
		// Traitement des Cabin
		for (int i=4; i<FRAME_LENGTH; i+=5) {
			// TODO
		}
		System.out.println("Frame "+(++frame));
	}
	
	/**
	 * Lit une frame du Lidar et g�re la synchronisation : essaye de se resynchroniser si une d�synchro est d�tect�e
	 * Lance une exception si la synchronisation n'a pas pu �tre retrouv�e
	 * 
	 * @param response un tableau de 80 �l�ments
	 * @return indique si l'acquisition s'est faite du premier coup ou pas
	 * @throws LidarCommandException
	 */
	protected void acquireFrame() throws LidarCommandException, IOException {
		int resyncCount = 0;
		int checksumErrorCount = 0;
		byte[] twoBytesBuffer = new byte[2];

		while (true) {
			LidarCommandExpressScan.this.readToFillBufferFully(this.frameWithHeader);
			
			// Une frame doit commencer par 0xA 0x5, localis�s respectivement dans les deux premier bytes
			// Si ce n'est pas le cas, on fait d�filer le flux jusqu'� retrouver le flag
			// Par al suite le checksum nous dira si on s'est bien recal�
			while (!isStartFlag(this.frameWithHeader[0], this.frameWithHeader[1])) {
				for (int i=2; i<80; i++) {
					this.frameWithHeader[i-2] = this.frameWithHeader[i];
				}
				LidarCommandExpressScan.this.readToFillBufferFully(twoBytesBuffer);
				this.frameWithHeader[78] = twoBytesBuffer[0];
				this.frameWithHeader[79] = twoBytesBuffer[1];
				resyncCount++;
				
				if (resyncCount > RESYNCHRO_THRESHOLD) {
					throw new LidarCommandFailedException("Trop de d�synchronisation du flux de lecture du Lidar");
				}
			}
			
			// Si checksum valide alors on a la certitude de ne plus �tre d�synchronis�
			// Et que response contient des donn�es coh�rentes
			if (this.validateFrameCheckSum(this.frameWithHeader)) {
				this.shiftFrame(this.frameWithHeader, resyncCount == 0 && checksumErrorCount == 0);
				break;
			} else {
				checksumErrorCount++;
				if (checksumErrorCount > CHECKSUM_ERROR_THRESHOLD) {
					throw new LidarCommandFailedException("Le lidar a renvoy� de trop nombreuses frames avec un checksum erron�");
				}
			}
		}
		
	}
	
	/**
	 * Indique si les deux bytes donn�s correspondent au flag de d�but de frame
	 * @param b1
	 * @param b2
	 * @return
	 */
	protected boolean isStartFlag(byte b1, byte b2) {
		return (b1 >> 4) == 0xA && (b2 >> 4) == 0x5;
	}
	
	/**
	 * Valide le checksum d'une frame
	 * 
	 * @param response doit �tre un tableau de 80 �l�ments
	 */
	protected boolean validateFrameCheckSum(byte[] response) {
		// La valeur du checksum est r�partie dans les bits de poids faible des index 0 et 1 de la frame
		byte theoricalChecksum = (byte) ((response[0] << 4 & 0xF0) | (response[1] & 0xF));
		
		// Le cheksum est un XOR de tous les bytes de l'index 2 � 79
		byte realCheckSum = response[2];
		for (int i=3; i<response.length; i++) {
			realCheckSum |= response[i];
		}
		
		return theoricalChecksum == realCheckSum;
	}
	
	/**
	 * D�cale le FrameNplus1 dans frameN recopie la frame donn�es dans FrameNplus1
	 * 
	 * @param frameWithHeader
	 * @param isFollowing indique si la frame donn�e suis imm�diatement l'anceinne frameNplus1
	 */
	protected void shiftFrame(byte[] frameWithHeader, boolean isFollowing) {
		// recopie de fram n+1 dans n
		for (int i=0; i<FRAME_LENGTH; i++) {
			this.frameN[i] = this.frameNplus1[i];
		}
		
		// recopie de la nouvelle frame dans frame n
		for (int i=0; i<FRAME_LENGTH; i++) {
			this.frameNplus1[i] = frameWithHeader[i+4];
		}
		
		this.frameNplus1IsFollowingFrameN = isFollowing;
		
	}

	@Override
	public String getCommandName() {
		return "EXPRESS_SCAN";
	}

}
