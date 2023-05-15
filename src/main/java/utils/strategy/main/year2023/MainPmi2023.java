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

public class MainPmi2023 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Vert
        // Départ 220,1300
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();
        int score = 0;

        // Ejection premièrecherry bouboule
        TaskList vidangeDepart =  new TaskList(2000);
        // TODO vider première boule
        vidangeDepart.add(
            new Go("On quitte le bord", -250)
        );
        objectifsCouleur0.add(vidangeDepart.generateObjectif("Vidange depart", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(vidangeDepart.generateMirrorObjectif("Vidange depart", objectifsCouleur3000.size()+1, score, 1));

        // Aspiration des cerises sud
        TaskList aspirationsSud =  new TaskList(2000);
        aspirationsSud.add(
            new DeleteZone("Libération violet 2", "east_cake_purple_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération violet 2", "west_cake_purple_2", Tache.Mirror.SPECIFIC)
        );

        aspirationsSud.add(
            new DeleteZone("Libération jaune 2", "east_cake_yellow_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération jaune 2", "west_cake_yellow_2", Tache.Mirror.SPECIFIC)
        );
        aspirationsSud.add(
            new DeleteZone("Libération brun 2", "east_cake_brown_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération brun 2", "west_cake_brown_2", Tache.Mirror.SPECIFIC)
        );
        aspirationsSud.add(
            new DeleteZone("Libération brun 1", "east_cake_brown_1", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération brun 1", "west_cake_brown_1", Tache.Mirror.SPECIFIC)
        );

        aspirationsSud.add(
            new GoToAstar("Position aspiration", 2650, 1180)
        );
        aspirationsSud.add(
            new Face("Position aspiration", 3000, 1180)
        );
        // TODO start aspiration
        aspirationsSud.add(
            new SetSpeed("Reduction de vitesse", 25)
        );
        for (int i = 1; i < 10; i++) {
            aspirationsSud.add(new GoTo("Position aspiration", 2650 + i * 30, 1180));
            aspirationsSud.add(new Face("Position aspiration", 3000, 1180));
            aspirationsSud.add(new Wait("On laisse le temps", 200));
        }
        // TODO stop aspiration
        aspirationsSud.add(
            new SetSpeed("Vitesse normale", 100)
        );
        aspirationsSud.add(
            new GoToBack("Sortie aspiration", 2600, 1180)
        );
        objectifsCouleur0.add(aspirationsSud.generateObjectif("Cherry bouboule sud", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(aspirationsSud.generateMirrorObjectif("Cherry bouboule sud", objectifsCouleur3000.size()+1, score, 1));

        // On va se faire vomir
        TaskList evacuationCherryBouboule =  new TaskList(2000);
        evacuationCherryBouboule.add(
            new GoToAstar("Position vidage bouboule", 2800, 1775)
        );
        evacuationCherryBouboule.add(
            new Face("Alignement vidage bouboule", 3000, 1775)
        );
        evacuationCherryBouboule.add(
            new Go("Position vomie", 100)
        );
        evacuationCherryBouboule.add(
            new GoToBack("Sortie vomie", 2800, 1775)
        );
        // todo vidange
        objectifsCouleur0.add(evacuationCherryBouboule.generateObjectif("Vomie sud", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(evacuationCherryBouboule.generateMirrorObjectif("Vomie sud", objectifsCouleur3000.size()+1, score, 1));

        // Aspiration des cerises nord
        TaskList aspirationsNord =  new TaskList(2000);
        aspirationsNord.add(
            new GoToAstar("Position aspiration", 450, 1180)
        );
        aspirationsNord.add(
            new Face("Position aspiration", 0, 1180)
        );
        aspirationsNord.add(
            new GoTo("Position aspiration", 350, 1180)
        );
        // TODO start aspiration
        aspirationsNord.add(
            new SetSpeed("Reduction de vitesse", 25)
        );
        for (int i = 1; i < 10; i++) {
            aspirationsNord.add(new GoTo("Position aspiration", 350 - i * 30, 1180));
            aspirationsNord.add(new Face("Position aspiration", 0, 1180));
            aspirationsNord.add(new Wait("On laisse le temps", 200));
        }
        // TODO stop aspiration
        aspirationsNord.add(
            new SetSpeed("Vitesse normale", 100)
        );
        aspirationsNord.add(
            new GoToBack("Sortie aspiration", 450, 1180)
        );
        objectifsCouleur0.add(aspirationsNord.generateObjectif("Cherry bouboule nord", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(aspirationsNord.generateMirrorObjectif("Cherry bouboule nord", objectifsCouleur3000.size()+1, score, 1));

        // On va se faire vomir
        TaskList evacuationCherryBouboule2 =  new TaskList(2000);
        aspirationsSud.add(
            new AddZone("Blocage assiette 3", "start0_3", Tache.Mirror.SPECIFIC),
            new AddZone("Blocage assiette 3", "start3000_3", Tache.Mirror.SPECIFIC)
        );
        evacuationCherryBouboule2.add(
            new GoToAstar("Position vidage bouboule", 2500, 1775)
        );
        evacuationCherryBouboule2.add(
            new Face("Alignement vidage bouboule", 0, 1775)
        );
        evacuationCherryBouboule2.add(
            new GoTo("Position vidage bouboule", 2800, 1775)
        );
        evacuationCherryBouboule2.add(
            new Face("Alignement vidage bouboule", 3000, 1775)
        );
        evacuationCherryBouboule2.add(
            new Go("Position vomie", 100)
        );
        // todo vidange
        objectifsCouleur0.add(evacuationCherryBouboule2.generateObjectif("Vomie nord", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(evacuationCherryBouboule2.generateMirrorObjectif("Vomie nord", objectifsCouleur3000.size()+1, score, 1));

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
            Position startPoint = new Position(2900, 1775, 0);
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
            Position startPoint = new Position(2900, 225, 0);
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
