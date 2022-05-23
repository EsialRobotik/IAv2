package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;

public class FenwickLeftArms extends ActionReflexiveAbstract {

    public FenwickLeftArms(ActionFileBinder actionFileBinder) {
        super(actionFileBinder);
    }

    @Override
    public void execute() {
        logger.info("Start action " + this.getClass());
        if (finished) {
            logger.info("Action already finished " + this.getClass());
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SONDE_GAUCHE_OUT.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SONDES.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SONDE_GAUCHE_IN.ordinal());
                finished = true;
            }
        }).start();
    }

    @Override
    public String getActionFlag() {
        return null;
    }
}
