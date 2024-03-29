package manager;


import api.log.LoggerFactory;
import asserv.Position;
import detection.DetectionInterface;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import pathfinding.table.Point;
import pathfinding.table.Table;

import java.util.HashMap;

/**
 * Created by icule on 12/05/17.
 */
public class UltraSoundManager {
    private DetectionInterface detectionInterface;
    private Logger logger;
    private int windowSize;

    private Thread thread;
    private boolean[][] detection;
    private Position[] detectionPosition;

    private MovementManager movementManager;
    private Table table;

    private volatile boolean interrupted = false;

    private Position posFrontLeft;
    private Position posFront;
    private Position posFrontRight;
    private Position posBack;

    private HashMap<String, Integer> thresholdMap;

    public UltraSoundManager(DetectionInterface detectionInterface, int windowSize, Table table, MovementManager movementManager) {
        this.windowSize = windowSize;
        this.detection = new boolean[detectionInterface.getUltraSoundSensorCount()][windowSize];
        this.detectionPosition = new Position[detectionInterface.getUltraSoundSensorCount()];
        LoggerFactory.init(Level.TRACE);
        this.logger = LoggerFactory.getLogger(UltraSoundManager.class);

        this.movementManager = movementManager;
        this.detectionInterface = detectionInterface;
        this.table = table;

        this.posFrontLeft = this.detectionInterface.getUltrasoundFrontLeft().getPosition();
        if (detectionInterface.getUltraSoundSensorCount() == 4) {
            this.posFront = this.detectionInterface.getUltrasoundFront().getPosition();
        }
        this.posFrontRight = this.detectionInterface.getUltrasoundFrontRight().getPosition();
        this.posBack = this.detectionInterface.getUltrasoundBack().getPosition();

        this.thresholdMap = new HashMap<>();
        this.thresholdMap.put("FrontLeft", this.detectionInterface.getUltrasoundFrontLeft().getThreshold());
        if (detectionInterface.getUltraSoundSensorCount() == 4) {
            this.thresholdMap.put("Front", this.detectionInterface.getUltrasoundFront().getThreshold());
        }
        this.thresholdMap.put("FrontRight", this.detectionInterface.getUltrasoundFrontRight().getThreshold());
        this.thresholdMap.put("Back", this.detectionInterface.getUltrasoundBack().getThreshold());
    }

    private static Position getObstaclePosition(Position posRobot, Position posDetector, long distance) {
        double xObstacleRelativeToRobot, yObstacleRelativeToRobot;
        int xObstacleRelativeToTable, yObstacleRelativeToTable;

        xObstacleRelativeToRobot = posDetector.getX() + distance * Math.cos(posDetector.getTheta());
        yObstacleRelativeToRobot = posDetector.getY() + distance * Math.sin(posDetector.getTheta());

        xObstacleRelativeToTable = (int) (posRobot.getX()
                + xObstacleRelativeToRobot * Math.cos(posRobot.getTheta())
                - yObstacleRelativeToRobot * Math.sin(posRobot.getTheta()) );

        yObstacleRelativeToTable = (int) (posRobot.getY()
                + xObstacleRelativeToRobot * Math.sin(posRobot.getTheta())
                + yObstacleRelativeToRobot * Math.cos(posRobot.getTheta()) );

        return new Position(xObstacleRelativeToTable, yObstacleRelativeToTable);
    }

    public void start() {
        thread = new Thread(() -> {
            while(!interrupted){
                boolean[] tempDetection = new boolean[4];
                final long[] pull = detectionInterface.ultraSoundDetection();
                Position position = movementManager.getPosition();
                this.detectionPosition = new Position[detectionInterface.getUltraSoundSensorCount()];

                //First one is front left
                if (pull[0] < thresholdMap.get("FrontLeft")) {
                    Position pos = getObstaclePosition(position, posFrontLeft, pull[0]);
                    logger.debug("Ultrasound Avant gauche : " + pull[0]);
                    if (this.mustStop(pos)) {
                       tempDetection[0] = true;
                       this.detectionPosition[0] = pos;
                        logger.info("Ultrasound Avant gauche : STOP (" + pos.getX() + "," + pos.getY() + ")");
                    } else {
                        logger.info("Ultrasound Avant gauche : IGNORER (" + pos.getX() + "," + pos.getY() + ")");
                    }
                }

                //front middle
                if (detectionInterface.getUltraSoundSensorCount() == 4) {
                    if (pull[1] < thresholdMap.get("Front")) {
                        Position pos = getObstaclePosition(position, posFront, pull[1]);
                        logger.debug("Ultrasound Avant milieu : " + pull[1]);
                        if (this.mustStop(pos)) {
                            tempDetection[1] = true;
                            this.detectionPosition[1] = pos;
                            logger.info("Ultrasound Avant milieu : STOP (" + pos.getX() + "," + pos.getY() + ")");
                        } else {
                            logger.info("Ultrasound Avant milieu : IGNORER (" + pos.getX() + "," + pos.getY() + ")");
                        }
                    }
                }

                //front right
                int pullId = detectionInterface.getUltraSoundSensorCount() == 4 ? 2 : 1;
                if (pull[pullId] < thresholdMap.get("FrontRight")) {
                    Position pos = getObstaclePosition(position, posFrontRight, pull[pullId]);
                    logger.debug("Ultrasound Avant droit : " + pull[pullId]);
                    if (this.mustStop(pos)) {
                        tempDetection[pullId] = true;
                        this.detectionPosition[pullId] = pos;
                        logger.info("Ultrasound Avant droit : STOP (" + pos.getX() + "," + pos.getY() + ")");
                    } else {
                        logger.info("Ultrasound Avant droit : IGNORER (" + pos.getX() + "," + pos.getY() + ")");
                    }
                }

                //back middle
                pullId = detectionInterface.getUltraSoundSensorCount() == 4 ? 3 : 2;
                if (pull[pullId] < thresholdMap.get("Back")) {
                    Position pos = getObstaclePosition(position, posBack, pull[pullId]);
                    logger.debug("Ultrasound Arriere : " + pull[pullId]);
                    if (this.mustStop(pos)) {
                        tempDetection[pullId] = true;
                        this.detectionPosition[pullId] = pos;
                        logger.info("Ultrasound Arriere : STOP (" + pos.getX() + "," + pos.getY() + ")");
                    } else {
                        logger.info("Ultrasound Arriere : IGNORER (" + pos.getX() + "," + pos.getY() + ")");
                    }
                }

                for (int i = 0; i < tempDetection.length; i++) {
                    if (!tempDetection[i]) {
                        detection[i] = new boolean[windowSize];
                    } else {
                        for (int j = 0; j < detection[i].length; j++) {
                            if (!detection[i][j]) {
                                detection[i][j] = true;
                                break;
                            }
                        }
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void startDebug() {
        thread = new Thread(() -> {
            int sensorCount = detectionInterface.getUltraSoundSensorCount();
            while(!interrupted){
                boolean[] tempDetection = new boolean[sensorCount];
                final long[] pull = detectionInterface.ultraSoundDetection();
                Position position = movementManager.getPosition();

                if (sensorCount == 4) {
                    //First one is front left
                    Position pos = getObstaclePosition(position, posFrontLeft, pull[0]);
                    System.out.println("Ultrasound Avant gauche : " + pull[0] + " = " + pos.getX() + "," + pos.getY());

                    //front middle
                    pos = getObstaclePosition(position, posFront, pull[1]);
                    System.out.println("Ultrasound Avant milieu : " + pull[1] + " = " + pos.getX() + "," + pos.getY());

                    //front right
                    pos = getObstaclePosition(position, posFrontRight, pull[2]);
                    System.out.println("Ultrasound Avant droit : " + pull[2] + " = " + pos.getX() + "," + pos.getY());

                    //back middle
                    pos = getObstaclePosition(position, posBack, pull[3]);
                    System.out.println("Ultrasound Arriere : " + pull[3] + " = " + pos.getX() + "," + pos.getY());
                } else if (sensorCount == 3) {
                    //First one is front left
                    Position pos = getObstaclePosition(position, posFrontLeft, pull[0]);
                    System.out.println("Ultrasound Avant gauche : " + pull[0] + " = " + pos.getX() + "," + pos.getY());

                    //front middle
                    pos = getObstaclePosition(position, posFrontRight, pull[1]);
                    System.out.println("Ultrasound Avant droit : " + pull[1] + " = " + pos.getX() + "," + pos.getY());

                    //front right
                    pos = getObstaclePosition(position, posBack, pull[2]);
                    System.out.println("Ultrasound Arriere : " + pull[2] + " = " + pos.getX() + "," + pos.getY());
                }
                System.out.println("################################################");
                for (int i = 0; i < tempDetection.length; i++) {
                    if (!tempDetection[i]) {
                        detection[i] = new boolean[windowSize];
                    } else {
                        for (int j = 0; j < detection[i].length; j++) {
                            if (!detection[i][j]) {
                                detection[i][j] = true;
                                break;
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        interrupted = true;
    }

    public boolean hasBeenDetected() {
        for (int i = 0; i < detection.length; i++) {
            boolean res = true;
            for (int j = 0; j < detection[i].length; j++) {
                res &= detection[i][j];
            }
            if (res) {
                return true;
            }
        }
        return false;
    }

    public boolean[] getDetectionResult() {
        boolean[] result = new boolean[4];
        for (int i = 0; i < detection.length; i++) {
            boolean res = true;
            for (int j = 0; j < detection[i].length; j++) {
                res &= detection[i][j];
            }
            result[i] = res;
        }
        return result;
    }

    public boolean mustStop(Position position) {
        return position.getX() > 50 && position.getX() < (table.getxSize() - 50)
            && position.getY() > 50 && position.getY() < (table.getySize() - 50)
            && !this.table.isPointInDetectionIgnoreZone(new Point(position));
    }

    public Position[] getDetectionPosition() {
        return detectionPosition;
    }

    public static void main(String[] args) throws Exception {
        boolean[][] detection = new boolean[4][2];
        detection[0][0] = true;
        detection[0][1] = true;
        System.out.println(detection[0][0]);
        System.out.println(detection[0][1]);
        detection[0] = new boolean[2];
        System.out.println(detection[0][0]);
        System.out.println(detection[0][1]);
    }
}
