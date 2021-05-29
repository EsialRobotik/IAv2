package utils.strategy.task;

import pathfinding.table.Point;
import utils.strategy.Tache;

public class Go extends Tache {

    public Go(String desc, int dist) {
        super(desc, 0, dist, Type.DEPLACEMENT, SubType.GO, -1, Mirror.MIRRORY);
    }

    public Go(String desc, int dist, Mirror mirror) {
        super(desc, 0, dist, Type.DEPLACEMENT, SubType.GO, -1, mirror);
    }

    public Go(String desc, int dist, int timeout) {
        super(desc, 0, dist, Type.DEPLACEMENT, SubType.GO, -1, Mirror.MIRRORY, timeout);
    }

    public Go(String desc, int dist, Mirror mirror, int timeout) {
        super(desc, 0, dist, Type.DEPLACEMENT, SubType.GO, -1, mirror, timeout);
    }

    @Override
    public void execute(Point startPoint) {
        System.out.println("go-from#" + startPoint.x + ";" + startPoint.y);
        System.out.println("go#" + this.dist);
    }
}