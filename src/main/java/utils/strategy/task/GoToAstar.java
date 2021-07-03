package utils.strategy.task;

import asserv.Position;
import pathfinding.table.Point;
import utils.strategy.Tache;

import java.util.List;

public class GoToAstar extends Tache {

    public GoToAstar(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_ASTAR, -1, Mirror.MIRRORY);
    }

    public GoToAstar(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_ASTAR, -1, mirror);
    }

    @Override
    public String execute(Position startPoint) {
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
        StringBuilder res = new StringBuilder();
        this.endPoint = startPoint;
        List<Point> path = pathFinding.getLastComputedPath();
        if (path.size() == 0) {
            System.err.println("Erreur de pathfinding");
            return "";
        }
        for (Point p : path) {
            if (this.endPoint.getX() != p.x || this.endPoint.getY() != p.y) {
                this.endPoint = new Position(p.x, p.y, this.calculateTheta(this.endPoint, p.x, p.y));
            }
            res.append("{ " +
                "\"task\":\""+this.desc+"\"," +
                "\"command\":\"goto-astar#" + + p.x + ";" + p.y + "\"," +
                "\"position\":" + this.endPoint.toJson() +
            "},");
        }
        return res.toString();
    }
}
