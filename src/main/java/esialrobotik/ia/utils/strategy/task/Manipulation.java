package esialrobotik.ia.utils.strategy.task;

import esialrobotik.ia.asserv.Position;
import esialrobotik.ia.utils.strategy.Tache;

public class Manipulation extends Tache {

    public Manipulation(String desc, int actionId) {
        super(desc, 0, 0, Type.MANIPULATION, null, actionId, Mirror.MIRRORY);
    }

    public Manipulation(String desc, int actionId, Mirror mirror) {
        super(desc, 0, 0, Type.MANIPULATION, null, actionId, mirror);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = startPoint;
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"action#" + + this.actionId + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}