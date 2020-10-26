package utils.web.handler;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;

import utils.web.AX12Http;

public class FileHandler extends AbstractHandler {
	
	protected File dataDir;

	public FileHandler(AX12Http ax12Http, File dataDir) {
		super(ax12Http);
		if (!dataDir.isDirectory()) {
			throw new IllegalArgumentException("File must be a directory");
		}
		this.dataDir = dataDir;
	}

	@Override
	public String getUrlSuffix() {
		return "file";
	}

	@Override
	protected void handleRequest(HttpExchange t) throws IOException, HandlerException {
		String url = t.getRequestURI().getPath();
		int pos = url.indexOf(getUrlSuffix() + "/");
		int prefixLength = getUrlSuffix().length();
		if (pos > 0) {
			url = url.substring(pos + prefixLength + 1);
		}
		
		if (url.startsWith("list")) {
			handleListRequest(t);
		} else if (url.equals("read")) {
			handleReadRequest(t);
		} else if (url.equals("save")) {
			handleSaveRequest(t);
		} else {
			this.sendError(t, "404 Not Found", 404);
		}
	}
	
	protected void handleListRequest(HttpExchange t) throws IOException, HandlerException {
		this.checkMethod(t, "get");
		
		File[] files = this.dataDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".json");
			}
		});
		
		Arrays.sort(files);
		
		JsonArray jsa = new JsonArray();
		for (File f : files) {
			JsonObject o = new JsonObject();
			o.add("rawName", new JsonPrimitive(f.getName()));
			o.add("name", new JsonPrimitive(f.getName().substring(0, f.getName().length() - 5)));
			jsa.add(o);
		}
		
		this.sendResponse(t, jsa.toString(), 200);
	}
	
	protected void handleReadRequest(HttpExchange t) throws IOException, HandlerException {
		this.checkMethod(t, "post");
		
		JsonObject o = this.parseRequestBodyAsJsonObject(t);
		File file = this.extrtactFile(o);
		
		if (!file.exists() || !file.isFile()) {
			throw new HandlerException("Fichier non trouvé", 404);
		}
		
		JsonParser jp = new JsonParser();
		JsonElement e = jp.parse(new FileReader(file));
		
		JsonObject r = new JsonObject();
		r.add("content", e);
		
		this.sendResponse(t, r.toString(), 200);
	}
	
	protected void handleSaveRequest(HttpExchange t) throws IOException, HandlerException {
		this.checkMethod(t, "post");
		JsonObject o = this.parseRequestBodyAsJsonObject(t);
		File file = this.extrtactFile(o);
		
		if (!o.has("content") || !o.get("content").isJsonObject()) {
			throw new HandlerException("content est vide", 406);
		}
		
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new HandlerException("Le nom de fichier donné correspond à un répertoire déjà existant", 409);
			}
		}
		
		Gson g = new Gson();
		JsonWriter jw = new JsonWriter(new FileWriter(file));
		jw.setIndent("    ");
		g.toJson(o.get("content").getAsJsonObject(), jw);
		jw.close();
		
		this.sendResponse(t, "{\"status\":\"ok\"}", 200);
	}

	/**
	 * Retourne le fichier de données dont le nom se trouve dans la clé fileName
	 * Effectue des vérifications de sécurité sur le nom et l'emplacement du fichier
	 * @param o
	 * @return
	 * @throws HandlerException
	 */
	protected File extrtactFile(JsonObject o) throws HandlerException {
		if (!o.has("fileName") || !o.get("fileName").isJsonPrimitive()) {
			throw new HandlerException("fileName doit être une chaine", 406);
		}
		
		String fileName = o.get("fileName").getAsString();
		
		if (fileName.contains("/")) {
			throw new HandlerException("fileName ne doit pas contenir de slash (/)", 406);
		}
		
		if (!fileName.toLowerCase().endsWith(".json")) {
			throw new HandlerException("fileName doit terminer par l'extension .json", 406);
		}
		
		return new File(this.dataDir.getAbsolutePath() + File.separator + fileName);
	}
}
