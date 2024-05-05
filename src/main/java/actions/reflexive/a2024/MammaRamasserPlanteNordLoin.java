package actions.reflexive.a2024;

import actions.ActionFileBinder;
import actions.ActionReflexiveAbstract;
import api.communication.SerialRxTx;

import java.io.IOException;
import java.util.Scanner;

public class MammaRamasserPlanteNordLoin extends ActionReflexiveAbstract {

    String searchResult;

    public MammaRamasserPlanteNordLoin(ActionFileBinder actionFileBinder) {
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
                SerialRxTx serial = actionFileBinder.getSerialLink();
                Scanner scanner = null;
                try {
                    scanner = new Scanner(serial.getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                executeSubActions(ActionFileBinder.ActionFile.MAMMA_PINCE_LEVER_VERTICAL.ordinal());
                String scanResult = executeSubActions(ActionFileBinder.ActionFile.MAMMA_CHARIOT_ALIGNER_PLANTE.ordinal());
                System.out.println("Chariot result : " + scanResult);
                if (scanResult.trim().contains("ko")) {
                    searchResult = "plant_n_ko";
                    finished = true;
                    return;
                }
                executeSubActions(ActionFileBinder.ActionFile.MAMMA_PINCE_LEVER_RAMI.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.MAMMA_PINCE_OUVRIR.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.MAMMA_PINCE_BAISSER.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.MAMMA_PINCE_FERMER.ordinal());
                executeSubActions(ActionFileBinder.ActionFile.MAMMA_PINCE_LEVER_RAMI.ordinal());
                finished = true;
            }
        }).start();
    }

    @Override
    public String getActionFlag() {
        return searchResult;
    }
}
