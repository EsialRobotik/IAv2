package utils.strategy;

import asserv.Position;
import com.google.gson.annotations.SerializedName;
import pathfinding.PathFinding;

/**
 * Created by franc on 27/04/2018.
 */
public class Tache implements Cloneable {

    public enum Type {
        @SerializedName("deplacement")
        DEPLACEMENT("deplacement"),
        @SerializedName("manipulation")
        MANIPULATION("manipulation"),
        @SerializedName("element")
        ELEMENT("element"),
        @SerializedName("ignore_detection")
        IGNORE_DETECTION("ignore_detection")
        ;

        private final String text;

        /**
         * @param text
         */
        Type(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }

    public enum SubType {
        @SerializedName("go")
        GO("go"),
        @SerializedName("goto")
        GOTO("goto"),
        @SerializedName("goto_chain")
        GOTO_CHAIN("goto_chain"),
        @SerializedName("face")
        FACE("face"),
        @SerializedName("goto_back")
        GOTO_BACK("goto_back"),
        @SerializedName("goto_astar")
        GOTO_ASTAR("goto_astar"),
        @SerializedName("set_speed")
        SET_SPEED("set_speed"),
        @SerializedName("suppression")
        SUPPRESSION("suppression"),
        @SerializedName("ajout")
        AJOUT("ajout"),
        @SerializedName("wait_chrono")
        WAIT_CHRONO("wait_chrono"),
        @SerializedName("wait")
        WAIT("wait")
        ;

        private final String text;

        /**
         * @param text
         */
        SubType(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }
    }

    public enum Mirror {
        NONE,
        MIRRORY,
        SPECIFIC
    }

    public String desc;
    public int id;
    public int positionX;
    public int positionY;
    public int dist;
    public Type type;
    public SubType subtype;
    public String itemId;
    public int actionId;
    public Mirror mirror;
    public int timeout = -1;

    public PathFinding pathFinding;
    public Position endPoint;

    public Tache(String desc, int id, int positionX, int positionY, Type type, SubType subtype, int actionId, Mirror mirror) {
        this.desc = desc;
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = type;
        this.subtype = subtype;
        this.actionId = actionId;
        this.mirror = mirror;
    }

    public Tache(String desc, int id, int dist, Type type, SubType subtype, int actionId, Mirror mirror) {
        this.desc = desc;
        this.id = id;
        this.dist = dist;
        this.type = type;
        this.subtype = subtype;
        this.actionId = actionId;
        this.mirror = mirror;
    }

    public Tache(String desc, int id, int dist, Type type, SubType subtype, int actionId, Mirror mirror, int timeout) {
        this.desc = desc;
        this.id = id;
        this.dist = dist;
        this.type = type;
        this.subtype = subtype;
        this.actionId = actionId;
        this.mirror = mirror;
        this.timeout = timeout;
    }

    public Tache(String desc, int id, Type type, SubType subtype, String itemId, Mirror mirror) {
        this.desc = desc;
        this.id = id;
        this.type = type;
        this.subtype = subtype;
        if (mirror.equals(Mirror.SPECIFIC)) {
            this.itemId = itemId;
        } else  {
            this.itemId = "0_" + itemId;
        }
        this.mirror = mirror;
    }

    public Tache() {
    }

    public Tache(Tache t) {
        this.desc = t.desc;
        this.id = t.id;
        this.positionX = t.positionX;
        this.positionY = t.positionY;
        this.dist = t.dist;
        this.type = t.type;
        this.subtype = t.subtype;
        this.actionId = t.actionId;
        this.itemId = t.itemId;
        this.mirror = t.mirror;
        this.timeout = t.timeout;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SubType getSubtype() {
        return subtype;
    }

    public void setSubtype(SubType subtype) {
        this.subtype = subtype;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public Mirror getMirror() {
        return mirror;
    }

    public void setMirror(Mirror mirror) {
        this.mirror = mirror;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String execute(Position startPoint) {
        return "";
    }

    public Position getEndPoint() {
        return this.endPoint;
    }

    public double calculateTheta(Position currentPosition, int finalX, int finalY) {
        if (finalY == currentPosition.getY()) {
            return finalX > currentPosition.getX() ? 0 : Math.PI;
        } else if (finalX == currentPosition.getX()) {
            return finalY > currentPosition.getY() ? Math.PI / 2 : -Math.PI / 2;
        } else {
            double adjust = 0;
            if (finalY > currentPosition.getY() && finalX > currentPosition.getX()) {
                adjust = 0;
            } else if (finalY > currentPosition.getY() || finalX < currentPosition.getX()) {
                adjust = Math.PI;
            }
            return
                adjust +
                Math.atan(((double)(finalY - currentPosition.getY())) / ((double)(finalX - currentPosition.getX())));
        }
    }

    @Override
    public String toString() {
        return "\n\t\t\t\tTache{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", dist=" + dist +
                ", type=" + type +
                ", subtype=" + subtype +
                ", actionId=" + actionId +
                '}';
    }

    @Override
    public Tache clone() throws CloneNotSupportedException {
        return (Tache)super.clone();
    }
}
