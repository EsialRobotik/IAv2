package esialrobotik.ia.actions;

import esialrobotik.ia.actions.a2023.ActionFileBinder;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.manager.CommunicationManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * The purpose of this class is to handle the execution of esialrobotik.ia.actions
 * Created by icule on 21/05/17.
 */
public class ActionSupervisor {
    private ActionInterface actionInterface;
    private ActionExecutor currentActionExecutor;
    private ArrayList<Integer> initActionsId;
    private CommunicationManager communicationManager;
    private String actionFlag;
    protected Logger logger;

    public ActionSupervisor(ActionInterface actionInterface, ArrayList<String> initActions) {
        logger = LoggerFactory.getLogger(ActionSupervisor.class);
        this.actionInterface = actionInterface;
        this.initActionsId = new ArrayList<>();
        for (String action : initActions) {
            this.initActionsId.add(ActionFileBinder.ActionFile.valueOf(action).ordinal());
        }
    }

    public ActionExecutor getActionExecutor(int id) {
        return actionInterface.getActionExecutor(id);
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void executeCommand(int id) {
        logger.info("Execute command " + id);
        actionFlag = null;
        currentActionExecutor = actionInterface.getActionExecutor(id);
        currentActionExecutor.resetActionState();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                currentActionExecutor.execute();
            }
        });
        t.start();
    }

    public void stopActions() {
        actionInterface.stopActions();
    }

    public void init() {
        logger.info("Init actionneurs");
        for (int actionId : this.initActionsId) {
            logger.info("Init action : " + actionId);
            this.executeCommand(actionId);
            while (!this.isLastExecutionFinished()) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Funny action
     * @return score
     */
    public int funnyAction(FunnyActionDescription funnyActionDescription) {
        return actionInterface.funnyAction(funnyActionDescription);
    }

    public boolean isLastExecutionFinished() {
        if (currentActionExecutor.finished()) {
            actionFlag = currentActionExecutor.getActionFlag();
            return true;
        }
        return false;
    }

    public void setCommunicationManager(CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        this.actionInterface.setCommunicationManager(communicationManager);
    }
}
