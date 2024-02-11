package esialrobotik.ia.actions.a2020;

import esialrobotik.ia.actions.ActionCollection;
import esialrobotik.ia.actions.ActionDescriptor;
import esialrobotik.ia.actions.ActionExecutor;
import esialrobotik.ia.actions.Step;
import esialrobotik.ia.api.communication.Shell;
import esialrobotik.ia.api.log.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import esialrobotik.ia.manager.CommunicationManager;
import org.slf4j.event.Level;
import org.slf4j.Logger;

import java.io.IOException;

public class ArucoCamAction implements ActionExecutor {

    protected boolean finished = false;
    protected Shell shell;
    private Logger logger;

    protected ActionCollection actionCollection;
    protected CommunicationManager communicationManager;
    protected ActionDescriptor nord0, sud0, nord3000, sud3000;

    public ArucoCamAction(Shell shell, ActionCollection actionCollection) {
        this.shell = shell;
        this.actionCollection = actionCollection;

        this.logger = LoggerFactory.getLogger(ArucoCamAction.class);

        Gson gson = new Gson();
        JsonObject jsonObjectif = gson.fromJson(
            "{\"couleur0\":[{\"desc\":\"Port Nord\",\"id\":1,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"On se gare\",\"id\":1,\"positionX\":200,\"positionY\":500,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto_astar\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":2,\"positionX\":200,\"positionY\":200,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]},{\"desc\":\"Port Sud\",\"id\":2,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"On se gare\",\"id\":1,\"positionX\":1400,\"positionY\":500,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto_astar\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":2,\"positionX\":1440,\"positionY\":200,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}],\"couleur3000\":[{\"desc\":\"Port Nord\",\"id\":1,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"On se gare\",\"id\":1,\"positionX\":200,\"positionY\":2500,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto_astar\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":2,\"positionX\":200,\"positionY\":2800,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]},{\"desc\":\"Port Sud\",\"id\":2,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"On se gare\",\"id\":1,\"positionX\":1400,\"positionY\":2500,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto_astar\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":2,\"positionX\":1440,\"positionY\":2800,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}]}\n",
            JsonObject.class
        );

        this.nord0 = new ActionDescriptor(
            jsonObjectif.getAsJsonArray("couleur0").get(0).getAsJsonObject(),
            actionCollection.isStepByStep()
        );
        this.sud0 = new ActionDescriptor(
            jsonObjectif.getAsJsonArray("couleur0").get(1).getAsJsonObject(),
            actionCollection.isStepByStep()
        );
        this.nord3000 = new ActionDescriptor(
            jsonObjectif.getAsJsonArray("couleur3000").get(0).getAsJsonObject(),
            actionCollection.isStepByStep()
        );
        this.sud3000 = new ActionDescriptor(
            jsonObjectif.getAsJsonArray("couleur3000").get(1).getAsJsonObject(),
            actionCollection.isStepByStep()
        );
    }

    public void setCommunicationManager(CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
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
                    logger.info("Analyse du tag... OU PAS !!!!");
                    shell.send("a");
                    String result = shell.read();
                    logger.info("Tag = " + result);
                    if (!result.contains("#NORD#") && !result.contains("#SUD#")) {
                        logger.info("Analyse du tag bis");
                        try {
                            result = shell.read();
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                        logger.info("Tag = " + result);
                    }

                    if (result.contains("#NORD#")) {
                        logger.info("NORD détecté");
                        if (actionCollection.isColor0()) {
                            actionCollection.addAction(nord0);
                            logger.info("Load Nord0");
                        } else {
                            actionCollection.addAction(nord3000);
                            logger.info("Load Nord3000");
                        }
//                        communicationManager.sendActionData(ActionFileBinder.ActionFile.PMI_BOUSSOLE.ordinal(), "NORD");
                    } else if (result.contains("#SUD#")) {
                        logger.info("SUD détecté");
                        if (actionCollection.isColor0()) {
                            actionCollection.addAction(sud0);
                            logger.info("Load Sud0");
                        } else {
                            actionCollection.addAction(sud3000);
                            logger.info("Load Sud3000");
                        }
//                        communicationManager.sendActionData(ActionFileBinder.ActionFile.PMI_BOUSSOLE.ordinal(), "SUD");
                    } else {
                        logger.info("JE SAIS PAS !!! MAIS JAMAIS AU NORD !!!");
                        if (actionCollection.isColor0()) {
                            actionCollection.addAction(sud0);
                            logger.info("Load Sud0");
                        } else {
                            actionCollection.addAction(sud3000);
                            logger.info("Load Sud3000");
                        }
//                        communicationManager.sendActionData(ActionFileBinder.ActionFile.PMI_BOUSSOLE.ordinal(), "NORD");
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
        // nothing
    }

    @Override
    public String getActionFlag() {
        return null;
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        LoggerFactory.setDefaultLevel(Level.TRACE);
        Shell shell = new Shell("python /home/pi/2020Aruco/testPiCameraArucoShell.py --quiet");
        shell.start();
        ActionCollection actionCollection = new ActionCollection("configCollection.json");
        actionCollection.prepareActionList(false);
        ArucoCamAction action = new ArucoCamAction(shell, actionCollection);
        action.execute();
        Thread.sleep(1000);
        ActionDescriptor actionDescriptor = actionCollection.getActionList().get(actionCollection.getActionList().size()-1);
        System.out.println(actionDescriptor);
        while (actionDescriptor.hasNextStep()) {
            Step step = actionDescriptor.getNextStep();
            System.out.println(step);
        }
    }
}
