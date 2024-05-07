package actions;

import api.ax12.AX12LinkSerial;
import api.communication.SerialRxTx;
import api.qik.Qik;
import asserv.AsservInterface;
import manager.CommunicationManager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class ActionFileBinder {

	protected ActionExecutor[] actionsList;
	protected File dataDir;
	protected AX12LinkSerial ax12Link;
	protected ActionCollection actionCollection;
	protected CommunicationManager communicationManager;
	protected Qik qikLink;
	protected SerialRxTx serialLink;
	protected AsservInterface asservInterface;

	public enum ActionFile {
		/**
		 * Actions 2019
		 */
		PREPARATION_PDISTRIB("preparation_pdistrib.json", true, ActionFile.ACTION_AX12),
		RECUPERATION_PDISTRIB("recuperation_pdistrib.json", false, ActionFile.ACTION_AX12),
		INIT("init.json", false, ActionFile.ACTION_AX12),
		PREPARATION_GDISTRIB_BLEU("preparation_gdistrib_bleu.json", false, ActionFile.ACTION_AX12),
		RECUPERATION_GDISTRIB_BLEU("recuperation_gdistrib_bleu.json", false, ActionFile.ACTION_AX12),
		RANGEMENT_BRAS_PILE_1("rangement_bras_pile_1.json", false, ActionFile.ACTION_AX12),
		RANGEMENT_BRAS_PILE_2("rangement_bras_pile_2.json", false, ActionFile.ACTION_AX12),
		RANGEMENT_BRAS_PILE_3("rangement_bras_pile_3.json", false, ActionFile.ACTION_AX12),
		PREPARATION_GDISTRIB_ROUGES("preparation_gdistrib_rouges.json", false, ActionFile.ACTION_AX12),
		RECUPERATION_GDISTRIB_ROUGES("recuperation_gdistrib_rouges.json", false, ActionFile.ACTION_AX12),
		LARGAGE_GDISTRIB_1("largage_gdistrib_1.json", false, ActionFile.ACTION_AX12),
		LARGAGE_GDISTRIB_2("largage_gdistrib_2.json", false, ActionFile.ACTION_AX12),
		LARGAGE_GDISTRIB_3("largage_gdistrib_3.json", false, ActionFile.ACTION_AX12),
		LARGAGE_GDISTRIB_4("largage_gdistrib_4.json", false, ActionFile.ACTION_AX12),
		LARGAGE_GDISTRIB_5("largage_gdistrib_5.json", false, ActionFile.ACTION_AX12),
		PREPARATION_RECUR_GOLD("preparation_recup_gold.json", false, ActionFile.ACTION_AX12),
		PREPARATION_LARGAGE_GOLD("preparation_largage_gold.json", false, ActionFile.ACTION_AX12),
		LARGAGE_GOLD("largage_gold.json", false, ActionFile.ACTION_AX12),
		LARGAGE_GDISTRIB_MIROIR_2("largage_gdistrib_2_miroir.json", false, ActionFile.ACTION_AX12),
		PREPARATION_RECUR_GOLD_MIROIR("preparation_recup_gold_miroir.json", false, ActionFile.ACTION_AX12),
		PREPARATION_LARGAGE_GOLD_MIROIR("preparation_largage_gold_miroir.json", false, ActionFile.ACTION_AX12),

		/**
		 * Actions 2020
		 */
		A2020_INIT("init.json", false, ActionFile.ACTION_AX12),
		A2020_BAISSER_BRAS_DROIT("baisser_bras_droit.json", false, ActionFile.ACTION_AX12),
		A2020_BAISSER_BRAS_GAUCHE("baisser_bras_gauche.json", false, ActionFile.ACTION_AX12),
		A2020_LEVER_BRAS_DROIT("lever_bras_droit.json", true, ActionFile.ACTION_AX12),
		A2020_LEVER_BRAS_GAUCHE("lever_bras_gauche.json", true, ActionFile.ACTION_AX12),
		A2020_PREPARER_RAMASSAGE("preparer_ramassage.json", true, ActionFile.ACTION_AX12),
		A2020_TOUT_RAMASSER("tout_ramasser.json", false, ActionFile.ACTION_AX12),
		A2020_LEVER_GOBELETS("lever_gobelets.json", false, ActionFile.ACTION_AX12),
		A2020_PREPARER_LARGAGE("preparer_largage.json", false, ActionFile.ACTION_AX12),
		A2020_LARGUER_DOIGTS_IMPAIRE("largage_impair.json", false, ActionFile.ACTION_AX12),
		A2020_LARGUER_DOIGTS_PAIRE("largage_pair.json", false, ActionFile.ACTION_AX12),
		A2020_PREPARER_PHARE("preparer_phare.json", false, ActionFile.ACTION_AX12),
		A2020_FUNNY_ACTION("sortir_drapeau.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_RANGER_BRAS_DROIT("pmi_ranger_bras_droit.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_RANGER_BRAS_GAUCHE("pmi_ranger_bras_gauche.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_SORTIR_LEVER_BRAS_DROIT("pmi_sortir_lever_bras_droit.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_SORTIR_LEVER_BRAS_GAUCHE("pmi_sortir_lever_bras_gauche.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_POSER_BRAS_DROIT("pmi_poser_bras_droit.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_POSER_BRAS_GAUCHE("pmi_poser_bras_gauche.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_ATTRAPER_BRAS_DROIT("pmi_attraper_bras_droit.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_ATTRAPER_BRAS_GAUCHE("pmi_attraper_bras_gauche.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_LACHER_BRAS_DROIT("pmi_lacher_bras_droit.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_LACHER_BRAS_GAUCHE("pmi_lacher_bras_gauche.json", false, ActionFile.ACTION_AX12),
		A2020_PMI_ALLUMER_PHARE("pmi_allumer_phare.json", false, ActionFile.ACTION_AX12),
		A2020_ARUCO_CAM("a2020.ArucoCamAction", false, ActionFile.ACTION_REFLEXIVE),
		A2020_PMI_BOUSSOLE("a2020.PmiBoussoleAction", false, ActionFile.ACTION_REFLEXIVE),
		A2020_OUVRIR_DOIGTS_1A4("ouvrir_doigts_1a4.json", false, ActionFile.ACTION_AX12),
		A2020_OUVRIR_DOIGTS_2A5("ouvrir_doigts_2a5.json", false, ActionFile.ACTION_AX12),
		A2020_OUVRIR_DOIGTS_1A5("ouvrir_doigts_1a5.json", false, ActionFile.ACTION_AX12),

		/**
		 * Actions 2022
		 */
		A2022_INIT_BIG("init_big.json", false, ActionFile.ACTION_AX12),
		A2022_INIT_SMALL("init_small.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_OUT("fenwick_out.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_IN("fenwick_in.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_RED("a2022.FenwickRed", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_GREEN("a2022.FenwickGreen", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_BLUE("a2022.FenwickBlue", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_FOUILLE_DROITE_1("a2022.FenwickFouilleDroite1", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_FOUILLE_GAUCHE_1("a2022.FenwickFouilleGauche1", false, ActionFile.ACTION_REFLEXIVE),
		A2022_PASSPASS_GET_STATUE("a2022.PasspassGetStatue", false, ActionFile.ACTION_REFLEXIVE),
		A2022_PASSPASS_PUT_FAKE_STATUE("a2022.PasspassPutFakeStatue", false, ActionFile.ACTION_REFLEXIVE),
		A2022_PASSPASS_TAKE("a2022.PasspassTake", false, ActionFile.ACTION_REFLEXIVE),
		A2022_PASSPASS_STORE("a2022.PasspassStore", false, ActionFile.ACTION_REFLEXIVE),
		A2022_PASSPASS_UNSTORE("a2022.PasspassUnstore", false, ActionFile.ACTION_REFLEXIVE),
		A2022_PASSPASS_SWITCH("a2022.PasspassSwitch", false, ActionFile.ACTION_REFLEXIVE),
		A2022_PASSPASS_PUT_RELEASE("a2022.PasspassPutRelease", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_ASCENSEUR_INIT("z", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_ASCENSEUR_IN("fenwick_ascenseur_in.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_ASCENSEUR_OUT("fenwick_ascenseur_out.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_ASCENSEUR_POMPE_SUCK("fenwick_ascenseur_pompe_suck.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_ASCENSEUR_POMPE_RELEASE("fenwick_ascenseur_pompe_release.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_BRAS_DROIT_OUT("fenwick_bras_droit_out.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_BRAS_DROIT_IN("fenwick_bras_droit_in.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_BRAS_GAUCHE_OUT("fenwick_bras_gauche_out.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_BRAS_GAUCHE_IN("fenwick_bras_gauche_in.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_ASCENSEUR_HAUTEUR_TOP("g150", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_ASCENSEUR_HAUTEUR_PILE_3("g55", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_ASCENSEUR_HAUTEUR_PILE_2("g40", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_ASCENSEUR_HAUTEUR_PILE_1("g25", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_ASCENSEUR_HAUTEUR_LACHER("g5", false, ActionFile.ACTION_SERIAL),
		A2022_PASSPASS_AX_BRAS_POSE_OUT("passpass_ax_bras_pose_out.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_BRAS_POSE_IN("passpass_ax_bras_pose_in.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_BRAS_PRISE_TAKE_UP("passpass_ax_bras_prise_take_up.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_BRAS_PRISE_TAKE_DOWN("passpass_ax_bras_prise_take_down.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_BRAS_PRISE_STORE("passpass_ax_bras_prise_store.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_BRAS_STORE_OUT("passpass_ax_bras_store_out.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_BRAS_STORE_IN("passpass_ax_bras_store_in.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_BRAS_STORE_POMPE_ON("passpass_ax_bras_store_pompe_on.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_BRAS_STORE_POMPE_OFF("passpass_ax_bras_store_pompe_off.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_AX_SWITCH_FACE("passpass_switch_face.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_SONDE_DROITE_OUT("fenwick_sonde_droite_out.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_SONDE_DROITE_IN("fenwick_sonde_droite_in.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_SONDE_GAUCHE_OUT("fenwick_sonde_gauche_out.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_SONDE_GAUCHE_IN("fenwick_sonde_gauche_in.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_FOUILLE_DROITE_4("a2022.FenwickFouilleDroite4", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_FOUILLE_GAUCHE_4("a2022.FenwickFouilleGauche4", false, ActionFile.ACTION_REFLEXIVE),
		A2022_PASSPASS_AX_SWITCH_KISS("passpass_switch_kiss.json", false, ActionFile.ACTION_AX12),
		A2022_PASSPASS_VITRINE_ALLUMER("http://192.168.0.106:8000/light_on", true, ActionFile.ACTION_HTTP),
		A2022_FENWICK_SOLO_GET_STATUETTE("a2022.FenwickSoloGetStatuette", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_SOLO_ASCENSCEUR_GET_STATUETTE("g125", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_SOLO_PUT_STATUETTE("a2022.FenwickSoloPutStatuette", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE("g185", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_SOLO_ASCENSCEUR_PUT_STATUETTE_BIS("g180", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_SOLO_PUT_FAKE("a2022.FenwickSoloPutFake", false, ActionFile.ACTION_REFLEXIVE),
		A2022_FENWICK_SOLO_ASCENSCEUR_GET_FAKE("g55", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_SOLO_ASCENSCEUR_DROP_FAKE("g80", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_SOLO_ASCENSCEUR_DROP_FAKE_BIS("g75", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_SOLO_ASCENSCEUR_GET_STATUETTE_TOP("g210", false, ActionFile.ACTION_SERIAL),
		A2022_FENWICK_SOLO_ASCENSCEUR_AX_DROP_STATUETTE("fenwick_solo_ascensceur_ax_drop_statuette.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_ASCENSEUR_OUT_FAKE("fenwick_ascenseur_out_fake.json", false, ActionFile.ACTION_AX12),
		A2022_FENWICK_IN_FAKE("fenwick_in_fake.json", false, ActionFile.ACTION_AX12),

		/**
		 * Actions 2023
		 */
		ROB_PINCE_MOBILE_ATTRAPER("rob_pince_mobile_attraper.json", false, ActionFile.ACTION_AX12),
		ROB_PINCE_MOBILE_RELACHER_SERRE("rob_pince_mobile_relacher_serre.json", false, ActionFile.ACTION_AX12),
		ROB_PINCE_MOBILE_RELACHER_LARGE("rob_pince_mobile_relacher_large.json", false, ActionFile.ACTION_AX12),
		ROB_PINCE_MOBILE_OUVRIR_XLARGE("rob_pince_mobile_ouvrir_xlarge.json", false, ActionFile.ACTION_AX12),
		ROB_ASCENSEUR_INIT("rob_ascenseur_init.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_INIT_TOUT_EN_HAUT("rob_ascenseur_init_tout_en_haut.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_NIV1("rob_ascenseur_niv1.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_NIV3("rob_ascenseur_niv3.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_SOL("rob_ascenseur_sol.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_SOL_BUTEE("rob_ascenseur_sol_butee.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_SOLMARGE("rob_ascenseur_solmarge.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_HAUT("rob_ascenseur_haut.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_DEMO("rob_test_empilement_3_gateaux.json", false, ActionFile.ACTION_LIST),
		ROB_EMPILER_PREMIER_GATEAU("rob_empiler_premier_gateau.json", false, ActionFile.ACTION_LIST),
		ROB_EMPILER_GATEAU_SUIVANT("rob_empiler_gateau_suivant.json", false, ActionFile.ACTION_LIST),
		ROB_DEPILER_TRANCHE("rob_depiler_tranche.json", false, ActionFile.ACTION_LIST),
		ROB_DEPILER_TRANCHE_X9("rob_depiler_tranche_x9.json", false, ActionFile.ACTION_LIST),
		COOKING_PRENDRE_CERISE("cooking_prendre_cerise.json", false, ActionFile.ACTION_AX12),
		COOKING_DEPOSER_CERISE("cooking_deposer_cerise.json", false, ActionFile.ACTION_AX12),
		COOKING_RANGER_BRAS("cooking_ranger_bras.json", false, ActionFile.ACTION_AX12),
		COOKING_DEMO_DEPOSE_CERISE("cooking_demo_depose_cerise.json", false, ActionFile.ACTION_LIST),
		COOKING_DEMO_CERISE("cooking_demo_depose_cerise.json", false, ActionFile.ACTION_LIST),
		COOKING_DEMO_10CERISES("cooking_demo_depose_10cerises.json", false, ActionFile.ACTION_LIST),
		COOKING_INIT("cooking_init.json", false, ActionFile.ACTION_LIST),
		PUKING_INIT("puking_init.json", false, ActionFile.ACTION_LIST),
		PUKING_FUNNY_ACTION_RESET("puking_funny_action_reset.json", false, ActionFile.ACTION_AX12),
		PUKING_FUNNY_ACTION_AX("puking_funny_action_ax.json", false, ActionFile.ACTION_AX12),
		PUKING_FUNNY_ACTION_TRIGGER("puking_funny_action_trigger.json", false, ActionFile.ACTION_LIST),
		PUKING_TURBINE_POSITION_ASPIRATION_DROIT("puking_turbine_position_aspiration_droit.json", false, ActionFile.ACTION_AX12),
		PUKING_TURBINE_POSITION_ASPIRATION_GAUCHE("puking_turbine_position_aspiration_gauche.json", false, ActionFile.ACTION_AX12),
		PUKING_TURBINE_POSITION_CENTRE("puking_turbine_position_centre.json", false, ActionFile.ACTION_AX12),
		PUKING_TURBINE_POSITION_SOUFFLER("puking_turbine_position_souffler.json", false, ActionFile.ACTION_AX12),
		PUKING_TURBINE_POSITION_RESERVOIR_AUX("puking_turbine_position_reservoir_aux.json", false, ActionFile.ACTION_AX12),
		PUKING_TURBINE_MOTEUR_INIT("10", false, ActionFile.ACTION_SERIAL),
		PUKING_TURBINE_MOTEUR_STOP("0", false, ActionFile.ACTION_SERIAL),
		PUKING_TURBINE_MOTEUR_ASPIRER_MOYEN("25", false, ActionFile.ACTION_SERIAL),
		PUKING_TURBINE_MOTEUR_ASPIRER_FORT("30", false, ActionFile.ACTION_SERIAL),
		PUKING_TURBINE_MOTEUR_SOUFFLER_MOYEN("-25", false, ActionFile.ACTION_SERIAL),
		PUKING_TURBINE_MOTEUR_SOUFFLER_FORT("-30", false, ActionFile.ACTION_SERIAL),
		PUKING_SOUFFLER_PREMIERE_BOUBOULE("puking_souffler_premiere_bouboule.json", false, ActionFile.ACTION_LIST),
		PUKING_SOUFFLER_TOUTES_LES_BOUBOULES("puking_souffler_toutes_les_bouboules.json", false, ActionFile.ACTION_LIST),
		PUKING_STOCKER_BOUBOULES("puking_stocker_bouboules.json", false, ActionFile.ACTION_LIST),
		PUKING_DESTOCKER_BOUBOULES("puking_destocker_bouboules.json", false, ActionFile.ACTION_LIST),
		PUKING_DEMO_STOCKAGE("puking_demo_stockage.json", false, ActionFile.ACTION_LIST),

		/**
		 * Actions 2024
		 */
		MAMMA_INIT("mamma_init.json", false, ActionFile.ACTION_LIST),
		MAMMA_DOIGT_SOLAIRE_OUT_DROIT("mamma_doigt_solaire_out_droit.json", false, ActionFile.ACTION_AX12),
		MAMMA_DOIGT_SOLAIRE_OUT_GAUCHE("mamma_doigt_solaire_out_gauche.json", false, ActionFile.ACTION_AX12),
		MAMMA_DOIGT_SOLAIRE_IN_DROIT("mamma_doigt_solaire_in_droit.json", false, ActionFile.ACTION_AX12),
		MAMMA_DOIGT_SOLAIRE_IN_GAUCHE("mamma_doigt_solaire_in_gauche.json", false, ActionFile.ACTION_AX12),
		MAMMA_RAMASSER_PLANTE("mamma_ramasser_plante.json", false, ActionFile.ACTION_LIST),
		MAMMA_RAMASSER_POT("mamma_ramasser_pot.json", false, ActionFile.ACTION_LIST),
		MAMMA_RAMASSER_PLANTE_NORD_LOIN("a2024.MammaRamasserPlanteNordLoin", false, ActionFile.ACTION_REFLEXIVE),
		MAMMA_DEPOSER_PLANTE("mamma_deposer_plante.json", false, ActionFile.ACTION_LIST),
		MAMMA_DEPOSER_POT("mamma_deposer_pot.json", false, ActionFile.ACTION_LIST),
		MAMMA_CHARIOT_HOME("mamma_chariot_home.json", false, ActionFile.ACTION_ASCENSEUR),
		MAMMA_CHARIOT_MIDDLE("mamma_chariot_middle.json", false, ActionFile.ACTION_ASCENSEUR),
		MAMMA_CHARIOT_ALIGNER_PLANTE("mamma_chariot_aligner_plante.json", false, ActionFile.ACTION_ASCENSEUR),
		MAMMA_CHARIOT_CHERCHER_EMMERDE("mamma_chariot_chercher_emmerde.json", false, ActionFile.ACTION_ASCENSEUR),
		MAMMA_CHARIOT_CHERCHER_GROSSE_EMMERDE("mamma_chariot_chercher_grosse_emmerde.json", false, ActionFile.ACTION_ASCENSEUR),
		MAMMA_PINCE_INIT("mamma_pince_init.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_FERMER_PLANTE("mamma_pince_fermer_plante.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_FERMER_POT("mamma_pince_fermer_pot.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_OUVRIR("mamma_pince_ouvrir.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_OUVRIR_RAMASSER_POT("mamma_pince_ouvrir_ramasser_pot.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_LEVER_HORIZONTAL("mamma_pince_lever_horizontal.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_LEVER_VERTICAL("mamma_pince_lever_vertical.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_LEVER_RAMI("mamma_pince_lever_rami.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_BAISSER_PLANTE("mamma_pince_baisser_plante.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_BAISSER_POT_ATTRAPER("mamma_pince_baisser_pot_attraper.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_BAISSER_POT_LACHER("mamma_pince_baisser_pot_lacher.json", false, ActionFile.ACTION_AX12),
		MAMMA_PINCE_RANGER("mamma_pince_ranger.json", false, ActionFile.ACTION_LIST),
		MAMMA_PLACEMENT_PLANTE_DYNAMIQUE("a2024.MammaPlacementPlanteDynamique", false, ActionFile.ACTION_REFLEXIVE),
		MAMMA_PLACEMENT_POT_DYNAMIQUE("a2024.MammaPlacementPotDynamique", false, ActionFile.ACTION_REFLEXIVE),
		MAMMA_PLACEMENT_POT_BIS_DYNAMIQUE("a2024.MammaPlacementPotBisDynamique", false, ActionFile.ACTION_REFLEXIVE),
		MAMMA_CHARIOT_TEST("mamma_chariot_test.json", false, ActionFile.ACTION_LIST),

		DELAY_250_MS("250", false, ActionFile.ACTION_DELAY),
		DELAY_500_MS("500", false, ActionFile.ACTION_DELAY),
		DELAY_1000_MS("1000", false, ActionFile.ACTION_DELAY),
		;

		public static final String ACTION_AX12 = "ax12";

		public static final String ACTION_ASCENSEUR = "ascenseur";

		public static final String ACTION_SERIAL = "serial";
		public static final String ACTION_REFLEXIVE = "reflexive";

		public static final String ACTION_LIST = "subroutine";

		public static final String ACTION_HTTP = "http";

		public static final String ACTION_DELAY = "delay";

		public final String nomFichier;
		public final boolean instantReturn;
		public final String type;

		ActionFile(String nomFichier, boolean instantReturn, String type) {
			this.nomFichier = nomFichier;
			this.instantReturn = instantReturn;
			this.type = type;
		}
	}

	public ActionFileBinder(AX12LinkSerial link, String dataDir, ActionCollection actionCollection) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		this.dataDir = new File(dataDir);
		this.actionCollection = actionCollection;
		this.ax12Link = link;
		loadFiles();
	}

	public ActionFileBinder(AX12LinkSerial link, String dataDir, ActionCollection actionCollection, Qik qikLink, SerialRxTx serialLink, AsservInterface asservInterface) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		this.dataDir = new File(dataDir);
		this.actionCollection = actionCollection;
		this.ax12Link = link;
		this.qikLink = qikLink;
		this.serialLink = serialLink;
		this.asservInterface = asservInterface;
		loadFiles();
	}
	
	protected void loadFiles() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		ax12Link.enableDtr(false);
		ax12Link.enableRts(false);

		ActionFile[] files = ActionFile.values();
		actionsList = new ActionExecutor[files.length];

		for (int i = 0; i < files.length; i++) {
			switch (files[i].type) {
				case ActionFile.ACTION_AX12:
					File f = new File(this.dataDir.getAbsolutePath() + File.separator + files[i].nomFichier);
					if (f.exists()) {
						actionsList[i] = new ActionAX12Json(ax12Link, f, files[i].instantReturn);
					}
					break;
				case ActionFile.ACTION_SERIAL:
					actionsList[i] = new ActionSerial(serialLink, files[i].nomFichier);
					break;
				case ActionFile.ACTION_ASCENSEUR:
					actionsList[i] = new ActionAscenseurJson(serialLink, this.dataDir, files[i].nomFichier);
					break;
				case ActionFile.ACTION_REFLEXIVE:
					Class<?> cl = Class.forName("actions.reflexive." + files[i].nomFichier);
					Constructor<?> cons = cl.getConstructor(ActionFileBinder.class);
					actionsList[i] = (ActionExecutor) cons.newInstance(this);
					break;
				case ActionFile.ACTION_LIST:
					actionsList[i] = new ActionList(this, this.dataDir, files[i].nomFichier);
					break;
				case ActionFile.ACTION_HTTP:
					try {
						actionsList[i] = new ActionHttp(new URL(files[i].nomFichier));
					} catch (MalformedURLException e) {
						throw new RuntimeException(e);
					}
					break;
				case ActionFile.ACTION_DELAY:
					actionsList[i] = new ActionDelay(files[i].nomFichier);
					break;
			}
		}
	}
	
	public int getActionExecutorIdForActionFile(ActionFile af) {
		return af.ordinal();
	}
	
	public ActionExecutor getActionExecutor(int id) {
		return actionsList[id];
	}

	public void stopActions() {
		this.ax12Link.disableAx12AndShutdownLink();
		ax12Link.enableDtr(false);
		ax12Link.enableRts(false);
	}

	public int funnyAction(FunnyActionDescription funnyActionDescription) {
		if (funnyActionDescription.actionId > -1) {
			ActionExecutor actionExecutor = this.getActionExecutor(funnyActionDescription.actionId);
			actionExecutor.execute();
		}
		return funnyActionDescription.score;
	}

	public void setCommunicationManager(CommunicationManager communicationManager) {
		this.communicationManager = communicationManager;
	}

	public AX12LinkSerial getAx12Link() {
		return ax12Link;
	}

	public CommunicationManager getCommunicationManager() {
		return communicationManager;
	}

	public Qik getQikLink() {
		return qikLink;
	}

	public SerialRxTx getSerialLink() {
		return serialLink;
	}

	public ActionCollection getActionCollection() {
		return actionCollection;
	}

	public AsservInterface getAsservInterface() {
		return asservInterface;
	}
}
