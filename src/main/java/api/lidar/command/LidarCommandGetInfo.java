package api.lidar.command;

import java.io.IOException;

import api.lidar.command.exception.LidarCommandFailedException;
import api.lidar.command.exception.LidarCommandNotFinishedException;
import api.lidar.command.response.LidarResponseInfo;
import api.lidar.link.RpLidarLink;
import api.lidar.utils.LidarHelper;

public class LidarCommandGetInfo extends LidarCommand {
	
	protected LidarResponseInfo infos;

	public LidarCommandGetInfo(RpLidarLink link) {
		super(link);
	}

	@Override
	protected byte getCommandCode() {
		return (byte) 0x50;
	}

	@Override
	protected byte[] getCommandPayload() {
		return null;
	}

	@Override
	protected byte[] getExpectedResponseDescriptor() {
		return toByteArray(0xA5, 0x5A, 0x14, 0x00, 0x00, 0x00, 0x04);
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
		
		byte[] buf = new byte[20];
		this.readToFillBufferFully(buf);
		
		LidarResponseInfo ri = new LidarResponseInfo();
		ri.modelId = LidarHelper.unsignedByteToInt(buf[0]);
		ri.firmwareMinor = LidarHelper.unsignedByteToInt(buf[1]);
		ri.firmwareMajor = LidarHelper.unsignedByteToInt(buf[2]);
		ri.hardwareVersion = LidarHelper.unsignedByteToInt(buf[3]);
		ri.serialNumber = new byte[16];
		for (int i=0; i<ri.serialNumber.length; i++) {
			ri.serialNumber[i] = buf[i+4];
		}
		this.infos = ri;
	}
	
	/**
	 * Renvoie la réponse de la commande
	 * 
	 * @return
	 * @throws LidarCommandNotFinishedException si la commande ne s'est pas encore exécutée ou n'est pas encore temrinée
	 * @throws LidarCommandFailedException si la commande a échoué
	 */
	public LidarResponseInfo getResult() throws LidarCommandNotFinishedException, LidarCommandFailedException {
		if (this.getCommandState() != COMMAND_STATE.FINISHED) {
			throw new LidarCommandNotFinishedException();
		}
		
		if (this.executionException != null || this.infos == null) {
			throw new LidarCommandFailedException("La commande a échoué", this.executionException);
		}
		
		
		return this.infos;
	}

	@Override
	public String getCommandName() {
		return "GET_INFO";
	}
	
}
