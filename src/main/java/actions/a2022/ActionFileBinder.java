package actions.a2022;

import actions.*;
import api.ax12.AX12LinkSerial;
import manager.CommunicationManager;

import java.io.File;

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
		INIT_BIG("init_big.json", false), // 0
		INIT_SMALL("init_small.json", false), // 1

		FENWICK_OUT("fenwick_out.json", false), // 2
		FENWICK_IN("fenwick_in.json", false), // 3
		FENWICK_RED("fenwick_red.json", false), // 4
		FENWICK_GREEN("fenwick_green.json", false), // 5
		FENWICK_BLUE("fenwick_blue.json", false), // 6
		FENWICK_RIGTH_ARMS("fenwick_rigth_arms.json", false), // 7
		FENWICK_LEFT_ARMS("fenwick_left_arms.json", false), // 8
		;

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
			File f = new File(this.dataDir.getAbsolutePath() + File.separator + files[i].nomFichier);
			actionsList[i] = new ActionAX12Json(ax12Link, f, files[i].instantReturn);
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
}
