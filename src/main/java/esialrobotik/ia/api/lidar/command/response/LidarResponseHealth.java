package esialrobotik.ia.api.lidar.command.response;

public class LidarResponseHealth {

	public enum HEALTH {
		GOOD,
		WARNING,
		ERROR
	}
	
	public int errorCode;
	
	public HEALTH health;
	
	@Override
	public String toString() {
		return health.name()+", error "+errorCode;
	}
	
}
