package actions.a2018;

import actions.ActionExecutor;
import actions.ActionInterface;
import api.communication.Serial;

import java.util.List;

/**
 * Created by franc on 06/05/2018.
 */
public class Actions implements ActionInterface {

    private Serial serialAX12;
    private AX12Serial customSerialAX12;

    private List<ActionExecutor> actionExecutors;

    public Actions() {
//        this.serialAX12 = new Serial(actionModuleConfiguration.getSerialPort(), Baud.getInstance(actionModuleConfiguration.getBaud()));
//        this.serialAX12.setDTR(true);
//        this.customSerialAX12 = new AX12Serial(this.serialAX12);
//        // On instancie les différents types d'actions, on fait les init et on stocke tout ça dans la liste
//        actionExecutors = new ArrayList<>();
//
//        /*
//         * Controle des bras latéraux
//         * 0 - Rentrer Bras Droit
//         * 1 - Sortir Bras Droit
//         * 2 - Rentrer Bras Gauche
//         * 3 - Sortir Bras Gauche
//         */
//        actionExecutors.add(new BrasDroitRentrer().init(this.customSerialAX12));
//        actionExecutors.add(new BrasDroitSortir().init(this.customSerialAX12));
//        actionExecutors.add(new BrasGaucheRentrer().init(this.customSerialAX12));
//        actionExecutors.add(new BrasGaucheSortir().init(this.customSerialAX12));
//
//        /*
//         * 4 - Lancement Eau Propre
//         * 5 - Largage Eau Sale Droit
//         * 6 - Largage Eau Sale Gauche
//         * 7 - Largage Eau Sale Préparation
//         * 8 - Rangement Tubes
//         * 9 - Remplissage
//         * 10 - Remplissage Préparation
//         * 11 - Remplissage Rangement
//         */
//        actionExecutors.add(new LancementEauPropre().init(this.customSerialAX12));
//        actionExecutors.add(new LargageEauSaleDroit().init(this.customSerialAX12));
//        actionExecutors.add(new LargageEauSaleGauche().init(this.customSerialAX12));
//        actionExecutors.add(new LargageEauSalePreparation().init(this.customSerialAX12));
//        actionExecutors.add(new RangementTubes().init(this.customSerialAX12));
//        actionExecutors.add(new Remplissage().init(this.customSerialAX12));
//        actionExecutors.add(new RemplissagePreparation().init(this.customSerialAX12));
//        actionExecutors.add(new RemplissageRangement().init(this.customSerialAX12));
//
//        /*
//         * 12 - Préparation de l'allumage de l'interrupteur
//         * 13 - Allumage de l'interrupteur
//         */
//        actionExecutors.add(new InterrupteurPreparer().init(this.customSerialAX12));
//        actionExecutors.add(new InterrupteurAllumer().init(this.customSerialAX12));
    }

    @Override
    public ActionExecutor getActionExecutor(int id) {
        return this.actionExecutors.get(id);
    }

    @Override
    public void stopActions() {

    }

}