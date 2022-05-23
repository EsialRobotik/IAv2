package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;
import api.qik.Qik;

import java.io.IOException;

public class PasspassSwitch extends ActionReflexiveAbstract {

    public PasspassSwitch(ActionFileBinder actionFileBinder) {
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
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_SWITCH_FACE.ordinal());
                Qik qik = actionFileBinder.getQikLink();
                try {
                    qik.setM0Speed(127);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_SWITCH_KISS.ordinal());
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
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_SWITCH_FACE.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_POSE_IN.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_PRISE_TAKE_UP.ordinal());
                finished = true;
            }
        }).start();
    }
}
