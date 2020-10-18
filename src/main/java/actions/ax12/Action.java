package actions.ax12;

public abstract class Action {
	
	/**
	 * Apply the state to the AX12
	 * 
	 * @throws ActionException
	 */
	public abstract void doAction() throws ActionException;
	
	/**
	 * Tells if the actions has been done
	 * Always returns false if the doAction() has never been called or its last call returned an error
	 * 
	 * @return
	 * @throws ActionException if the previous call to doAction() has thrown an error
	 */
	public abstract boolean actionDone() throws ActionException;
	
	/**
	 * @return
	 */
	public abstract String getActionId();
	
	/**
	 * 
	 * @return
	 */
	public abstract String getActionActuatorId();
	
	/**
	 * 
	 * @return
	 */
	public abstract String getActionValueAsString();
	
}
