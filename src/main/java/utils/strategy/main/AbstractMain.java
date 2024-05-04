package utils.strategy.main;

import api.log.LoggerFactory;
import asserv.Position;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import pathfinding.PathFinding;
import pathfinding.table.Table;
import pathfinding.table.astar.Astar;
import utils.strategy.Objectif;
import utils.strategy.Strategie;
import utils.strategy.Tache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMain {
    public static List<Objectif> objectifsCouleur0 = new ArrayList<>();
    public static List<Objectif> objectifsCouleur3000 = new ArrayList<>();

    public static int year;
    public static int startX_0;
    public static int startY_0;
    public static double startTheta_0;
    public static int startX_3000;
    public static int startY_3000;
    public static double startTheta_3000;

    public static void generateStrategy() throws IOException {
        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/" + year + "/configCollection.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LoggerFactory.init(Level.OFF);
        System.out.println("Test de la strat 0");
        try {
            Table table = new Table("config/" + year + "/table0.tbl");
            table.loadJsonFromFile("config/" + year + "/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(startX_0, startY_0, startTheta_0);
            StringBuilder stratSimu = new StringBuilder("[");
            stratSimu.append("{ \"task\":\"Position de départ\",\"command\":\"start\",\"position\":" + startPoint.toJson() + "},");
            for (Objectif objectif : strat.couleur0) {
                for (Tache task: objectif.taches) {
                    task.pathFinding = pathFinding;
                    String execution = task.execute(startPoint);
                    System.out.println(execution);
                    stratSimu.append(execution);
                    startPoint = task.getEndPoint();
                }
            }
            stratSimu.deleteCharAt(stratSimu.length()-1);
            stratSimu.append("]");
            try (PrintWriter stratFile = new PrintWriter("config/" + year + "/strat_simu_0.json")) {
                stratFile.println(stratSimu);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Test de la strat 3000");
        try {
            Table table = new Table("config/" + year + "/table3000.tbl");
            table.loadJsonFromFile("config/" + year + "/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(startX_3000, startY_3000, startTheta_3000);
            StringBuilder stratSimu = new StringBuilder("[");
            stratSimu.append("{ \"task\":\"Position de départ\",\"command\":\"start\",\"position\":" + startPoint.toJson() + "},");
            for (Objectif objectif : strat.couleur3000) {
                for (Tache task: objectif.taches) {
                    task.pathFinding = pathFinding;
                    String execution = task.execute(startPoint);
                    System.out.println(execution);
                    stratSimu.append(execution);
                    startPoint = task.getEndPoint();
                }
            }
            stratSimu.deleteCharAt(stratSimu.length()-1);
            stratSimu.append("]");
            try (PrintWriter stratFile = new PrintWriter("config/" + year + "/strat_simu_3000.json")) {
                stratFile.println(stratSimu);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Génération du JS pour le simulateur");
        StringBuilder strategyBig0 = new StringBuilder();
        StringBuilder strategyBig3000 = new StringBuilder();
        strategyBig0.append(new String(Files.readAllBytes(Paths.get("config/" + year + "/strat_simu_0.json"))));
        strategyBig3000.append(new String(Files.readAllBytes(Paths.get("config/" + year + "/strat_simu_3000.json"))));

        try (PrintWriter jsVariablesFile = new PrintWriter("src/main/resources/web/visualisator/" + year + "/strategyBig0.json")) {
            jsVariablesFile.println(strategyBig0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (PrintWriter jsVariablesFile = new PrintWriter("src/main/resources/web/visualisator/" + year + "/strategyBig3000.json")) {
            jsVariablesFile.println(strategyBig3000);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
