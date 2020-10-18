package actions.ax12.actions;

import actions.ax12.Action;
import actions.ax12.ActionException;

public class WaitingAction extends Action {
	
	protected long waitingTime;
	protected long timeout;
	
	public WaitingAction(long waitingTime) {
		this.waitingTime = waitingTime;
		this.timeout = 0;
	}

	@Override
	public void doAction() throws ActionException {
		this.timeout = System.currentTimeMillis() + this.waitingTime;
	}

	@Override
	public boolean actionDone() throws ActionException {
		return this.timeout > 0 && this.timeout < System.currentTimeMillis() ;
	}

	@Override
	public String getActionId() {
		return "waiting";
	}

	@Override
	public String getActionActuatorId() {
		return null;
	}

	@Override
	public String getActionValueAsString() {
		return this.waitingTime + " ms";
	}
	
	public long getWaitingTime() {
		return this.waitingTime;
	}

}
