package manager;

import actions.ActionExecutor;
import actions.ActionSupervisor;
import api.communication.HotspotSocket;
import api.log.LoggerFactory;
import org.apache.logging.log4j.Logger;
import pathfinding.PathFinding;

import java.net.URISyntaxException;

public class CommunicationManager {

    private PathFinding pathFinding;
    private ActionSupervisor actionSupervisor;
    private HotspotSocket hotspotSocket;
    private Logger logger;

    public CommunicationManager(PathFinding pathFinding, ActionSupervisor actionSupervisor, String host, int port) {
        this.pathFinding = pathFinding;
        this.actionSupervisor = actionSupervisor;
        this.logger = LoggerFactory.getLogger(CommunicationManager.class);
        try {
            this.hotspotSocket = new HotspotSocket(host, port, "robot");
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            this.logger.error("socket creation failed");
        }
    }

    public void sendDeleteZone(String zoneId) {
        this.hotspotSocket.send("delete-zone#" + zoneId);
    }

    public void sendAddZone(String zoneId) {
        this.hotspotSocket.send("add-zone#" + zoneId);
    }

    public void sendActionData(int actionId, String data) {
        this.hotspotSocket.send("action-data#" + actionId + "#" + data);
    }

    public void readFromServer() {
        String data = this.hotspotSocket.read();
        if (data != null) {
            String[] dataSplit = data.split("#");
            switch (dataSplit[0]) {
                case "delete-zone":
                    this.pathFinding.liberateElementById(dataSplit[1]);
                    break;
                case "add-zone":
                    this.pathFinding.lockElementById(dataSplit[1]);
                    break;
                case "action-data":
                    ActionExecutor actionExecutor = this.actionSupervisor.getActionExecutor(Integer.parseInt(dataSplit[1]));
                    actionExecutor.setData(dataSplit[2]);
                    this.actionSupervisor.executeCommand(Integer.parseInt(dataSplit[1]));
                    break;
            }
        }
    }
}
