package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;
import api.communication.Serial;

import java.util.Scanner;

public class FenwickFouilleDroite4 extends ActionReflexiveAbstract {

    String probeResult;

    public FenwickFouilleDroite4(ActionFileBinder actionFileBinder) {
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
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SONDE_DROITE_OUT.ordinal());
                Serial serial = actionFileBinder.getSerialLink();
                Scanner scanner = new Scanner(serial.getInputStream());
                probeResult = scanner.nextLine().trim();
                if (probeResult.equals("n") || probeResult.equals("p")) {
                    probeResult = "fouille4KO";
                } else {
                    probeResult = "fouille4OK";
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SONDE_DROITE_IN.ordinal());
                if (probeResult.equals("fouille2OK")) {
                    executeSubActions(ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_OUT.ordinal());
                    executeSubActions(ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_IN.ordinal());
                }
                finished = true;
            }
        }).start();
    }

    @Override
    public String getActionFlag() {
        return probeResult;
    }
}
