package utils.strategy.task;

import asserv.Position;
import utils.strategy.Tache;

public class DeleteZone extends Tache {

    public DeleteZone(String desc, String itemId) {
        super(desc, 0, Type.ELEMENT, SubType.SUPPRESSION, itemId, Mirror.MIRRORY);
    }

    public DeleteZone(String desc, String itemId, Mirror mirror) {
        super(desc, 0, Type.ELEMENT, SubType.SUPPRESSION, itemId, mirror);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = startPoint;
        this.pathFinding.liberateElementById(this.itemId);
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"delete-zone#" + this.itemId + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
