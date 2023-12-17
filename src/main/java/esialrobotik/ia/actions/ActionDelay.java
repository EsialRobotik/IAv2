package esialrobotik.ia.actions;

import esialrobotik.ia.manager.CommunicationManager;

public class ActionDelay implements ActionExecutor {

    protected long tempsMs;

    protected boolean finished;

    public ActionDelay(String tempsMs) throws NumberFormatException {
        this(Long.parseLong(tempsMs));
    }

    public ActionDelay(long tempsMs) {
        this.tempsMs = tempsMs;
        this.finished = false;
    }

    @Override
    public void execute() {
        try {
            Thread.sleep(this.tempsMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.finished = true;
        }
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
    public void setData(String data) {
    }

    @Override
    public void setCommunicationManager(CommunicationManager communicationManager) {
    }

    @Override
    public String getActionFlag() {
        return null;
    }
}
