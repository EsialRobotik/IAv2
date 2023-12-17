package esialrobotik.ia.api.lidar;

import esialrobotik.ia.api.lidar.command.response.LidarResponseHealth;
import esialrobotik.ia.api.lidar.command.response.LidarResponseInfo;
import esialrobotik.ia.api.lidar.command.response.LidarResponseSampleRate;

public interface RpLidarInterface {

	public LidarResponseHealth getHealth() throws LidarException;
	
	/**
	 * Démarre un scan dans un nouveau thread
	 */
	public void startScan(RpLidarScanHandlerInterface scanHandler) throws LidarException;
	
	/**
	 * Démarre un scan express
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
	 * Récupère le taux d'échantillonage du Lidar
	 * 
	 * @return
	 * @throws LidarException
	 */
	public LidarResponseSampleRate getSampleRate() throws LidarException;
	
	/**
	 * Récupère des informations sur le Lidar
	 * @return
	 * @throws LidarException
	 */
	public LidarResponseInfo getInfos() throws LidarException;
	
}
