package utils.web.handler;

import java.io.IOException;

import actions.ax12.ActionOrchestrator;
import com.sun.net.httpserver.HttpExchange;

import utils.web.AX12Http;


public class StopHandler extends AbstractHandler {

	public StopHandler(AX12Http ax12Http) {
		super(ax12Http);
	}

	@Override
	public void handleRequest(HttpExchange arg0) throws IOException {
		ActionOrchestrator ao = this.ax12Http.getActionOrchestrator();
		if (ao != null) {
			ao.stopPlayingPools();
		}
	}

	@Override
	public String getUrlSuffix() {
		return "stop";
	}
	
}
