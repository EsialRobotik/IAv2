package api.lidar.command.exception;

import api.lidar.command.LidarCommandException;

public class LidarCommandNotFinishedException extends LidarCommandException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LidarCommandNotFinishedException() {
		super("La commade n'est pas terminée");
	}

	
	public LidarCommandNotFinishedException(String msg) {
		super(msg);
	}

	public LidarCommandNotFinishedException(String msg, Throwable previous) {
		super(msg, previous);
	}
	
}
