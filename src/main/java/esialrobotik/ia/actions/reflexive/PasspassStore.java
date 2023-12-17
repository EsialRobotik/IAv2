package esialrobotik.ia.actions.reflexive;

import esialrobotik.ia.actions.ActionReflexiveAbstract;
import esialrobotik.ia.actions.a2022.ActionFileBinder;
import esialrobotik.ia.api.qik.Qik;

import java.io.IOException;

public class PasspassStore extends ActionReflexiveAbstract {

    public PasspassStore(ActionFileBinder actionFileBinder) {
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
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_PRISE_STORE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_STORE_POMPE_ON.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_STORE_OUT.ordinal());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Qik qik = actionFileBinder.getQikLink();
                try {
                    qik.setM1Speed(-127);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    qik.setM1Speed(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_STORE_IN.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_PRISE_TAKE_UP.ordinal());
                finished = true;
            }
        }).start();
    }
}
