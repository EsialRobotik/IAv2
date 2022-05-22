package actions;

import manager.CommunicationManager;

/**
 * Created by Guillaume on 18/05/2017.
 */
public interface ActionExecutor {

    /**
     * Execute the actions.
     */
    void execute();

    /**
     * Is the current execution of the task finished.
     *
     * @return finished or not
     */
    boolean finished();

    /**
     * On fait comme si l'actions avait pas démarée
     */
    void resetActionState();

    /**
     * Set data to an action (for action linked to robots communication)
     * @param data
     */
    void setData(String data);

    void setCommunicationManager(CommunicationManager communicationManager);

    /**
     * Renvoit l'action flag pour skip des objectifs si besoin
     * @return actionFlag ou null
     */
    String getActionFlag();

}
