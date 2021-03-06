package pathfinding.table;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pathfinding.table.shape.Circle;
import pathfinding.table.shape.Shape;
import pathfinding.table.shape.ShapeFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    private boolean[][] forbiddenArea;

    private List<Shape> shapeList;

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

    public void loadJsonFromString(String json){
        Gson gson = new Gson();
        JsonObject configRootNode = gson.fromJson(json, JsonObject.class);
        loadConfig(configRootNode);
    }

    private void loadConfig(JsonObject rootElement){
        shapeList = new ArrayList<Shape>();
        xSize = rootElement.get("tailleX").getAsInt();
        rectifiedXSize = xSize / 10;
        ySize = rootElement.get("tailleY").getAsInt();
        rectifiedYSize = ySize / 10;
        color0 = rootElement.get("couleur0").getAsString();
        color3000 = rootElement.get("couleur3000").getAsString();

        for(JsonElement jsonElement : rootElement.getAsJsonArray("zonesInterdites")){
            shapeList.add(ShapeFactory.getShape(jsonElement.getAsJsonObject()));
        }
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

    public void drawTable() {
        this.forbiddenArea = new boolean[rectifiedXSize][rectifiedYSize];
        for(int i = 0; i < rectifiedXSize; ++i) {
            for(int j = 0; j < rectifiedYSize; ++j) {
                this.forbiddenArea[i][j] = false;
            }
        }

        for(Shape shape : shapeList) {
            boolean[][] temp = shape.drawShapeEdges(rectifiedXSize, rectifiedYSize);

            for(int i = 0; i < rectifiedXSize; ++i) {
                for(int j = 0; j < rectifiedYSize; ++j) {
                    if(temp[i + rectifiedXSize][j + rectifiedYSize]){
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

    public void computeForbiddenArea(int margin) {
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

    public void printTable() {
        for(int i = 0; i < forbiddenArea.length; ++i) {
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
        }
        catch (IOException e) {
            e.printStackTrace();

        }
        finally {
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

    public static void main(String[] args) throws IOException {
        // A lancer directement depuis l'IDE pour générer la table
        Table table = new Table();
        table.loadJsonFromFile("table.json");

        table.drawTable();
        table.computeForbiddenArea(195);

        File f = new File("table.tbl");

        table.saveToFile(f.getName());
        System.out.println("Generation of the table succesfull.");
    }
}
