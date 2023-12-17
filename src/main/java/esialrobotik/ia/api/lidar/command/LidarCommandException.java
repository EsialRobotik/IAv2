package esialrobotik.ia.api.lidar.command;

import esialrobotik.ia.api.lidar.LidarException;

public class LidarCommandException extends LidarException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LidarCommandException(String msg) {
		super(msg);
	}
	
	public LidarCommandException(String msg, Throwable previous) {
		super(msg, previous);
	}
	
}
