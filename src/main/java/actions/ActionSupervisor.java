package actions;

import java.util.ArrayList;

/**
 * The purpose of this class is to handle the execution of actions
 * Created by icule on 21/05/17.
 */
public class ActionSupervisor {
    private ActionInterface actionInterface;
    private ActionExecutor currentActionExecutor;
    private ArrayList<Integer> initActionsId;

    public ActionSupervisor(ActionInterface actionInterface, ArrayList<Integer> initActionsId) {
        this.actionInterface = actionInterface;
        this.initActionsId = initActionsId;
    }

    public void executeCommand(int id) {
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
        }
    }

    /**
     * Funny action
     * @return score
     */
    public int funnyAction() {
        return actionInterface.funnyAction();
    }

    public boolean isLastExecutionFinished() {
        return this.currentActionExecutor.finished();
    }
}
