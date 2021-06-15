package utils.strategy.task;

import asserv.Position;
import pathfinding.table.Point;
import utils.strategy.Tache;

public class AddZone extends Tache {

    public AddZone(String desc, String itemId) {
        super(desc, 0, Type.ELEMENT, SubType.AJOUT, itemId, Mirror.MIRRORY);
    }

    public AddZone(String desc, String itemId, Mirror mirror) {
        super(desc, 0, Type.ELEMENT, SubType.AJOUT, itemId, mirror);
    }

    @Override
    public void execute(Position startPoint) {
        this.endPoint = startPoint;
        this.pathFinding.lockElementById(this.itemId);
        System.out.println("{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"add-zone#" + this.itemId + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},");
    }
}
