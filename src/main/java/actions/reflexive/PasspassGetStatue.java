package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;
import manager.CommunicationManager;

public class PasspassGetStatue extends ActionReflexiveAbstract {

    public PasspassGetStatue(ActionFileBinder actionFileBinder) {
        super(actionFileBinder);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public void resetActionState() {

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
