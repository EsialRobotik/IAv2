package actions.a2020;

import actions.ActionCollection;
import actions.ActionDescriptor;
import actions.ActionExecutor;
import api.log.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import manager.CommunicationManager;
import org.apache.logging.log4j.Logger;

public class PmiBoussoleAction implements ActionExecutor {

    protected boolean finished = false;
    private Logger logger;

    protected ActionCollection actionCollection;
    protected ActionDescriptor nord0, sud0, nord3000, sud3000;

    private String commData;

    public PmiBoussoleAction(ActionCollection actionCollection) {
        this.actionCollection = actionCollection;

        this.logger = LoggerFactory.getLogger(PmiBoussoleAction.class);

        Gson gson = new Gson();
        this.nord0 = new ActionDescriptor(
            gson.fromJson(
                "{\"desc\":\"Port Nord\",\"id\":1,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"On se gare\",\"id\":1,\"positionX\":200,\"positionY\":500,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto_astar\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":2,\"positionX\":160,\"positionY\":200,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":160,\"positionY\":90,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
            ),
            actionCollection.isStepByStep()
        );
        this.sud0 = new ActionDescriptor(
            gson.fromJson(
                "{\"desc\":\"Port Sud\",\"id\":2,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"On se gare\",\"id\":1,\"positionX\":1400,\"positionY\":500,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto_astar\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":2,\"positionX\":1440,\"positionY\":200,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":1440,\"positionY\":90,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
            ),
            actionCollection.isStepByStep()
        );
        this.nord3000 = new ActionDescriptor(
            gson.fromJson(
                "{\"desc\":\"Port Nord\",\"id\":1,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"On se gare\",\"id\":1,\"positionX\":200,\"positionY\":2500,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto_astar\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":2,\"positionX\":160,\"positionY\":2800,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":160,\"positionY\":2910,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
            ),
            actionCollection.isStepByStep()
        );
        this.sud3000 = new ActionDescriptor(
            gson.fromJson(
                "{\"desc\":\"Port Sud\",\"id\":2,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"On se gare\",\"id\":1,\"positionX\":1400,\"positionY\":2500,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto_astar\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":2,\"positionX\":1440,\"positionY\":2800,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":1440,\"positionY\":2910,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
            ),
            actionCollection.isStepByStep()
        );
    }

    @Override
    public void execute() {
        if (finished) {
            return;
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    logger.info("Analyse de la data");
                    logger.info("Tag = " + commData);
                    if (commData.contains("NORD")) {
                        logger.info("NORD détecté");
                        if (actionCollection.isColor0()) {
                            actionCollection.addAction(nord0);
                            logger.info("Load Nord0");
                        } else {
                            actionCollection.addAction(nord3000);
                            logger.info("Load Nord3000");
                        }
                    } else if (commData.contains("SUD")) {
                        logger.info("SUD détecté");
                        if (actionCollection.isColor0()) {
                            actionCollection.addAction(sud0);
                            logger.info("Load Sud0");
                        } else {
                            actionCollection.addAction(sud3000);
                            logger.info("Load Sud3000");
                        }
                    } else {
                        logger.info("JE SAIS PAS !!! MAIS JAMAIS AU NORD !!!");
                        if (actionCollection.isColor0()) {
                            actionCollection.addAction(sud0);
                            logger.info("Load Sud0");
                        } else {
                            actionCollection.addAction(sud3000);
                            logger.info("Load Sud3000");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
        this.commData = data;
    }

    public void setCommunicationManager(CommunicationManager communicationManager) {
        // nothing
    }
}
