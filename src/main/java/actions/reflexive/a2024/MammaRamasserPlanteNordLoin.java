package actions.reflexive.a2024;

import actions.ActionReflexiveAbstract;
import actions.a2023.ActionFileBinder;
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
                executeSubActions(ActionFileBinder.ActionFile.MAMMA_RAMASSER_PLANTE.ordinal());
                SerialRxTx serial = actionFileBinder.getSerialLink();
                Scanner scanner = null;
                try {
                    scanner = new Scanner(serial.getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String scanResult = scanner.nextLine().trim();
                if (scanResult.trim().equals("ko")) {
                    searchResult = "plant_n_ko";
                }
                finished = true;
            }
        }).start();
    }

    @Override
    public String getActionFlag() {
        return searchResult;
    }
}
