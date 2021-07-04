package actions;

import manager.CommunicationManager;

/**
 * Created by franc on 10/02/2017.
 */
public interface ActionInterface {

    ActionExecutor getActionExecutor(int id);

    void stopActions();

    int funnyAction();

    void setCommunicationManager(CommunicationManager communicationManager);
}
