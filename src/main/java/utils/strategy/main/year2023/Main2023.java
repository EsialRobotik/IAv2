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
import utils.strategy.TaskList;
import utils.strategy.task.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main2023 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Vert
        // Départ en 2800,1775,Pi
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();
        int score = 0;

        // Récupération gateaux violet 2
        TaskList recuperationGateauViolet2_0 =  new TaskList(2000);
        recuperationGateauViolet2_0.add(
            new Go("Step de départ bizarre", 1)
        );
        // todo init ramassage
        recuperationGateauViolet2_0.add(
            new GoTo("Récupération violet 2", 2630, 1775)
        );
        // todo lever gateaux
        recuperationGateauViolet2_0.add(
            new DeleteZone("Libération violet 2", "east_cake_purple_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération violet 2", "west_cake_purple_2", Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(recuperationGateauViolet2_0.generateObjectif("Violet 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationGateauViolet2_0.generateMirrorObjectif("Violet 2", objectifsCouleur3000.size()+1, score, 1));

        // Récupération gateaux Jaune 2
        TaskList recuperationGateauJaune2_0 =  new TaskList(2000);
        recuperationGateauJaune2_0.add(
                new GoTo("Récupération jaune 2", 2430, 1775)
        );
        // todo ramasser gateaux
        // todo lever gateaux
        recuperationGateauJaune2_0.add(
            new DeleteZone("Libération jaune 2", "east_cake_yellow_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération jaune 2", "west_cake_yellow_2", Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(recuperationGateauJaune2_0.generateObjectif("Jaune 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationGateauJaune2_0.generateMirrorObjectif("Jaune 2", objectifsCouleur3000.size()+1, score, 1));

        // Récupération gateaux Brun 2
        TaskList recuperationGateauBrun2_0 =  new TaskList(2000);
        recuperationGateauBrun2_0.add(
                new GoToAstar("Récupération brun 2", 2200, 1275)
        );
        recuperationGateauBrun2_0.add(
                new Face("Récupération brun 2", 0, 1275)
        );
        recuperationGateauBrun2_0.add(
                new GoTo("Récupération brun 2", 2080, 1275)
        );
        // todo ramasser gateaux
        // todo lever gateaux
        recuperationGateauBrun2_0.add(
            new DeleteZone("Libération brun 2", "east_cake_brown_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération brun 2", "west_cake_brown_2", Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(recuperationGateauBrun2_0.generateObjectif("Brun 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationGateauBrun2_0.generateMirrorObjectif("Brun 2", objectifsCouleur3000.size()+1, score, 1));

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/2023/configCollection.json")) {
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
            Position startPoint = new Position(2800, 1775, Math.PI);
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
            try (PrintWriter stratFile = new PrintWriter("config/2023/strat_simu_0.json")) {
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
            Position startPoint = new Position(2800, 225, Math.PI);
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
            try (PrintWriter stratFile = new PrintWriter("config/2023/strat_simu_3000.json")) {
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
        jsVariables.append("var strategyBig0 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/strat_simu_0.json"))));
        jsVariables.append(";");
        jsVariables.append("var strategyBig3000 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/strat_simu_3000.json"))));
        jsVariables.append(";");
        if (Files.exists(Paths.get("config/2023/strat_simu_pmi_0.json"))) {
            jsVariables.append("var strategySmall0 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/strat_simu_pmi_0.json"))));
            jsVariables.append(";");
        }
        if (Files.exists(Paths.get("config/2023/strat_simu_pmi_3000.json"))) {
            jsVariables.append("var strategySmall3000 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2023/strat_simu_pmi_3000.json"))));
            jsVariables.append(";");
        }
        try (PrintWriter jsVariablesFile = new PrintWriter("src/main/resources/web/visualisator/2023/variables.js")) {
            jsVariablesFile.println(jsVariables);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
