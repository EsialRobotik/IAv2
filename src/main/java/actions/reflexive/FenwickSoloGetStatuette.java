package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;

public class FenwickSoloGetStatuette extends ActionReflexiveAbstract {

    public FenwickSoloGetStatuette(ActionFileBinder actionFileBinder) {
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
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_TOP.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_OUT.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_POMPE_SUCK.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_GET_STATUETTE.ordinal());
                // wait to suck
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_GET_STATUETTE_TOP.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS.ordinal());
                finished = true;
            }
        }).start();
    }
}
