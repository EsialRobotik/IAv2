package api.lidar.command.exception;

import api.lidar.command.LidarCommandException;

/**
 * Lorsqu'une tentative de commande a �t� effectu�e sur le Lidar alors qu'une autre commande n'�tait pas termin�e
 * @author gryttix
 *
 */
public class LidarCommandAlreadyRunningException extends LidarCommandException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LidarCommandAlreadyRunningException(String msg) {
		super(msg);
	}

	public LidarCommandAlreadyRunningException(String msg, Throwable previous) {
		super(msg, previous);
	}
	
}
