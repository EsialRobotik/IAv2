package manager;

import api.communication.HotspotSocket;
import api.log.LoggerFactory;
import org.apache.logging.log4j.Logger;
import pathfinding.PathFinding;

public class CommunicationManager {

    private PathFinding pathFinding;
    private HotspotSocket hotspotSocket;
    private Logger logger;

    public CommunicationManager(PathFinding pathFinding, String host, int port) {
        this.pathFinding = pathFinding;
        this.logger = LoggerFactory.getLogger(CommunicationManager.class);
        try {
            this.hotspotSocket = new HotspotSocket(host, port, "robot");
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.logger.error("socket creation failed");
        }
    }

    public void sendDeleteZone(String zoneId) {
        this.hotspotSocket.write("delete-zone#" + zoneId);
    }

    public void sendAddZone(String zoneId) {
        this.hotspotSocket.write("add-zone#" + zoneId);
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
                // todo ajouter les retours caméra
            }
        }
    }
}
