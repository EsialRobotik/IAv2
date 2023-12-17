package esialrobotik.ia.actions.reflexive;

import esialrobotik.ia.actions.ActionReflexiveAbstract;
import esialrobotik.ia.actions.a2022.ActionFileBinder;
import esialrobotik.ia.api.qik.Qik;

import java.io.IOException;

public class PasspassGetStatue extends ActionReflexiveAbstract {

    public PasspassGetStatue(ActionFileBinder actionFileBinder) {
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
                    qik.setM0Speed(127);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_POSE_OUT.ordinal());
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeSubActions(ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_POSE_IN.ordinal());
                finished = true;
            }
        }).start();
    }
}
