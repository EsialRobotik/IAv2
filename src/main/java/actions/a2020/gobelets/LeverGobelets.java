package actions.a2020.gobelets;

import actions.a2020.ActionAX12Abstract;

public class LeverGobelets extends ActionAX12Abstract {

	@Override
	protected void childExecution() {
		this.go(ACTION_AX12.RAIL_LEVER_MAX);
	}

}
