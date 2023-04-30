package utils.strategy.task;

import asserv.Position;
import utils.strategy.Tache;

public class WaitChrono extends Tache {

    public WaitChrono(String desc, int chrono) {
        super(desc, 0, 0, Type.DEPLACEMENT, SubType.WAIT_CHRONO, -1, Mirror.MIRRORY, chrono);
    }

    public WaitChrono(String desc, int chrono, Mirror mirror) {
        super(desc, 0, 0, Type.DEPLACEMENT, SubType.WAIT_CHRONO, -1, mirror, chrono);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = startPoint;
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"wait_chrono#" + + this.dist + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
