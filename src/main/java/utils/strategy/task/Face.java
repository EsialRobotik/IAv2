package utils.strategy.task;

import asserv.Position;
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
    public void execute(Position startPoint) {
        this.endPoint = startPoint;
        this.endPoint.setTheta(this.calculateTheta(startPoint, positionX, positionY));
        System.out.println("{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"face#" + this.positionX + ";" + this.positionY + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},");
    }
}
