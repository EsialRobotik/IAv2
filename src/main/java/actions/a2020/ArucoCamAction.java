package actions.a2020;

import actions.ActionCollection;
import actions.ActionDescriptor;
import actions.ActionExecutor;
import actions.Step;
import api.communication.Shell;
import api.log.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ArucoCamAction implements ActionExecutor {

    protected boolean finished = false;
    protected Shell shell;
    private Logger logger;

    protected ActionCollection actionCollection;
    protected ActionDescriptor nord0, sud0, nord3000, sud3000;

    public ArucoCamAction(Shell shell, ActionCollection actionCollection) {
        this.shell = shell;
        this.actionCollection = actionCollection;

        this.logger = LoggerFactory.getLogger(ArucoCamAction.class);

        Gson gson = new Gson();
        this.nord0 = new ActionDescriptor(
            gson.fromJson(
                "{\"desc\":\"Port Nord\",\"id\":1,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":150,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
            ),
            actionCollection.isStepByStep()
        );
        this.sud0 = new ActionDescriptor(
            gson.fromJson(
                "{\"desc\":\"Port Sud\",\"id\":2,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au sud\",\"id\":2,\"positionX\":1300,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":1300,\"positionY\":150,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
            ),
            actionCollection.isStepByStep()
        );
        this.nord3000 = new ActionDescriptor(
            gson.fromJson(
                "{\"desc\":\"Port Nord\",\"id\":1,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":2850,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
            ),
            actionCollection.isStepByStep()
        );
        this.sud3000 = new ActionDescriptor(
            gson.fromJson(
                "{\"desc\":\"Port Sud\",\"id\":2,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au sud\",\"id\":2,\"positionX\":1300,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":1300,\"positionY\":2850,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
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
                    shell.send("a");
                    String result = shell.read();
//                    String result = "#NORD#";
                    if (result.contains("#NORD#")) {
                        logger.info("NORD détecté");
                        if (actionCollection.isColor0()) {
                            actionCollection.addAction(nord0);
                            logger.info("Load Nord0");
                        } else {
                            actionCollection.addAction(nord3000);
                            logger.info("Load Nord3000");
                        }
                    } else if (result.contains("#SUD#")) {
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

    public static void main(String args[]) throws IOException, InterruptedException {
        LoggerFactory.init(Level.TRACE);
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
