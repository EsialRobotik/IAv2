package core;

import actions.a2019.ActionFileBinder;
import actions.a2019.ax12.AX12LinkException;
import api.gpio.ColorDetector;
import api.gpio.Tirette;
import api.lcd.LCD;
import api.log.LoggerFactory;
import asserv.AsservInterface;
import asserv.Position;
import manager.ConfigurationManager;
import manager.DetectionManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The goal of this class if to bootstrap the code, init the robot and launch the match
 * Created by icule on 21/05/17.
 */
public class Main {

    private static String configFilePath;

    public Main() throws IOException, InterruptedException, AX12LinkException {
        //Load of the configuration first
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadConfiguration(configFilePath);

        //Loading the core
        MasterLoop masterLoop = new MasterLoop(
            configurationManager.getMovementManager(),
            configurationManager.getDetectionManager(),
            configurationManager.getActionCollection(),
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
        if(!res) { //We run out of action, and not of time, let's wait for the end of the match
            while(true) {
                Thread.sleep(1000);
                if(masterLoop.isMatchFinished()) {
                    break;
                }
            }
        }

        //End of the game. Let's wait a few more seconds (for funny action and to be sure) and let's return
        Thread.sleep(9000);
        LoggerFactory.shutdown();
    }


    public static void main(String[] args) throws Exception {
        if (args.length >= 2) {

            if (args.length == 3 ) {
                configFilePath = args[2];
            } else {
                configFilePath = "config.json";
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

            switch (args[1]) {
                default:
                case "help":
                    printUsage();
                    return;
                case "main":
                    // Exécution normal de l'IA
                    new Main();
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
                case "coupe-off":
                    // Danse de la coupe off
                    Main.coupeOffDance();
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
        System.out.println("\t- detection : Test de la detection");
        System.out.println("\t- interrupteur : Test interrupteurs");
        System.out.println("\t- lcd : Test de l'écran LCD");
        System.out.println("\t- coupe-off : Danse de la coupe off\n");

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
        configurationManager.loadConfiguration(configFilePath, ConfigurationManager.CONFIG_TEST_DETECTION); // TODO changer ça

        LCD lcd = configurationManager.getLcdDisplay();
        int i = 0;
        while (true) {
            lcd.println("Coucou " + i);
            i++;
            Thread.sleep(500);
        }
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
