package actions;

import api.communication.SerialRxTx;
import api.qik.Qik;
import manager.CommunicationManager;

/**
 * Created by franc on 10/02/2017.
 */
public interface ActionInterface {

    ActionExecutor getActionExecutor(int id);

    void stopActions();

    int funnyAction(FunnyActionDescription funnyActionDescription);

    void setCommunicationManager(CommunicationManager communicationManager);

    Qik getQikLink();

    SerialRxTx getSerialLink();
}
