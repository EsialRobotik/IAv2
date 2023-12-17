package esialrobotik.ia.utils.web.handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import esialrobotik.ia.utils.web.AX12Http;

public abstract class AbstractHandler implements HttpHandler {
	
	protected AX12Http ax12Http;
	
	public AbstractHandler(AX12Http ax12Http) {
		this.ax12Http = ax12Http;
	}
	
	public void registerServer(HttpServer server) {
		server.createContext("/api/" + this.getUrlSuffix(), this);
	}
	
	@Override
	public void handle(HttpExchange t) throws IOException {
		try {
			this.handleRequest(t);
		} catch (HandlerException e) {
			this.sendError(t, e.getMessage(), e.getHttpCode());
		} catch (Exception e) {
			this.sendError(t, "Erreur : " + e.getMessage(), 500);
		}
	}
	
	/**
	 * Factorisation du code d'envoi d'une réponse Json simple
	 * @param t
	 * @param rawJson
	 * @param httpCode
	 * @throws IOException
	 */
	protected void sendResponse(HttpExchange t, String rawJson, int httpCode) throws IOException {
		t.getResponseHeaders().add("Content-Type", "application/json");
		t.sendResponseHeaders(httpCode, rawJson.length());
		OutputStream os = t.getResponseBody();
		os.write(rawJson.getBytes());
		os.close();
	}
	
	/**
	 * Renvoie une erreur standrdisée au format Json
	 * @param t
	 * @param error
	 * @param httpCode
	 * @throws IOException
	 */
	protected void sendError(HttpExchange t, String error, int httpCode) throws IOException {
		if (error == null) {
			error = "null";
		}
		this.sendResponse(t, "{\"error\":" + new JsonPrimitive(error).toString() + "}", httpCode);
	}
	
	/**
	 * Extrait le corp de la requête et essaye de le parse de json vers un json object
	 * Retourne une 406 si aucun Json object n'est trouvé
	 * @param t
	 * @return null si aucun json object valide n'a pu être extrait
	 * @throws IOException
	 */
	protected JsonObject parseRequestBodyAsJsonObject(HttpExchange t) throws IOException, HandlerException {
		JsonReader jr = new JsonReader(new InputStreamReader(t.getRequestBody()));
		JsonParser jp = new JsonParser();
		try {
			JsonElement elt = jp.parse(jr);			
			if (elt.isJsonObject()) {
				return elt.getAsJsonObject();
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} finally {
			jr.close();
		}
		
		throw new HandlerException("Data must be valid json object", 406);
	}
	
	/**
	 * Vérifie la méthode utilisée dans la requête et renvoie un 405 dans la requête si elle n'est pas bonne
	 * Ne tient pas compte de la casse de la méthode
	 * 	
	 * @param t
	 * @param method
	 * @return false si la méthode n'est pas respectée, true sinon
	 * @throws IOException
	 */
	protected void checkMethod(HttpExchange t, String method) throws HandlerException {
		if (!t.getRequestMethod().toLowerCase().equals(method.toLowerCase())) {
			throw new HandlerException("Method not allowed, " + method + " expected", 405);
		}
	}
	
	protected void checkAx12LinkConnected() throws HandlerException {
		if (this.ax12Http.getAx12Link() == null) {
			throw new HandlerException("Aucun lien vers les AX12", 503);
		}
	}
	
	public abstract String getUrlSuffix();
	
	protected abstract void handleRequest(HttpExchange t) throws IOException, HandlerException;
	

}
