package esialrobotik.ia.api.lidar;

public class LidarException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LidarException(String msg) {
		super(msg);
	}
	
	public LidarException(String msg, Throwable previous) {
		super(msg, previous);
	}

}
