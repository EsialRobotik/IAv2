package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;

public class FenwickBlue extends ActionReflexiveAbstract {

    public FenwickBlue(ActionFileBinder actionFileBinder) {
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
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_PILE_1.ordinal());
                // wait to suck
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_TOP.ordinal());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_OUT.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_LACHER.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_POMPE_RELEASE.ordinal());
                // wait to release
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_TOP.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_IN.ordinal());
                finished = true;
            }
        }).start();
    }
}
