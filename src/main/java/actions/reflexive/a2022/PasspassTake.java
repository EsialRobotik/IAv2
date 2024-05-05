package actions.reflexive.a2022;

import actions.ActionFileBinder;
import actions.ActionReflexiveAbstract;
import api.qik.Qik;

import java.io.IOException;

public class PasspassTake extends ActionReflexiveAbstract {

    public PasspassTake(ActionFileBinder actionFileBinder) {
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
                Qik qik = actionFileBinder.getQikLink();
                try {
                    qik.setM1Speed(127);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.A2022_PASSPASS_AX_BRAS_PRISE_TAKE_DOWN.ordinal());
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.A2022_PASSPASS_AX_BRAS_PRISE_TAKE_UP.ordinal());
                finished = true;
            }
        }).start();
    }
}
