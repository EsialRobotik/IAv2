package esialrobotik.ia.utils.strategy.task;

import esialrobotik.ia.asserv.Position;
import esialrobotik.ia.utils.strategy.Tache;

public class Go extends Tache {

    public Go(String desc, int dist) {
        super(desc, 0, dist, Type.DEPLACEMENT, SubType.GO, -1, Mirror.MIRRORY);
    }

    public Go(String desc, int dist, Mirror mirror) {
        super(desc, 0, dist, Type.DEPLACEMENT, SubType.GO, -1, mirror);
    }

    public Go(String desc, int dist, int timeout) {
        super(desc, 0, dist, Type.DEPLACEMENT, SubType.GO, -1, Mirror.MIRRORY, timeout);
    }

    public Go(String desc, int dist, Mirror mirror, int timeout) {
        super(desc, 0, dist, Type.DEPLACEMENT, SubType.GO, -1, mirror, timeout);
    }

    @Override
    public String execute(Position startPoint) {
        int newX = startPoint.getX() + (int) (this.dist * Math.cos(startPoint.getTheta()));
        int newY = startPoint.getY() + (int) (this.dist * Math.sin(startPoint.getTheta()));
        this.endPoint = new Position(newX, newY, startPoint.getTheta());
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"go#" + this.dist + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
