package esialrobotik.ia.pathfinding.table;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import esialrobotik.ia.pathfinding.table.shape.Circle;
import esialrobotik.ia.pathfinding.table.shape.Shape;
import esialrobotik.ia.pathfinding.table.shape.ShapeFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by icule on 28/03/17.
 */
public class Table {
    private int xSize;
    private int rectifiedXSize;
    private int ySize;
    private int rectifiedYSize;
    private String color0;
    private String color3000;
    private int margin;

    private boolean[][] forbiddenArea;
    private List<List<Point>> detectionIgnoreQuadrilaterium;

    private List<Shape> shapeList;
    private Map<Shape, List<Point>> elementsList;

    public Table() {}

    public Table(String filePath) throws IOException {
        this.loadFromSaveFile(filePath);
    }

    public void loadJsonFromFile(String filePath) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);
        loadConfig(configRootNode);
    }

    public void loadJsonFromFile(String filePath, List<String> zoneToSkip) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);
        loadConfig(configRootNode, zoneToSkip);
        drawTable();
        computeForbiddenArea();
    }

    public void loadJsonFromString(String json){
        Gson gson = new Gson();
        JsonObject configRootNode = gson.fromJson(json, JsonObject.class);
        loadConfig(configRootNode);
    }

    public void loadFromSaveFile(String filename) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            line = br.readLine();
            String[] temp = line.split(" ");
            this.xSize = Integer.parseInt(temp[0]);
            this.rectifiedXSize = xSize / 10;
            this.ySize = Integer.parseInt(temp[1]);
            this.rectifiedYSize = ySize / 10;

            this.forbiddenArea = new boolean[rectifiedXSize][rectifiedYSize];
            int acc = 0;
            while ((line = br.readLine()) != null) {
                for(int j = 0; j < forbiddenArea[0].length ; ++j) {
                    if(line.charAt(j) == 'x') {
                        forbiddenArea[acc][j] = true;
                    }
                    else {
                        forbiddenArea[acc][j] = false;
                    }
                }
                ++acc;
            }
        }
        finally {
            if(br != null) {
                br.close();
            }
        }
    }

    private void loadConfig(JsonObject rootElement){
        loadConfig(rootElement, new ArrayList<>());
    }

    private void loadConfig(JsonObject rootElement, List<String> zoneToSkip){
        shapeList = new ArrayList<Shape>();
        xSize = rootElement.get("sizeX").getAsInt();
        rectifiedXSize = xSize / 10;
        ySize = rootElement.get("sizeY").getAsInt();
        rectifiedYSize = ySize / 10;
        color0 = rootElement.get("color0").getAsString();
        color3000 = rootElement.get("color3000").getAsString();
        margin = rootElement.get("marge").getAsInt();

        if (zoneToSkip.size() > 0) {
            for (JsonElement jsonElement : rootElement.getAsJsonArray("forbiddenZones")) {
                Shape shape = ShapeFactory.getShape(jsonElement.getAsJsonObject());
                if (zoneToSkip.contains(shape.getId()) || shape.getId().contains("_margin")) {
                    continue;
                }
                shapeList.add(shape);
            }
        }

        elementsList = new HashMap<>();
        for (JsonElement jsonElement : rootElement.getAsJsonArray("dynamicZones")) {
            Shape shape = ShapeFactory.getShape(jsonElement.getAsJsonObject());
            elementsList.put(shape, getPointsFromShape(shape));
        }

        detectionIgnoreQuadrilaterium = new ArrayList<>();
        for (JsonElement jsonElement : rootElement.getAsJsonArray("detectionIgnoreZone")) {
            List<Point> points = new ArrayList<>();
            points.add(new Point(
                jsonElement.getAsJsonObject().get("x1").getAsInt(),
                jsonElement.getAsJsonObject().get("y1").getAsInt()
            ));
            points.add(new Point(
                jsonElement.getAsJsonObject().get("x2").getAsInt(),
                jsonElement.getAsJsonObject().get("y2").getAsInt()
            ));
            points.add(new Point(
                jsonElement.getAsJsonObject().get("x3").getAsInt(),
                jsonElement.getAsJsonObject().get("y3").getAsInt()
            ));
            points.add(new Point(
                jsonElement.getAsJsonObject().get("x4").getAsInt(),
                jsonElement.getAsJsonObject().get("y4").getAsInt()
            ));
            addPointsToDetectionIgnoreQuadrilaterium(points);
        }
    }

    public void addPointsToDetectionIgnoreQuadrilaterium(List<Point> points) {
        detectionIgnoreQuadrilaterium.add(points);
    }

    public int getxSize(){
        return xSize;
    }

    public int getySize(){
        return ySize;
    }

    public int getRectifiedXSize() {
        return rectifiedXSize;
    }

    public int getRectifiedYSize() {
        return rectifiedYSize;
    }

    public String getColor0Name(){
        return color0;
    }

    public String getColor3000Name(){
        return color3000;
    }

    public List<Shape> getShapeList(){
        return shapeList;
    }

    public Map<Shape, List<Point>> getElementsList() {
        return elementsList;
    }

    public List<Point> findElementById(String itemId) {
        for (Shape shape : this.elementsList.keySet()) {
            if (shape.getId().equals(itemId)) {
                return this.elementsList.get(shape);
            }
        }
        return new ArrayList<>();
    }

    public void drawTable() {
        this.forbiddenArea = new boolean[rectifiedXSize][rectifiedYSize];
        for (int i = 0; i < rectifiedXSize; ++i) {
            for(int j = 0; j < rectifiedYSize; ++j) {
                this.forbiddenArea[i][j] = false;
            }
        }

        for (Shape shape : shapeList) {
            boolean[][] temp = shape.drawShapeEdges(rectifiedXSize, rectifiedYSize);

            for (int i = 0; i < rectifiedXSize; ++i) {
                for (int j = 0; j < rectifiedYSize; ++j) {
                    if (temp[i + rectifiedXSize][j + rectifiedYSize]) {
                        this.forbiddenArea[i][j] = true;
                    }
                }
            }
        }
    }

    private void setValue(boolean[][] board, int x, int y, boolean val) {
        if(x >= 0 && y >= 0 && x <= board.length && y <= board[0].length) {
            board[x][y] = val;
        }
    }

    public void computeForbiddenArea() {
        int rectifiedMargin = (int)Math.ceil(margin / 10.);

        int boardLength = forbiddenArea.length;
        int boardWidth = forbiddenArea[0].length;


        boolean[][] buffer = new boolean[boardLength + 2 * (rectifiedMargin + 1)][boardWidth + 2 * (rectifiedMargin + 1)];
        for(int i = 0; i < buffer.length; ++i) {
            for(int j = 0; j < buffer[0].length; ++j) {
                buffer[i][j] = false;
            }
        }

        Circle circle = new Circle(rectifiedMargin * 10, rectifiedMargin * 10, rectifiedMargin * 10);
        int bufferSize = (rectifiedMargin + 1) * 2;
        boolean[][] shapeBuffer = circle.drawShapeEdges((rectifiedMargin + 1) * 2, (rectifiedMargin + 1) * 2);

        for(int i = 0; i < boardLength; ++i) {
            for(int j = 0; j < boardWidth; ++j) {
                if(forbiddenArea[i][j]) {
                    for(int i1 = bufferSize; i1 < bufferSize * 2; ++i1) {
                        for(int j1 = bufferSize; j1 < bufferSize * 2; ++j1) {
                            if(shapeBuffer[i1][j1]){
                                setValue(buffer, i + 1 + i1 - bufferSize, j + 1 + j1 - bufferSize, true);
                                setValue(buffer, i + 2 + i1 - bufferSize, j + 1 + j1 - bufferSize, true);
                                setValue(buffer, i + 1 + i1 - bufferSize, j + 2 + j1 - bufferSize, true);
                                setValue(buffer, i + 2 + i1 - bufferSize, j + 2 + j1 - bufferSize, true);
                            }
                        }
                    }
                }
            }
        }

        for(int i1 = rectifiedMargin + 1; i1 < forbiddenArea.length + rectifiedMargin + 1; ++i1) {
            for(int j1 = rectifiedMargin + 1; j1 < forbiddenArea[0].length + rectifiedMargin + 1; ++j1) {
                if(buffer[i1][j1]){
                    forbiddenArea[i1 - rectifiedMargin -  1][j1 - rectifiedMargin - 1] = true;
                }
            }
        }

        //Now we disable table side we draw directly in the main buffer
        for(int i = 0; i < rectifiedMargin; ++i) {
            for(int j = 0; j < rectifiedYSize; ++j) {
                forbiddenArea[i][j] = true;
            }
        }

        for(int i = rectifiedXSize - rectifiedMargin; i < rectifiedXSize; ++i) {
            for(int j = 0; j < rectifiedYSize; ++j) {
                forbiddenArea[i][j] = true;
            }
        }

        for(int i = 0; i < rectifiedXSize; ++i) {
            for(int j = 0; j < rectifiedMargin; ++j) {
                forbiddenArea[i][j] = true;
            }

            for(int j = rectifiedYSize - rectifiedMargin; j < rectifiedYSize; ++j) {
                forbiddenArea[i][j] = true;
            }
        }
    }

    public boolean[][] computeForbiddenAreaForElement(boolean[][] element) {
        int rectifiedMargin = (int)Math.ceil(margin / 10.);

        int boardLength = element.length;
        int boardWidth = element[0].length;


        boolean[][] buffer = new boolean[boardLength + 2 * (rectifiedMargin + 1)][boardWidth + 2 * (rectifiedMargin + 1)];
        for(int i = 0; i < buffer.length; ++i) {
            for(int j = 0; j < buffer[0].length; ++j) {
                buffer[i][j] = false;
            }
        }

        Circle circle = new Circle(rectifiedMargin * 10, rectifiedMargin * 10, rectifiedMargin * 10);
        int bufferSize = (rectifiedMargin + 1) * 2;
        boolean[][] shapeBuffer = circle.drawShapeEdges((rectifiedMargin + 1) * 2, (rectifiedMargin + 1) * 2, false);

        for(int i = 0; i < boardLength; ++i) {
            for(int j = 0; j < boardWidth; ++j) {
                if(element[i][j]) {
                    for(int i1 = bufferSize; i1 < bufferSize * 2; ++i1) {
                        for(int j1 = bufferSize; j1 < bufferSize * 2; ++j1) {
                            if(shapeBuffer[i1][j1]){
                                setValue(buffer, i + 1 + i1 - bufferSize, j + 1 + j1 - bufferSize, true);
                                setValue(buffer, i + 2 + i1 - bufferSize, j + 1 + j1 - bufferSize, true);
                                setValue(buffer, i + 1 + i1 - bufferSize, j + 2 + j1 - bufferSize, true);
                                setValue(buffer, i + 2 + i1 - bufferSize, j + 2 + j1 - bufferSize, true);
                            }
                        }
                    }
                }
            }
        }

        for(int i1 = rectifiedMargin + 1; i1 < element.length + rectifiedMargin + 1; ++i1) {
            for(int j1 = rectifiedMargin + 1; j1 < element[0].length + rectifiedMargin + 1; ++j1) {
                if(buffer[i1][j1]){
                    element[i1 - rectifiedMargin -  1][j1 - rectifiedMargin - 1] = true;
                }
            }
        }

        //Now we disable table side we draw directly in the main buffer
        for(int i = 0; i < rectifiedMargin; ++i) {
            for(int j = 0; j < rectifiedYSize; ++j) {
                element[i][j] = true;
            }
        }

        for(int i = rectifiedXSize - rectifiedMargin; i < rectifiedXSize; ++i) {
            for(int j = 0; j < rectifiedYSize; ++j) {
                element[i][j] = true;
            }
        }

        for(int i = 0; i < rectifiedXSize; ++i) {
            for(int j = 0; j < rectifiedMargin; ++j) {
                element[i][j] = true;
            }

            for(int j = rectifiedYSize - rectifiedMargin; j < rectifiedYSize; ++j) {
                element[i][j] = true;
            }
        }

        return element;
    }

    public List<Point> getZonePointsById(int margin, String forbiddenZoneId) throws Exception {
        // On récupère la shape
        Shape cleanShape = null;
        for (Shape shape : this.shapeList) {
            if (shape.getId().equals(forbiddenZoneId)) {
                cleanShape = shape;
            }
        }

        if (cleanShape == null) {
            throw new Exception("Unknown shape " + forbiddenZoneId);
        }

        ArrayList<Point> points = new ArrayList<>();

        // On calcul la zone à partir de l'id
        boolean[][] shapeBuffer = cleanShape.drawShapeEdges(rectifiedXSize, rectifiedYSize);

        // On calcul la marge
        int rectifiedMargin = (int)Math.ceil(margin / 10.);
        int bufferSize = (rectifiedMargin + 1) * 2;

        for (int i = rectifiedXSize; i <= shapeBuffer.length - rectifiedXSize; i++) {
            for (int j = rectifiedYSize; j <= shapeBuffer[0].length - rectifiedYSize; j++) {
                if (shapeBuffer[i][j]) {
                    points.add(new Point(i - rectifiedXSize, j - rectifiedYSize));
                    for (int i1 = bufferSize; i1 < bufferSize * 2; ++i1) {
                        for (int j1 = bufferSize; j1 < bufferSize * 2; ++j1) {
                            if (i + 1 + i1 - bufferSize > 0 && i + 1 + i1 - bufferSize <= rectifiedXSize) {
                                if (j + 1 + j1 - bufferSize > 0 && j + 1 + j1 - bufferSize <= rectifiedYSize) {
                                    points.add(new Point(i + 1 + i1 - bufferSize - rectifiedXSize, j + 1 + j1 - bufferSize - rectifiedYSize));
                                }
                                if (j + 2 + j1 - bufferSize > 0 && j + 2 + j1 - bufferSize <= rectifiedYSize) {
                                    points.add(new Point(i + 1 + i1 - bufferSize - rectifiedXSize, j + 2 + j1 - bufferSize - rectifiedYSize));
                                }
                            }
                            if (i + 2 + i1 - bufferSize > 0 && i + 1 + i + 2 + i1 - bufferSize <= rectifiedXSize) {
                                if (j + 1 + j1 - bufferSize > 0 && j + 1 + j1 - bufferSize <= rectifiedYSize) {
                                    points.add(new Point(i + 2 + i1 - bufferSize - rectifiedXSize, j + 1 + j1 - bufferSize - rectifiedYSize));
                                }
                                if (j + 2 + j1 - bufferSize > 0 && j + 2 + j1 - bufferSize <= rectifiedYSize) {
                                    points.add(new Point(i + 2 + i1 - bufferSize - rectifiedXSize, j + 2 + j1 - bufferSize - rectifiedYSize));
                                }
                            }
                        }
                    }
                }
            }
        }

        return points;
    }

    public void printTable() {
        for (int i = 0; i < forbiddenArea.length; ++i) {
            for(int j = 0; j < forbiddenArea[0].length; ++j){
                System.out.print(forbiddenArea[i][j]?"x":"o");
            }
            System.out.print("\n");
        }
    }

    public void printBuffer(boolean[][] buffer) {
        for(int i = 0; i < buffer.length; ++i) {
            for(int j = 0; j < buffer[0].length; ++j){
                System.out.print(buffer[i][j]?"x":"o");
            }
            System.out.print("\n");
        }
    }

    public String toString() {
        String res = "";
        for(int i = 0; i < forbiddenArea.length; ++i) {
            for(int j = 0; j < forbiddenArea[0].length; ++j){
                res += forbiddenArea[i][j]?"x":"o";
            }
            res += "\n";
        }
        return res;
    }

    public void saveToFile(String filename) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            bw.write(this.getxSize() + " " + this.getySize() + "\n");
            bw.write(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isAreaForbidden(int x, int y) {
        return forbiddenArea[x][y];
    }

    /**
     * Vérifie si une position est dans une zone interdite ou hors de la table
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return true si en dehors de la table ou dans une zone interdite
     */
    public boolean isAreaForbiddenSafe(int x, int y) {
        if(x < 0 || y < 0 || x >= rectifiedXSize || y >= rectifiedYSize) {
            return true;
        }
        return forbiddenArea[x][y];
    }

    /**
     * Vérifie si un point est dans une zone de détection à ignorer
     * TODO fonctionne uniquement avec des quadrilatères alignés sur la table avec les points dans l'ordre
     * TODO à améliorer pour faire mieux
     * @param point
     * @return
     */
    public boolean isPointInDetectionIgnoreZone(Point point) {
        for (List<Point> quadrilaterium: this.detectionIgnoreQuadrilaterium) {
            if (point.x >= quadrilaterium.get(0).x && point.x <= quadrilaterium.get(2).x
                && point.y >= quadrilaterium.get(0).y && point.y <= quadrilaterium.get(2).y) {
                return true;
            }
        }
        return false;
    }

    public List<Point> getPointsFromShape(Shape shape) {
        List<Point> points = new ArrayList<>();
        boolean[][] temp = shape.drawShapeEdges(rectifiedXSize, rectifiedYSize, false);
        temp = computeForbiddenAreaForElement(temp);
        for (int i = rectifiedXSize; i <= rectifiedXSize * 2; i++) {
            for (int j = rectifiedYSize; j <= rectifiedYSize * 2; j++) {
                if (temp[i][j]) {
                    points.add(new Point(i - rectifiedXSize, j - rectifiedYSize));
                }
            }
        }
        return points;
    }

    public static void main(String[] args) throws IOException {
        int year = 2024;

        try {
            // A lancer directement depuis l'IDE pour générer la table
            Table table = new Table();
            ArrayList<String> zoneToSkip = new ArrayList<>();
            table.loadJsonFromFile("config/" + year + "/table.json", zoneToSkip);

            table.drawTable();
            table.computeForbiddenArea();

            File f = new File("table0.tbl");

            table.saveToFile("config/" + year + "/" + f.getName());

            table = new Table();
            zoneToSkip = new ArrayList<>();
            table.loadJsonFromFile("config/" + year + "/table.json", zoneToSkip);

            table.drawTable();
            table.computeForbiddenArea();

            f = new File("table3000.tbl");

            table.saveToFile("config/" + year + "/" + f.getName());
            System.out.println("Generation of the table succesfull.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
