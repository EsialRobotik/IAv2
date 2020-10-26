package utils.web.handler;

import java.io.IOException;

import actions.ax12.ActionOrchestrator;
import actions.ax12.ActionOrchestratorHelper;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import utils.web.AX12Http;

public class PlayHandler extends AbstractHandler {

	public PlayHandler(AX12Http ax12Http) {
		super(ax12Http);
	}

	@Override
	public void handleRequest(HttpExchange t) throws IOException, HandlerException {
		this.checkMethod(t, "post");
		this.checkAx12LinkConnected();
		
		JsonObject o = this.parseRequestBodyAsJsonObject(t);
		
		try {
			ActionOrchestrator ao = ActionOrchestratorHelper.actionOrchestratorFromJson(o, ax12Http.getAx12Link());
			ax12Http.setActionOrchestrator(ao);
			ao.playAllPool();
			
			this.sendResponse(t, "{\"status\": \"ok\"}", 200);	
		} catch (Exception e) {
			this.sendError(t, e.getMessage(), 500);
		}
	}

	@Override
	public String getUrlSuffix() {
		return "play";
	}

}
