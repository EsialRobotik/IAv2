package asserv;

import api.communication.Serial;
import api.log.LoggerFactory;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.SerialDataEventListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

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
    public void setOdometrieX(int x) {
        logger.info("setOdometrieX : " + x);
        serial.write("Osx" + x);
    }

    @Override
    public void setOdometrieY(int y) {
        logger.info("setOdometrieY : " + y);
        serial.write("Osy" + y);
    }

    @Override
    public void setOdometrieTheta(double theta) {
        logger.info("setOdometrieTheta");
        serial.write("Osa" + theta);
    }

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

    public void calage2018(boolean isColor0) throws InterruptedException {
        // On init
        initialize();

        // On semet au ralentie
        enableLowSpeed(true);

        // On se colle à la bordure de 2000
        go(-200);
        Thread.sleep(2000);
        enableRegulatorAngle(false);
        Thread.sleep(2000);
        resetRegulatorAngle();

        // On set le Y puis on avance un peu
        setOdometrieY(isColor0 ? 102 : 3000 - 102);
        setOdometrieTheta((isColor0 ? 1 : -1) * Math.PI/2);
        enableRegulatorAngle(true);
        emergencyStop();
        emergencyReset();
        go(120);
        Thread.sleep(1000);

        // On tourne de 90° pour mettre le cul vers la bordure de 2000
        turn(isColor0 ? -90 : 90);

        // On recule contre la bordure
        Thread.sleep(1000);
        go(-200);
        Thread.sleep(2000);
        enableRegulatorAngle(false);
        Thread.sleep(2000);

        setOdometrieX(102);
        emergencyStop();
        emergencyReset();

        // On se remet à vitesse normale
        enableLowSpeed(false);
        enableRegulatorAngle(true);

        // On se positionne dans la zone de départ
        go(200);
        Position depart = new Position(400, isColor0 ? 200 : 3000 - 200);
        goTo(depart);
        Position alignement = new Position(1000, isColor0 ? 850 : 3000 - 850);
        face(alignement);
    }

    @Override
    public void calage(boolean isColor0) throws InterruptedException {
        // On init
//        initialize();
//        Thread.sleep(2000);

        // On effectue le calage grâce à une calle dans le coin côté N, sur le bord de la table
        setOdometrie(
            530 + 150,
            isColor0 ? (40 + 125) : (3000 - (40 + 125)),
            (isColor0 ? 1 : -1) * Math.PI/2
        );
        this.logger.info("Position setted");
    }

    @Override
    public void goStart(boolean isColor0) throws InterruptedException {
        // On se positionne dans la zone de départ
        go(100);
        this.logger.info("Go 100");
        waitForAsserv();
        Position depart = new Position(800, isColor0 ? 200 : 3000 - 200);
        this.logger.info("Goto " + depart.toString());
        goTo(depart);
        waitForAsserv();
        Position alignement = new Position(800, isColor0 ? 3000 : 0 );
        this.logger.info("Goto " + alignement.toString());
        face(alignement);
        waitForAsserv();
        this.logger.info("goStart finished");
    }

    public static void main(String[] args) throws Exception {
        boolean[] fuu = new boolean[4];
        fuu[0] = true;
        fuu[1] = false;
        fuu[2] = false;
        fuu[3] = true;
        System.out.println(Arrays.toString(fuu));
    }
}
