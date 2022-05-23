package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;

public class FenwickRightArms extends ActionReflexiveAbstract {

    public FenwickRightArms(ActionFileBinder actionFileBinder) {
        super(actionFileBinder);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public String getActionFlag() {
        return null;
    }
}
