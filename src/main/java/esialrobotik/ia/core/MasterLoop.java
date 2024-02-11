package esialrobotik.ia.core;

import esialrobotik.ia.actions.*;
import esialrobotik.ia.api.chrono.Chrono;
import esialrobotik.ia.api.gpio.ColorDetector;
import esialrobotik.ia.api.gpio.Tirette;
import esialrobotik.ia.api.lcd.LCD;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.asserv.AsservInterface;
import esialrobotik.ia.asserv.Position;
import esialrobotik.ia.manager.CommunicationManager;
import esialrobotik.ia.manager.DetectionManager;
import esialrobotik.ia.manager.MovementManager;
import org.slf4j.Logger;
import esialrobotik.ia.pathfinding.PathFinding;
import esialrobotik.ia.pathfinding.table.Point;
import esialrobotik.ia.pathfinding.table.TableColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 17/05/2017.
 */
public class MasterLoop {
    private MovementManager movementManager;
    private DetectionManager detectionManager;
    private CommunicationManager communicationManager;
    private ActionCollection actionCollection;
    private PathFinding pathFinding;
    private ColorDetector colorDetector;
    private Chrono chrono;
    private Tirette tirette;
    private LCD lcdDisplay;
    private FunnyActionDescription funnyActionDescription;

    private volatile boolean interrupted;

    private ActionSupervisor actionSupervisor;
    private ActionDescriptor currentAction;
    private Step currentStep;

    private Logger logger;

    private int score = 0;

    public MasterLoop(MovementManager movementManager,
                      DetectionManager detectionManager,
                      CommunicationManager communicationManager,
                      ActionCollection actionCollection,
                      ActionSupervisor actionSupervisor,
                      PathFinding pathFinding,
                      ColorDetector colorDetector,
                      Chrono chrono,
                      Tirette tirette,
                      LCD lcdDisplay,
                      FunnyActionDescription funnyActionDescription) {
        this.movementManager = movementManager;
        this.detectionManager = detectionManager;
        this.communicationManager = communicationManager;
        this.actionCollection = actionCollection;
        this.pathFinding = pathFinding;
        this.colorDetector = colorDetector;
        this.chrono = chrono;
        this.tirette = tirette;
        this.lcdDisplay = lcdDisplay;
        this.actionSupervisor = actionSupervisor;
        this.funnyActionDescription = funnyActionDescription;

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
        // 1/ We pull the first esialrobotik.ia.actions to do
        currentAction = actionCollection.getNextActionToPerform();
        currentStep = currentAction.getNextStep(); //Should not be null

        logger.info("Fetch of first esialrobotik.ia.actions");
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

        lcdDisplay.score(score);
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
                    }
                }

                // 2/ Check if the current step Status
                if (astarLaunch) { //We are computing a path let's check if it's ok now
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
                            logger.info("Suite de l'esialrobotik.ia.actions, step = " + currentStep.getDesc());
                        } else { //Previous esialrobotik.ia.actions has ended, time to fetch a new one
                            logger.info("Action terminé, mise à jour du score");
                            score += currentAction.getPoints();
                            lcdDisplay.score(score);
                            currentAction = actionCollection.getNextActionToPerform();
                            if (currentAction == null) {//Nothing more to do. #sadness
                                logger.info("Plus rien à faire :'(");
                                break;
                            } else {
                                currentStep = currentAction.getNextStep();
                                logger.info("Nouvelle esialrobotik.ia.actions = " + currentAction.getDesc());
                                logger.info("Nouvelle step = " + currentStep.getDesc());
                            }
                        }
                        //Switch... switch... switch, yeah I heard about them once, but never met :P
                        if (currentStep.getActionType() == Step.Type.MANIPULATION) {
                            logger.info("Manip id : " + currentStep.getActionId());
                            actionSupervisor.executeCommand(currentStep.getActionId());
                        } else if (currentStep.getActionType() == Step.Type.DEPLACEMENT) {
                            logger.info("Déplacement " + currentStep.getSubType());
                            if (currentStep.getSubType() == Step.SubType.GOTO_ASTAR) {
                                // We need to launch the astar
                                launchAstar(positionToPoint(currentStep.getEndPosition()));
                                astarLaunch = true;
                            } else if (currentStep.getSubType() == Step.SubType.GOTO_CHAIN) {
                                logger.info("Goto chain");
                                List<Point> path = new ArrayList<>();
                                Position endPos = currentStep.getEndPosition();
                                path.add(new Point(endPos.getX(), endPos.getY()));
                                while (currentAction.getNextStepReal() != null
                                    && currentAction.getNextStepReal().getSubType() == Step.SubType.GOTO_CHAIN) {
                                    currentStep = currentAction.getNextStep();
                                    logger.info("Compute enchain, step = " + currentStep.getDesc());
                                    endPos = currentStep.getEndPosition();
                                    path.add(new Point(endPos.getX(), endPos.getY()));
                                }
                                logger.info("Enchain " + path.size() + " actions");
                                movementManager.executeMovement(path);
                            } else {
                                movementManager.executeStepDeplacement(currentStep);
                            }
                        } else if (currentStep.getActionType() == Step.Type.ELEMENT) {
                            if (currentStep.getSubType() == Step.SubType.SUPPRESSION) {
                                logger.info("Libération de la zone interdite " + currentStep.getItemId());
                                pathFinding.liberateElementById(currentStep.getItemId());
                                communicationManager.sendDeleteZone(currentStep.getItemId());
                            } else if (currentStep.getSubType() == Step.SubType.AJOUT) {
                                logger.info("Ajout de la zone interdite " + currentStep.getItemId());
                                pathFinding.lockElementById(currentStep.getItemId());
                                communicationManager.sendAddZone(currentStep.getItemId());
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
            communicationManager.readFromServer();
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
        if (type == Step.Type.DEPLACEMENT && currentStep.getSubType() == Step.SubType.WAIT_CHRONO) {
            return (currentStep.getTimeout() * 1000L) <= chrono.getTimeSinceBeginning();
        } else if (type == Step.Type.DEPLACEMENT && currentStep.getSubType() == Step.SubType.WAIT) {
            try {
                Thread.sleep(currentStep.getTimeout());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else if ((type == Step.Type.DEPLACEMENT) && movementManager.isLastOrderedMovementEnded()) {
            return true;
        } else if (type == Step.Type.MANIPULATION && actionSupervisor.isLastExecutionFinished()) {
            if (actionSupervisor.getActionFlag() != null) {
                actionCollection.addActionFlag(actionSupervisor.getActionFlag());
            }
            return true;
        } else if (type == Step.Type.ELEMENT) {
            return  true;
        }
        return false;
    }

    //Function to be call to set up the robot and lead him to starting point
    public void init() {
        logger.info("Init mainLoop");

        // Calage bordure
        lcdDisplay.println(colorDetector.isColor0() ? TableColor.COLOR_0.toString() : TableColor.COLOR_3000.toString() + " ou reset ?");
        logger.info("Attente mise en place tirette pour init calage");
        lcdDisplay.println("Attente tirette init");

        tirette.waitForTirette(true);
        tirette.waitForTirette(false);
        logger.info("Initialisation des actionneurs");
        actionSupervisor.init();

        lcdDisplay.println("Attente tirette GoStart");
        logger.info("Attente tirette mise en position de depart");

        tirette.waitForTirette(true);
        tirette.waitForTirette(false);
        logger.info("Start zone de depart");
        movementManager.goStart(colorDetector.isColor0());

        // Wait tirette remise
        lcdDisplay.clear();
        lcdDisplay.println("Attente tirette depart");
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
        //Stop the esialrobotik.ia.asserv here
        logger.info("Shutting done esialrobotik.ia.asserv");
        movementManager.haltAsserv(false);

        //Don't forget esialrobotik.ia.actions
        logger.info("Shutting done esialrobotik.ia.detection");
        detectionManager.stopDetection();
        logger.info("Shutting down esialrobotik.ia.actions");
        actionSupervisor.stopActions();

        interrupted = true;
        //Launch the funny esialrobotik.ia.actions if needed
        logger.info("Funny action");
        int funnyScore = actionSupervisor.funnyAction(funnyActionDescription);

        logger.info("Funny action terminé, mise à jour du score");
        score += funnyScore;
        lcdDisplay.score(score);
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