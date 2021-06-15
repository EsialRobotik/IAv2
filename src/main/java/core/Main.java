package core;

import actions.ActionCollection;
import actions.a2020.ActionFileBinder;
import api.ax12.AX12LinkException;
import api.ax12.AX12LinkSerial;
import api.communication.Shell;
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
import pathfinding.PathFinding;
import pathfinding.table.Point;
import utils.web.AX12Http;
import utils.web.ResourcesManager;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The goal of this class if to bootstrap the code, init the robot and launch the match
 * Created by icule on 21/05/17.
 */
public class Main {

    public static String configFilePath = "config.json";

    public Main(boolean stepByStep) throws IOException, InterruptedException, AX12LinkException {
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
            actionCollection,
            configurationManager.getActionSupervisor(),
            configurationManager.getPathfinding(),
            configurationManager.getColorDetector(),
            configurationManager.getChrono(),
            configurationManager.getTirette(),
            configurationManager.getLcdDisplay()
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
            Logger logger = LoggerFactory.getLogger(PathFinding.class);
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
                case "actionneur":
                    // Test actionneurs
                    Main.testActionneurs();
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
        System.out.println("\t- actionneur : Test de l'init des actionneurs");
        System.out.println("\t- pathfinding : Test le calcul de pathfinding\n");
        System.out.println("\t- coupe-off : Danse de la coupe off\n");
        System.out.println("\t- config-ax12 : Lance l'utilitaire de configuration des AX12\n");

        System.out.println("configFile : chemin du fichier de configuration à utiliser. Par defaut, './config.json'\n");
    }

    private static void testDetection() throws IOException, InterruptedException, AX12LinkException {
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

    private static void testInterrupteurs() throws IOException, InterruptedException, AX12LinkException {
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

    private static void testLcd() throws IOException, InterruptedException, AX12LinkException {
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

    private static void testPathfinding() throws InterruptedException, IOException, AX12LinkException {
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

    private static void testActionneurs() throws IOException, AX12LinkException {

    }

    private static void coupeOffDance() throws IOException, AX12LinkException {
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

            if(configObject.has("serie")) {
                SerialPort sp = AX12LinkSerial.getSerialPort(configObject.get("serie").getAsString());

                if (sp == null) {
                    throw new RuntimeException("Aucun port série "+" trouvé !");
                }

                AX12LinkSerial ax12Link = new AX12LinkSerial(sp, configObject.get("baud").getAsInt());
                File dataDir = new File(configObject.get("dataDir").getAsString()).getCanonicalFile();

                File webRootDir = new File("webRootDir");
                webRootDir.mkdir();
                ResourcesManager.mountHtmlDir(webRootDir);
                new AX12Http(webRootDir, dataDir, ax12Link);
            }
        } catch (AX12LinkException |IOException e) {
            e.printStackTrace();
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
