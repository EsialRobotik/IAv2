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
		INIT_ROB_PINCE("rob_pinces_init.json", true, ActionFile.ACTION_AX12),

		INIT_ROB_ASCENSEUR("rob_ascenseur_init.json", false, ActionFile.ACTION_ASCENSEUR),

		ROB_PINCE_MOBILE_RELACHER("rob_pince_mobile_relacher.json", false, ActionFile.ACTION_AX12),
		ROB_PINCE_MOBILE_ATTRAPER("rob_pince_mobile_attraper.json", false, ActionFile.ACTION_AX12),
		ROB_PINCE_FIXE_RELACHER("rob_pince_fixe_relacher.json", false, ActionFile.ACTION_AX12),
		ROB_PINCE_FIXE_ATTRAPER("rob_pince_fixe_attraper.json", false, ActionFile.ACTION_AX12),
		ROB_ASCENSEUR_SOL("rob_ascenseur_sol.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_PREEMPILEMENT_3PARTS("rob_ascenseur_preempilement_3parts.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_PREEMPILEMENT_2PARTS("rob_ascenseur_preempilement_2parts.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_PREEMPILEMENT_1PARTS("rob_ascenseur_preempilement_1parts.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_EMPILEMENT_3PARTS("rob_ascenseur_empilement_3parts.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_EMPILEMENT_2PARTS("rob_ascenseur_empilement_2parts.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_EMPILEMENT_1PARTS("rob_ascenseur_empilement_1parts.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_PREDEPILMEENT("rob_ascenseur_predepilmeent.json", false, ActionFile.ACTION_ASCENSEUR),
		ROB_ASCENSEUR_DEPILEMENT_1("rob_ascenseur_depilement_1.json", false, ActionFile.ACTION_ASCENSEUR),
		EMPILER_PREMIER_GATEAU("rob_empiler_premier_gateau.json", false, ActionFile.ACTION_LIST),
		EMPILER_GATEAU_SUIVANT("rob_empiler_gateau_suivant.json", false, ActionFile.ACTION_LIST),
		;

		public static final String ACTION_AX12 = "ax12";

		public static final String ACTION_ASCENSEUR = "ascenseur";

		public static final String ACTION_SERIAL = "serial";
		public static final String ACTION_REFLEXIVE = "reflexive";

		public static final String ACTION_LIST = "subroutine";

		public static final String ACTION_HTTP = "http";

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
}
