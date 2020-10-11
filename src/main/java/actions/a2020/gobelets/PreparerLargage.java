package actions.a2020.gobelets;

import actions.a2020.ActionAX12Abstract;

public class PreparerLargage extends ActionAX12Abstract {

	@Override
	protected void childExecution() {
		this.go(ACTION_AX12.RAIL_BAISSER_POUR_DEPOSE);
	}

}
