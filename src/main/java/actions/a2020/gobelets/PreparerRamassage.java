package actions.a2020.gobelets;

import actions.a2020.ActionAX12Abstract;

public class PreparerRamassage extends ActionAX12Abstract {

	@Override
	protected void childExecution() {
		this.go(ACTION_AX12.RAIL_BAISSER_POUR_RECOLTER);
		this.go(ACTION_AX12.RELACHER_GOBELET1);
		this.go(ACTION_AX12.RELACHER_GOBELET2);
		this.go(ACTION_AX12.RELACHER_GOBELET3);
		this.go(ACTION_AX12.RELACHER_GOBELET4);
		this.go(ACTION_AX12.RELACHER_GOBELET5);
	}

}
