package pathfinding.table.shape;

import com.google.gson.JsonObject;
import pathfinding.table.Point;

/**
 * Created by icule on 29/03/17.
 */
public class Circle extends Shape{
    private Point center;
    private int radius;
    private static double epsilon = 0.00001;

    public Circle(JsonObject jsonObject){
      this.center = new Point(jsonObject.get("centre").getAsJsonObject());
      this.radius = jsonObject.get("rayon").getAsInt();
      this.id = jsonObject.get("id").getAsString();
      if (jsonObject.get("active") != null) {
          this.active = jsonObject.get("active").getAsBoolean();
      } else {
          this.active = false;
      }
    }

    public Circle(int x, int y, int radius){
        this.center = new Point(x, y);
        this.radius = radius;
    }

    public int getRadius(){
        return radius;
    }

    public Point getCenter(){
        return this.center;
    }

    public boolean[][] drawShapeEdges(int length, int width, boolean fill) {
        boolean[][] board = this.getEmptyBoard(length * 3, width * 3);

        //We divide the circle in 1k part and compute point each time.
        // We work with the original millimetric table and will convert in cm when drawing
        final double radSplit = Math.PI / 1000.;
        double currentValue = 0;
        int shiftedXCenter = center.getX() + length * 10;
        int shiftedYCenter = center.getY() + width * 10;
        while(currentValue < (Math.PI* 2)) {
            double xFloat = (shiftedXCenter + radius * Math.cos(currentValue)) / 10.;
            double yFloat = (shiftedYCenter + radius * Math.sin(currentValue)) / 10.;

            final int x = (int)(xFloat);
            final int y = (int)(yFloat);
            if((xFloat - x) > epsilon && (yFloat -y) > epsilon) {
                board[x][y] = true;
            }
            currentValue += radSplit;
        }
        if (fill) {
            ShapeFiller shapeFiller = new ShapeFiller(board);
            shapeFiller.fillBoard();
        }

        return board;
    }
}
