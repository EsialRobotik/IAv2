package esialrobotik.ia.manager;

import esialrobotik.ia.actions.ActionExecutor;
import esialrobotik.ia.actions.ActionSupervisor;
import esialrobotik.ia.api.communication.HotspotSocket;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.pathfinding.PathFinding;
import org.slf4j.Logger;

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
        this.sendHotspotSocketData("delete-zone#" + zoneId);
    }

    public void sendAddZone(String zoneId) {
        this.sendHotspotSocketData("add-zone#" + zoneId);
    }

    public void sendActionData(int actionId, String data) {
        this.sendHotspotSocketData("action-data#" + actionId + "#" + data);
    }

    private void sendHotspotSocketData(String data) {
        try {
            this.logger.info("Send socket date : " + data);
            this.hotspotSocket.send(data);
        } catch (Exception e) {
            this.logger.error("Socket error");
            this.logger.error(e.getMessage());
        }
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
