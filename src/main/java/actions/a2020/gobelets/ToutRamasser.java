package actions.a2020.gobelets;

import actions.a2020.ActionAX12Abstract;

public class ToutRamasser extends ActionAX12Abstract {

	@Override
	protected void childExecution() {
		this.go(ACTION_AX12.ATTRAPER_GOBELET1);
		this.go(ACTION_AX12.ATTRAPER_GOBELET2);
		this.go(ACTION_AX12.ATTRAPER_GOBELET3);
		this.go(ACTION_AX12.ATTRAPER_GOBELET4);
		this.go(ACTION_AX12.ATTRAPER_GOBELET5);
		attend(500);
		this.go(ACTION_AX12.RAIL_LEVER_POUR_RECOLTER);
	}

}
