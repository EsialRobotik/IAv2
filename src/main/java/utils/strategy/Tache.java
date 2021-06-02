package utils.strategy;

import com.google.gson.annotations.SerializedName;
import pathfinding.PathFinding;
import pathfinding.table.Point;

/**
 * Created by franc on 27/04/2018.
 */
public class Tache {

    public enum Type {
        @SerializedName("deplacement")
        DEPLACEMENT("deplacement"),
        @SerializedName("manipulation")
        MANIPULATION("manipulation"),
        @SerializedName("element")
        ELEMENT("element")
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
        AJOUT("ajout")
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
        this.itemId = "0_" + itemId;
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

    public void execute(Point startPoint) {}

    public Point getEndPoint() {
        return new Point(this.positionX, this.positionY);
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
}
