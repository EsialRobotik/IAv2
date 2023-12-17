package esialrobotik.ia.utils.web.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import esialrobotik.ia.api.ax12.AX12;
import esialrobotik.ia.api.ax12.AX12Exception;
import esialrobotik.ia.api.ax12.AX12Link;
import esialrobotik.ia.api.ax12.AX12LinkException;
import esialrobotik.ia.api.ax12.value.AX12Compliance;
import esialrobotik.ia.utils.web.AX12Http;

public class SettingsHandler extends AbstractHandler {

	public SettingsHandler(AX12Http ax12Http) {
		super(ax12Http);
	}

	@Override
	protected void handleRequest(HttpExchange t) throws IOException, HandlerException {
		this.checkMethod(t, "post");
		this.checkAx12LinkConnected();
		
		JsonObject o = this.parseRequestBodyAsJsonObject(t);
		if (!o.has("settings") || !o.get("settings").isJsonPrimitive()) {
			throw new HandlerException("settings doit être une string", 500);
		}
		String factor = o.get("settings").getAsString();
		
		if (factor.equals("release")) {
			handleReleaseAx(o);	
		}
		
		if (factor.equals("behaviour")) {
			handleBehaviour(o);	
		}
		
		if (factor.equals("pumps")) {
			handlePumps(o);	
		}
		
		this.sendResponse(t, "{\"status\":\"ok\"}", 200);
	}

	@Override
	public String getUrlSuffix() {
		return "settings";
	}
	
	protected void handleReleaseAx(JsonObject o) throws HandlerException {
		List<AX12> axs = this.ax12FromJson(o);
		
		for (AX12 ax : axs) {
			try {
				ax.disableTorque();
			} catch (AX12LinkException | AX12Exception e) {
				throw new HandlerException("Erreur de relache de l'AX #"+ax.getAddress()+" : "+e.getMessage(), 500);
			}
		}
	}
	
	protected void handleBehaviour(JsonObject o) throws HandlerException {
		List<AX12> axs = this.ax12FromJson(o);
		
		int vitesse = this.extractValue(o, "speed", 0, 1023);
		int acceleration = this.extractValue(o, "acceleration", 0, 255);
		int precision = this.extractValue(o, "precision", 1, 7);
		
		for (AX12 ax : axs) {
			try {
				ax.setServoSpeed(vitesse);
				ax.setCcwComplianceSlope(acceleration);
				ax.setCwComplianceSlope(acceleration);
				AX12Compliance compliance = AX12Compliance.fromFriendlyValue(precision);
				ax.setCcwComplianceMargin(compliance);
				ax.setCwComplianceMargin(compliance);
			} catch (AX12LinkException | AX12Exception | IllegalArgumentException e) {
				throw new HandlerException("Erreur de relache de l'AX #"+ax.getAddress()+" : "+e.getMessage(), 500);
			}
		}
	}
	
	protected void handlePumps(JsonObject o) throws HandlerException {
		
		boolean enable = o.get("enable").getAsBoolean();
		AX12Link a = this.ax12Http.getAx12Link();
		try {
			a.enableDtr(enable);
			a.enableRts(enable);
		} catch (AX12LinkException e) {
			e.printStackTrace();
		}
	}
	
	protected int extractValue(JsonObject o, String value, int min, int max) throws HandlerException {
		if (!o.has(value) || !o.get(value).isJsonPrimitive()) {
			throw new HandlerException(value + " doit être un entier", 406);
		}
		
		try {
			int v = o.get(value).getAsInt();
			
			if (v > max) {
				new HandlerException(value + " doit être inférieure à " + max, 406);
			}
			
			if (v < min) {
				new HandlerException(value + " doit être supérieur à " + min, 406);
			}
			
			return v;
		} catch (ClassCastException e) {
			throw new HandlerException(value + " doit être un entier", 406, e);
		}
	}
	
	protected List<AX12> ax12FromJson(JsonObject o) throws HandlerException {
		if (!o.has("ax12ids") || !o.get("ax12ids").isJsonArray()) {
			throw new HandlerException("La liste des AX12 doit être un tableau d'entiers", 406);
		}
		
		JsonArray jsa = o.get("ax12ids").getAsJsonArray();
		int size = jsa.size();
		
		if (size == 0) {
			throw new HandlerException("La liste des AX12 est vide", 406);
		}
		
		List<AX12> list = new ArrayList<>();
		
		for (int i=0; i<size; i++) {
			if (!jsa.get(i).isJsonPrimitive()) {
				throw new HandlerException("L'id #"+i+" n'est pas un entier", 406);
			}
			
			list.add(new AX12(jsa.get(i).getAsInt(), this.ax12Http.getAx12Link()));
		}
		
		return list;
	}

}
