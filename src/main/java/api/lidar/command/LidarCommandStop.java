package api.lidar.command;

import java.io.IOException;

import api.lidar.link.RpLidarLink;

public class LidarCommandStop extends LidarCommand {

	public LidarCommandStop(RpLidarLink link) {
		super(link);
	}

	@Override
	protected byte getCommandCode() {
		return (byte) 0x25;
	}

	@Override
	protected byte[] getCommandPayload() {
		return null;
	}

	@Override
	protected byte[] getExpectedResponseDescriptor() {
		return EMPTY_BYTE_ARRAY;
	}
	
	@Override
	protected void executePreRequest() throws LidarCommandException, IOException {
		this.stopRotation();
	}
	
	@Override
	protected void executeCustomPostRequest() throws LidarCommandException, IOException {
		this.stopRotation();
		this.link.cleanInput();
	}

	@Override
	public String getCommandName() {
		return "STOP";
	}

}
