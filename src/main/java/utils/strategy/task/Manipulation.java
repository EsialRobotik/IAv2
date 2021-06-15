package utils.strategy.task;

import asserv.Position;
import pathfinding.table.Point;
import utils.strategy.Tache;

public class Manipulation extends Tache {

    public Manipulation(String desc, int actionId) {
        super(desc, 0, 0, Type.MANIPULATION, null, actionId, Mirror.MIRRORY);
    }

    public Manipulation(String desc, int actionId, Mirror mirror) {
        super(desc, 0, 0, Type.MANIPULATION, null, actionId, mirror);
    }

    @Override
    public void execute(Position startPoint) {
        System.out.println("action#" + this.actionId);
        this.endPoint = startPoint;
    }
}
