package esialrobotik.ia.actions.reflexive;

import esialrobotik.ia.actions.ActionReflexiveAbstract;
import esialrobotik.ia.actions.a2022.ActionFileBinder;

public class FenwickRed extends ActionReflexiveAbstract {

    public FenwickRed(ActionFileBinder actionFileBinder) {
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
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_ASCENSEUR_HAUTEUR_PILE_3.ordinal());
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
