package pathfinding;

import pathfinding.table.Point;
import pathfinding.table.astar.Astar;
import pathfinding.table.astar.LineSimplificator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by icule on 19/05/17.
 */
public class PathFinding {
    //TODO add an interface to allow to change the path finding algorithm
    private Astar astar;

    private boolean computationStart;
    private boolean computationEnded;
    private List<Point> computedPath;

    public PathFinding(Astar astar) {
        this.astar = astar;
        computationEnded = true;
        computationStart = false;
    }

    public void computePath(final Point start, final Point end) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                //NOTE the astar work at a 1/10 resolution
                Point rectifiedStart = new Point(start.getX() / 10, start.getY() / 10);
                Point rectifiedEnd = new Point(end.getX() / 10, end.getY() / 10);
                Stack<Point> path = astar.getChemin(rectifiedStart, rectifiedEnd);
                List<Point> temp = LineSimplificator.getSimpleLines(path);
                Collections.reverse(temp);
                computedPath = new ArrayList<>();
                for(Point p : temp) {
                    computedPath.add(new Point(p.getX() * 10, p.getY() * 10));
                }
                computationEnded = true;
                computationStart = false;
            }
        });
        computationStart = true;
        computationEnded = false;
        computedPath = null;
        t.start();
    }

    public boolean isComputationStart() {
        return this.computationStart;
    }

    public boolean isComputationEnded() {
        return this.computationEnded;
    }

    public List<Point> getLastComputedPath() {
        return this.computedPath;
    }

    public void setComputedPath(List<Point> list) {
        this.computedPath = list;
    }
}
