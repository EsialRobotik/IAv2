package actions.reflexive;

import actions.ActionReflexiveAbstract;
import actions.a2022.ActionFileBinder;
import api.communication.SerialRxTx;

import java.io.IOException;
import java.util.Scanner;

public class FenwickFouilleDroite1 extends ActionReflexiveAbstract {

    String probeResult;

    public FenwickFouilleDroite1(ActionFileBinder actionFileBinder) {
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
                SerialRxTx serial = actionFileBinder.getSerialLink();
                Scanner scanner = null;
                try {
                    scanner = new Scanner(serial.getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                probeResult = scanner.nextLine().trim();
                if (probeResult.equals("n") || probeResult.equals("t")) {
                    probeResult = "fouille1KO";
                } else {
                    probeResult = "fouille1OK";
                }
                executeSubActions(ActionFileBinder.ActionFile.FENWICK_SONDE_DROITE_IN.ordinal());
                if (probeResult.equals("fouille1OK")) {
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
