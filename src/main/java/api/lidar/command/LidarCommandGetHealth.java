package api.lidar.command;

import java.io.IOException;

import api.lidar.command.exception.LidarCommandFailedException;
import api.lidar.command.exception.LidarCommandNotFinishedException;
import api.lidar.command.response.LidarResponseHealth;
import api.lidar.command.response.LidarResponseHealth.HEALTH;
import api.lidar.link.RpLidarLink;

public class LidarCommandGetHealth extends LidarCommand {

	protected LidarResponseHealth health;
	
	public LidarCommandGetHealth(RpLidarLink link) {
		super(link);
		this.health = null;
	}

	@Override
	protected byte getCommandCode() {
		return (byte) 0x52;
	}

	@Override
	protected byte[] getCommandPayload() {
		return null;
	}

	@Override
	protected byte[] getExpectedResponseDescriptor() {
		return toByteArray(0xA5, 0x5A, 0x03, 0x00, 0x00, 0x00, 0x06);
	}
	
	@Override
	protected void executePreRequest() throws LidarCommandException, IOException {
		// Rien à faire
	}
	
	@Override
	protected void executeCustomPostRequest() throws LidarCommandException, IOException {
		if (this.executionException != null) {
			return;
		}
		
		byte[] buf = new byte[3];
		this.readToFillBufferFully(buf);
		
		LidarResponseHealth health = new LidarResponseHealth();
		
		health.errorCode = 0x00 | buf[2];
		health.errorCode |= buf[1] << 8;
	
		switch (buf[0]) {
			case (byte) 0x00:
				health.health = HEALTH.GOOD;
			break;
			case (byte) 0x01:
				health.health = HEALTH.WARNING;
			break;
			case (byte) 0x02:
				health.health = HEALTH.ERROR;
			break;
			default:
				throw new LidarCommandException("Le Lidar a donné une réponse inattendue");
		}
		
		this.health = health;
	}
	
	/**
	 * Récupère la réponse de la commande
	 * 
	 * @return
	 * @throws LidarCommandFailedException
	 * @throws LidarCommandNotFinishedException
	 */
	public LidarResponseHealth getResult() throws LidarCommandFailedException, LidarCommandNotFinishedException {
		if (this.getCommandState() != COMMAND_STATE.FINISHED) {
			throw new LidarCommandNotFinishedException();
		}
		
		if (this.executionException != null || this.health == null) {
			throw new LidarCommandFailedException("La commande a échoué", this.executionException);
		}
		
		return this.health;
	}

	@Override
	public String getCommandName() {
		return "GET_HEALTH";
	}

}
