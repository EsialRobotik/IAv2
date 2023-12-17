package esialrobotik.ia.actions;

import esialrobotik.ia.manager.CommunicationManager;

/**
 * Created by franc on 10/02/2017.
 */
public interface ActionInterface {

    ActionExecutor getActionExecutor(int id);

    void stopActions();

    int funnyAction(FunnyActionDescription funnyActionDescription);

    void setCommunicationManager(CommunicationManager communicationManager);
}
