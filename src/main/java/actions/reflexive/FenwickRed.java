package actions.reflexive;

import actions.ActionExecutor;
import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;

public class FenwickRed extends ActionReflexiveAbstract {

    public FenwickRed(ActionFileBinder actionFileBinder) {
        super(actionFileBinder);
    }

    @Override
    public void execute() {
        if (finished) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                ActionExecutor actionExecutor = actionFileBinder.getActionExecutor(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_PILE_3.ordinal());
                actionExecutor.execute();
                waitForAction(actionExecutor);
                actionExecutor = actionFileBinder.getActionExecutor(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_POMPE_SUCK.ordinal());
                actionExecutor.execute();
                waitForAction(actionExecutor);
                actionExecutor = actionFileBinder.getActionExecutor(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_TOP.ordinal());
                actionExecutor.execute();
                waitForAction(actionExecutor);
                actionExecutor = actionFileBinder.getActionExecutor(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_OUT.ordinal());
                actionExecutor.execute();
                waitForAction(actionExecutor);
                actionExecutor = actionFileBinder.getActionExecutor(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_LACHER.ordinal());
                actionExecutor.execute();
                waitForAction(actionExecutor);
                actionExecutor = actionFileBinder.getActionExecutor(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_POMPE_RELEASE.ordinal());
                actionExecutor.execute();
                waitForAction(actionExecutor);
                actionExecutor = actionFileBinder.getActionExecutor(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_TOP.ordinal());
                actionExecutor.execute();
                waitForAction(actionExecutor);
                actionExecutor = actionFileBinder.getActionExecutor(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_IN.ordinal());
                actionExecutor.execute();
                waitForAction(actionExecutor);
                finished = true;
            }
        }).start();
    }
}
