package manager;

import actions.ActionCollection;
import actions.ActionSupervisor;
import actions.a2020.ActionFileBinder;
import api.ax12.AX12LinkException;
import api.ax12.AX12LinkSerial;
import api.chrono.Chrono;
import api.gpio.ColorDetector;
import api.gpio.Tirette;
import api.lcd.LCD;
import api.lcd.LcdI2cSegment;
import api.lcd.seed.LcdI2c;
import api.log.LoggerFactory;
import asserv.Asserv;
import asserv.AsservInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import detection.DetectionInterface;
import detection.DetectionInterfaceImpl;
import detection.lidar.LidarInterface;
import detection.lidar.RpLidar;
import gnu.io.SerialPort;
import org.apache.logging.log4j.Logger;
import pathfinding.PathFinding;
import pathfinding.table.Table;
import pathfinding.table.astar.Astar;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    private ActionCollection actionCollection;
    private ActionSupervisor actionSupervisor;
    private PathFinding pathfinding;
    private ColorDetector colorDetector;
    private Tirette tirette;
    private Chrono chrono;
    private LCD lcdDisplay;
    private AsservInterface asserv;
    private ActionFileBinder actionFileBinder;

    public void loadConfiguration(String path) throws IOException, AX12LinkException {
        this.loadConfiguration(path,CONFIG_NOMINAL);
    }

    public void loadConfiguration(String path, int config) throws IOException, AX12LinkException {
        Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

        Gson gson = new Gson();
        Table table = null;
        Reader reader = Files.newBufferedReader(Paths.get(path));

        JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);

        JsonObject configObject = configRootNode.get("asserv").getAsJsonObject();
        if (config != CONFIG_PATHFINDING) {
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
            int windowSize = configObject.get("windowSize").getAsInt();
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

            if(configObject.has("serie")) {
                SerialPort sp = AX12LinkSerial.getSerialPort(configObject.get("serie").getAsString());
                AX12LinkSerial ax12Link = new AX12LinkSerial(sp, configObject.get("baud").getAsInt());
                String dataDir = configObject.get("dataDir").getAsString();
                actionFileBinder = new ActionFileBinder(ax12Link, dataDir, actionCollection);
                actionSupervisor = new ActionSupervisor(actionFileBinder);
            }
        }

        if( config == CONFIG_NOMINAL ||
                config == CONFIG_ACTIONNEUR ||
                config == CONFIG_PATHFINDING ||
                config == CONFIG_COUPEOFF) {
            if (table != null) {
                logger.info("Load pathfinding");
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
}
