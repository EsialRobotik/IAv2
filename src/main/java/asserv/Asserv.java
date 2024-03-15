package asserv;

import api.communication.Serial;
import api.log.LoggerFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.SerialDataEventListener;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Implémentation de l'asservissement pour raspberry
 */
public class Asserv implements AsservInterface {

    /**
     * Port série pour communiquer avec l'asserv
     */
    protected Serial serial;

    /**
     * Position du robot
     */
    protected Position position;

    protected MovementDirection direction;

    /**
     * Compteur de status pour éviter de finir les actions avant de les commencer
     */
    protected int statusCountdown = 0;
    protected final Object lock = new Object();

    /**
     * Status de la dernière commande
     */
    protected AsservStatus asservStatus;

    /**
     * Taille de la file de commande restant dans l'asserv
     */
    protected int queueSize;

    /**
     * Logger
     */
    protected Logger logger = null;

    /**
     * Config de l'asserv
     */
    protected JsonObject config;

    /**
     * Constructeur
     * @param serialPort Port série
     * @param baudRate Baud rate
     */
    public Asserv(String serialPort, Baud baudRate) {
        logger = LoggerFactory.getLogger(Asserv.class);

        logger.info("Initialisation de la liason série de l'asserv, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        serial = new Serial(serialPort, baudRate);
        serial.addReaderListeners((SerialDataEventListener) serialDataEvent -> {
            try {
                String serialBuffer = serialDataEvent.getAsciiString();
                logger.trace("Position : " + serialBuffer);
                parseAsservPosition(serialBuffer);
            } catch (IOException e) {
                logger.error("Echec du parsing de la position : " + e.getMessage());
            }
        });

        position = new Position(0, 0);
    }

    public Asserv(JsonObject config) {
        this.logger = LoggerFactory.getLogger(Asserv.class);
        this.config = config;

        String serialPort = config.get("serie").getAsString();
        Baud baudRate = Baud.getInstance(config.get("baud").getAsInt());

        this.logger.info("Initialisation de la liason série de l'asserv, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        this.serial = new Serial(serialPort, baudRate);
        this.serial.addReaderListeners((SerialDataEventListener) serialDataEvent -> {
            try {
                String serialBuffer = serialDataEvent.getAsciiString();
                this.logger.trace("Position : " + serialBuffer);
                parseAsservPosition(serialBuffer);
            } catch (IOException e) {
                this.logger.error("Echec du parsing de la position : " + e.getMessage());
            }
        });

        this.position = new Position(0, 0);
    }

    /*******************************************************************************************************************
     * Commandes basiques
     ******************************************************************************************************************/

    @Override
    public void initialize() {
        logger.info("init");
        serial.write("I");
    }

    @Override
    public void stop() {
        logger.info("stop");
        serial.write("M0");
    }

    @Override
    public void emergencyStop() {
        logger.info("emergencyStop");
        asservStatus = AsservStatus.STATUS_HALTED;
        serial.write("h");
        direction = MovementDirection.NONE;
    }

    @Override
    public void emergencyReset() {
        logger.info("emergencyReset");
        asservStatus = AsservStatus.STATUS_IDLE;
        serial.write("r");
    }

    @Override
    public void go(int dist) {
        logger.info("go : " + dist);
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = dist > 0 ? MovementDirection.FORWARD : MovementDirection.BACKWARD;
        serial.write("v" + dist);
    }

    @Override
    public void turn(int degree) {
        logger.info("turn : " + degree);
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.NONE;
        serial.write("t" + degree);
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
        serial.write("g" + position.getX() + "#" + position.getY());
    }

    @Override
    public void goToChain(Position position) {
        logger.info("goToChain : " + position.toString());
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.FORWARD;
        serial.write("e" + position.getX() + "#" + position.getY());
    }

    @Override
    public void goToReverse(Position position) {
        logger.info("goToReverse : " + position.toString());
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.BACKWARD;
        serial.write("b" + position.getX() + "#" + position.getY());
    }

    @Override
    public void face(Position position) {
        logger.info("goToFace : " + position.toString());
        asservStatus = AsservStatus.STATUS_RUNNING;
        synchronized (lock) {
            statusCountdown = 2;
        }
        direction = MovementDirection.NONE;
        serial.write("f" + position.getX() + "#" + position.getY());
    }

    /*******************************************************************************************************************
     * Commandes controle odométrie
     ******************************************************************************************************************/

    @Override
    public void setOdometrie(int x, int y, double theta) {
        logger.info("setOdometrie");
        serial.write("P" + x + "#" + y + "#" + theta);
    }

    /*******************************************************************************************************************
     * Commandes controle régulateurs
     ******************************************************************************************************************/

    @Override
    public void enableLowSpeed(boolean enable) {
        logger.info("enableLowSpeed : " + enable);
        serial.write(enable ? "S25" : "S100");
    }

    @Override
    public void setSpeed(int pct) {
        logger.info("setSpeed " + pct + "%");
        serial.write("S" + pct);
    }

    @Override
    public void enableRegulatorAngle(boolean enable) {
        logger.info("enableRegulatorAngle : " + enable);
        serial.write(enable ? "Rae" : "Rad");
    }

    @Override
    public void resetRegulatorAngle() {
        logger.info("resetRegulatorAngle");
        serial.write("Rar");
    }

    @Override
    public void enableRegulatorDistance(boolean enable) {
        logger.info("enableRegulatorDistance : " + enable);
        serial.write(enable ? "Rde" : "Rdd");
    }

    @Override
    public void resetRegulatorDistance() {
        logger.info("resetRegulatorDistance");
        serial.write("Rdr");
    }

    @Override
    public void enableMotors(boolean enable) {
        logger.info("enable motors " + enable);
        serial.write("M" + (enable ? 1 : 0));
    }

    /**
     * Parse le retour de la boucle d'asserv contenant la position du robot
     * #<positionX>;<positionY>;<angle>;<commandStatus>;<cmdQueueSize>;<vitesseG>;<vitesseD>
     * @param str Position du robot renvoyée par l'asserv
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
            logger.info("Trace asserv non parsable : " + str);
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
                    enableRegulatorAngle(temp.get("enable").getAsBoolean());
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
