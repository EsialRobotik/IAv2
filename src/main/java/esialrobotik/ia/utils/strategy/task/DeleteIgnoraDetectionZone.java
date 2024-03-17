package esialrobotik.ia.utils.strategy.task;

import esialrobotik.ia.asserv.Position;
import esialrobotik.ia.utils.strategy.Tache;

public class DeleteIgnoraDetectionZone extends Tache {

    public DeleteIgnoraDetectionZone(String desc, String itemId) {
        super(desc, 0, Type.IGNORE_DETECTION, SubType.AJOUT, itemId, Mirror.MIRRORY);
    }

    public DeleteIgnoraDetectionZone(String desc, String itemId, Mirror mirror) {
        super(desc, 0, Type.IGNORE_DETECTION, SubType.AJOUT, itemId, mirror);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = startPoint;
        this.pathFinding.lockElementById(this.itemId);
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"delete-ignore-detection-zone#" + this.itemId + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
