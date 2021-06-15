package utils.strategy.task;

import asserv.Position;
import pathfinding.table.Point;
import utils.strategy.Tache;

public class GoToBack extends Tache {

    public GoToBack(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_BACK, -1, Mirror.MIRRORY);
    }

    public GoToBack(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_BACK, -1, mirror);
    }

    @Override
    public void execute(Position startPoint) {
        System.out.println("goto-back#" + this.positionX + ";" + this.positionY);
        this.endPoint = new Position(
            this.positionX,
            this.positionY,
            (this.calculateTheta(startPoint, this.positionX, this.positionY) - Math.PI) % (2 * Math.PI)
        );
    }
}
