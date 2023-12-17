package esialrobotik.ia.utils.web.handler;

import java.io.IOException;

import esialrobotik.ia.api.ax12.AX12;
import esialrobotik.ia.api.ax12.value.AX12Position;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import esialrobotik.ia.utils.web.AX12Http;

public class ReadHandler extends AbstractHandler {

	public ReadHandler(AX12Http ax12Http) {
		super(ax12Http);
	}

	@Override
	public void handleRequest(HttpExchange t) throws IOException, HandlerException {
		this.checkMethod(t, "post");
		
		JsonObject o = this.parseRequestBodyAsJsonObject(t);
		
		if (!o.has("property") || !o.get("property").isJsonPrimitive()) {
			this.sendError(t, "property doit être une chaîne de caractères", 406);
			return;
		}
		
		String property = o.get("property").getAsString();
		
		if (property.equals("ax12Angle")) {
			this.handleAx12Angle(o, t);
		} else {
			this.sendError(t, "Propriété inconnue '" + property, 406);
		}
	}

	@Override
	public String getUrlSuffix() {
		return "read";
	}
	
	/**
	 * Gère la lecture de la position de l'AX12
	 * @param o
	 * @param t
	 * @throws IOException
	 * @throws HandlerException 
	 */
	protected void handleAx12Angle(JsonObject o, HttpExchange t) throws IOException, HandlerException {
		if (!o.has("id") || !o.get("id").isJsonPrimitive()) {
			this.sendError(t, "id doit être un nombre entier", 406);
			return;
		}
		
		this.checkAx12LinkConnected();
		
		try {
			AX12 ax12 = new AX12(o.get("id").getAsInt(), this.ax12Http.getAx12Link());
			AX12Position ap = ax12.readServoPosition();

			this.sendResponse(t, "{\"rawPosition\":" + ap.getRawAngle() + ",\"readablePosition\":" + ap.getAngleAsDegrees() + "}", 200);

		} catch (Exception e) {			
			this.sendError(t, e.getMessage(), 500);
		}
	}

}
