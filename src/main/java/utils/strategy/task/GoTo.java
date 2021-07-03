package utils.strategy.task;

import asserv.Position;
import utils.strategy.Tache;

public class GoTo extends Tache {

    public GoTo(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO, -1, Mirror.MIRRORY);
    }

    public GoTo(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO, -1, mirror);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = new Position(this.positionX, this.positionY, this.calculateTheta(startPoint, this.positionX, this.positionY));
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"goto#" + this.positionX + ";" + this.positionY + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
