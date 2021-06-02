package utils.strategy.task;

import pathfinding.table.Point;
import utils.strategy.Tache;

public class GoToChain extends Tache {

    public GoToChain(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_CHAIN, -1, Mirror.MIRRORY);
    }

    public GoToChain(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_CHAIN, -1, mirror);
    }

    @Override
    public void execute(Point startPoint) {
        System.out.println("goto-chain-from#" + startPoint.x + ";" + startPoint.y);
        System.out.println("goto-chain#" + this.positionX + ";" + this.positionY);
    }
}
