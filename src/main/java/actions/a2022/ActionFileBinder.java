package actions.a2022;

import actions.*;
import api.ax12.AX12LinkSerial;
import api.communication.Serial;
import api.qik.Qik;
import manager.CommunicationManager;

import java.io.File;

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
		FENWICK_RIGTH_ARMS("FenwickRightArms", false, "reflexive"), // 7
		FENWICK_LEFT_ARMS("FenwickLeftArms", false, "reflexive"), // 8

		PASSPASS_GET_STATUE("PasspassGetStatue", false, "reflexive"), // 9
		PASSPASS_PUT_FAKE("PasspassPutFake", false, "reflexive"), // 10
		PASSPASS_TAKE("PasspassTake", false, "reflexive"), // 11
		PASSPASS_STORE("PasspassStore", false, "reflexive"), // 12
		PASSPASS_UNSTORE("PasspassUnstore", false, "reflexive"), // 13
		PASSPASS_SWITCH("PasspassSwitch", false, "reflexive"), // 13
		PASSPASS_PUT("PasspassPut", false, "reflexive"), // 14
		PASSPASS_PUT_RELEASE("PasspassPutRelease", false, "reflexive"), // 15

		FENWICK_ASCENSEUR_INIT("z", false, "serial"), // 16
//		FENWICK_ASCENSEUR_HAUTEUR_TOP("g120", false, "serial"), // 17
//		FENWICK_ASCENSEUR_HAUTEUR_PILE_3("g70", false, "serial"), // 18
//		FENWICK_ASCENSEUR_HAUTEUR_PILE_2("g55", false, "serial"), // 19
//		FENWICK_ASCENSEUR_HAUTEUR_PILE_1("g40", false, "serial"), // 20
		FENWICK_ASCENSEUR_IN("fenwick_ascenseur_in.jon"), // 17
		FENWICK_ASCENSEUR_OUT("fenwick_ascenseur_out.jon"), // 18
		FENWICK_ASCENSEUR_POMPE("fenwick_ascenseur_pompe.jon"), // 19
		FENWICK_BRAS_DROIT_OUT("fenwick_bras_droit_out.jon"), // 20
		FENWICK_BRAS_DROIT_IN("fenwick_bras_droit_in.jon"), // 21
		FENWICK_BRAS_GAUCHE_OUT("fenwick_bras_gauche_out.jon"), // 22
		FENWICK_BRAS_GAUCHE_IN("fenwick_bras_gauche_in.jon"), // 23

		PASSPASS_AX_GET_STATUE("passpass_get_statue.json"), // 9
		PASSPASS_AX_PUT_FAKE("passpass_put_fake.json"), // 10
		PASSPASS_AX_TAKE("passpass_take.json"), // 11
		PASSPASS_AX_STORE("passpass_store.json"), // 12
		PASSPASS_AX_UNSTORE("passpass_unstore.json"), // 13
		PASSPASS_AX_SWITCH("passpass_switch.json"), // 13
		PASSPASS_AX_PUT("passpass_put.json"), // 14
		PASSPASS_AX_PUT_RELEASE("passpass_put_release.json"), // 15
		;

		public static final String ACTION_AX12 = "ax12";
		public static final String ACTION_QIK = "qik";
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

	public ActionFileBinder(AX12LinkSerial link, String dataDir, ActionCollection actionCollection) {
		this.dataDir = new File(dataDir);
		this.actionCollection = actionCollection;
		this.ax12Link = link;
		loadFiles();
	}

	public ActionFileBinder(AX12LinkSerial link, String dataDir, ActionCollection actionCollection, Qik qikLink, Serial serialLink) {
		this.dataDir = new File(dataDir);
		this.actionCollection = actionCollection;
		this.ax12Link = link;
		this.qikLink = qikLink;
		this.serialLink = serialLink;
		loadFiles();
	}
	
	protected void loadFiles() {
		ax12Link.enableDtr(false);
		ax12Link.enableRts(false);

		ActionFile[] files = ActionFile.values();
		actionsList = new ActionExecutor[files.length];

		for (int i = 0; i < files.length; i++) {
			if (files[i].type.equals(ActionFile.ACTION_AX12)) {
				File f = new File(this.dataDir.getAbsolutePath() + File.separator + files[i].nomFichier);
				actionsList[i] = new ActionAX12Json(ax12Link, f, files[i].instantReturn);
			} else  {
				// TODO
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
