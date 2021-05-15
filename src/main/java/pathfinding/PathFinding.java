package pathfinding;

import api.log.LoggerFactory;
import org.apache.logging.log4j.Level;
import pathfinding.table.Point;
import pathfinding.table.Table;
import pathfinding.table.astar.Astar;
import pathfinding.table.astar.LineSimplificator;
import pathfinding.table.shape.Shape;

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

        for (Shape shape : this.astar.getTable().getElementsList().keySet()) {
            if (shape.isActive()) {
                for (Point p : this.astar.getTable().getElementsList().get(shape)) {
                    this.astar.setTemporaryAccessible(p.x, p.y, false);
                }
            }
        }
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

    public void liberateElementById(String elementId) {
        for (Point p : astar.getTable().findElementById(elementId)) {
            astar.setTemporaryAccessible(p.x, p.y, true);
        }
    }

    public void lockElementById(String elementId) {
        for (Point p : astar.getTable().findElementById(elementId)) {
            astar.setTemporaryAccessible(p.x, p.y, false);
        }
    }

    public static void main(String[] args) throws Exception {
        LoggerFactory.init(Level.INFO);
        long start = System.currentTimeMillis();
        Table table = new Table("table0.tbl");
        table.loadJsonFromFile("table.json");
        System.out.println("table load in " + (System.currentTimeMillis() - start) + "ms");

        PathFinding pathFinding = new PathFinding(new Astar(table));

//        pathFinding.computePath(
//            new Point(800, 560),
//            new Point(610, 670)
//        );
//        while (!pathFinding.isComputationEnded()) {
//            Thread.sleep(500);
//        }
//        System.out.println("Path");
//        System.out.print("[");
//        for (Point p : pathFinding.getLastComputedPath()) {
//            System.out.print("["+p.x+","+p.y+"],");
//        }
//        System.out.println("]");

        pathFinding.liberateElementById("0_bouee3");

        pathFinding.computePath(
                new Point(610, 700),
                new Point(190, 415)
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

        pathFinding.liberateElementById("0_bouee1");
        pathFinding.lockElementById("0_chenal_depart_n");

//        pathFinding.computePath(
//                new Point(230,225),
//                new Point(1500, 230)
//        );
//        while (!pathFinding.isComputationEnded()) {
//            Thread.sleep(500);
//        }
//        System.out.println("Path");
//        System.out.print("[");
//        for (Point p : pathFinding.getLastComputedPath()) {
//            System.out.print("["+p.x+","+p.y+"],");
//        }
//        System.out.println("]");

        pathFinding.liberateElementById("0_bouee2");
        pathFinding.lockElementById("0_chenal_depart_s");

        pathFinding.computePath(
                new Point(210, 280),
                new Point(1410,210)
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
