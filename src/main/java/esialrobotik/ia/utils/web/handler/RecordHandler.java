package esialrobotik.ia.utils.web.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import esialrobotik.ia.api.ax12.AX12;
import esialrobotik.ia.api.ax12.AX12Exception;
import esialrobotik.ia.api.ax12.AX12LinkException;
import esialrobotik.ia.api.ax12.value.AX12Position;
import esialrobotik.ia.utils.web.AX12Http;

public class RecordHandler extends AbstractHandler {

	public RecordHandler(AX12Http ax12Http) {
		super(ax12Http);
	}

	@Override
	public String getUrlSuffix() {
		return "record";
	}

	@Override
	protected void handleRequest(HttpExchange t) throws IOException, HandlerException {
		// Check basiques
		this.checkMethod(t, "post");
		
		this.checkAx12LinkConnected();
		
		// récupération des id d'ax12 à enregistrer
		JsonObject o = this.parseRequestBodyAsJsonObject(t);
		if (!o.has("ax12ids") || !o.get("ax12ids").isJsonArray()) {
			throw new HandlerException("ax12ids doit être un tableau d'ids", 406);
		}
		
		JsonArray ids = o.get("ax12ids").getAsJsonArray();
		int length = ids.size();
		
		if (length == 0) {
			throw new HandlerException("ax12ids ne doit pas être vide", 406);
		}
		
		// Instanciation des AX12, échec instanciation => erreur 500
		List<AX12> ax12s = new ArrayList<>();
		for (int i=0; i<length; i++) {
			JsonElement e = ids.get(i);
			if (!e.isJsonPrimitive()) {
				throw new HandlerException("ax12ids #"+i+" doit être un entier", 406);
			}
			
			ax12s.add(new AX12(e.getAsInt(), this.ax12Http.getAx12Link()));
		}
		
		// Lecture des AX12, échec => erreur 500
		JsonArray jsa =new JsonArray();
		for (AX12 ax : ax12s) {
			JsonObject jso = new JsonObject();
			try {
				AX12Position pos = ax.readServoPosition();
				
				jso.addProperty("ax12id", ax.getAddress());
				jso.addProperty("rawAngle", pos.getRawAngle());
				jso.addProperty("readableAngle", pos.getAngleAsDegrees());
				
				jsa.add(jso);
			} catch (AX12LinkException | AX12Exception e) {
				throw new HandlerException("Erreur de lecture de l'ax12 #" + ax.getAddress() + " : " + e.getMessage(), 500);
			}
			
		}
		
		// Envoi de toutes les valeurs
		this.sendResponse(t, jsa.toString(), 200);
	}

}
