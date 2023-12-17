package esialrobotik.ia.utils.strategy.task;

import esialrobotik.ia.asserv.Position;
import esialrobotik.ia.utils.strategy.Tache;

public class AddZone extends Tache {

    public AddZone(String desc, String itemId) {
        super(desc, 0, Type.ELEMENT, SubType.AJOUT, itemId, Mirror.MIRRORY);
    }

    public AddZone(String desc, String itemId, Mirror mirror) {
        super(desc, 0, Type.ELEMENT, SubType.AJOUT, itemId, mirror);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = startPoint;
        this.pathFinding.lockElementById(this.itemId);
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"add-zone#" + this.itemId + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
