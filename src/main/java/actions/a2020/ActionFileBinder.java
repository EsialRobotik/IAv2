package actions.a2020;

import actions.ActionAX12Json;
import actions.ActionExecutor;
import actions.ActionInterface;
import api.ax12.AX12LinkSerial;
import api.communication.Shell;

import java.io.File;
import java.io.IOException;

public class ActionFileBinder implements ActionInterface {
	
	protected ActionExecutor[] actionsList;
	protected File dataDir;
	protected AX12LinkSerial ax12Link;
	
	public enum ActionFile {
		/**
		 * Initialisation avant match
		 */
		INIT("init.json", false), // 0

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
		PREPARER_RAMASSAGE("preparer_ramassage.json", false), // 5
		TOUT_RAMASSER("tout_ramasser.json", false), // 6
		LEVER_GOBELETS("lever_gobelets.json", false), // 7

		/**
		 * Largage
		 */
		PREPARER_LARGAGE("preparer_largage.json", false), // 8
		LARGUER_DOIGTS_IMPAIRE("larguer_doigts_impaire.json", false), // 9
		LARGUER_DOIGTS_PAIRE("larguer_doigts_paire.json", false); // 10

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
	
	public ActionFileBinder(AX12LinkSerial link, String dataDir) {
		this.dataDir = new File(dataDir);
		loadFiles(link);
	}
	
	protected void loadFiles(AX12LinkSerial ax12Link) {
		ax12Link.enableDtr(false);
		ax12Link.enableRts(false);

		ActionFile[] files = ActionFile.values();
		actionsList = new ActionExecutor[files.length + 1];

		for (int i = 0; i < files.length; i++) {
			File f = new File(this.dataDir.getAbsolutePath() + File.separator + files[i].nomFichier);
			actionsList[i] = new ActionAX12Json(ax12Link, f, files[i].instantReturn);
		}

		try {
			Shell shell = new Shell("python /home/pi/2020Aruco/testPiCameraArucoShell.py --quiet");
			shell.start();
			actionsList[files.length] = new ArucoCamAction(shell);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
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

}
