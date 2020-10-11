package actions.a2020.bras;

import actions.a2020.ActionAX12Abstract;

public class RangerBrasDroit extends ActionAX12Abstract {

	@Override
	protected void childExecution() {
		this.go(ACTION_AX12.BRAS_DROIT_RANGER);
	}

}
