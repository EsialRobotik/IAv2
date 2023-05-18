package utils.strategy.main.year2023;

import actions.a2023.ActionFileBinder;
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

public class MainHomologation2023 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Vert
        // Départ en 2800,1775,Pi
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();
        int score = 0;

        // Récupération gateaux violet 2
        TaskList recuperationGateauViolet2 =  new TaskList(2000);
        recuperationGateauViolet2.add(
            new Go("Step de départ bizarre", 1)
        );
        recuperationGateauViolet2.add(
            new Manipulation("Init pince", ActionFileBinder.ActionFile.ROB_PINCE_MOBILE_RELACHER_LARGE.ordinal())
        );
        recuperationGateauViolet2.add(
            new Manipulation("Init ascenceur", ActionFileBinder.ActionFile.ROB_ASCENSEUR_SOLMARGE.ordinal())
        );
        recuperationGateauViolet2.add(
            new GoTo("Récupération violet 2", 2630, 1775)
        );
        recuperationGateauViolet2.add(
            new Manipulation("Prise violet 2", ActionFileBinder.ActionFile.ROB_EMPILER_PREMIER_GATEAU.ordinal())
        );
        recuperationGateauViolet2.add(
            new DeleteZone("Libération violet 2", "east_cake_purple_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération violet 2", "west_cake_purple_2", Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(recuperationGateauViolet2.generateObjectif("Violet 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationGateauViolet2.generateMirrorObjectif("Violet 2", objectifsCouleur3000.size()+1, score, 1));

        // Récupération gateaux Jaune 2
        TaskList recuperationGateauJaune2 =  new TaskList(2000);
        recuperationGateauJaune2.add(
            new GoTo("Récupération jaune 2", 2420, 1775)
        );
        recuperationGateauJaune2.add(
            new Face("Récupération jaune 2", 0, 1775)
        );
        recuperationGateauJaune2.add(
            new Manipulation("Prise jaune 2", ActionFileBinder.ActionFile.ROB_PINCE_MOBILE_RELACHER_LARGE.ordinal())
        );
        recuperationGateauJaune2.add(
            new GoToBack("Prise jaune 2", 2390, 1775)
        );
        recuperationGateauJaune2.add(
            new Manipulation("Prise jaune 2", ActionFileBinder.ActionFile.ROB_ASCENSEUR_SOL.ordinal())
        );
        recuperationGateauJaune2.add(
            new Manipulation("Prise jaune 2", ActionFileBinder.ActionFile.ROB_ASCENSEUR_SOL_BUTEE.ordinal())
        );
        recuperationGateauJaune2.add(
            new GoTo("Prise jaune 2", 2440, 1775)
        );
        recuperationGateauJaune2.add(
            new Manipulation("Prise jaune 2", ActionFileBinder.ActionFile.ROB_PINCE_MOBILE_ATTRAPER.ordinal())
        );
        recuperationGateauJaune2.add(
            new Manipulation("Prise jaune 2", ActionFileBinder.ActionFile.ROB_ASCENSEUR_NIV3.ordinal())
        );
        recuperationGateauJaune2.add(
            new DeleteZone("Libération jaune 2", "east_cake_yellow_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération jaune 2", "west_cake_yellow_2", Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(recuperationGateauJaune2.generateObjectif("Jaune 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationGateauJaune2.generateMirrorObjectif("Jaune 2", objectifsCouleur3000.size()+1, score, 1));

        // Marquage homologation
        score = 6;
        TaskList recuperationGateauBrun2 =  new TaskList(2000);
        recuperationGateauBrun2.add(
            new GoTo("homolo 1", 1200, 1775)
        );
        recuperationGateauBrun2.add(
            new Face("homolo 1", 0, 1775)
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 1", ActionFileBinder.ActionFile.ROB_ASCENSEUR_SOL.ordinal())
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 1", ActionFileBinder.ActionFile.ROB_ASCENSEUR_SOL_BUTEE.ordinal())
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 1", ActionFileBinder.ActionFile.ROB_PINCE_MOBILE_RELACHER_LARGE.ordinal())
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 1", ActionFileBinder.ActionFile.ROB_ASCENSEUR_NIV3.ordinal())
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 1", ActionFileBinder.ActionFile.ROB_PINCE_MOBILE_ATTRAPER.ordinal())
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 1", ActionFileBinder.ActionFile.ROB_ASCENSEUR_HAUT.ordinal())
        );
        recuperationGateauBrun2.add(
            new GoToBack("homolo 2", 1400, 1775)
        );
        recuperationGateauBrun2.add(
            new Face("homolo 2", 0, 1775)
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 2", ActionFileBinder.ActionFile.ROB_ASCENSEUR_SOL.ordinal())
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 2", ActionFileBinder.ActionFile.ROB_ASCENSEUR_SOL_BUTEE.ordinal())
        );
        recuperationGateauBrun2.add(
            new Manipulation("homolo 2", ActionFileBinder.ActionFile.ROB_PINCE_MOBILE_RELACHER_LARGE.ordinal())
        );
        recuperationGateauBrun2.add(
            new GoToBack("homolo 2", 1200, 1775)
        );
        objectifsCouleur0.add(recuperationGateauBrun2.generateObjectif("Brun 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationGateauBrun2.generateMirrorObjectif("Brun 2", objectifsCouleur3000.size()+1, score, 1));

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
            Position startPoint = new Position(2720, 1775, Math.PI);
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
            Position startPoint = new Position(2720, 225, Math.PI);
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