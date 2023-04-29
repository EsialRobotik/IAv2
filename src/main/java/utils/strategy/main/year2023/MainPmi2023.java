package utils.strategy.main.year2023;

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

public class MainPmi2023 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Vert
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();
        int score = 0;

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/2023/configCollectionPmi.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LoggerFactory.init(Level.OFF);
        System.out.println("Test de la strat 0");
        try {
            Table table = new Table("config/2023/table0.tbl");
            table.loadJsonFromFile("config/2023/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(900, 240, 0);
            StringBuilder stratSimu = new StringBuilder("[");
            stratSimu.append("{ \"task\":\"Position de départ\",\"command\":\"start\",\"position\":" + startPoint.toJson() + "},");
            for (Objectif objectif : strat.couleur0) {
                for (Tache task : objectif.taches) {
                    task.pathFinding = pathFinding;
                    String execution = task.execute(startPoint);
                    System.out.println(execution);
                    stratSimu.append(execution);
                    startPoint = task.getEndPoint();
                }
            }
            stratSimu.deleteCharAt(stratSimu.length() - 1);
            stratSimu.append("]");
            try (PrintWriter stratFile = new PrintWriter("config/2023/strat_simu_pmi_0.json")) {
                stratFile.println(stratSimu);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Test de la strat 3000");
        try {
            Table table = new Table("config/2023/table3000.tbl");
            table.loadJsonFromFile("config/2023/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(900, 2760, 0);
            StringBuilder stratSimu = new StringBuilder("[");
            stratSimu.append("{ \"task\":\"Position de départ\",\"command\":\"start\",\"position\":" + startPoint.toJson() + "},");
            for (Objectif objectif : strat.couleur3000) {
                for (Tache task : objectif.taches) {
                    task.pathFinding = pathFinding;
                    String execution = task.execute(startPoint);
                    System.out.println(execution);
                    stratSimu.append(execution);
                    startPoint = task.getEndPoint();
                }
            }
            stratSimu.deleteCharAt(stratSimu.length() - 1);
            stratSimu.append("]");
            try (PrintWriter stratFile = new PrintWriter("config/2023/strat_simu_pmi_3000.json")) {
                stratFile.println(stratSimu);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Génération du JS pour le simulateur");
        StringBuilder jsVariables = new StringBuilder();
        jsVariables.append("var jsonTable = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/table.json"))));
        jsVariables.append(";");
        if (Files.exists(Paths.get("config/2023/strat_simu_0.json"))) {
            jsVariables.append("var strategyBig0 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/strat_simu_0.json"))));
            jsVariables.append(";");
        }
        if (Files.exists(Paths.get("config/2023/strat_simu_3000.json"))) {
            jsVariables.append("var strategyBig3000 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/strat_simu_3000.json"))));
            jsVariables.append(";");
        }
        jsVariables.append("var strategySmall0 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/strat_simu_pmi_0.json"))));
        jsVariables.append(";");
        jsVariables.append("var strategySmall3000 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/strat_simu_pmi_3000.json"))));
        jsVariables.append(";");
        try (PrintWriter jsVariablesFile = new PrintWriter("src/main/resources/web/visualisator/2023/variables.js")) {
            jsVariablesFile.println(jsVariables);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
