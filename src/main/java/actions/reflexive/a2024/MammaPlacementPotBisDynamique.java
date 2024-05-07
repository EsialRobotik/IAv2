package actions.reflexive.a2024;

import actions.ActionFileBinder;
import actions.ActionReflexiveAbstract;
import asserv.AsservInterface;

public class MammaPlacementPotBisDynamique extends ActionReflexiveAbstract {
    String searchResult;
    private int optimalDistance = 130;

    public MammaPlacementPotBisDynamique(ActionFileBinder actionFileBinder) {
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
                AsservInterface asservInterface = actionFileBinder.getAsservInterface();
                asservInterface.go(-50);
                asservInterface.waitForAsserv();
                String scanResult = executeSubActions(ActionFileBinder.ActionFile.MAMMA_CHARIOT_CHERCHER_GROSSE_EMMERDE.ordinal());
                logger.info("Chercher grosse emmerde : " + scanResult);
                if (scanResult.trim().contains("ko")) {
                    searchResult = "plant_n_ko";
                    finished = true;
                    return;
                } else {
                    String[] data = scanResult.trim().split(" ");
                    int distance = Integer.parseInt(data[0]);
                    if (distance > 350) {
                        searchResult = "plant_n_ko";
                        finished = true;
                        return;
                    }
                    // on se prépare à ramasser
                    executeSubActions(ActionFileBinder.ActionFile.MAMMA_PINCE_OUVRIR.ordinal());
                    executeSubActions(ActionFileBinder.ActionFile.MAMMA_PINCE_BAISSER_POT_ATTRAPER.ordinal());

                    // on se repositionne
                    asservInterface.go(distance - optimalDistance);
                    asservInterface.waitForAsserv();
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
