package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;

public class FenwickSoloPutFake extends ActionReflexiveAbstract {

    public FenwickSoloPutFake(ActionFileBinder actionFileBinder) {
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
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_IN.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_POMPE_SUCK.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_GET_FAKE.ordinal());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_TOP.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_OUT.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_DROP_FAKE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_POMPE_RELEASE.ordinal());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_DROP_FAKE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_DROP_FAKE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_DROP_FAKE_BIS.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_TOP.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_IN.ordinal());
                finished = true;
            }
        }).start();
    }
}
