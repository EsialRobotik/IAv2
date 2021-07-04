package actions;

import actions.ax12.ActionOrchestrator;
import actions.ax12.ActionOrchestratorHelper;
import api.ax12.AX12Link;
import manager.CommunicationManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class ActionAX12Json implements ActionExecutor {

	protected ActionOrchestrator ao;
	protected Semaphore semaphore;
	protected boolean finished;
	protected boolean forcedFinished;

	public ActionAX12Json(AX12Link ax12link, File fileToLoad) {
		try {
			ao = ActionOrchestratorHelper.unserializeFromJson(ax12link, fileToLoad);
		} catch (IOException e) {
			e.printStackTrace();
		}
		semaphore = new Semaphore(1);
		finished = false;
		forcedFinished = false;
	}

	public ActionAX12Json(AX12Link ax12link, File fileToLoad, boolean instantReturn) {
		try {
			ao = ActionOrchestratorHelper.unserializeFromJson(ax12link, fileToLoad);
		} catch (IOException e) {
			e.printStackTrace();
		}
		semaphore = new Semaphore(1);
		finished = false;
		forcedFinished = instantReturn;
	}
	
	@Override
	public void execute() {
		if (finished || !semaphore.tryAcquire()) {
			return;
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ActionAX12Json.this.ao.playAllPool();
				finished = true;
				semaphore.release();
			}
		}).start();
	}

	@Override
	public boolean finished() {
		return this.finished || this.forcedFinished;
	}

	@Override
	public void resetActionState() {
		this.finished = false;
	}

	@Override
	public void setData(String data) {
		// nothing
	}

	@Override
	public void setCommunicationManager(CommunicationManager communicationManager) {
		// nothing
	}

}
