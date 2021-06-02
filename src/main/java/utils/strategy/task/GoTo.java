package utils.strategy.task;

import pathfinding.table.Point;
import utils.strategy.Tache;

public class GoTo extends Tache {

    public GoTo(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO, -1, Mirror.MIRRORY);
    }

    public GoTo(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO, -1, mirror);
    }

    @Override
    public void execute(Point startPoint) {
        System.out.println("goto-from#" + startPoint.x + ";" + startPoint.y);
        System.out.println("goto#" + this.positionX + ";" + this.positionY);
    }
}
