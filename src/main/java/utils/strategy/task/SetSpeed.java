package utils.strategy.task;

import asserv.Position;
import pathfinding.table.Point;
import utils.strategy.Tache;

public class SetSpeed extends Tache {

    public SetSpeed(String desc, int speed) {
        super(desc, 0, speed, Type.DEPLACEMENT, SubType.SET_SPEED, -1, Mirror.MIRRORY);
    }

    public SetSpeed(String desc, int speed, Mirror mirror) {
        super(desc, 0, speed, Type.DEPLACEMENT, SubType.SET_SPEED, -1, mirror);
    }

    @Override
    public void execute(Position startPoint) {
        System.out.println("speed#" + this.dist);
        this.endPoint = startPoint;
    }
}
