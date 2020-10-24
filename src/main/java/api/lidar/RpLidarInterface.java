package api.lidar;

import api.lidar.command.response.LidarResponseHealth;
import api.lidar.command.response.LidarResponseInfo;
import api.lidar.command.response.LidarResponseSampleRate;

public interface RpLidarInterface {

	public LidarResponseHealth getHealth() throws LidarException;
	
	/**
	 * D�marre un scan dans un nouveau thread
	 */
	public void startScan(RpLidarScanHandlerInterface scanHandler) throws LidarException;
	
	/**
	 * D�marre un scan express
	 * 
	 * @param scanHandler
	 * @throws LidarException
	 */
	public void startScanExpress(RpLidarScanHandlerInterface scanHandler) throws LidarException;
	
	/**
	 * Stop un scan, qu'il soit normal ou express
	 */
	public void stopScan() throws LidarException;
	
	/**
	 * Reboot le lidar
	 */
	public void reset() throws LidarException;
	
	/**
	 * Indique si un scan est en cours
	 * @return
	 */
	public boolean scanIsRunning();
	
	/**
	 * R�cup�re le taux d'�chantillonage du Lidar
	 * 
	 * @return
	 * @throws LidarException
	 */
	public LidarResponseSampleRate getSampleRate() throws LidarException;
	
	/**
	 * R�cup�re des informations sur le Lidar
	 * @return
	 * @throws LidarException
	 */
	public LidarResponseInfo getInfos() throws LidarException;
	
}
