package actions;

import actions.a2022.ActionFileBinder;
import manager.CommunicationManager;

abstract public class ActionReflexiveAbstract implements ActionExecutor {

    protected ActionFileBinder actionFileBinder;
    protected boolean finished;

    public ActionReflexiveAbstract(ActionFileBinder actionFileBinder) {
        this.actionFileBinder = actionFileBinder;
        this.finished = true;
    }

    @Override
    public boolean finished() {
        return this.finished;
    }

    @Override
    public void resetActionState() {}

    @Override
    public void setData(String data) {}

    @Override
    public void setCommunicationManager(CommunicationManager communicationManager) {}

    @Override
    public String getActionFlag() {
        return null;
    }

    public void waitForAction(ActionExecutor actionExecutor) {
        while (!actionExecutor.finished()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
