package utils.strategy.main.year2022;

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
import utils.strategy.TaskList;
import utils.strategy.task.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainHomologuation2022 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie homologuation");

        // Liste des objectifs de chaque côté
        // 0 = Jaune, 3000 = Violet
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /**
         * On va pousser un élément dans le campement
         * - 1 point par échantillon dans le campement;
         * Score = 1
         */
        int score = 1;
        TaskList recuperationDistributeurCentral0 =  new TaskList();
        recuperationDistributeurCentral0.add(
            new Go("Step de départ bizarre", 1)
        );
        recuperationDistributeurCentral0.add(
            new GoToAstar("Mise en place", 475, 1200)
        );
        recuperationDistributeurCentral0.add(
            new Face("Alignement", 475, 0)
        );
        recuperationDistributeurCentral0.add(
            new GoTo("On marque", 475, 300)
        );
        recuperationDistributeurCentral0.add(
            new GoToBack("On recule pour le test", 475, 1200)
        );
        objectifsCouleur0.add(recuperationDistributeurCentral0.generateObjectif("Distributeur central", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationDistributeurCentral0.generateMirrorObjectif("Distributeur central", objectifsCouleur3000.size()+1, score, 1));

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/2022/configCollectionHomologuation.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LoggerFactory.init(Level.OFF);
        System.out.println("Test de la strat 0");
        try {
            Table table = new Table("config/2022/table0.tbl");
            table.loadJsonFromFile("config/2022/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(600, 200, Math.PI / 2);
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
            try (PrintWriter stratFile = new PrintWriter("config/2022/strat_simu_0.json")) {
                stratFile.println(stratSimu);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Test de la strat 3000");
        try {
            Table table = new Table("config/2022/table3000.tbl");
            table.loadJsonFromFile("config/2022/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(600, 2800, -Math.PI / 2);
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
            try (PrintWriter stratFile = new PrintWriter("config/2022/strat_simu_3000.json")) {
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
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/table.json"))));
        jsVariables.append(";");
        jsVariables.append("var strategyBig0 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_0.json"))));
        jsVariables.append(";");
        jsVariables.append("var strategyBig3000 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_3000.json"))));
        jsVariables.append(";");
        if (Files.exists(Paths.get("config/2022/strat_simu_pmi_0.json"))) {
            jsVariables.append("var strategySmall0 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_pmi_0.json"))));
            jsVariables.append(";");
        }
        if (Files.exists(Paths.get("config/2022/strat_simu_pmi_3000.json"))) {
            jsVariables.append("var strategySmall3000 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_pmi_3000.json"))));
            jsVariables.append(";");
        }
        try (PrintWriter jsVariablesFile = new PrintWriter("src/main/resources/web/visualisator/2022/variables.js")) {
            jsVariablesFile.println(jsVariables);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
