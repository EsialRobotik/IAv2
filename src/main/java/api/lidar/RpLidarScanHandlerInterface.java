package api.lidar;

/**
 * D�crit une interface pour recevoir des donn�es en provenance du Lidar
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
	 * @param quality la qualit� de la mesure
	 * @param nouvelleRotation indique s'il s'agit d'une nouvelle s�rie de mesure du Lidar => le Lidar entame une nouvelle r�volution. true = nouvelle rotation
	 * @param angle l'angle de la mesure en degr�s
	 * @param distance la distane de l'obtacle par rapport au lidar en mm
	 */
	public void handleLidarScan(int quality, boolean nouvelleRotation, double angle, double distance);
	
	/**
	 * Notifie l'obesrveur que le scan s'est termin� pour la raison donn�e
	 * 
	 * @param reason
	 */
	public void scanStopped(SCAN_STOP_REASON reason);
	
	/**
	 * Indique qu'une erreur r�cup�rable s'est produite
	 * 
	 * @param error
	 */
	public void lidarRecoverableErrorOccured(RECOVERRABLE_ERROR error);
}
