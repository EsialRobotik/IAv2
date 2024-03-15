package utils.strategy.task;

import asserv.Position;
import utils.strategy.Tache;

public class AddIgnoraDetectionZone extends Tache {

    public AddIgnoraDetectionZone(String desc, String itemId) {
        super(desc, 0, Type.IGNORE_DETECTION, SubType.AJOUT, itemId, Mirror.MIRRORY);
    }

    public AddIgnoraDetectionZone(String desc, String itemId, Mirror mirror) {
        super(desc, 0, Type.IGNORE_DETECTION, SubType.AJOUT, itemId, mirror);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = startPoint;
        this.pathFinding.lockElementById(this.itemId);
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"add-ignore-detection-zone#" + this.itemId + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
