package api.lidar.link;

import java.io.IOException;
import java.io.InputStream;

import api.communication.SerialDevice;
import api.lidar.utils.LidarHelper;
import com.pi4j.io.serial.Baud;

/**
 * Proxy pour accéder au port de communication du Lidar
 * 
 * @author gryttix
 *
 */
public class RpLidarLink {

	protected SerialDevice serialDevice;
	protected InputStream in;

	public RpLidarLink(String sp) throws IOException {
		this.serialDevice = new SerialDevice(sp, Baud._115200);
		this.in = this.serialDevice.getInputStream();
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

	public void write(byte[] b) throws IOException {
		//this.serialDevice.write(b);
	}

	/**
	 * Démarre ou arrête la rotation du Lidar
	 * 
	 * @param enable true = démarrer, falmse = arrêter
	 * @throws IOException
	 */
	public void enableRotation(boolean enable) {
		//this.serialDevice.setDTR(!enable);
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
