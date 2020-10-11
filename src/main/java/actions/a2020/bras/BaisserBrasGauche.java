package actions.a2020.bras;

import actions.a2020.ActionAX12Abstract;

public class BaisserBrasGauche extends ActionAX12Abstract {

	@Override
	protected void childExecution() {
		this.go(ACTION_AX12.BRAS_GAUCHE_BAISSER);
	}

}
