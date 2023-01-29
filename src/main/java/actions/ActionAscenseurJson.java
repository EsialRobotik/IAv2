package actions;

import api.communication.Serial;
import api.communication.SerialRxTx;
import api.log.LoggerFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.pi4j.io.serial.SerialDataEventListener;
import manager.CommunicationManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActionAscenseurJson implements ActionExecutor {

    private final SerialRxTx serial;
    private final String filename;

    private File dataDir;

    private final List<String> commands;
    protected boolean finished;
    protected Logger logger;

    public ActionAscenseurJson(SerialRxTx serial, File datadir, String filename) {
        logger = LoggerFactory.getLogger(ActionAscenseurJson.class);
        logger.info("init whith file : " + filename);
        this.commands = new ArrayList<>();
        this.serial = serial;
        this.filename = filename;
        this.finished = false;
        this.dataDir = datadir;
        this.parseFile();
    }

    @Override
    public void execute() {
        if (finished) {
            logger.info(" finished command : " + filename);
            return;
        }
        logger.info(" execute command : " + filename);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = serial.getInputStream();
                    boolean hadError = false;
                    for (String cmd : commands) {
                        try {
                            serial.getOutputStream().write(cmd.getBytes());
                            serial.getOutputStream().flush();
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                if (is.available() > 0) {
                                    sb.append((char) is.read());
                                    String result = sb.toString().trim();
                                    if (result.equals("ok") || result.equals("err")) {
                                        logger.info(ActionAscenseurJson.class.getName() + " command <"+cmd+"> of " + filename + " finished");
                                        break;
                                    }
                                }
                                Thread.sleep(10);
                            }
                        } catch (IOException | InterruptedException e) {
                            logger.error(e);
                        }
                    }
                    logger.info(ActionAscenseurJson.class.getName() + " all commands of " + filename + " finished");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    finished = true;
                }
            }
        }).start();
    }

    protected void parseFile()
    {
        try {
            JsonReader jr = new JsonReader(new FileReader(this.dataDir.getAbsolutePath() + File.separator + filename));
            try {
                JsonElement elt = JsonParser.parseReader(jr);
                if (!elt.isJsonArray()) {
                    logger.error(this.filename+" : le contenu du fichier doit être un tableau de commandes");
                } else {
                    for (JsonElement jse: elt.getAsJsonArray()) {
                        if (!jse.isJsonObject()) {
                            continue;
                        }
                        JsonObject jso = jse.getAsJsonObject();
                        if (jso.has("command") && jso.get("command").isJsonPrimitive()) {
                            commands.add(jso.get("command").getAsString());
                        }
                    }
                    if (commands.isEmpty()) {
                        logger.error("Aucune commande d'ascenseur trouvée dans le fichier "+filename);
                    }
                }
            } catch (JsonParseException e) {
                logger.error(e);
            }
            jr.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public boolean finished() {
        return this.finished;
    }

    @Override
    public void resetActionState() {
        this.finished = false;
    }

    @Override
    public void setData(String data) {}

    @Override
    public void setCommunicationManager(CommunicationManager communicationManager) {}

    @Override
    public String getActionFlag() {
        return null;
    }
}
