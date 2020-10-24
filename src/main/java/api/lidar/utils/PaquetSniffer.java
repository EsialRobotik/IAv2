package api.lidar.utils;

import java.io.IOException;

import api.lidar.link.RpLidarLink;

/**
 * Classe utilitaire pour faciliter le d�bugage de la lisiaon s�rie avec le Lidar
 * @author gryttix
 *
 */
public class PaquetSniffer {
	
	protected RpLidarLink link;
	
	/**
	 * Un tableau dont les contiennent les valeurs hexadecimales des requ�tes suivies du nom de la requ�te
	 * Les espaces sont ignor�s lors de la recherche de correspondance
	 * Le # marque la s�paration hexad�cimal / nom de la requ�te
	 */
	protected static final byte[][] REQUESTS_HEXA = new byte[][] {
		new byte[] { (byte) 0xA5, (byte) 0x25 },
		new byte[] { (byte) 0xA5, (byte) 0x40 },
		new byte[] { (byte) 0xA5, (byte) 0x20 },
		new byte[] { (byte) 0xA5, (byte) 0x82, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x22 },
		new byte[] { (byte) 0xA5, (byte) 0x21 },
		new byte[] { (byte) 0xA5, (byte) 0x50 },
		new byte[] { (byte) 0xA5, (byte) 0x52 },
		new byte[] { (byte) 0xA5, (byte) 0x59 },
	};
	
	protected static final String[] REQUESTS_NAMES = new String[] {
			"STOP",
			"RESET",
			"SCAN",
			"EXPRESS_SCAN",
			"FORCE_SCAN",
			"GET_INFO",
			"GET_HEALTH",
			"GET_SAMPLERATE"
	};
	
	/**
	 * La longueur maximale d'une requ�te, soit le nombre de byte de la requ�te la plus longue
	 */
	protected static final int MAX_REQUEST_LENGTH = 9;
	
	/**
	 * La valeur indiquant le d�but d'une requ�te
	 */
	protected static final byte START_REQUEST_BYTE = (byte) 0xA5;
	
	/**
	 * 
	 * @param link
	 */
	public PaquetSniffer(RpLidarLink link) {
		this.link = link;
	}

	/**
	 * Ecoute le flux d'entr�e et affiche dans la console les bytes envoy�s au Lidar en hexadecimal
	 * Chaque fois qu'un paquet 0xA5 est re�u, un saut de lign est ins�r� avant son affichage
	 * Cette m�thode bloque ind�finiment 
	 */
	public void sniffRequests() {
		System.out.println("Sniffer de requ�tes lanc�");
		// On d�termine la longueur max d'une requ�te
		try {
			byte[] b = new byte[1];
			byte[] bufferRequest = new byte[MAX_REQUEST_LENGTH];
			int offsetBufferRequest = 0;
			while (true) {
				if (this.link.tryToReadNByteWith1Retry(b, 1) > 0) {
					// Nouvelle commande qui d�marre pr�matur�ment ?
					if (b[0] == START_REQUEST_BYTE && offsetBufferRequest > 0) {
						String request = this.tryToMatchRequest(bufferRequest, 0);
						System.out.println(request == null ? "#commande inconnue" : request);
						offsetBufferRequest = 0;
					}
					
					System.out.print(LidarHelper.unsignedBytesToHex(b)+" ");
					bufferRequest[offsetBufferRequest++] = b[0];
					
					// Si on a trouv� une requ�te => on passe � la ligne suivante
					String request = this.tryToMatchRequest(bufferRequest, 0);
					if (request != null) {
						System.out.println(request);
						offsetBufferRequest = 0;
						resetBuffer(bufferRequest);
					}
					
					// Nouvelle requ�te ou taille d'une requ�te d�pass�e => on essaye de deviner la pr�c�dente, sinon on passe � la ligne
					if (offsetBufferRequest >= MAX_REQUEST_LENGTH) {
						System.out.println("#requete inconnue#");
						offsetBufferRequest = 0;
						resetBuffer(bufferRequest);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Essaye de retrouver la commande invoqu�e dans la suite de byte donn�es
	 * @param circularBuffer un buffer de bytes circulaire
	 * @param bufferStartOffset le point de d�part du buffer
	 * @return
	 */
	protected String tryToMatchRequest(byte[] circularBuffer, int bufferStartOffset) {
		if (circularBuffer == null || circularBuffer.length == 0 || bufferStartOffset < 0) {
			return "";
		}
		
		for (int i=0; i<REQUESTS_HEXA.length; i++) {
			byte[] requestHex = REQUESTS_HEXA[i];
			boolean flagFound = true;
			for (int j=0; j<requestHex.length; j++) {
				if (requestHex[j] != circularBuffer[(bufferStartOffset + j) % circularBuffer.length]) {
					flagFound = false;
					break;
				}
			}
			
			if (flagFound) {
				return REQUESTS_NAMES[i];
			}
		}
		
		return null;
	}
	
	protected void resetBuffer(byte[] buffer) {
		for (int i=0; i<buffer.length; i++) {
			buffer[i] = (byte) 0x00;
		}
	}
	
}
