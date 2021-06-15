package utils.strategy.task;

import asserv.Position;
import pathfinding.table.Point;
import utils.strategy.Tache;

public class GoToAstar extends Tache {

    public GoToAstar(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_ASTAR, -1, Mirror.MIRRORY);
    }

    public GoToAstar(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_ASTAR, -1, mirror);
    }

    @Override
    public void execute(Position startPoint) {
        pathFinding.computePath(
                new Point(startPoint),
                new Point(this.positionX, this.positionY)
        );
        while (!pathFinding.isComputationEnded()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.endPoint = startPoint;
        for (Point p : pathFinding.getLastComputedPath()) {
            this.endPoint = new Position(p.x, p.y, this.calculateTheta(this.endPoint, p.x, p.y));
            System.out.println("goto-astar#" + p.x + ";" + p.y);
            System.out.println(this.endPoint);
        }
    }
}
