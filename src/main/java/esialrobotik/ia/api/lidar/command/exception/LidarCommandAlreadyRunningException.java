package esialrobotik.ia.api.lidar.command.exception;

import esialrobotik.ia.api.lidar.command.LidarCommandException;

/**
 * Lorsqu'une tentative de commande a été effectuée sur le Lidar alors qu'une autre commande n'était pas terminée
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
