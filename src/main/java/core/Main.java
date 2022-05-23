package core;

import actions.ActionCollection;
import actions.ActionExecutor;
import actions.a2022.ActionFileBinder;
import api.ax12.AX12LinkException;
import api.ax12.AX12LinkSerial;
import api.camera.Camera;
import api.communication.HotspotSocket;
import api.communication.Serial;
import api.communication.Shell;
import api.custom.LiftProbe2022;
import api.gpio.ColorDetector;
import api.gpio.Tirette;
import api.lcd.LCD;
import api.log.LoggerFactory;
import asserv.AsservInterface;
import asserv.Position;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gnu.io.SerialPort;
import manager.ConfigurationManager;
import manager.DetectionManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.opencv.aruco.Aruco;
import org.opencv.aruco.Dictionary;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import pathfinding.PathFinding;
import pathfinding.table.Point;
import utils.web.AX12Http;
import utils.web.ResourcesManager;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The goal of this class if to bootstrap the code, init the robot and launch the match
 * Created by icule on 21/05/17.
 */
public class Main {

    public static String configFilePath = "config.json";

    public static Logger logger = null;

    public Main(boolean stepByStep) throws IOException, InterruptedException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath);

        ActionCollection actionCollection = configurationManager.getActionCollection();
        actionCollection.setStepByStepMode(stepByStep);
        if (stepByStep) {
            Scanner scanner = new Scanner(System.in);
            actionCollection.setScanner(scanner);
        }

        //Loading the core
        MasterLoop masterLoop = new MasterLoop(
            configurationManager.getMovementManager(),
            configurationManager.getDetectionManager(),
            configurationManager.getCommunicationManager(),
            actionCollection,
            configurationManager.getActionSupervisor(),
            configurationManager.getPathfinding(),
            configurationManager.getColorDetector(),
            configurationManager.getChrono(),
            configurationManager.getTirette(),
            configurationManager.getLcdDisplay(),
            configurationManager.getFunnyActionDescription()
        );

        //Init
        masterLoop.init();

        //Launch the main loop
        boolean res = masterLoop.mainLoop();
        if(!res) { //We run out of actions, and not of time, let's wait for the end of the match
            while(true) {
                Thread.sleep(1000);
                if(masterLoop.isMatchFinished()) {
                    break;
                }
            }
        }

        //End of the game. Let's wait a few more seconds (for funny actions and to be sure) and let's return
        Thread.sleep(9000);
        LoggerFactory.shutdown();
    }

    public static void main(String[] args) throws Exception {
        if (args.length >= 2) {

            if (args.length == 3 ) {
                configFilePath = args[2];
            }

            File tmpFile = new File(configFilePath);
            if(!tmpFile.exists()){
                System.out.println("The configuration file does not exist : " + configFilePath);
                return;
            }

            switch (args[0]) {
                default:
                case "help":
                    printUsage();
                    return;
                case "TRACE":
                    LoggerFactory.init(Level.TRACE);
                    break;
                case "INFO":
                    LoggerFactory.init(Level.INFO);
                    break;
                case "DEBUG":
                    LoggerFactory.init(Level.DEBUG);
                    break;
                case "ERROR":
                    LoggerFactory.init(Level.ERROR);
                    break;
            }
            logger = LoggerFactory.getLogger(Main.class);
            logger.info("init logger");

            switch (args[1]) {
                default:
                case "help":
                    printUsage();
                    return;
                case "main":
                    // Exécution normal de l'IA
                    new Main(false);
                    break;
                case "step":
                    // Exécution normal de l'IA en step by step
                    new Main(true);
                    break;
                case "detection":
                    // Test de la détection
                    Main.testDetection();
                    break;
                case "interrupteur":
                    // Test interrupteurs
                    Main.testInterrupteurs();
                    break;
                case "lcd":
                    // Test du LCD
                    Main.testLcd();
                    break;
                case "shell":
                    // Test shell
                    Main.testShell();
                    break;
                case "pathfinding":
                    Main.testPathfinding();
                    break;
                case "coupe-off":
                    // Danse de la coupe off
                    Main.coupeOffDance();
                    break;
                case "config-ax12":
                    Main.configAX12();
                    break;
                case "test-lift":
                    Main.testLift();
                    break;
                case "hotspot":
                    Main.testHotspot();
                    break;
                case "camera":
                    Main.camera();
                    break;
                case "actions":
                    Main.testActions();
                    break;
                case "funny-action":
                    Main.funnyAction();
                    break;
            }

        } else {
            System.out.println("L'IA attends 2 arguments pour demarrer");
            printUsage();
            return;
        }
    }

    private static void printUsage() {

        System.out.println("\nUTILISATION:");
        System.out.println("java -jar esialrobotik.ia-all-2.0 TRACE_LEVEL EXECUTION_TYPE [configFile]\n");
        System.out.println("TRACELEVEL : ");
        System.out.println("\t- TRACE : Active les logs en mode TRACE (defaut)");
        System.out.println("\t- INFO : Active les logs en mode INFO");
        System.out.println("\t- DEBUG : Active les logs en mode DEBUG");
        System.out.println("\t- ERROR : Active les logs en mode ERROR\n");

        System.out.println("EXECUTION_TYPE : ");
        System.out.println("\t- main : Execution normal de l'IA");
        System.out.println("\t- step : Execution normal de l'IA en StepByStep");
        System.out.println("\t- detection : Test de la detection");
        System.out.println("\t- interrupteur : Test interrupteurs");
        System.out.println("\t- lcd : Test de l'écran LCD");
        System.out.println("\t- shell : Test du shell (lance une capture de la caméra et une analyse Aruco)");
        System.out.println("\t- pathfinding : Test le calcul de pathfinding");
        System.out.println("\t- coupe-off : Danse de la coupe off");
        System.out.println("\t- config-ax12 : Lance l'utilitaire de configuration des AX12");
        System.out.println("\t- test-lift : Lance une console de test de l'ascenseur et de lasondes carrés de fouille de la grosse Princesse 2022");
        System.out.println("\t- hotspot : Test la communication socket via le hotspot");
        System.out.println("\t- camera : Test la camera");
        System.out.println("\t- actions : Test des actions");
        System.out.println("\t- funny-action : Test de la funny action en utilisant l'interrupteur de couleur comme déclencheur\n");

        System.out.println("configFile : chemin du fichier de configuration à utiliser. Par defaut, './config.json'\n");
    }

    private static void testDetection() throws IOException, InterruptedException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath, ConfigurationManager.CONFIG_TEST_DETECTION);

        DetectionManager detectionManager = configurationManager.getDetectionManager();
        detectionManager.initAPI();
        detectionManager.startDetectionDebug();
        while (true){
            Thread.sleep(1000);
        }
    }

    private static void testInterrupteurs() throws IOException, InterruptedException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath, ConfigurationManager.CONFIG_TEST_INTERRUPTEURS);

        ColorDetector colorDetector = configurationManager.getColorDetector();
        Tirette tirette = configurationManager.getTirette();
        while (true){
            Thread.sleep(500);
            System.out.println("Couleur0 ? " + colorDetector.isColor0());
            System.out.println("Tirette présente ? " + tirette.getTiretteState());
        }
    }

    private static void testLcd() throws IOException, InterruptedException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath, ConfigurationManager.CONFIG_TEST_LCD);

        LCD lcd = configurationManager.getLcdDisplay();
        int i = 0;
        while (true) {
            lcd.println("Coucou " + i);
            i++;
            Thread.sleep(500);
        }
    }

    private static void testShell() throws IOException, InterruptedException {
        Shell shell = new Shell("python /home/pi/2020Aruco/testPiCameraArucoShell.py --quiet");
        shell.start();
        System.out.println("Start");
        Thread.sleep(2000);
        long time = System.currentTimeMillis();
        System.out.println("Ask");
        shell.send("a");
        System.out.println("Read");
        System.out.println(shell.read());
        System.out.println("Resultat en " + (System.currentTimeMillis() - time) + "ms");
    }

    private static void testPathfinding() throws InterruptedException, IOException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println("testPathfinding");
        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath, ConfigurationManager.CONFIG_PATHFINDING);

        System.out.println("Path 1");
        PathFinding pathFinding = configurationManager.getPathfinding();
        pathFinding.computePath(
                new Point(800, 200),
                new Point(540, 700)
        );
        while (!pathFinding.isComputationEnded()) {
            Thread.sleep(500);
        }
        System.out.println("Path");
        System.out.print("[");
        for (Point p : pathFinding.getLastComputedPath()) {
            System.out.print("["+p.x+","+p.y+"],");
        }
        System.out.println("]");

        System.out.println("Path 2");
        pathFinding.computePath(
                new Point(540, 700),
                new Point(250, 450)
        );
        while (!pathFinding.isComputationEnded()) {
            Thread.sleep(500);
        }
        System.out.println("Path");
        System.out.print("[");
        for (Point p : pathFinding.getLastComputedPath()) {
            System.out.print("["+p.x+","+p.y+"],");
        }
        System.out.println("]");

        System.out.println("Free zone");
        pathFinding.liberateElementById("0_bouee3");

        System.out.println("Path 3");
        pathFinding.computePath(
                new Point(250, 450),
                new Point(800, 500)
        );
        while (!pathFinding.isComputationEnded()) {
            Thread.sleep(500);
        }
        System.out.println("Path");
        System.out.print("[");
        for (Point p : pathFinding.getLastComputedPath()) {
            System.out.print("["+p.x+","+p.y+"],");
        }
        System.out.println("]");
    }

    private static void coupeOffDance() throws IOException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath , ConfigurationManager.CONFIG_COUPEOFF);

        // Loading the core
        AsservInterface asserv = configurationManager.getAsserv();
        ActionFileBinder actions = configurationManager.getActionFileBinder();
        Tirette tirette = configurationManager.getTirette();
        Logger logger = LoggerFactory.getLogger(Main.class);

        tirette.waitForTirette(true);
        tirette.waitForTirette(false);

        //On prépare du random
        Random random = new Random();
        ArrayList<String> randomDance = new ArrayList<>();
        randomDance.add("goto");
        randomDance.add("goto");
        randomDance.add("goto");
        randomDance.add("turn");
        randomDance.add("turn");
        randomDance.add("turn");
        randomDance.add("escalier1");
        randomDance.add("escalier2");


        for (int i = 0; i < 100; i++){
            String action = randomDance.get(random.nextInt(randomDance.size()));

            switch (action) {
                case "goto":
                    int x = random.nextInt(5)*100;
                    int y = random.nextInt(5)*100;
                    asserv.goTo(new Position(x,y));
                    Main.waitForAsserv(asserv);
                    break;
                case "turn":
                    int angle = random.nextInt(360);
                    if (random.nextInt(2) == 1) {
                        angle *= -1;
                    }
                    asserv.turn(angle);
                    Main.waitForAsserv(asserv);
                    break;
                case "goAndBack":
                    int go = random.nextInt(10)*10;
                    asserv.go(go);
                    Main.waitForAsserv(asserv);
                    asserv.go(-go);
                    Main.waitForAsserv(asserv);
                    break;
                case "interrupteur":
                    actions.getActionExecutor(13).execute();
                    break;
                case "brasDroit":
                    actions.getActionExecutor(1).execute();
                    actions.getActionExecutor(0).execute();
                    break;
                case "brasGauche":
                    actions.getActionExecutor(3).execute();
                    actions.getActionExecutor(2).execute();
                    break;
                case "largageDroit":
                    actions.getActionExecutor(5).execute();
                    actions.getActionExecutor(7).execute();
                    break;
                case "largageGauche":
                    actions.getActionExecutor(6).execute();
                    actions.getActionExecutor(7).execute();
                    break;
                case "vol":
                    actions.getActionExecutor(1).execute();
                    actions.getActionExecutor(3).execute();
                    actions.getActionExecutor(0).execute();
                    actions.getActionExecutor(2).execute();
                    break;
                case "escalier1":
                    actions.getActionExecutor(11).execute();
                    break;
                case "escalier2":
                    actions.getActionExecutor(18).execute();
                    break;
            }
        }
    }

    private static void configAX12() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(configFilePath));
            JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);
            JsonObject configObject = configRootNode.get("actions").getAsJsonObject();
            JsonObject configAx12 = configObject.get("ax12").getAsJsonObject();

            SerialPort sp = AX12LinkSerial.getSerialPort(configAx12.get("serie").getAsString());

            if (sp == null) {
                throw new RuntimeException("Aucun port série "+" trouvé !");
            }

            boolean combineRxTx = configAx12.has("combineRxTx") && configObject.get("combineRxTx").getAsBoolean();
            AX12LinkSerial ax12Link = new AX12LinkSerial(sp, configAx12.get("baud").getAsInt(), combineRxTx);
            File dataDir = new File(configObject.get("dataDir").getAsString()).getCanonicalFile();

            File webRootDir = new File("webRootDir");
            webRootDir.mkdir();
            ResourcesManager.mountHtmlDir(webRootDir);
            new AX12Http(webRootDir, dataDir, ax12Link);
        } catch (AX12LinkException |IOException e) {
            e.printStackTrace();
        }
    }

    private static void testLift() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(configFilePath));
            JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);
            JsonObject configObject = configRootNode.get("actions").getAsJsonObject();
            JsonObject configSerial = configObject.get("serial").getAsJsonObject();

            Serial serial = new Serial(configSerial.get("serie").getAsString(), configSerial.get("baud").getAsInt());
            LiftProbe2022 liftProbe2022 = new LiftProbe2022(serial);

            Scanner in = new Scanner(System.in);
            String cmd;
            System.out.println("Commandes :\n  exit\n  reset : home de l'ascenseur");
            System.out.println("  a : affiche la hauteur de l'ascenseur\n  p : affiche le carré de fouille sondé");
            System.out.println("  g<mm> : envoie l'ascenseur à la hauteur demandée");
            System.out.print(">");
            while((cmd = in.nextLine()) != null) {
                try {
                    if (cmd.startsWith("exit")) {
                        break;
                    } else if (cmd.startsWith("reset")) {
                        liftProbe2022.makeLiftHome();
                    } else if (cmd.equals("p")) {
                        System.out.println(liftProbe2022.probeExcavations().name());
                    } else if (cmd.equals("a")) {
                        System.out.println(liftProbe2022.fetchLiftPosition()+"mm");
                    } else if (cmd.startsWith("g") && cmd.length() > 1) {
                        liftProbe2022.setLiftPosition(Integer.parseInt(cmd.substring(1)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print(">");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testActions() throws IOException, ClassNotFoundException, InvocationTargetException, AX12LinkException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        logger.info("Start actions test");

        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath, ConfigurationManager.CONFIG_ACTIONNEUR);

        while (true) {
            Scanner in = new Scanner(System.in);
            String actionId;
            System.out.println("Id de l'action à exécuter (voir ActionFileBinder)");
            System.out.print(">");
            while((actionId = in.nextLine()) != null) {
                configurationManager.getActionSupervisor().executeCommand(Integer.parseInt(actionId));
                while (!configurationManager.getActionSupervisor().isLastExecutionFinished()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Id de l'action à exécuter (voir ActionFileBinder)");
                System.out.print(">");
            }
        }
    }

    private static void camera() throws IOException {
        logger.info("Start camera test");
        Camera camera = new Camera();
        logger.info("Camera initialised");
        File picture = camera.takePicture("test-image.jpg");
        logger.info("Picture took");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat inputImage = Imgcodecs.imread(picture.getName());
        logger.info("input image converted as matrix");
        List<Mat> corners = new ArrayList<>();
        Mat markerIds = new Mat();
        Dictionary dictionary = Aruco.getPredefinedDictionary(Aruco.DICT_4X4_100);
        // DetectorParameters parameters = DetectorParameters.create();
        logger.info("Start markers detection");
        Aruco.detectMarkers(inputImage, dictionary, corners, markerIds);
        logger.info("Detection complete");
        System.out.println(markerIds);
    }

    public static void funnyAction() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        try {
            configurationManager.loadConfiguration(configFilePath);
            ColorDetector colorDetector = configurationManager.getColorDetector();
            ActionExecutor ae = configurationManager.getActionSupervisor().getActionExecutor(configurationManager.getFunnyActionDescription().actionId);
            boolean triggered = false;
            while (true) {
                Thread.sleep(50);
                if (colorDetector.isColor0() && !triggered) {
                    ae.resetActionState();
                    ae.execute();
                    System.out.println("Trigger");
                    triggered = true;
                }
                if (!colorDetector.isColor0()) {
                    triggered = false;
                }
            }
        } catch (IOException | AX12LinkException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testHotspot() throws URISyntaxException, InterruptedException {
        HotspotSocket socket = new HotspotSocket("192.168.0.102", 4269, "robot");
        int i = 0;
        while (true) {
            socket.send("Coucou from socket : " + i);
            Thread.sleep(500);
            i++;
        }
    }

    private static void waitForAsserv(AsservInterface asservInterface) {
        while (asservInterface.getQueueSize() == 0 && asservInterface.getAsservStatus() == AsservInterface.AsservStatus.STATUS_IDLE) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Asserv OK");
    }
}
