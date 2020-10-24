package api.lidar;

/**
 * Décrit une interface pour recevoir des données en provenance du Lidar
 * @author gryttix
 *
 */
public interface RpLidarScanHandlerInterface {
	
	public enum SCAN_STOP_REASON {
		USER_REQUEST,
		COMMUNICATION_ERROR,
	}
	
	public enum RECOVERRABLE_ERROR {
		CHECKSUM,
		DESYNCHRO,
	}

	/**
	 * 
	 * @param quality la qualité de la mesure
	 * @param nouvelleRotation indique s'il s'agit d'une nouvelle série de mesure du Lidar => le Lidar entame une nouvelle révolution. true = nouvelle rotation
	 * @param angle l'angle de la mesure en degrés
	 * @param distance la distane de l'obtacle par rapport au lidar en mm
	 */
	public void handleLidarScan(int quality, boolean nouvelleRotation, double angle, double distance);
	
	/**
	 * Notifie l'obesrveur que le scan s'est terminé pour la raison donnée
	 * 
	 * @param reason
	 */
	public void scanStopped(SCAN_STOP_REASON reason);
	
	/**
	 * Indique qu'une erreur récupérable s'est produite
	 * 
	 * @param error
	 */
	public void lidarRecoverableErrorOccured(RECOVERRABLE_ERROR error);
}
