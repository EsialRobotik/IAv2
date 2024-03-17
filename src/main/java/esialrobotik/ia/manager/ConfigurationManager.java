package esialrobotik.ia.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import esialrobotik.ia.actions.ActionCollection;
import esialrobotik.ia.actions.ActionSupervisor;
import esialrobotik.ia.actions.FunnyActionDescription;
import esialrobotik.ia.actions.a2023.ActionFileBinder;
import esialrobotik.ia.api.ax12.AX12LinkException;
import esialrobotik.ia.api.ax12.AX12LinkSerial;
import esialrobotik.ia.api.chrono.Chrono;
import esialrobotik.ia.api.communication.SerialRxTx;
import esialrobotik.ia.api.gpio.ColorDetector;
import esialrobotik.ia.api.gpio.Tirette;
import esialrobotik.ia.api.lcd.LCD;
import esialrobotik.ia.api.lcd.LcdI2cSegment;
import esialrobotik.ia.api.lcd.seed.LcdI2c;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.api.qik.Qik;
import esialrobotik.ia.asserv.Asserv;
import esialrobotik.ia.asserv.AsservInterface;
import esialrobotik.ia.detection.DetectionInterface;
import esialrobotik.ia.detection.DetectionInterfaceImpl;
import esialrobotik.ia.detection.lidar.LidarInterface;
import esialrobotik.ia.detection.lidar.RpLidar;
import esialrobotik.ia.pathfinding.PathFinding;
import esialrobotik.ia.pathfinding.table.Table;
import esialrobotik.ia.pathfinding.table.astar.Astar;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class is responsible for the loading of the robot
 * Created by icule on 20/05/17.
 */
public class ConfigurationManager {

    public static int CONFIG_NOMINAL            = 0;
    public static int CONFIG_TEST_DETECTION     = 1;
    public static int CONFIG_TEST_INTERRUPTEURS = 2;
    public static int CONFIG_COUPEOFF           = 3;
    public static int CONFIG_TEST_LCD           = 4;
    public static int CONFIG_ACTIONNEUR         = 5;
    public static int CONFIG_PATHFINDING        = 6;

    private MovementManager movementManager;
    private LidarManager lidarManager;
    private UltraSoundManager ultraSoundManager;
    private DetectionManager detectionManager;
    private CommunicationManager communicationManager;
    private ActionCollection actionCollection;
    private ActionSupervisor actionSupervisor;
    private PathFinding pathfinding;
    private ColorDetector colorDetector;
    private Tirette tirette;
    private Chrono chrono;
    private LCD lcdDisplay;
    private AsservInterface asserv;
    private ActionFileBinder actionFileBinder;
    private FunnyActionDescription funnyActionDescription;

    public void loadConfiguration(String path) throws IOException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.loadConfiguration(path,CONFIG_NOMINAL);
    }

    public void loadConfiguration(String path, int config) throws IOException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

        Gson gson = new Gson();
        Table table = null;
        Reader reader = Files.newBufferedReader(Paths.get(path));

        JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);

        JsonObject configObject = configRootNode.get("asserv").getAsJsonObject();
        if (config != CONFIG_PATHFINDING && config != CONFIG_ACTIONNEUR) {
            logger.info("AsservAPIConfiguration = " + configObject.toString());
            asserv = new Asserv(configObject);
            movementManager = new MovementManager(asserv);
        }

        if( config == CONFIG_TEST_INTERRUPTEURS||
                config == CONFIG_TEST_DETECTION ||
                config == CONFIG_NOMINAL ||
                config == CONFIG_PATHFINDING ||
                config == CONFIG_COUPEOFF) {
            logger.info("LoadConfiguration : Misc. (tirette, couleur etc.)");
            colorDetector = new ColorDetector(configRootNode.get("gpioColorSelector").getAsInt());
            tirette = new Tirette(configRootNode.get("gpioTirette").getAsInt());
            chrono = new Chrono(configRootNode.get("matchDuration").getAsInt());
        }

        if( config == CONFIG_TEST_DETECTION ||
                config == CONFIG_NOMINAL ||
                config == CONFIG_PATHFINDING ||
                config == CONFIG_COUPEOFF) {
            logger.info("Load Table");
            table = new Table(colorDetector.isColor0() ? configRootNode.get("table0Path").getAsString() : configRootNode.get("table3000Path").getAsString());
            table.loadJsonFromFile(configRootNode.get("tableJsonPath").getAsString());
        }

        if( config == CONFIG_TEST_DETECTION ||
            config == CONFIG_NOMINAL ||
            config == CONFIG_COUPEOFF) {
            logger.info("LoadConfiguration : Detection HW");
            configObject = configRootNode.get("detection").getAsJsonObject();

            if(configObject.has("lidar")) {
                logger.info("LoadConfiguration : Lidar is present");
                LidarInterface lidarInterface = new RpLidar(configObject.get("lidar").getAsJsonObject().get("port").getAsString());
                lidarManager = new LidarManager(lidarInterface, movementManager);
            }

            DetectionInterface detectionInterface = new DetectionInterfaceImpl(configObject.getAsJsonObject("ultrasound"));
            int windowSize = configObject.getAsJsonObject("ultrasound").get("windowSize").getAsInt();
            ultraSoundManager = new UltraSoundManager(detectionInterface, windowSize, table, movementManager);
            detectionManager = new DetectionManager(detectionInterface, lidarManager, ultraSoundManager);
        }

        if( config == CONFIG_NOMINAL ||
            config == CONFIG_ACTIONNEUR ||
            config == CONFIG_COUPEOFF) {
            logger.info("LoadConfiguration : Actions");
            logger.info("Command file : " + configRootNode.get("commandFile").getAsString());

            configObject = configRootNode.get("actions").getAsJsonObject();
            actionCollection = new ActionCollection(configRootNode.get("commandFile").getAsString());

            if(configObject.has("ax12")) {
                AX12LinkSerial ax12Link = null;
                if (configObject.has("ax12")) {
                    JsonObject ax12Config = configObject.get("ax12").getAsJsonObject();
                    SerialPort sp = AX12LinkSerial.getSerialPort(ax12Config.get("serie").getAsString());
                    ax12Link = new AX12LinkSerial(sp, ax12Config.get("baud").getAsInt());
                }
                Qik qikLink = null;
                if (configObject.has("qik")) {
                    qikLink = new Qik(configObject.getAsJsonObject("qik"));
                }
                SerialRxTx serialLink = null;
                if (configObject.has("serial")) {
                    JsonObject serialConfig = configObject.getAsJsonObject("serial");
                    serialLink = new SerialRxTx(serialConfig.get("serie").getAsString(), serialConfig.get("baud").getAsInt());
                }
                String dataDir = configObject.get("dataDir").getAsString();
                JsonArray initArray = configObject.getAsJsonArray("init");
                ArrayList<String> initActions = new ArrayList<>();
                for (JsonElement actionId : initArray) {
                    initActions.add(actionId.getAsString());
                }
                actionFileBinder = new ActionFileBinder(ax12Link, dataDir, actionCollection, qikLink, serialLink);

                actionSupervisor = new ActionSupervisor(actionFileBinder, initActions);
                if (configObject.has("funnyAction")) {
                    JsonObject funnyAction = configObject.getAsJsonObject("funnyAction");
                    funnyActionDescription = new FunnyActionDescription(
                        ActionFileBinder.ActionFile.valueOf(funnyAction.get("action").getAsString()).ordinal(),
                        funnyAction.get("score").getAsInt()
                    );
                } else {
                    funnyActionDescription = new FunnyActionDescription(-1, 0);
                }
            }
        }

        if( config == CONFIG_NOMINAL ||
                config == CONFIG_ACTIONNEUR ||
                config == CONFIG_PATHFINDING ||
                config == CONFIG_COUPEOFF) {
            if (table != null) {
                logger.info("Load esialrobotik.ia.pathfinding");
                pathfinding = new PathFinding(new Astar(table));
            }
        }

        if( config == CONFIG_NOMINAL ||
                config == CONFIG_COUPEOFF ||
                config == CONFIG_TEST_LCD) {
            //Only if LCD configuration is found in the configuration file
            if (configRootNode.has("lcd")) {
                configObject = configRootNode.get("lcd").getAsJsonObject();
                if (configObject.get("type").getAsString().equals("segment")) {
                    logger.info("Load LCD Segment");
                    lcdDisplay = new LcdI2cSegment(configObject.get("i2cAddress").getAsInt(), configObject.get("lineCount").getAsInt(), configObject.get("lineLength").getAsInt());
                } else if (configObject.get("type").getAsString().equals("full")) {
                    logger.info("Load LCD Full");
                    lcdDisplay = new LcdI2c(configObject.get("i2cAddress").getAsInt());
                } else {
                    logger.error("Missing LCD type");
                }
            }
        }

        if(config == CONFIG_NOMINAL) {
            JsonObject socketConfig = configRootNode.getAsJsonObject("comSocket");
            communicationManager = new CommunicationManager(pathfinding, actionSupervisor, socketConfig.get("host").getAsString(),socketConfig.get("port").getAsInt());
            actionSupervisor.setCommunicationManager(communicationManager);
        }
    }

    public MovementManager getMovementManager() {
        return movementManager;
    }

    public LidarManager getLidarManager() {
        return lidarManager;
    }

    public UltraSoundManager getUltraSoundManager() {
        return ultraSoundManager;
    }

    public DetectionManager getDetectionManager() {
        return detectionManager;
    }

    public CommunicationManager getCommunicationManager() {
        return communicationManager;
    }

    public ActionCollection getActionCollection() {
        return actionCollection;
    }

    public ActionSupervisor getActionSupervisor() {
        return actionSupervisor;
    }

    public PathFinding getPathfinding() {
        return pathfinding;
    }

    public ColorDetector getColorDetector() {
        return colorDetector;
    }

    public Tirette getTirette() {
        return tirette;
    }

    public Chrono getChrono() {
        return chrono;
    }

    public LCD getLcdDisplay() {
        return lcdDisplay;
    }

    public AsservInterface getAsserv() {
        return asserv;
    }

    public ActionFileBinder getActionFileBinder() {
        return actionFileBinder;
    }

    public FunnyActionDescription getFunnyActionDescription() {
        return funnyActionDescription;
    }
}
