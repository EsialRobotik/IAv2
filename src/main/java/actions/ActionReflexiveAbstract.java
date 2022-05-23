package actions;

import actions.a2022.ActionFileBinder;
import api.log.LoggerFactory;
import manager.CommunicationManager;
import org.apache.logging.log4j.Logger;

abstract public class ActionReflexiveAbstract implements ActionExecutor {

    protected ActionFileBinder actionFileBinder;
    protected boolean finished;
    protected Logger logger;

    public ActionReflexiveAbstract(ActionFileBinder actionFileBinder) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.actionFileBinder = actionFileBinder;
        this.finished = true;
    }

    @Override
    public boolean finished() {
        return this.finished;
    }

    @Override
    public void resetActionState() {
        this.finished = false;
    }

    @Override
    public void setData(String data) {}

    @Override
    public void setCommunicationManager(CommunicationManager communicationManager) {}

    @Override
    public String getActionFlag() {
        return null;
    }

    protected void executeSubActions(int actionId) {
        logger.info("Sub action " + actionId);
        ActionExecutor actionExecutor = actionFileBinder.getActionExecutor(actionId);
        actionExecutor.resetActionState();
        logger.info("Sub action " + actionId + " reset");
        actionExecutor.execute();
        logger.info("Sub action " + actionId + " started");
        waitForAction(actionExecutor);
        logger.info("Sub action " + actionId + " ended");
    }

    protected void waitForAction(ActionExecutor actionExecutor) {
        while (!actionExecutor.finished()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
