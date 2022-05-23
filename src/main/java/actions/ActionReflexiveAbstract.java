package actions;

import actions.a2022.ActionFileBinder;

abstract public class ActionReflexiveAbstract implements ActionExecutor {

    protected ActionFileBinder actionFileBinder;

    public ActionReflexiveAbstract(ActionFileBinder actionFileBinder) {
        this.actionFileBinder = actionFileBinder;
    }
}
