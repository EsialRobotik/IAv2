package actions;

/**
 * The purpose of this class is to handle the execution of actions
 * Created by icule on 21/05/17.
 */
public class ActionSupervisor {
    private ActionInterface actionInterface;
    private ActionExecutor currentActionExecutor;

    public ActionSupervisor(ActionInterface actionInterface) {
        this.actionInterface = actionInterface;
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

    public void funnyAction() {
        actionInterface.funnyAction();
    }

    public boolean isLastExecutionFinished() {
        return this.currentActionExecutor.finished();
    }
}
