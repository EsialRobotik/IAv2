package actions.ax12.actions;

import actions.ax12.Action;
import actions.ax12.ActionException;
import api.ax12.AX12;
import api.ax12.AX12Exception;
import api.ax12.AX12LinkException;

public class AX12DisableTorqueAction extends Action {
	
	protected AX12 ax12;
	protected boolean actionDone;
	
	public AX12DisableTorqueAction(AX12 ax12) {
		this.ax12 = ax12;
		this.actionDone = false;
	}
	
	@Override
	public void doAction() throws ActionException {
		try {
			this.ax12.disableTorque();
			this.actionDone = true;
		} catch (AX12LinkException | AX12Exception e) {
			throw new ActionException("Error disabling torque on AX #"+this.ax12.getAddress(), e);
		}
	}

	@Override
	public boolean actionDone() {
		return this.actionDone;
	}

	@Override
	public String getActionId() {
		return "torque";
	}

	@Override
	public String getActionValueAsString() {
		return "disable";
	}

	@Override
	public String getActionActuatorId() {
		return "" + this.ax12.getAddress();
	}
	
	public AX12 getAx12() {
		return this.ax12;
	}
}
