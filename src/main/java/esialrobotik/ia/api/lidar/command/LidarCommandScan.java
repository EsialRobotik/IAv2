package esialrobotik.ia.api.lidar.command;

import java.io.IOException;

import esialrobotik.ia.api.lidar.link.RpLidarLink;
import esialrobotik.ia.api.lidar.RpLidarScanHandlerInterface;
import esialrobotik.ia.api.lidar.RpLidarScanHandlerInterface.SCAN_STOP_REASON;

public class LidarCommandScan extends LidarCommand {

	protected RpLidarScanHandlerInterface scanHandler;
	
	private static final int MAX_RETRY_COUNT = 5; // on laisse au lidar 5 secondes pour se lancer
	private static final long RETRY_PERIOD_MS = 200;
	
	public LidarCommandScan(RpLidarLink link, RpLidarScanHandlerInterface scanHandler) {
		super(link);
		this.scanHandler = scanHandler;
	}

	@Override
	protected byte getCommandCode() {
		return (byte) 0x20;
	}

	@Override
	protected byte[] getCommandPayload() {
		return null;
	}

	@Override
	protected byte[] getExpectedResponseDescriptor() {
		return toByteArray(0xA5, 0x5A, 0x05, 0x00, 0x00, 0x40, 0x81);
	}
	
	@Override
	protected void executePreRequest() throws LidarCommandException, IOException {
		this.startRotation();
		this.link.cleanInput();
	}
	
	/**
	 * Lit les positions renvoyées par le Lidar
	 * 
	 * @throws LidarCommandException
	 */
	@Override
	protected void executeCustomPostRequest() throws LidarCommandException {
		if (this.executionException != null) {
			return;
		}
		int retryCount = 0;
		byte[] readBuffer = new byte[50]; // On va lire par paquets de 10 points
		int qte;
		try {
			// On attend un peu la mise en place du flux de données
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		while (this.canContinue()) {
			try {
				qte = this.link.tryToReadNByteWith1Retry(readBuffer, readBuffer.length);
				if (qte %5 != 0) {
					throw new LidarCommandException("Erreur de consistance des données cartographiques du Lidar");
				}
				
				if (qte == 0) {
					retryCount++;
					if (retryCount > MAX_RETRY_COUNT) {
						throw new LidarCommandException("Le lidar ne renvoie pas/plus de données");
					}
					Thread.sleep(RETRY_PERIOD_MS);
					continue;
				} else {
					retryCount = 0;
				}
				
				for (int i=0; i<qte; i+=5) {
					/*
					 * D'après la doc :
					 * byte[0] => 8-2 quality / 1 !S / 0 S
					 * byte[1] => 8-1 angle_q6[6:0 / 0 C
					 * byte[2] => 8-0 angle_q6[14:7]
					 * byte[3] => 8-0 distance_q2[7:0]
					 * byte[4] => 8-0 distance_q2[15:8]
					 * 
					 * S : indicateur d'une nouvelle révolution (= nouveau scan)
					 * !S : la négation de l'indicateur précédent, utilisé pour vérifier la consistance des données
					 * C : bit toujours à 1, utile pour vérifier la consistance des données
					 * quality : la qualité de la mesure (= la puissance de réflexion du laser)
					 * angle_q6 : l'angle de la mesure relative à l'orientation du lidar en degrés. A diviser par 64.0 pour avoir la vraie valeur
					 * distance_q2 : la distance du point réfléchi par rapport au lidar en millimètres. A diviser par 4.0 pour avoir al vraie valeur. 0 = mesure invalide.
					 */
					boolean s = (readBuffer[i] & 0x01) == 0x01;
					if (s != ((readBuffer[i] & 0x02) == 0x0) || (readBuffer[i+1] & 0x01) == 0x0) { // S == !S && C == 1 ?
						continue;
					}
					
					long distance = (((readBuffer[i+4] & 0xFF) << 8) | (readBuffer[i+3] & 0xFF));
					if (distance == 0) { // Distance invalide
						continue;
					}
				
					this.scanHandler.handleLidarScan(
						((readBuffer[i] >> 2) & 0xFF), // qualité
						s, // nouvelle rotation
						(((readBuffer[i+2] & 0xFF) << 7) | ((readBuffer[i+1] & 0xFF) >> 1)) / 64.0, // angle en degrés
						distance / 4.0 // distance en mm
					);
				}
			} catch (IOException e) {
				throw new LidarCommandException("Erreur de lecture des positions du Lidar", e);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			this.link.write(new byte[]{REQUEST_START_BYTE, (byte) 0x25});
			this.link.cleanInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void endCommand() {
		this.scanHandler.scanStopped(this.executionException == null ? SCAN_STOP_REASON.USER_REQUEST : SCAN_STOP_REASON.COMMUNICATION_ERROR);
	}

	@Override
	public String getCommandName() {
		return "SCAN";
	}

}
