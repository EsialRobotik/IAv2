package utils.strategy.task;

import asserv.Position;
import utils.strategy.Tache;

public class SetSpeed extends Tache {

    public SetSpeed(String desc, int speed) {
        super(desc, 0, speed, Type.DEPLACEMENT, SubType.SET_SPEED, -1, Mirror.MIRRORY);
    }

    public SetSpeed(String desc, int speed, Mirror mirror) {
        super(desc, 0, speed, Type.DEPLACEMENT, SubType.SET_SPEED, -1, mirror);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = startPoint;
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"speed#" + + this.dist + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
