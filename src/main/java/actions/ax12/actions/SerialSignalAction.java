package actions.ax12.actions;

import actions.ax12.Action;
import actions.ax12.ActionException;
import api.ax12.AX12Link;
import api.ax12.AX12LinkException;

public class SerialSignalAction extends Action {

	protected AX12Link ax12Link;
	protected boolean enable;
	protected SIGNAL signal;
	
	protected boolean actionDone;
	
	public enum SIGNAL {
		DTR,
		RTS
	}
	
	public SerialSignalAction(AX12Link ax12Link, SIGNAL signal, boolean enable) {
		if (signal == null) {
			throw new IllegalArgumentException("signal ne doit pas être null");
		}
		this.ax12Link = ax12Link;
		this.enable = enable;
		this.signal = signal;
		this.actionDone = false;
	}
	
	@Override
	public void doAction() throws ActionException {
		try {
			switch (this.signal) {
			case DTR:
					this.ax12Link.enableDtr(this.enable);
				break;
			case RTS:
				this.ax12Link.enableRts(this.enable);
				break;
			}
		} catch (AX12LinkException e) {
			e.printStackTrace();
		}
		this.actionDone = true;
	}

	@Override
	public boolean actionDone() throws ActionException {
		return this.actionDone;
	}

	@Override
	public String getActionId() {
		return "serialsignal";
	}

	@Override
	public String getActionActuatorId() {
		return this.signal.name();
	}

	@Override
	public String getActionValueAsString() {
		return this.enable ? "enabled" : "disabled";
	}
	
	public SIGNAL getSignal() {
		return this.signal;
	}

	public boolean getEnabled() {
		return this.enable;
	}

}
