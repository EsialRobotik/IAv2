package actions;

import manager.CommunicationManager;

import java.util.ArrayList;

/**
 * The purpose of this class is to handle the execution of actions
 * Created by icule on 21/05/17.
 */
public class ActionSupervisor {
    private ActionInterface actionInterface;
    private ActionExecutor currentActionExecutor;
    private ArrayList<Integer> initActionsId;
    private CommunicationManager communicationManager;
    private String actionFlag;

    public ActionSupervisor(ActionInterface actionInterface, ArrayList<Integer> initActionsId) {
        this.actionInterface = actionInterface;
        this.initActionsId = initActionsId;
    }

    public ActionExecutor getActionExecutor(int id) {
        return actionInterface.getActionExecutor(id);
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void executeCommand(int id) {
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
        for (int actionId : this.initActionsId) {
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
