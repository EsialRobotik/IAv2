package core;

import actions.ActionCollection;
import actions.ActionDescriptor;
import actions.ActionSupervisor;
import actions.Step;
import api.chrono.Chrono;
import api.gpio.ColorDetector;
import api.gpio.Tirette;
import api.lcd.LCD;
import api.log.LoggerFactory;
import asserv.AsservInterface;
import asserv.Position;
import manager.DetectionManager;
import manager.MovementManager;
import org.apache.logging.log4j.Logger;
import pathfinding.PathFinding;
import pathfinding.table.Point;
import pathfinding.table.TableColor;

import java.util.Arrays;

/**
 * Created by Guillaume on 17/05/2017.
 */
public class MasterLoop {
    private MovementManager movementManager;
    private DetectionManager detectionManager;
    private ActionCollection actionCollection;
    private PathFinding pathFinding;
    private ColorDetector colorDetector;
    private Chrono chrono;
    private Tirette tirette;
    private LCD lcdDisplay;

    private volatile boolean interrupted;

    private ActionSupervisor actionSupervisor;
    private ActionDescriptor currentAction;
    private Step currentStep;

    private Logger logger;

    private int score = 0;

    public MasterLoop(MovementManager movementManager,
                      DetectionManager detectionManager,
                      ActionCollection actionCollection,
                      ActionSupervisor actionSupervisor,
                      PathFinding pathFinding,
                      ColorDetector colorDetector,
                      Chrono chrono,
                      Tirette tirette,
                      LCD lcdDisplay) {
        this.movementManager = movementManager;
        this.detectionManager = detectionManager;
        this.actionCollection = actionCollection;
        this.pathFinding = pathFinding;
        this.colorDetector = colorDetector;
        this.chrono = chrono;
        this.tirette = tirette;
        this.lcdDisplay = lcdDisplay;
        this.actionSupervisor = actionSupervisor;

        this.interrupted = false; // Chiotte de bordel de saloperie d'enflure de connerie !
        this.logger = LoggerFactory.getLogger(MasterLoop.class);
    }


    //NOTE robot is at starting point before reaching this
    public boolean mainLoop() {
        logger.info("Begin of main loop");
        //When we arrived here everything is set up so we just need to launch the first path finding,
        // and wait for the beginning of the match
        boolean everythingDone = false;
        boolean astarLaunch = false;
        boolean somethingDetected = false;
        boolean movingForward = false;

        actionCollection.prepareActionList(colorDetector.isColor0());
        logger.info("ActionList size : " + actionCollection.getActionList().size());

        // FIRST COMPUTATION HERE
        // 1/ We pull the first actions to do
        currentAction = actionCollection.getNextActionToPerform();
        currentStep = currentAction.getNextStep(); //Should not be null

        logger.info("Fetch of first actions");
        logger.info(currentStep.toString());

        logger.info("Trajectory load, let's wait for tirette");
        // 4/ We wait for the beginning of the match
        tirette.waitForTirette(false);

        // 5/ start of the timer start the main loop
        logger.info("Tirette pull, begin of the match");
        if(!actionCollection.isStepByStep()) {
            chrono.startMatch(this);
        }
        movementManager.setMatchStarted(true);
        movementManager.executeStepDeplacement(currentStep);

        logger.debug("while " + !interrupted);
        lcdDisplay.clear();
        lcdDisplay.println("Score : " + score);
//        String remainingTime = chrono.toString();
        while (!interrupted) {
            if (!somethingDetected) {
                // 1/ we check if we detect something
                boolean[] detected = this.detectionManager.getEmergencyDetectionMap();
                //System.out.println(Arrays.toString(detected));
                if (detected[0] || detected[1] || detected[2] || detected[3]) {
                    //We detect something, we get the movement direction and we check if we detect it in the right side
                    AsservInterface.MovementDirection direction = this.movementManager.getMovementDirection();

                    if (direction.equals(AsservInterface.MovementDirection.FORWARD)
                            && (detected[0] || detected[1] || detected[2])) {
                        logger.info("C'est devant, faut s'arrêter");
                        //We detect something. That's horrible
                        movementManager.haltAsserv(true);
                        movingForward = true;
                        somethingDetected = true;
                        continue;

                    } else if (direction.equals(AsservInterface.MovementDirection.BACKWARD)
                            && detected[3]) {
                        logger.info("C'est derrière, faut s'arrêter");
                        // something is sneaking on us, grab the rocket launcher
                        movementManager.haltAsserv(true);
                        movingForward = false;
                        somethingDetected = true;
                        continue;
                    } /*else {
                        System.out.println("WTF ??");
                        System.out.println(direction);
                    }*/
                }

                // 2/ Check if the current step Status
                if (astarLaunch) { //We are computing a path let's check if it's ok now
                    logger.debug("astarLaunch");
                    if (pathFinding.isComputationEnded()) {
                        movementManager.executeMovement(pathFinding.getLastComputedPath());
                        astarLaunch = false;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Si on a fini l'étape en cour, on lance la suivante
                    if (currentStepEnded()) {
                        logger.info("currentStepEnded : " + currentStep.getDesc());
                        currentStep = null;
                        //Time to fetch the next one
                        if (currentAction.hasNextStep()) {
                            currentStep = currentAction.getNextStep();
                            logger.info("Suite de l'actions, step = " + currentStep.getDesc());
                        } else { //Previous actions has ended, time to fetch a new one
                            logger.info("Action terminé, mise à jour du score");
                            score += currentAction.getPoints();
                            lcdDisplay.clear();
                            lcdDisplay.println("Score : " + score);
                            currentAction = actionCollection.getNextActionToPerform();
                            if (currentAction == null) {//Nothing more to do. #sadness
                                logger.info("Plus rien à faire :'(");
                                break;
                            } else {
                                currentStep = currentAction.getNextStep();
                                logger.info("Nouvelle actions = " + currentAction.getDesc());
                                logger.info("Nouvelle step = " + currentStep.getDesc());
                            }
                        }
                        //Switch... switch... switch, yeah I heard about them once, but never met :P
                        if (currentStep.getActionType() == Step.Type.MANIPULATION) {
                            logger.info("Manip id : " + currentStep.getActionId());
                            actionSupervisor.executeCommand(currentStep.getActionId());
                        } else if (currentStep.getActionType() == Step.Type.DEPLACEMENT) {
                            logger.info("Déplacement");
                            if (currentStep.getSubType() == Step.SubType.GOTO_ASTAR) {
                                // We need to launch the astar
                                launchAstar(positionToPoint(currentStep.getEndPosition()));
                                astarLaunch = true;
                            } else {
                                movementManager.executeStepDeplacement(currentStep);
                            }
                        }
                    }
                    // todo faut vérifier si la voie est libre, sinon on s'arrête et on recalcul
                }
            } else { //We detect something last loop. let's check if we still see it, either let's resume the move
                //If we want to put smart code, it's here
                boolean[] detected = this.detectionManager.getEmergencyDetectionMap();
                if (movingForward && !detected[0] && !detected[1] && !detected[2]) {
                    logger.info("OK devant");
                    movementManager.resumeAsserv();
                    somethingDetected = false;
                } else if (!movingForward && !detected[3]) {
                    logger.info("OK derrière");
                    movementManager.resumeAsserv();
                    somethingDetected = false;
                } else {
                    logger.debug("Detection NOK");
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.info("Sortie du While");
        logger.info("Temps restant : " + chrono.toString());


        return !interrupted;
    }

    //This function could be simplified but at least it keeps things readeable
    private boolean currentStepEnded() {
        Step.Type type = currentStep.getActionType();
        if ((type == Step.Type.DEPLACEMENT) && movementManager.isLastOrderedMovementEnded()) {
            return true;
        } else if (type == Step.Type.MANIPULATION && actionSupervisor.isLastExecutionFinished()) {
            return true;
        }
        return false;
    }

    //Function to be call to set up the robot and lead him to starting point
    public void init() {
        logger.info("Init mainLoop");

        // Calage bordure
        lcdDisplay.println(colorDetector.isColor0() ? TableColor.COLOR_0.toString() : TableColor.COLOR_3000.toString());
        logger.info("Attente mise en place tirette pour init calage");
        lcdDisplay.println("Attente tirette");
        tirette.waitForTirette(true);
        logger.info("Attente retrait tirette pour init calage");
        lcdDisplay.println(colorDetector.isColor0() ? TableColor.COLOR_0.toString() : TableColor.COLOR_3000.toString());
        lcdDisplay.println("Enlever tirette");
        tirette.waitForTirette(false);
        logger.info("Start calage bordure");
        lcdDisplay.println("Lancement calage bordure");
        movementManager.calage(colorDetector.isColor0());

        logger.info("Initialisation des actionneurs");
        lcdDisplay.println("Init actions");
        actionSupervisor.executeCommand(0);

        lcdDisplay.println("Attente tirette");
        logger.info("Attente tirette mise en position de depart");
        tirette.waitForTirette(true);
        lcdDisplay.println("tirette gostart");
        tirette.waitForTirette(false);
        logger.info("Start zone de depart");
        lcdDisplay.println("position depart");
        movementManager.goStart(colorDetector.isColor0());

        // Wait tirette remise
        lcdDisplay.println("Attente tirette");
        logger.info("Init ended, wait for tirette");
        tirette.waitForTirette(true);
        logger.info("Tirette inserted. End of initialization.");
        logger.info("Pret au depart");
        detectionManager.initAPI();
        detectionManager.startDetection();
        lcdDisplay.println("LET'S ROCK !");
    }

    public void matchEnd() {
        logger.info("End of the match");
        lcdDisplay.println("End of match");
        //Stop the asserv here
        logger.info("Shutting done asserv");
        movementManager.haltAsserv(false);

        //Don't forget actions
        logger.info("Shutting done detection");
        detectionManager.stopDetection();
        logger.info("Shutting down actions");
        actionSupervisor.stopActions();

        interrupted = true;
        //Launch the funny actions if needed
        logger.info("Funny action");
        actionSupervisor.funnyAction();

        logger.info("Funny action terminé, mise à jour du score");
        score += currentAction.getPoints();
        lcdDisplay.clear();
        lcdDisplay.println("Score : " + score);
    }

    //Start the computation of the path.
    private void launchAstar(Point destination) {
        pathFinding.computePath(positionToPoint(movementManager.getPosition()), destination);
    }

    public boolean isMatchFinished() {
        return this.interrupted;
    }

    private Point positionToPoint(Position p) {
        return new Point(p.getX(), p.getY());
    }
}
