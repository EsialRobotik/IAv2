package esialrobotik.ia.actions;

import esialrobotik.ia.actions.a2023.ActionFileBinder;
import esialrobotik.ia.api.log.LoggerFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import esialrobotik.ia.manager.CommunicationManager;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ActionList implements ActionExecutor {

    protected File dataDir;
    protected String filename;
    protected Logger logger;
    protected ActionInterface actionInterface;

    protected List<ActionFileBinder.ActionFile> actions;

    protected boolean finished;

    public ActionList(ActionInterface actionInterface, File dataDir, String filename) {
        this.logger = LoggerFactory.getLogger(ActionList.class);
        this.actionInterface = actionInterface;
        this.dataDir = dataDir;
        this.filename = filename;
        this.actions = new ArrayList<>();
        parseActions();
        this.finished = false;
    }

    protected void parseActions() {
        logger.info("init with file "+filename);
        JsonElement je;
        try {
            je = JsonParser.parseReader(new JsonReader(new FileReader(this.dataDir.getAbsolutePath() + File.separator + this.filename)));
            if (je.isJsonObject()) {
                JsonObject jso = je.getAsJsonObject();
                if (jso.has("actions")) {
                    JsonElement actions = jso.get("actions");
                    if (actions.isJsonArray()) {
                        int index = 0;
                        for (JsonElement action: actions.getAsJsonArray()) {
                            if (action.isJsonPrimitive()) {
                                String enumTarget = action.getAsString();
                                try {
                                    this.actions.add(ActionFileBinder.ActionFile.valueOf(enumTarget));
                                } catch (IllegalArgumentException e) {
                                    logger.warn("l'action n°"+index+" de "+filename+" ne désigne aucune action coonnue : "+enumTarget);
                                }
                            } else {
                                logger.warn("l'action n°"+index+" de "+filename+" n'est pas une string");
                            }
                            index++;
                        }
                    }
                }
            }
            if (this.actions.isEmpty()) {
                logger.error("Le fichier "+filename+" doit contenir un objet avec un attribut 'esialrobotik.ia.actions' qui est une liste non vide d'énumération de la classe ActionFileBinder à exécuter l'une après l'autre");
            }
        } catch (FileNotFoundException e) {
            logger.error("Erreur : "+e.getMessage());
        }
    }
    @Override
    public void execute() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (ActionFileBinder.ActionFile action: actions) {
                    ActionExecutor ae = actionInterface.getActionExecutor(action.ordinal());
                    ae.resetActionState();
                    ae.execute();
                    while (!ae.finished()) {
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            // Rien
                        }
                    }
                }

                finished = true;
            }
        }).start();
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
    public void setData(String data) {

    }

    @Override
    public void setCommunicationManager(CommunicationManager communicationManager) {

    }

    @Override
    public String getActionFlag() {
        return null;
    }
}
