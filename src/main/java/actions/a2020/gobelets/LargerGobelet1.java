package actions.a2020.gobelets;

import actions.a2020.ActionAX12Abstract;

public class LargerGobelet1 extends ActionAX12Abstract {

	@Override
	protected void childExecution() {
		this.go(ACTION_AX12.RELACHER_GOBELET1);
	}

}
