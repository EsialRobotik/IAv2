package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;

public class FenwickSoloPutStatuette extends ActionReflexiveAbstract {

    public FenwickSoloPutStatuette(ActionFileBinder actionFileBinder) {
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
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_AX_DROP_STATUETTE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_POMPE_RELEASE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE.ordinal());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_VITRINE_ALLUMER.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_IN.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_TOP.ordinal());
                finished = true;
            }
        }).start();
    }
}
