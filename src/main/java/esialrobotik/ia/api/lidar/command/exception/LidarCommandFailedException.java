package esialrobotik.ia.api.lidar.command.exception;

import esialrobotik.ia.api.lidar.command.LidarCommandException;

public class LidarCommandFailedException extends LidarCommandException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LidarCommandFailedException(String msg) {
		super(msg);
	}

	public LidarCommandFailedException(String msg, Throwable previous) {
		super(msg, previous);
	}
}