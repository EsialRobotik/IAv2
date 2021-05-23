package utils.strategy.task;

import utils.strategy.Tache;

public class GoToAstar extends Tache {

    public GoToAstar(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_ASTAR, -1, Mirror.MIRRORY);
    }

    public GoToAstar(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_ASTAR, -1, mirror);
    }
}
