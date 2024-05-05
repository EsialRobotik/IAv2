package actions.reflexive.a2024;

import actions.ActionFileBinder;
import actions.ActionReflexiveAbstract;
import asserv.AsservInterface;

public class MammaPlacementPotDynamique extends ActionReflexiveAbstract {
    String searchResult;

    public MammaPlacementPotDynamique(ActionFileBinder actionFileBinder) {
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
                String scanResult = executeSubActions(ActionFileBinder.ActionFile.MAMMA_CHARIOT_CHERCHER_EMMERDE.ordinal());
                System.out.println("Chercher les emmerdes : " + scanResult);
                if (scanResult.trim().contains("ko")) {
                    searchResult = "plant_n_ko";
                    finished = true;
                    return;
                } else {
                    String[] data = scanResult.trim().split(" ");
                    int distance = Integer.parseInt(data[0]);
                    AsservInterface asservInterface = actionFileBinder.getAsservInterface();
                    if (distance > 300) {
                        searchResult = "plant_n_ko";
                        finished = true;
                        return;
                    }
                    // on se repositionne
                    asservInterface.go(distance - 150);
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
