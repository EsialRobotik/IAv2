package actions.a2020;

import actions.*;
import api.ax12.AX12LinkSerial;
import api.communication.Shell;
import manager.CommunicationManager;

import java.io.File;
import java.io.IOException;

public class ActionFileBinder implements ActionInterface {
	
	protected ActionExecutor[] actionsList;
	protected File dataDir;
	protected AX12LinkSerial ax12Link;
	protected ActionCollection actionCollection;
	protected CommunicationManager communicationManager;
	
	public enum ActionFile {
		/**
		 * Initialisation avant match
		 */
		INIT("init.json", true), // 0

		/**
		 * Gestion des bras
		 */
		BAISSER_BRAS_DROIT("baisser_bras_droit.json", false), // 1
		BAISSER_BRAS_GAUCHE("baisser_bras_gauche.json", false), // 2
		LEVER_BRAS_DROIT("lever_bras_droit.json", true), // 3
		LEVER_BRAS_GAUCHE("lever_bras_gauche.json", true), // 4

		/**
		 * Rammassage des bouées
		 */
		PREPARER_RAMASSAGE("preparer_ramassage.json", true), // 5
		TOUT_RAMASSER("tout_ramasser.json", false), // 6
		LEVER_GOBELETS("lever_gobelets.json", false), // 7

		/**
		 * Largage
		 */
		PREPARER_LARGAGE("preparer_largage.json", false), // 8
		LARGUER_DOIGTS_IMPAIRE("largage_impair.json", false), // 9
		LARGUER_DOIGTS_PAIRE("largage_pair.json", false), // 10

		/**
		 * Phare
		 */
		PREPARER_PHARE("preparer_phare.json", false), // 11

		FUNNY_ACTION("sortir_drapeau.json"), // 12

		/**
		 * PMI Init
		 */
		PMI_RANGER_BRAS_DROIT("pmi_ranger_bras_droit.json"), // 13
		PMI_RANGER_BRAS_GAUCHE("pmi_ranger_bras_gauche.json"), // 14

		/**
		 * PMI Ramassage de bouée
		 */
		PMI_SORTIR_LEVER_BRAS_DROIT("pmi_sortir_lever_bras_droit.json"), // 15
		PMI_SORTIR_LEVER_BRAS_GAUCHE("pmi_sortir_lever_bras_gauche.json"), // 16
		PMI_POSER_BRAS_DROIT("pmi_poser_bras_droit.json"), // 17
		PMI_POSER_BRAS_GAUCHE("pmi_poser_bras_gauche.json"), // 18
		PMI_ATTRAPER_BRAS_DROIT("pmi_attraper_bras_droit.json"), // 19
		PMI_ATTRAPER_BRAS_GAUCHE("pmi_attraper_bras_gauche.json"), // 20
		PMI_LACHER_BRAS_DROIT("pmi_lacher_bras_droit.json"), // 21
		PMI_LACHER_BRAS_GAUCHE("pmi_lacher_bras_gauche.json"), // 22
		PMI_ALLUMER_PHARE("pmi_allumer_phare.json"), // 23

		ARUCO_CAM(""),
		PMI_BOUSSOLE("");

		public final String nomFichier;
		public final boolean instantReturn;

		private ActionFile(String nomFichier) {
			this.nomFichier = nomFichier;
			this.instantReturn = false;
		}

		private ActionFile(String nomFichier, boolean instantReturn) {
			this.nomFichier = nomFichier;
			this.instantReturn = instantReturn;
		}
	}
	
	public ActionFileBinder(AX12LinkSerial link, String dataDir, ActionCollection actionCollection) {
		this.dataDir = new File(dataDir);
		this.actionCollection = actionCollection;
		this.ax12Link = link;
		loadFiles();
	}
	
	protected void loadFiles() {
		ax12Link.enableDtr(false);
		ax12Link.enableRts(false);

		ActionFile[] files = ActionFile.values();
		actionsList = new ActionExecutor[files.length];

		for (int i = 0; i < files.length; i++) {
			if (i == ActionFile.ARUCO_CAM.ordinal()) {
				try {
					Shell shell = new Shell("python /home/pi/2020Aruco/testPiCameraArucoShell.py --quiet");
					shell.start();
					actionsList[i] = new ArucoCamAction(shell, this.actionCollection);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (i == ActionFile.PMI_BOUSSOLE.ordinal()) {
				actionsList[i] = new PmiBoussoleAction(this.actionCollection);
			} else {
				File f = new File(this.dataDir.getAbsolutePath() + File.separator + files[i].nomFichier);
				actionsList[i] = new ActionAX12Json(ax12Link, f, files[i].instantReturn);
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
		actionsList[ActionFile.ARUCO_CAM.ordinal()].setCommunicationManager(communicationManager);
	}
}
