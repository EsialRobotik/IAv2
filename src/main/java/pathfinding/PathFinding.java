package pathfinding;

import api.log.LoggerFactory;
import org.apache.logging.log4j.Level;
import pathfinding.table.Point;
import pathfinding.table.Table;
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
                List<Point> simplePath = LineSimplificator.getSimpleLines(path);
                Collections.reverse(simplePath);
                computedPath = new ArrayList<>();
                for(Point p : simplePath) {
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

    public static void main(String[] args) throws Exception {
        LoggerFactory.init(Level.INFO);
        Table table = new Table();
        ArrayList<String> zoneToSkip = new ArrayList<>();
        zoneToSkip.add("start0");
        table.loadJsonFromFile("table.json", zoneToSkip);

        Astar astar = new Astar(table);
        for (List<Point> points : table.getElementsList().values()) {
            for (Point p : points) {
                astar.setTemporaryAccessible(p.x, p.y, false);
            }
        }
        PathFinding pathFinding = new PathFinding(astar);

        pathFinding.computePath(
            new Point(800, 200),
            new Point(540, 700)
        );
        while (!pathFinding.isComputationEnded()) {
            Thread.sleep(500);
        }
        System.out.println("Path");
        System.out.print("[");
        for (Point p : pathFinding.getLastComputedPath()) {
            System.out.print("["+p.x+","+p.y+"],");
        }
        System.out.println("]");

        pathFinding.computePath(
                new Point(540, 700),
                new Point(250, 450)
        );
        while (!pathFinding.isComputationEnded()) {
            Thread.sleep(500);
        }
        System.out.println("Path");
        System.out.print("[");
        for (Point p : pathFinding.getLastComputedPath()) {
            System.out.print("["+p.x+","+p.y+"],");
        }
        System.out.println("]");

        for (Point p : table.getElementsList().get("0_bouee3")) {
            astar.setTemporaryAccessible(p.x, p.y, true);
        }

        pathFinding.computePath(
                new Point(250, 450),
                new Point(800, 500)
        );
        while (!pathFinding.isComputationEnded()) {
            Thread.sleep(500);
        }
        System.out.println("Path");
        System.out.print("[");
        for (Point p : pathFinding.getLastComputedPath()) {
            System.out.print("["+p.x+","+p.y+"],");
        }
        System.out.println("]");
    }
}
