package api.lidar.command;

import java.io.IOException;

import api.lidar.command.exception.LidarCommandFailedException;
import api.lidar.command.exception.LidarCommandNotFinishedException;
import api.lidar.command.response.LidarResponseSampleRate;
import api.lidar.link.RpLidarLink;
import api.lidar.utils.LidarHelper;

public class LidarCommandGetSampleRate extends LidarCommand {

	protected LidarResponseSampleRate sampleRate;
	
	public LidarCommandGetSampleRate(RpLidarLink link) {
		super(link);
		this.sampleRate = null;
	}	
	
	@Override
	protected byte getCommandCode() {
		return (byte) 0x59;
	}

	@Override
	protected byte[] getCommandPayload() {
		return null;
	}

	@Override
	protected byte[] getExpectedResponseDescriptor() {
		return toByteArray(0xA5, 0x5A, 0x04, 0x00, 0x00, 0x00, 0x15);
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
		
		byte[] buf = new byte[4];
		this.readToFillBufferFully(buf);
		
		LidarResponseSampleRate sampleRate = new LidarResponseSampleRate();
		sampleRate.timeStandard = LidarHelper.intFromBytes(buf[0],  buf[1]);
		sampleRate.timeExpress = LidarHelper.intFromBytes(buf[2],  buf[3]);
		
		this.sampleRate = sampleRate;
	}

	/**
	 * Récupère la réponse de la commande
	 * 
	 * @return
	 * @throws LidarCommandFailedException
	 * @throws LidarCommandNotFinishedException
	 */
	public LidarResponseSampleRate getResult() throws LidarCommandFailedException, LidarCommandNotFinishedException {
		if (this.getCommandState() != COMMAND_STATE.FINISHED) {
			throw new LidarCommandNotFinishedException();
		}
		
		if (this.executionException != null || this.sampleRate == null) {
			throw new LidarCommandFailedException("La commande a échoué", this.executionException);
		}
		
		
		return this.sampleRate;
	}

	@Override
	public String getCommandName() {
		return "GET_SAMPLERATE";
	}
	
}
