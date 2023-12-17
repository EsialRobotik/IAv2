package esialrobotik.ia.utils.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import esialrobotik.ia.actions.ax12.ActionOrchestrator;
import esialrobotik.ia.api.ax12.AX12Link;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import esialrobotik.ia.utils.web.handler.SettingsHandler;
import esialrobotik.ia.utils.web.handler.FileHandler;
import esialrobotik.ia.utils.web.handler.PlayHandler;
import esialrobotik.ia.utils.web.handler.ReadHandler;
import esialrobotik.ia.utils.web.handler.RecordHandler;
import esialrobotik.ia.utils.web.handler.StopHandler;

public class AX12Http implements HttpHandler {
	
	protected File staticContentFolder;
	protected ActionOrchestrator orchestrator;
	protected AX12Link ls;

	public AX12Http(File staticContentForlder, File dataDir, AX12Link ax12Link) {
		this.staticContentFolder = staticContentForlder;
		this.orchestrator = null;
		this.ls = ax12Link;
		
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
			
			new RecordHandler(this).registerServer(server);
			new PlayHandler(this).registerServer(server);
			new StopHandler(this).registerServer(server);
			new ReadHandler(this).registerServer(server);
			new SettingsHandler(this).registerServer(server);
			new FileHandler(this, dataDir).registerServer(server);
			
			// Contexte par défaut pour servir les fichiers html/js/css/images de la GUI
			server.createContext("/", this);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Default handler that tries to server static files
	 */
	@Override
	public void handle(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        
        // On sert l'index html sur la racine
        if (path.equals("/")) {
        	path = "/index.html";
        }
        
        File requestedFile = new File(this.staticContentFolder.getAbsolutePath() + File.separator + path).getCanonicalFile();
        
        // Si le fichier demandé est en dehors du dossier root => forbidden
        if (!requestedFile.getCanonicalPath().startsWith(this.staticContentFolder.getCanonicalPath())) {
        	String response = "403 (Forbidden)\n";
            t.sendResponseHeaders(403, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        // Si le fichier demandé n'est pas valide / n'existe pas => 404
        } else if (!requestedFile.isFile()) {
          String response = "404 (Not Found)\n";
          t.sendResponseHeaders(404, response.length());
          OutputStream os = t.getResponseBody();
          os.write(response.getBytes());
          os.close();
        // On sert le fichier demandé en devinant plus ou moins son mime type
        } else {
          String mime = "text/html";
          if(path.endsWith(".js")) mime = "application/javascript";
          if(path.endsWith("css")) mime = "text/css";            

          Headers h = t.getResponseHeaders();
          h.set("Content-Type", mime);
          t.sendResponseHeaders(200, 0);              

          OutputStream os = t.getResponseBody();
          FileInputStream fs = new FileInputStream(requestedFile);
          final byte[] buffer = new byte[1024*64]; // Un chtit buffer de lecture de 64ko
          int count = 0;
          while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer,0,count);
          }
          fs.close();
          os.close();
        }  
    }
	
	/**
	 * TODO : add a synchronization to protected against multithreading
	 * @return
	 */
	public ActionOrchestrator getActionOrchestrator() {
		return this.orchestrator;
	}
	
	/**
	 * TODO : add a synchronization to protected against multithreading
	 * @param ao
	 */
	public void setActionOrchestrator(ActionOrchestrator ao) {
		this.orchestrator = ao;
	}
	
	public AX12Link getAx12Link() {
		return ls;
	}
	
}
