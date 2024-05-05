package actions.reflexive.a2022;

import actions.ActionFileBinder;
import actions.ActionReflexiveAbstract;

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
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_IN_FAKE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_GET_STATUETTE_TOP.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_ASCENSEUR_POMPE_RELEASE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE.ordinal());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_PASSPASS_VITRINE_ALLUMER.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_SOLO_ASCENSCEUR_GET_STATUETTE_TOP.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.A2022_FENWICK_IN.ordinal());
                finished = true;
            }
        }).start();
    }
}
