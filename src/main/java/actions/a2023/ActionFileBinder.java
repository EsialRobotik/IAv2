package actions.a2023;

import actions.*;
import api.ax12.AX12LinkSerial;
import api.communication.SerialRxTx;
import api.qik.Qik;
import manager.CommunicationManager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class ActionFileBinder implements ActionInterface {
	// TODO abstraire cette classe pour arrêté de copié / collé tout le temps !!!

	protected ActionExecutor[] actionsList;
	protected File dataDir;
	protected AX12LinkSerial ax12Link;
	protected ActionCollection actionCollection;
	protected CommunicationManager communicationManager;
	protected Qik qikLink;
	protected SerialRxTx serialLink;

	public enum ActionFile {
		/**
		 * Initialisation avant match
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

		ActionFile(String nomFichier) {
			this.nomFichier = nomFichier;
			this.instantReturn = false;
			this.type = ACTION_AX12;
		}

		ActionFile(String nomFichier, boolean instantReturn) {
			this.nomFichier = nomFichier;
			this.instantReturn = instantReturn;
			this.type = ACTION_AX12;
		}

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

	public ActionFileBinder(AX12LinkSerial link, String dataDir, ActionCollection actionCollection, Qik qikLink, SerialRxTx serialLink) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		this.dataDir = new File(dataDir);
		this.actionCollection = actionCollection;
		this.ax12Link = link;
		this.qikLink = qikLink;
		this.serialLink = serialLink;
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
					actionsList[i] = new ActionAX12Json(ax12Link, f, files[i].instantReturn);
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
	
	@Override
	public ActionExecutor getActionExecutor(int id) {
		return actionsList[id];
	}

	@Override
	public void stopActions() {
		this.ax12Link.disableAx12AndShutdownLink();
		ax12Link.enableDtr(false);
		ax12Link.enableRts(false);
	}

	@Override
	public int funnyAction(FunnyActionDescription funnyActionDescription) {
		if (funnyActionDescription.actionId > -1) {
			ActionExecutor actionExecutor = this.getActionExecutor(funnyActionDescription.actionId);
			actionExecutor.execute();
		}
		return funnyActionDescription.score;
	}

	@Override
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
}
