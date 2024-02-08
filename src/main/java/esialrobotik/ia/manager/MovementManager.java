package esialrobotik.ia.manager;

import esialrobotik.ia.actions.Step;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.asserv.AsservInterface;
import esialrobotik.ia.asserv.Position;
import org.slf4j.Logger;
import esialrobotik.ia.pathfinding.table.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Handle the esialrobotik.ia.asserv. All call to this class must be has fast as possible.
 * Created by icule on 20/05/17.
 */
public class MovementManager {

    private AsservInterface asservInterface;
    private boolean isMatchStarted = false;

    private Step currentStep;

    private Logger logger;

    public MovementManager(AsservInterface asservInterface) {
        this.asservInterface = asservInterface;
        this.logger = LoggerFactory.getLogger(MovementManager.class);
    }

    /**
     * Liste de la série de commande GoTo à enchainer
     */
    private List<Point> gotoQueue = new ArrayList<Point>();

    public void haltAsserv(boolean temporary) {
        logger.info("haltAsserv, gotoQueue.size() = " + gotoQueue.size() + " - temporary = " + temporary);
        if (!temporary) {
            gotoQueue.clear();
        } else {
            logger.info("gotoQueue.size() = " + gotoQueue.size() + " - this.asservInterface.getQueueSize() = " + this.asservInterface.getQueueSize());
            if (gotoQueue.size() > 0 && gotoQueue.size() - this.asservInterface.getQueueSize() > 0 && this.asservInterface.getQueueSize() > 0) {
                gotoQueue = gotoQueue.subList(gotoQueue.size() - this.asservInterface.getQueueSize(), gotoQueue.size());
            }
            logger.info("new gotoQueue size = " + gotoQueue.size());
            logger.info(gotoQueue.toString());
        }
        this.asservInterface.emergencyStop();
        if (!temporary) {
            this.asservInterface.stop();
        }
    }

    /**
     * Resume the esialrobotik.ia.asserv. If the esialrobotik.ia.asserv was halted definitely it should not be restart
     * @return true if the resume was successful, false otherwise
     */
    public boolean resumeAsserv() {
        logger.info("resumeAsserv, gotoQueue.size() = " + gotoQueue.size());
        this.asservInterface.emergencyReset();
        if (gotoQueue.size() > 0) {
            executeMovement(new ArrayList<>(gotoQueue));
            // On attends un peu pour être certains que l'esialrobotik.ia.asserv à reçut au moins une nouvelle commande et est à jour
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            if (this.currentStep != null) {
                this.executeStepDeplacement(this.currentStep);
            }
            return false;
        }
    }

    public void executeMovement(List<Point> trajectory) {
        //Call for a goto solved by astar
        logger.info("executeMovement = " + trajectory);
        logger.info("isMatchStarted = " + isMatchStarted);
        gotoQueue.clear();
        if (trajectory.size() > 2) {
            // On supprime le premier point qui est le point de départ et le dernier pour finir sur un goto précis
            for (Point point : trajectory.subList(1, trajectory.size() - 2)) {
                gotoQueue.add(point);
                if (isMatchStarted) {
                    this.asservInterface.goToChain(new Position(point.x, point.y));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Point lastPoint = trajectory.get(trajectory.size() - 1);
        gotoQueue.add(lastPoint);
        if (isMatchStarted) {
            this.asservInterface.goTo(new Position(lastPoint.x, lastPoint.y));
        }
        logger.info("executeMovement gotoQueue = " + gotoQueue);
    }

    public void executeStepDeplacement(Step step) {
        //Here we receive a GO or a FACE
        this.currentStep = step;
        if (step.getSubType() == Step.SubType.FACE) {
            this.asservInterface.face(new Position(step.getEndPosition().getX(), step.getEndPosition().getY()));
        } else if (step.getSubType() == Step.SubType.GO) {
            this.asservInterface.go(step.getDistance());
            if (step.getTimeout() > 0) {
                this.asservInterface.enableLowSpeed(true);
                try {
                    Thread.sleep(step.getTimeout());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.asservInterface.emergencyStop();
                this.asservInterface.emergencyReset();
                this.asservInterface.enableLowSpeed(false);
            }
        } else if (step.getSubType() == Step.SubType.GOTO) {
            this.asservInterface.goTo(new Position(step.getEndPosition().getX(),step.getEndPosition().getY()));
        } else if (step.getSubType() == Step.SubType.GOTO_BACK) {
            this.asservInterface.goToReverse(new Position(step.getEndPosition().getX(), step.getEndPosition().getY()));
        } else if (step.getSubType() == Step.SubType.GOTO_CHAIN) {
            this.asservInterface.goToChain(new Position(step.getEndPosition().getX(), step.getEndPosition().getY()));
        } else if (step.getSubType() == Step.SubType.SET_SPEED) {
            this.asservInterface.setSpeed(step.getDistance());
        }
    }

    public Position getPosition() {
        return this.asservInterface.getPosition();
    }

    public boolean isLastOrderedMovementEnded() {
        boolean isFinished = this.asservInterface.getQueueSize() == 0 && this.asservInterface.getAsservStatus() == AsservInterface.AsservStatus.STATUS_IDLE;
        if (isFinished) {
            gotoQueue.clear();
            this.currentStep = null;
        }
        return isFinished;
    }

    public AsservInterface.MovementDirection getMovementDirection() {
        return this.asservInterface.getMovementDirection();
    }

    public void goStart(boolean isColor0) {
        try {
            this.asservInterface.goStart(isColor0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMatchStarted(boolean matchStarted) {
        isMatchStarted = matchStarted;
    }

}
