package utils.strategy.task;

import pathfinding.table.Point;
import utils.strategy.Tache;

public class Face extends Tache {

    public Face(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.FACE, -1, Mirror.MIRRORY);
    }

    public Face(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.FACE, -1, mirror);
    }

    @Override
    public void execute(Point startPoint) {
        System.out.println("face#" + this.positionX + ";" + this.positionY);
    }
}
