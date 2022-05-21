package utils.strategy.main.year2022;

import actions.a2022.ActionFileBinder;
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

public class MainPmi2022 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Pompes à 90 et 235mm du bord
        // 300 * 160

        // Liste des objectifs de chaque côté
        // 0 = Jaune, 3000 = Violet
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /**
         * Swap statuette
         * - 2 points pour la dépose de la statuette sur le piédestal pendant le temps de préparation;
         * - 5 points si la statuette n’est plus présente en fin de match sur le piédestal;
         * - 10 points si la réplique est présente en fin de match sur le piédestal;
         * - 2 points si l’équipe dépose une vitrine pendant le temps de préparation;
         * Score = 19
         */
        int score = 19;
        TaskList swapStatuette0 = new TaskList();
        TaskList swapStatuette3000 = new TaskList();
        swapStatuette0.setMirrorTaskList(swapStatuette3000);
        swapStatuette0.add(new Go("Step de départ bizarre", 1));
        swapStatuette0.add(new GoToAstar("Placement chantier récupération statuette", 1600, 500, Tache.Mirror.SPECIFIC), new GoToAstar("Placement chantier récupération statuette", 1500, 2600, Tache.Mirror.SPECIFIC));
        swapStatuette0.add(new GoTo("Placement chantier récupération statuette", 1720, 420, Tache.Mirror.SPECIFIC), new GoTo("Placement chantier récupération statuette", 1580, 2730, Tache.Mirror.SPECIFIC));
        swapStatuette0.add(new Face("Alignement récupération statuette", 1800, 342, Tache.Mirror.SPECIFIC), new Face("Alignement récupération statuette", 1840, 3000, Tache.Mirror.SPECIFIC));
        swapStatuette0.add(new Manipulation("Récupération statuette", ActionFileBinder.ActionFile.PASSPASS_GET_STATUE.ordinal()));
        swapStatuette0.add(new Go("Manoeuvre libération réplique", -300));
        swapStatuette0.add(new GoTo("Placement chantier libération réplique", 1500, 450, Tache.Mirror.SPECIFIC), new GoTo("Placement chantier libération réplique", 1500, 2450, Tache.Mirror.SPECIFIC));
        swapStatuette0.add(new GoTo("Placement chantier libération réplique", 1640, 280, Tache.Mirror.SPECIFIC), new GoTo("Placement chantier libération réplique", 1720, 2620, Tache.Mirror.SPECIFIC));
        swapStatuette0.add(new Face("Alignement libération réplique", 1930, 0, Tache.Mirror.SPECIFIC), new Face("Alignement libération réplique", 2000, 2935, Tache.Mirror.SPECIFIC));
        swapStatuette0.add(new Go("Placement final libération réplique", 40, 200));
        swapStatuette0.add(new Manipulation("Libération réplique", ActionFileBinder.ActionFile.PASSPASS_PUT_FAKE.ordinal()));
        swapStatuette0.add(new Go("Libération chantier", -200));
        Objectif objectifSwapStatuette0 = new Objectif("Distributeur central", objectifsCouleur0.size() + 1, score, 1, swapStatuette0);
        Objectif objectifSwapStatuette3000 = new Objectif("Distributeur central", objectifsCouleur3000.size() + 1, score, 1, null);
        try {
            objectifSwapStatuette3000.generateMirror(objectifSwapStatuette0.taches, swapStatuette3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifSwapStatuette0);
        objectifsCouleur3000.add(objectifSwapStatuette3000);

        /**
         * Dépose statuette dans la vitrine
         * - 15 points si la statuette est présente en fin de match dans la vitrine;
         * - 5 points supplémentaires si la vitrine est activée;
         * Score = 20
         */
        score = 20;
        TaskList deposeStatuette0 = new TaskList();
        TaskList deposeStatuette3000 = new TaskList();
        deposeStatuette0.setMirrorTaskList(deposeStatuette3000);
        deposeStatuette0.add(new GoToAstar("Déplacement exposition", 1750, 1340));
        deposeStatuette0.add(new GoTo("Déplacement exposition", 1000, 1340));
        deposeStatuette0.add(new GoToAstar("Déplacement exposition", 270, 315, Tache.Mirror.SPECIFIC), new GoToAstar("Déplacement exposition", 270, 2535, Tache.Mirror.SPECIFIC));
        deposeStatuette0.add(new GoTo("Placement exposition", 90, 315, Tache.Mirror.SPECIFIC), new GoTo("Placement exposition", 90, 2535, Tache.Mirror.SPECIFIC));
        deposeStatuette0.add(new Face("Alignement exposition", 0, 315, Tache.Mirror.SPECIFIC), new Face("Alignement exposition", 0, 2535, Tache.Mirror.SPECIFIC));
        deposeStatuette0.add(new Go("Collage statuette", 10, 200));
        deposeStatuette0.add(new Manipulation("Dépose statuette", ActionFileBinder.ActionFile.PASSPASS_GET_STATUE.ordinal()));
        deposeStatuette0.add(new Go("Sortie exposition", -190));
        Objectif objectifDeposeStatuette0 = new Objectif("Depose Statuette", objectifsCouleur0.size() + 1, score, 1, deposeStatuette0);
        Objectif objectifDeposeStatuette3000 = new Objectif("Depose Statuette", objectifsCouleur3000.size() + 1, score, 1, null);
        try {
            objectifDeposeStatuette3000.generateMirror(objectifDeposeStatuette0.taches, deposeStatuette3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifDeposeStatuette0);
        objectifsCouleur3000.add(objectifDeposeStatuette3000);


        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/2022/configCollectionPmi.json")) {
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
            try (PrintWriter stratFile = new PrintWriter("config/2022/strat_simu_pmi_0.json")) {
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
            try (PrintWriter stratFile = new PrintWriter("config/2022/strat_simu_pmi_3000.json")) {
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
        if (Files.exists(Paths.get("config/2022/strat_simu_0.json"))) {
            jsVariables.append("var strategyBig0 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_0.json"))));
            jsVariables.append(";");
        }
        if (Files.exists(Paths.get("config/2022/strat_simu_3000.json"))) {
            jsVariables.append("var strategyBig3000 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_3000.json"))));
            jsVariables.append(";");
        }
        jsVariables.append("var strategySmall0 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_pmi_0.json"))));
        jsVariables.append(";");
        jsVariables.append("var strategySmall3000 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_pmi_3000.json"))));
        jsVariables.append(";");
        try (PrintWriter jsVariablesFile = new PrintWriter("src/main/resources/web/visualisator/2022/variables.js")) {
            jsVariablesFile.println(jsVariables);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
