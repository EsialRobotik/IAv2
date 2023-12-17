package esialrobotik.ia.utils.strategy.task;

import esialrobotik.ia.asserv.Position;
import esialrobotik.ia.utils.strategy.Tache;

public class GoToChain extends Tache {

    public GoToChain(String desc, int positionX, int positionY) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_CHAIN, -1, Mirror.MIRRORY);
    }

    public GoToChain(String desc, int positionX, int positionY, Mirror mirror) {
        super(desc, 0, positionX, positionY, Type.DEPLACEMENT, SubType.GOTO_CHAIN, -1, mirror);
    }

    @Override
    public String execute(Position startPoint) {
        this.endPoint = new Position(this.positionX, this.positionY, this.calculateTheta(startPoint, this.positionX, this.positionY));
        return "{ " +
            "\"task\":\""+this.desc+"\"," +
            "\"command\":\"goto-chain#" + + this.positionX + ";" + this.positionY + "\"," +
            "\"position\":" + this.endPoint.toJson() +
        "},";
    }
}
