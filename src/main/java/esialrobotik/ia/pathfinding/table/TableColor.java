package esialrobotik.ia.pathfinding.table;

/**
 * Created by icule on 28/03/17.
 */
public enum TableColor {
    COLOR_0("Bleu"),
    COLOR_3000("Vert");

    private String colorName;

    TableColor(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return colorName;
    }
}
