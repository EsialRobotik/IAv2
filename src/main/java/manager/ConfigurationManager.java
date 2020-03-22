package manager;

import actions.ActionCollection;
import actions.ActionSupervisor;
import actions.a2019.ActionFileBinder;
import actions.a2019.ax12.AX12LinkException;
import actions.a2019.ax12.AX12LinkSerial;
import api.chrono.Chrono;
import api.gpio.ColorDetector;
import api.gpio.GPioPair;
import api.gpio.Tirette;
import api.lcd.LCD;
import api.lcd.LCD_I2C;
import api.log.LoggerFactory;
import asserv.Asserv;
import asserv.AsservInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import detection.DetectionInterface;
import detection.DetectionInterfaceImpl;
import detection.lidar.LidarInterface;
import detection.lidar.RpLidar;
import detection.ultrasound.SRF08Config;
import gnu.io.SerialPort;
import org.apache.logging.log4j.Logger;
import pathfinding.PathFinding;
import pathfinding.table.Table;
import pathfinding.table.astar.Astar;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the loading of the robot
 * Created by icule on 20/05/17.
 */
public class ConfigurationManager {

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
        Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(path));

        JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);

        JsonObject configObject = configRootNode.get("asserv").getAsJsonObject();
        System.out.println("Config asserv = " + configObject.toString());

        logger.info("AsservAPIConfiguration = " + configObject.toString());
        asserv = new Asserv(
                configObject.get("serie").getAsString(),
                Baud.getInstance(configObject.get("baud").getAsInt())
        );
        movementManager = new MovementManager(asserv);

        configObject = configRootNode.get("detection").getAsJsonObject();
        System.out.println("Config detect = " + configObject.toString());
        //LidarInterface lidarInterface = new RpLidar(configObject.get("lidar").getAsJsonObject().get("port").getAsString());
        //lidarManager = new LidarManager(lidarInterface, movementManager);

        DetectionInterface detectionInterface;

        System.out.println("DetectAPIConfiguration = " + configObject.toString());
        System.out.println("Type = " + configObject.getAsJsonObject("type").getAsString());

        if ("srf04".equals(configObject.getAsJsonObject("type").getAsString())) {
            System.out.println("SRF04");
            List<GPioPair> gPioPairList = new ArrayList<>();
            JsonArray gpioPairArray = configObject.getAsJsonArray("gpioList");
            for (JsonElement e : gpioPairArray) {
                JsonObject temp = e.getAsJsonObject();
                gPioPairList.add(new GPioPair(temp.get("in").getAsInt(), temp.get("out").getAsInt()));
            }
            detectionInterface = new DetectionInterfaceImpl(gPioPairList, configObject.get("type").getAsString());

        } else if("srf08" == configObject.get("type").getAsString()){
            System.out.println("SRF08");
            List<SRF08Config> srf08ConfList = new ArrayList<>();
            JsonArray srf08ConfArray = configObject.getAsJsonArray("i2cConfigList");
            for (JsonElement e : srf08ConfArray) {
                JsonObject temp = e.getAsJsonObject();
                srf08ConfList.add(new SRF08Config(temp.get("address").getAsInt(),temp.get("maxAnalogGain").getAsInt(),temp.get("range").getAsInt(),temp.get("desc").getAsString()));
            }
            detectionInterface = new DetectionInterfaceImpl(srf08ConfList);

        } else {
            //New sensor ?
            System.out.println("OUPS");
            detectionInterface = new DetectionInterfaceImpl(); //shall not happen
        }
        Table table = new Table(configRootNode.get("tablePath").getAsString());
        ultraSoundManager = new UltraSoundManager(detectionInterface, table, movementManager);
        detectionManager = new DetectionManager(detectionInterface, lidarManager, ultraSoundManager);

        configObject = configRootNode.get("action").getAsJsonObject();
        actionCollection = new ActionCollection(configRootNode.get("commandFile").getAsString());
        SerialPort sp = AX12LinkSerial.getSerialPort(configObject.get("serie").getAsString());
        AX12LinkSerial ax12Link = new AX12LinkSerial(sp, configObject.get("baud").getAsInt());
        actionFileBinder = new ActionFileBinder(ax12Link);
        actionSupervisor = new ActionSupervisor(actionFileBinder);

        pathfinding = new PathFinding(new Astar(table));

        colorDetector = new ColorDetector(configRootNode.get("gpioColorSelector").getAsInt());
        tirette = new Tirette(configRootNode.get("gpioTirette").getAsInt());
        chrono = new Chrono(configRootNode.get("matchDuration").getAsInt());
        lcdDisplay = new LCD_I2C();
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
