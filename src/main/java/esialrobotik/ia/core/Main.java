package esialrobotik.ia.core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.serial.Baud;
import esialrobotik.ia.actions.ActionCollection;
import esialrobotik.ia.actions.ActionExecutor;
import esialrobotik.ia.actions.a2023.ActionFileBinder;
import esialrobotik.ia.api.Pi4JContext;
import esialrobotik.ia.api.ax12.AX12;
import esialrobotik.ia.api.ax12.AX12Exception;
import esialrobotik.ia.api.ax12.AX12LinkException;
import esialrobotik.ia.api.ax12.AX12LinkSerial;
import esialrobotik.ia.api.ax12.value.AX12Position;
import esialrobotik.ia.api.communication.HotspotSocket;
import esialrobotik.ia.api.communication.SerialDevice;
import esialrobotik.ia.api.communication.Shell;
import esialrobotik.ia.api.custom.LiftProbe2022;
import esialrobotik.ia.api.gpio.ColorDetector;
import esialrobotik.ia.api.gpio.Tirette;
import esialrobotik.ia.api.lcd.LCD;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.api.qik.Qik;
import esialrobotik.ia.asserv.Asserv;
import esialrobotik.ia.asserv.AsservInterface;
import esialrobotik.ia.asserv.Position;
import esialrobotik.ia.manager.ConfigurationManager;
import esialrobotik.ia.manager.DetectionManager;
import esialrobotik.ia.pathfinding.PathFinding;
import esialrobotik.ia.pathfinding.table.Point;
import esialrobotik.ia.utils.ax12.Ax12MainConsole;
import esialrobotik.ia.utils.web.AX12Http;
import esialrobotik.ia.utils.web.ResourcesManager;
import gnu.io.SerialPort;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
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

        //Loading the esialrobotik.ia.core
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
        if(!res) { //We run out of esialrobotik.ia.actions, and not of time, let's wait for the end of the match
            while(true) {
                Thread.sleep(1000);
                if(masterLoop.isMatchFinished()) {
                    break;
                }
            }
        }

        //End of the game. Let's wait a few more seconds (for funny esialrobotik.ia.actions and to be sure) and let's return
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

            Signal.handle(new Signal("INT"), new SignalHandler() {
                public void handle(Signal sig) {
                    System.out.println("Performing ctl-C shutdown");
                    try {
                        Pi4JContext.getInstance().shutdown();
                    } catch (LifecycleException e) {
                        e.printStackTrace();
                    }
                    Thread.dumpStack();
                    System.exit(2);
                }
            });

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
                case "asserv":
                    // Test asserv connexion
                    Main.testAsserv();
                    break;
                case "coupe-off":
                    // Danse de la coupe off
                    Main.coupeOffDance();
                    break;
                case "ax12-cli":
                    Main.configAX12(false);
                    break;
                case "ax12-web":
                    Main.configAX12(true);
                    break;
                case "test-lift":
                    Main.testLift();
                    break;
                case "test-qik":
                    Main.testQik();
                    break;
                case "hotspot":
                    Main.testHotspot();
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
        System.out.println("\t- esialrobotik.ia.detection : Test de la esialrobotik.ia.detection");
        System.out.println("\t- interrupteur : Test interrupteurs");
        System.out.println("\t- lcd : Test de l'écran LCD");
        System.out.println("\t- shell : Test du shell (lance une capture de la caméra et une analyse Aruco)");
        System.out.println("\t- esialrobotik.ia.pathfinding : Test le calcul de esialrobotik.ia.pathfinding");
        System.out.println("\t- coupe-off : Danse de la coupe off");
        System.out.println("\t- ax12-cli : Lance l'utilitaire de config/test des AX12 en ligne de commande");
        System.out.println("\t- ax12-web : Lance l'utilitaire web de config/test des AX12");
        System.out.println("\t- test-lift : Lance une console de test de l'ascenseur et de la sonde des carrés de fouille de la grosse Princesse 2022");
        System.out.println("\t- test-qik : Lance une console de test de la qik");
        System.out.println("\t- hotspot : Test la communication socket via le hotspot");
        System.out.println("\t- esialrobotik.ia.actions : Test des esialrobotik.ia.actions");
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
            Thread.sleep(250);
        }

        //SRF08Config config = new SRF08Config(0x74, 31, 135, "fuu", 0, 0, 0, 350);
        //SRF08 srf = new SRF08(config);
        //while (true) {
        //    Thread.sleep(250);
        //    System.out.println(srf.getMeasure());
        //}
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

    private static void testAsserv() throws IOException, ClassNotFoundException, InvocationTargetException, AX12LinkException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        AsservInterface asserv = new Asserv(
                "/dev/serial/by-id/usb-STMicroelectronics_STM32_STLink_066FFF383133524157185717-if02",
                Baud._115200
        );
        while (true) {
            System.out.println(asserv.getPosition());
            Thread.sleep(200);
        }
    }

    private static void coupeOffDance() throws IOException, AX12LinkException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath , ConfigurationManager.CONFIG_COUPEOFF);

        // Loading the esialrobotik.ia.core
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

    private static void configAX12(boolean modeWeb) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(configFilePath));
            JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);
            JsonObject configObject = configRootNode.get("actions").getAsJsonObject();
            JsonObject configAx12 = configObject.get("ax12").getAsJsonObject();

            SerialPort sp = AX12LinkSerial.getSerialPort(configAx12.get("serie").getAsString());

            if (sp == null) {
                throw new RuntimeException("Aucun port série "+configAx12.get("serie").getAsString()+" trouvé !");
            }

            boolean combineRxTx = configAx12.has("combineRxTx") && configObject.get("combineRxTx").getAsBoolean();
            AX12LinkSerial ax12Link = new AX12LinkSerial(sp, configAx12.get("baud").getAsInt(), combineRxTx);

            if (modeWeb) {
                File dataDir = new File(configObject.get("dataDir").getAsString()).getCanonicalFile();

                File webRootDir = new File("webRootDir");
                webRootDir.mkdir();
                ResourcesManager.mountHtmlDir(webRootDir);
                new AX12Http(webRootDir, dataDir, ax12Link);
            } else {
                (new Ax12MainConsole(ax12Link)).mainLoop();
            }
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

            SerialDevice serialDevice = new SerialDevice(configSerial.get("serie").getAsString(), configSerial.get("baud").getAsInt());
            LiftProbe2022 liftProbe2022 = new LiftProbe2022(serialDevice);

            JsonObject configAx12 = configObject.get("ax12").getAsJsonObject();
            SerialPort spax12 = AX12LinkSerial.getSerialPort(configAx12.get("serie").getAsString());
            AX12 ax = null;
            try {
                ax = new AX12(2, new AX12LinkSerial(spax12, 115200));
                ax.setCwComplianceSlope(150);
                ax.setCwComplianceSlope(150);
                ax.setServoSpeed(128);
            } catch (AX12LinkException | AX12Exception e) {
                throw new RuntimeException(e);
            }

            Scanner in = new Scanner(System.in);
            String cmd;
            boolean pump = false;
            System.out.println("Commandes :\n  exit\n  reset : home de l'ascenseur");
            System.out.println("  a : affiche la hauteur de l'ascenseur\n  s : affiche le carré de fouille sondé");
            System.out.println("  g<mm> : envoie l'ascenseur à la hauteur demandée\n  ax: affiche l'angle de l'ax12");
            System.out.println("  axoff: désactive le couple de l'ax12\n  ax<angle>: règle l'angle de l'ax en degrés");
            System.out.println("  p: Allume ou éteint la pompe");
            System.out.print(">");
            while((cmd = in.nextLine()) != null) {
                try {
                    if (cmd.startsWith("exit")) {
                        break;
                    } else if (cmd.startsWith("reset")) {
                        liftProbe2022.makeLiftHome();
                    } else if (cmd.equals("s")) {
                        System.out.println(liftProbe2022.probeExcavations().name());
                    } else if (cmd.equals("a")) {
                        System.out.println(liftProbe2022.fetchLiftPosition()+"mm");
                    } else if (cmd.startsWith("g") && cmd.length() > 1) {
                        liftProbe2022.setLiftPosition(Integer.parseInt(cmd.substring(1)));
                    } else if (cmd.equals("axoff")) {
                        ax.disableTorque();
                    } else if (cmd.equals("ax")) {
                        System.out.println(ax.readServoPosition().getAngleAsDegrees());
                    } else if (cmd.startsWith("ax")) {
                        ax.setServoPosition(AX12Position.buildFromDegrees((double) Integer.parseInt(cmd.substring(2))));
                    } else if (cmd.equals("p")) {
                        pump = !pump;
                        spax12.setDTR(pump);
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

    private static void testQik() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(configFilePath));
            JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);

            Qik qik = new Qik(configRootNode.get("actions").getAsJsonObject().get("qik").getAsJsonObject());

            Scanner in = new Scanner(System.in);
            String cmd;
            System.out.println("Commandes :\n  exit: quitter\n  f: firmware version");
            System.out.println("  m0<vitesse> : vitesse moteur 0 -127 à +128\n  m1<vitesse> : vitesse moteur 1 -127 à +128");
            System.out.print(">");
            while((cmd = in.nextLine()) != null) {
                try {
                    if (cmd.startsWith("exit")) {
                        break;
                    } else if (cmd.equals("f")) {
                        System.out.println(qik.getFirmwareVersion());
                    } else if (cmd.startsWith("m0")) {
                        qik.setM0Speed(Integer.parseInt(cmd.substring(2)));
                    } else if (cmd.startsWith("m1")) {
                        qik.setM1Speed(Integer.parseInt(cmd.substring(2)));
                    } else if (cmd.equals("e")) {
                        System.out.println(qik.getErrors());
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
        logger.info("Start esialrobotik.ia.actions test");

        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath, ConfigurationManager.CONFIG_ACTIONNEUR);

        while (true) {
            Scanner in = new Scanner(System.in);
            String actionId;
            System.out.println("Id de l'action à exécuter (voir ActionFileBinder)");
            System.out.print(">");
            while((actionId = in.nextLine()) != null) {
                actionId = actionId.trim();
                int actionIdOrdinal = -1;
                try {
                    actionIdOrdinal = Integer.parseInt(actionId);
                } catch (NumberFormatException e) {
                    try {
                        actionIdOrdinal = ActionFileBinder.ActionFile.valueOf(actionId).ordinal();
                    } catch (IllegalArgumentException e2) {
                        // Peut être en upper case ?
                        try {
                            actionIdOrdinal = ActionFileBinder.ActionFile.valueOf(actionId.toUpperCase()).ordinal();
                        } catch (IllegalArgumentException e3) {
                            // tant pis
                        }
                    }
                }
                if (actionIdOrdinal > -1) {
                    configurationManager.getActionSupervisor().executeCommand(actionIdOrdinal);
                    while (!configurationManager.getActionSupervisor().isLastExecutionFinished()) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("Id non trouvé");
                }
                System.out.println("Id de l'action à exécuter (voir ActionFileBinder)");
                System.out.print(">");
            }
        }
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
