package api.lidar.link;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import api.lidar.utils.LidarHelper;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * Proxy pour accéder au port de communication du Lidar
 * 
 * @author gryttix
 *
 */
public class RpLidarLink {

	protected SerialPort serialPort;
	protected InputStream in;
	protected OutputStream out;
	
	private static final int BAUD_SPEED = 115200;
	private static final int RECEIVE_TIMEOUT_MS = 200;
	
	public RpLidarLink(SerialPort sp) throws IOException {
		this.serialPort = sp;
		this.in = sp.getInputStream();
		this.out = sp.getOutputStream();
		try {
			serialPort.setSerialPortParams(
					BAUD_SPEED,
			        SerialPort.DATABITS_8,
			        SerialPort.STOPBITS_1,
			        SerialPort.PARITY_NONE
	        );
			serialPort.enableReceiveTimeout(RECEIVE_TIMEOUT_MS);
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Identique {@link InputStream#read(byte[])}
	 * 
	 * @param b
	 * @return
	 * @throws IOException
	 */
	public int read(byte[] b) throws IOException {
		return this.in.read(b);
	}
	
	public int read(byte[] b, int offset, int len) throws IOException {
		return this.in.read(b, offset, len);
	}
	
	public void write(byte[] b, int offset, int length) throws IOException {
		this.out.write(b, offset, length);
	}
	
	public void flush() throws IOException {
		this.out.flush();
	}
	
	/**
	 * Démarre ou arrête la rotation du Lidar
	 * 
	 * @param enable true = démarrer, falmse = arrêter
	 * @throws IOException
	 */
	public void enableRotation(boolean enable) {
		this.serialPort.setDTR(!enable);
	}
	
	/**
	 * Essaye de lire 'len' bytes en appellant plusieurs fois la méthode read du flux de lecture du lidar
	 * Si un appel à red renvoie -1 l'essai s'achève et la méthode renvoie le nombre de bytes lus
	 * Un appel à read bloque pendant une certaine durée avant de rendre la main et de renvoyer -1 si aucun byte n'est lu 
	 * 
	 * @param buffer
	 * @param len
	 * @return
	 * @throws IOException
	 */
	public int tryToReadNByteWith1Retry(byte[] buffer, int len) throws IOException {
		int qte = 0;
		int currentOffset = 0;
		int retryCount = 1;
		
		while (retryCount >= 0 && currentOffset < len) {
			qte = this.in.read(buffer, currentOffset, len-currentOffset);
			if (qte == 0) {
				retryCount--;
			} else {
				currentOffset += qte;	
			}
		}
		return currentOffset;
	}
	
	/**
	 * Purge le flux d'entrée
	 * @throws IOException 
	 */
	public void cleanInput() throws IOException {
		byte[] n = new byte[16];
		int qte = 0;
		while ((qte = this.tryToReadNByteWith1Retry(n, n.length)) > 0) {
			System.out.println("Clean input, found "+qte+" bytes : "+LidarHelper.unsignedBytesToHex(n));
		}
	}
	
}
