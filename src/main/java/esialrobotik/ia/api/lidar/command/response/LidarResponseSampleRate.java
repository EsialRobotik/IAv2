package esialrobotik.ia.api.lidar.command.response;

public class LidarResponseSampleRate {

	/**
	 * Le temps d'acquisition d'un seul point en mode SCAN en micro secondes
	 */
	public int timeStandard;
	
	/**
	 * Le temps d'acquisition d'un seul point en mode EXPRESS_SCAN en micro secondes
	 */
	public int timeExpress;
	
	@Override
	public String toString() {
		return "Standard sample rate : "+this.timeStandard+"µs\nExpress sample rate : "+this.timeExpress+"µs";
	}
}
