package detection;

import api.communication.Serial;
import api.log.LoggerFactory;
import asserv.Asserv;
import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.SerialDataEventListener;
import org.apache.logging.log4j.Logger;
import pathfinding.table.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface with RPLidar A2 through ESP32
 * <a href="https://github.com/EsialRobotik/utilities/tree/master/rplidara2_esp32_proxy">rplidara2_esp32_proxy</a>
 */
public class Lidar {
    private Serial lidarSerial;
    private List<Point> detectedPoints;

    /**
     * Logger
     */
    protected Logger logger = null;

    public enum Mode {
        STANDARD,
        CLUSTERING,
        CLUSTERING_ONE_LINE
    }

    public enum Coordinate {
        CARTESIAN,
        POLAR_DEGREES,
        POLAR_RADIANS
    }

    private Coordinate currentCoordinateMode;

    /**
     * Constructeur
     * @param serialPort Port série
     * @param baudRate Baud rate
     */
    public Lidar(String serialPort, Baud baudRate) {
        logger = LoggerFactory.getLogger(Asserv.class);

        logger.info("Initialisation de la liason série du lidar, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        lidarSerial = new Serial(serialPort, baudRate);
        this.init(30, 1000);
        lidarSerial.addReaderListeners((SerialDataEventListener) serialDataEvent -> {
            try {
                String serialBuffer = serialDataEvent.getAsciiString();
                logger.trace("Lidar result : " + serialBuffer);
                parseLidarMeasures(serialBuffer);
            } catch (IOException e) {
                logger.error("Echec du parsing de la position : " + e.getMessage());
            }
        });
        this.detectedPoints = new ArrayList<>();
    }

    public Lidar(JsonObject config) {
        this.logger = LoggerFactory.getLogger(Asserv.class);

        String serialPort = config.get("serie").getAsString();
        Baud baudRate = Baud.getInstance(config.get("baud").getAsInt());

        this.logger.info("Initialisation de la liason série du lidar, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        this.lidarSerial = new Serial(serialPort, baudRate);
        this.init(config.get("quality").getAsInt(), config.get("distance").getAsInt());
        this.lidarSerial.addReaderListeners((SerialDataEventListener) serialDataEvent -> {
            try {
                String serialBuffer = serialDataEvent.getAsciiString();
                logger.trace("Lidar result : " + serialBuffer);
                parseLidarMeasures(serialBuffer);
            } catch (IOException e) {
                this.logger.error("Echec du parsing de la position : " + e.getMessage());
            }
        });
        this.detectedPoints = new ArrayList<>();
    }

    /**
     * Init lidar and start scan
     * @param quality Minimal quality level
     * @param distance Maximal distance
     */
    private void init(int quality, int distance) {
        this.reset();
        this.setCoordinateMode(Coordinate.CARTESIAN);
        this.setMode(Mode.CLUSTERING_ONE_LINE);
        this.setQuality(quality);
        this.setDistance(distance);
        this.startScan();
    }

    /**
     * Decode lidar measures
     * @param serialBuffer Lidar buffer from serial
     */
    private void parseLidarMeasures(String serialBuffer) {
        // Nettoyer la liste des points détectés
        detectedPoints.clear();

        // Nettoyer la chaîne de caractères
        serialBuffer = serialBuffer.trim();

        // Séparer les coordonnées par le caractère #
        String[] points = serialBuffer.split("#");

        // Parcourir chaque point
        for (String point : points) {
            // Séparer les coordonnées x et y par le caractère ;
            String[] coordinates = point.split(";");

            // Vérifier que nous avons bien deux coordonnées
            if (coordinates.length == 2) {
                try {
                    // Ajouter le point à la liste des points détectés
                    detectedPoints.add(new Point(
                        (int) Math.round(Double.parseDouble(coordinates[0])),
                        (int) Math.round(Double.parseDouble(coordinates[1]))
                    ));
                } catch (NumberFormatException e) {
                    logger.error("Erreur de format de nombre pour les coordonnées : " + point);
                }
            } else {
                logger.error("Erreur de format pour le point : " + point);
            }
        }

    }

    /******************************
     * Control Lidar
     ******************************/

    /**
     * Start measures
     */
    public void startScan() {
        lidarSerial.write("s");
    }

    /**
     * Stop measures
     */
    public void stopScan() {
        lidarSerial.write("h");
    }

    /**
     * Set scanning mode
     * @param mode Scanning mode
     */
    public void setMode(Mode mode) {
        if (mode.equals(Mode.STANDARD)) {
            lidarSerial.write("mf");
        } else if (mode.equals(Mode.CLUSTERING)) {
            lidarSerial.write("mc");
        } else if (mode.equals(Mode.CLUSTERING_ONE_LINE)) {
            lidarSerial.write("mo");
        }
    }

    /**
     * Start motor rotation
     */
    public void startMotor() {
        lidarSerial.write("r1");
    }

    /**
     * Stop motor rotation
     */
    public void stopMotor() {
        lidarSerial.write("r0");
    }

    /**
     * Ask Lidar information (serial number, etc.)
     */
    public void askLidarInfo() {
        lidarSerial.write("i");
    }

    /**
     * Reset everything
     */
    public void reset() {
        lidarSerial.write("e");
    }

    /**
     * Get quality threshold
     */
    public void getQuality() {
        lidarSerial.write("q");
    }

    /**
     * Set quality threshold
     * @param value Threshold value, from 1 to 63
     */
    public void setQuality(int value) {
        lidarSerial.write("q" + value);
    }

    /**
     * Get distance threshold
     */
    public void getDistance() {
        lidarSerial.write("d");
    }

    /**
     * Set distance threshold
     * @param value Threshold value in mm
     */
    public void setDistance(int value) {
        lidarSerial.write("d" + value);
    }

    /**
     * Get Lidar health
     */
    public void getHealth() {
        lidarSerial.write("l");
    }

    /**
     * Get coordinate mode
     */
    public void getCoordinateMode() {
        lidarSerial.write("f");
    }

    /**
     * Set coordinate mode
     */
    public void setCoordinateMode(Coordinate coordinateMode) {
        if (coordinateMode.equals(Coordinate.CARTESIAN)) {
            lidarSerial.write("fc");
        } else if (coordinateMode.equals(Coordinate.POLAR_DEGREES)) {
            lidarSerial.write("fd");
        } else if (coordinateMode.equals(Coordinate.POLAR_RADIANS)) {
            lidarSerial.write("fr");
        }
    }

    public static void main(String[] args) {
        System.out.println((int) Math.round(Double.parseDouble("3.48")));
        System.out.println((int) Math.round(Double.parseDouble("3.58")));
    }
}
