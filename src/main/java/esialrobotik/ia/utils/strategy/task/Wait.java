package esialrobotik.ia.utils.strategy.task;

import esialrobotik.ia.asserv.Position;
import esialrobotik.ia.utils.strategy.Tache;

public class Wait extends Tache {

    public Wait(String desc, int msCount) {
        super(desc, 0, 0, Type.DEPLACEMENT, SubType.WAIT, -1, Mirror.MIRRORY, msCount);
    }

    public Wait(String desc, int msCount, Mirror mirror) {
        super(desc, 0, 0, Type.DEPLACEMENT, SubType.WAIT, -1, mirror, msCount);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = startPoint;
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"wait#" + + this.dist + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
