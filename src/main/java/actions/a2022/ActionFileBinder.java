package actions.a2022;

import actions.*;
import api.ax12.AX12LinkSerial;
import api.communication.Serial;
import api.qik.Qik;
import manager.CommunicationManager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ActionFileBinder implements ActionInterface {
	// TODO abstraire cette classe pour arrêté de copié / collé tout le temps !!!

	protected ActionExecutor[] actionsList;
	protected File dataDir;
	protected AX12LinkSerial ax12Link;
	protected ActionCollection actionCollection;
	protected CommunicationManager communicationManager;
	protected Qik qikLink;
	protected Serial serialLink;

	public enum ActionFile {
		/**
		 * Initialisation avant match
		 */
		INIT_BIG("init_big.json"), // 0
		INIT_SMALL("init_small.json"), // 1

		FENWICK_OUT("fenwick_out.json"), // 2
		FENWICK_IN("fenwick_in.json"), // 3
		FENWICK_RED("FenwickRed", false, "reflexive"), // 4
		FENWICK_GREEN("FenwickGreen", false, "reflexive"), // 5
		FENWICK_BLUE("FenwickBlue", false, "reflexive"), // 6
		FENWICK_FOUILLE_DROITE_1("FenwickFouilleDroite1", false, "reflexive"), // 7
		FENWICK_FOUILLE_GAUCHE_1("FenwickFouilleGauche1", false, "reflexive"), // 8

		PASSPASS_GET_STATUE("PasspassGetStatue", false, "reflexive"), // 9
		PASSPASS_PUT_FAKE_STATUE("PasspassPutFakeStatue", false, "reflexive"), // 10
		PASSPASS_TAKE("PasspassTake", false, "reflexive"), // 11
		PASSPASS_STORE("PasspassStore", false, "reflexive"), // 12
		PASSPASS_UNSTORE("PasspassUnstore", false, "reflexive"), // 13
		PASSPASS_SWITCH("PasspassSwitch", false, "reflexive"), // 14
		PASSPASS_PUT_RELEASE("PasspassPutRelease", false, "reflexive"), // 16

		FENWICK_ASCENSEUR_INIT("z", false, "serial"), // 17
		FENWICK_ASCENSEUR_IN("fenwick_ascenseur_in.json"), // 18
		FENWICK_ASCENSEUR_OUT("fenwick_ascenseur_out.json"), // 19
		FENWICK_ASCENSEUR_POMPE_SUCK("fenwick_ascenseur_pompe_suck.json"), // 20
		FENWICK_ASCENSEUR_POMPE_RELEASE("fenwick_ascenseur_pompe_release.json"), // 21
		FENWICK_BRAS_DROIT_OUT("fenwick_bras_droit_out.json"), // 22
		FENWICK_BRAS_DROIT_IN("fenwick_bras_droit_in.json"), // 23
		FENWICK_BRAS_GAUCHE_OUT("fenwick_bras_gauche_out.json"), // 24
		FENWICK_BRAS_GAUCHE_IN("fenwick_bras_gauche_in.json"), // 25

		FENWICK_ASCENSEUR_HAUTEUR_TOP("g135", false, "serial"), // 26
		FENWICK_ASCENSEUR_HAUTEUR_PILE_3("g55", false, "serial"), // 27
		FENWICK_ASCENSEUR_HAUTEUR_PILE_2("g40", false, "serial"), // 28
		FENWICK_ASCENSEUR_HAUTEUR_PILE_1("g25", false, "serial"), // 29
		FENWICK_ASCENSEUR_HAUTEUR_LACHER("g5", false, "serial"), // 30

		PASSPASS_AX_BRAS_POSE_OUT("passpass_ax_bras_pose_out.json"), // 31
		PASSPASS_AX_BRAS_POSE_IN("passpass_ax_bras_pose_in.json"), // 32
		PASSPASS_AX_BRAS_PRISE_TAKE_UP("passpass_ax_bras_prise_take_up.json"), // 33
		PASSPASS_AX_BRAS_PRISE_TAKE_DOWN("passpass_ax_bras_prise_take_down.json"), // 34
		PASSPASS_AX_BRAS_PRISE_STORE("passpass_ax_bras_prise_store.json"), // 35

		PASSPASS_AX_BRAS_STORE_OUT("passpass_ax_bras_store_out.json"), // 36
		PASSPASS_AX_BRAS_STORE_IN("passpass_ax_bras_store_in.json"), // 37
		PASSPASS_AX_BRAS_STORE_POMPE_ON("passpass_ax_bras_store_pompe_on.json"), // 38
		PASSPASS_AX_BRAS_STORE_POMPE_OFF("passpass_ax_bras_store_pompe_off.json"), // 38
		PASSPASS_AX_SWITCH("passpass_switch.json"), // 39

		FENWICK_SONDE_DROITE_OUT("fenwick_sonde_droite_out.json"), // 40
		FENWICK_SONDE_DROITE_IN("fenwick_sonde_droite_in.json"), // 41
		FENWICK_SONDE_GAUCHE_OUT("fenwick_sonde_gauche_out.json"), // 42
		FENWICK_SONDE_GAUCHE_IN("fenwick_sonde_gauche_in.json"), // 43
		FENWICK_FOUILLE_DROITE_4("FenwickFouilleDroite4", false, "reflexive"), // 44
		FENWICK_FOUILLE_GAUCHE_4("FenwickFouilleGauche4", false, "reflexive"), // 45
		;

		public static final String ACTION_AX12 = "ax12";
		public static final String ACTION_SERIAL = "serial";
		public static final String ACTION_REFLEXIVE = "reflexive";

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

	public ActionFileBinder(AX12LinkSerial link, String dataDir, ActionCollection actionCollection, Qik qikLink, Serial serialLink) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
				case ActionFile.ACTION_REFLEXIVE:
					Class<?> cl = Class.forName("actions.reflexive." + files[i].nomFichier);
					Constructor<?> cons = cl.getConstructor(ActionFileBinder.class);
					actionsList[i] = (ActionExecutor) cons.newInstance(this);
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
		return 0;
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

	public Serial getSerialLink() {
		return serialLink;
	}
}
