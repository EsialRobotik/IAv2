package esialrobotik.ia.asserv;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.Serial;
import esialrobotik.ia.api.communication.SerialDevice;
import esialrobotik.ia.api.log.LoggerFactory;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Implémentation de l'asservissement pour raspberry
 */
public class Asserv implements AsservInterface {

    /**
     * Port série pour communiquer avec l'esialrobotik.ia.asserv
     */
    protected SerialDevice serialDevice;

    /**
     * Position du robot
     */
    protected Position position;

    protected MovementDirection direction;

    /**
     * Compteur de status pour éviter de finir les esialrobotik.ia.actions avant de les commencer
     */
    protected int statusCountdown = 0;
    protected final Object lock = new Object();

    /**
     * Status de la dernière commande
     */
    protected AsservStatus asservStatus;

    /**
     * Taille de la file de commande restant dans l'esialrobotik.ia.asserv
     */
    protected int queueSize;

    /**
     * Logger
     */
    protected Logger logger = null;

    /**
     * Config de l'esialrobotik.ia.asserv
     */
    protected JsonObject config;

    /**
     * Constructeur
     * @param serialPort Port série
     * @param baudRate Baud rate
     */
    public Asserv(String serialPort, Baud baudRate) {
        logger = LoggerFactory.getLogger(Asserv.class);

        logger.info("Initialisation de la liason série de l'esialrobotik.ia.asserv, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        serialDevice = new SerialDevice(serialPort, baudRate);
        startSerialReader();
        position = new Position(0, 0);
    }

    public Asserv(JsonObject config) {
        this.logger = LoggerFactory.getLogger(Asserv.class);
        this.config = config;

        String serialPort = config.get("serie").getAsString();
        Baud baudRate = Baud.getInstance(config.get("baud").getAsInt());

        this.logger.info("Initialisation de la liason série de l'esialrobotik.ia.asserv, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        this.serialDevice = new SerialDevice(serialPort, baudRate);
        startSerialReader();
        this.position = new Position(0, 0);
    }

    /*******************************************************************************************************************
     * Commandes basiques
     ******************************************************************************************************************/

    @Override
    public void initialize() {
        logger.info("init");
        serialDevice.write("I");
    }

    @Override
    public void stop() {
        logger.info("stop");
        serialDevice.write("M0");
    }

    @Override
    public void emergencyStop() {
        logger.info("emergencyStop");
        asservStatus = AsservStatus.STATUS_HALTED;
        serialDevice.write("h");
        direction = MovementDirection.NONE;
    }

    @Override
    public void emergencyReset() {
        logger.info("emergencyReset");
        asservStatus = AsservStatus.STATUS_IDLE;
        serialDevice.write("r");
    }

    @Override
    public void go(int dist) {
        logger.info("go : " + dist);
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = dist > 0 ? MovementDirection.FORWARD : MovementDirection.BACKWARD;
        serialDevice.write("v" + dist);
    }

    @Override
    public void turn(int degree) {
        logger.info("turn : " + degree);
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.NONE;
        serialDevice.write("t" + degree);
    }

    /*******************************************************************************************************************
     * Commandes GOTO
     ******************************************************************************************************************/

    @Override
    public void goTo(Position position) {
        logger.info("goTo : " + position.toString());
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.FORWARD;
        serialDevice.write("g" + position.getX() + "#" + position.getY());
    }

    @Override
    public void goToChain(Position position) {
        logger.info("goToChain : " + position.toString());
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.FORWARD;
        serialDevice.write("e" + position.getX() + "#" + position.getY());
    }

    @Override
    public void goToReverse(Position position) {
        logger.info("goToReverse : " + position.toString());
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.BACKWARD;
        serialDevice.write("b" + position.getX() + "#" + position.getY());
    }

    @Override
    public void face(Position position) {
        logger.info("goToFace : " + position.toString());
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.NONE;
        serialDevice.write("f" + position.getX() + "#" + position.getY());
    }

    /*******************************************************************************************************************
     * Commandes controle odométrie
     ******************************************************************************************************************/

    @Override
    public void setOdometrie(int x, int y, double theta) {
        logger.info("setOdometrie");
        serialDevice.write("P" + x + "#" + y + "#" + theta);
    }

    /*******************************************************************************************************************
     * Commandes controle régulateurs
     ******************************************************************************************************************/

    @Override
    public void enableLowSpeed(boolean enable) {
        logger.info("enableLowSpeed : " + enable);
        serialDevice.write(enable ? "S25" : "S100");
    }

    @Override
    public void setSpeed(int pct) {
        logger.info("setSpeed " + pct + "%");
        serialDevice.write("S" + pct);
    }

    @Override
    public void enableRegulatorAngle(boolean enable) {
        logger.info("enableRegulatorAngle : " + enable);
        serialDevice.write(enable ? "A1" : "A0");
    }

    @Override
    public void enableRegulatorDistance(boolean enable) {
        logger.info("enableRegulatorDistance : " + enable);
        serialDevice.write(enable ? "D1" : "D0");
    }

    @Override
    public void enableMotors(boolean enable) {
        logger.info("enable motors " + enable);
        serialDevice.write("M" + (enable ? 1 : 0));
    }

    private void startSerialReader() {
        Thread serialReaderThread = new Thread(() -> {
            // We use a buffered reader to handle the data received from the serial port
            BufferedReader br = new BufferedReader(new InputStreamReader(this.serialDevice.getInputStream()));
            Serial serial = this.serialDevice.getSerial();

            try {
                // Data from the esialrobotik.ia.asserv is recieved in lines
                String line = "";

                // Read data
                while (true) {
                    // First we need to check if there is data available to read.
                    // The read() command for pigpio-serial is a NON-BLOCKING call,
                    // in contrast to typical java input streams.
                    int available = serial.available();
                    if (available > 0) {
                        for (int i = 0; i < available; i++) {
                            byte b = (byte) br.read();
                            if (b < 32) {
                                // All non-string bytes are handled as line breaks
                                if (!line.isEmpty()) {
                                    // Here we should add code to parse the data to a GPS data object
                                    this.logger.trace("Position : " + line);
                                    parseAsservPosition(line);
                                    line = "";
                                }
                            } else {
                                line += (char) b;
                            }
                        }
                    } else {
                        Thread.sleep(10);
                    }
                }
            } catch (Exception e) {
                this.logger.error("Error reading data from serial: " + e.getMessage());
            }
        }, "SerialReader");
        serialReaderThread.setDaemon(true);
        serialReaderThread.start();
    }

    /**
     * Parse le retour de la boucle d'esialrobotik.ia.asserv contenant la position du robot
     * #<positionX>;<positionY>;<angle>;<commandStatus>;<cmdQueueSize>;<vitesseG>;<vitesseD>
     * @param str Position du robot renvoyée par l'esialrobotik.ia.asserv
     */
    private void parseAsservPosition(String str) {
        try {
            str = str.trim(); // Un petit trim pour virer la merde
            if (str.startsWith("#")) {
                str = str.substring(1);
                if (str.contains("#")) { // Si par hasard on reçoit deux lignes à la fois, on abandonne
                    return;
                }
                String[] data = str.split(";");

                position.setX(Integer.parseInt(data[0]));
                position.setY(Integer.parseInt(data[1]));
                position.setTheta(Double.parseDouble(data[2]));
                int asservStatusInt = Integer.parseInt(data[3]);
                switch (asservStatusInt) {
                    case 0:
                        synchronized (lock) {
                            statusCountdown--;
                            if (statusCountdown <= 0) {
                                asservStatus = AsservStatus.STATUS_IDLE;
                            }
                        }
                        break;
                    case 1:
                        asservStatus = AsservStatus.STATUS_RUNNING;
                        break;
                    case 2:
                        asservStatus = AsservStatus.STATUS_HALTED;
                        break;
                    case 3:
                        asservStatus = AsservStatus.STATUS_BLOCKED;
                        break;
                }
                queueSize = Integer.parseInt(data[4]);
            }
        } catch (Exception e) {
            logger.info("Trace esialrobotik.ia.asserv non parsable : " + str);
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public MovementDirection getMovementDirection() {
        return direction;
    }

    @Override
    public AsservStatus getAsservStatus() {
        return asservStatus;
    }

    @Override
    public int getQueueSize() {
        return queueSize;
    }

    public void waitForAsserv() {
        while (!(this.getQueueSize() == 0 && this.getAsservStatus() == AsservInterface.AsservStatus.STATUS_IDLE)) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void goStart(boolean isColor0) throws Exception {
        JsonArray start = this.config.getAsJsonArray(isColor0 ? "start0" : "start3000");
        enableLowSpeed(true);
        Thread.sleep(150);
        for (JsonElement instruction : start) {
            JsonObject temp = instruction.getAsJsonObject();
            logger.debug(temp.toString());
            Position depart;
            switch (temp.get("type").getAsString()) {
                case "go":
                    go(temp.get("dist").getAsInt());
                    this.logger.info("Go " + temp.get("dist").getAsInt());
                    break;
                case "go_timed":
                    this.logger.info("Go timed " + temp.get("dist").getAsInt());
                    go(temp.get("dist").getAsInt());
                    Thread.sleep(2000);
                    emergencyStop();
                    Thread.sleep(150);
                    emergencyReset();
                    this.logger.info("Go timed end " + temp.get("dist").getAsInt());
                    break;
                case "turn":
                    turn(temp.get("dist").getAsInt());
                    this.logger.info("Turn " + temp.get("dist").getAsInt());
                    break;
                case "goto":
                    depart = new Position(temp.get("x").getAsInt(), temp.get("y").getAsInt());
                    this.logger.info("Goto " + depart.toString());
                    goTo(depart);
                    break;
                case "goto_back":
                    depart = new Position(temp.get("x").getAsInt(), temp.get("y").getAsInt());
                    this.logger.info("Goto " + depart.toString());
                    goToReverse(depart);
                    break;
                case "face":
                    Position alignement = new Position(temp.get("x").getAsInt(), temp.get("y").getAsInt());
                    this.logger.info("Goto " + alignement.toString());
                    face(alignement);
                    break;
                case "angle":
                    this.logger.info("Enable regulator angle " + temp.get("enable").getAsBoolean());
                    //enableRegulatorAngle(temp.get("enable").getAsBoolean());
                    // todo remettre quand ça fonctionnera côté asserv
                    break;
                case "distance":
                    this.logger.info("Enable regulator distance " + temp.get("enable").getAsBoolean());
                    enableRegulatorDistance(temp.get("enable").getAsBoolean());
                    break;
                case "set_x":
                    this.logger.info("Set odometrie X : " + temp.get("value").getAsInt());
                    setOdometrie(temp.get("value").getAsInt(), position.getY(), temp.get("theta").getAsDouble());
                    break;
                case "set_y":
                    this.logger.info("Set odometrie Y : " + temp.get("value").getAsInt());
                    setOdometrie(position.getX(), temp.get("value").getAsInt(), temp.get("theta").getAsDouble());
                    break;
                case "speed":
                    this.logger.info("Set speed " + temp.get("value").getAsInt());
                    setSpeed(temp.get("value").getAsInt());
                    break;
                default:
                    throw new Exception("Instruction inconnue " + temp);
            }
            waitForAsserv();
        }
        enableLowSpeed(false);
        Thread.sleep(150);
        this.logger.info("goStart finished");
    }
}
