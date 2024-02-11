package esialrobotik.ia.utils.strategy.main.year2023;

import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.asserv.Position;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.event.Level;
import esialrobotik.ia.pathfinding.PathFinding;
import esialrobotik.ia.pathfinding.table.Table;
import esialrobotik.ia.pathfinding.table.astar.Astar;
import esialrobotik.ia.utils.strategy.Objectif;
import esialrobotik.ia.utils.strategy.Strategie;
import esialrobotik.ia.utils.strategy.Tache;
import esialrobotik.ia.utils.strategy.TaskList;
import esialrobotik.ia.utils.strategy.task.*;

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

        // Aspiration des cerises nord
        TaskList aspirationsNord =  new TaskList(2000);
        aspirationsNord.add(
            new DeleteZone("Libération assiette 2", "start0_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération assiette 2", "start3000_2", Tache.Mirror.SPECIFIC)
        );
        aspirationsNord.add(
            new Go("Step de départ bizarre", 1)
        );
        aspirationsNord.add(
            new GoTo("Position aspiration", 450, 1180)
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
            aspirationsNord.add(new Wait("On laisse le temps", 1000));
        }
        // TODO stop aspiration
        aspirationsNord.add(
            new SetSpeed("Vitesse normale", 100)
        );
        aspirationsNord.add(
            new GoToBack("Sortie aspiration", 450, 1180)
        );
        objectifsCouleur0.add(aspirationsNord.generateObjectif("Violet 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(aspirationsNord.generateMirrorObjectif("Violet 2", objectifsCouleur3000.size()+1, score, 1));

        // Aspiration des cerises est
        TaskList aspirationsEst =  new TaskList(2000);
        aspirationsEst.add(
            new GoToAstar("Position aspiration", 1100, 1800)
        );
        aspirationsEst.add(
            new GoTo("Position aspiration", 1200, 1805)
        );
        aspirationsEst.add(
            new Face("Position aspiration", 3000, 1805)
        );
        aspirationsEst.add(
            new GoTo("Position aspiration", 1300, 1805)
        );
        // TODO start aspiration
        aspirationsEst.add(
            new SetSpeed("Reduction de vitesse", 25)
        );
        for (int i = 1; i < 10; i++) {
            aspirationsEst.add(new GoTo("Position aspiration", 1300 + i * 30, 1805));
            aspirationsEst.add(new Wait("On laisse le temps", 1000));
        }
        // TODO stop aspiration
        aspirationsEst.add(
            new SetSpeed("Vitesse normale", 100)
        );
        aspirationsEst.add(
            new GoToBack("Sortie aspiration", 1200, 1805)
        );
        aspirationsEst.add(
            new GoTo("Sortie aspiration", 1100, 1800)
        );
        objectifsCouleur0.add(aspirationsEst.generateObjectif("Violet 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(aspirationsEst.generateMirrorObjectif("Violet 2", objectifsCouleur3000.size()+1, score, 1));

        // On va se faire vomir
        TaskList evacuationCherryBouboule =  new TaskList(2000);
        evacuationCherryBouboule.add(
            new DeleteZone("Libération violet 2", "east_cake_purple_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération violet 2", "west_cake_purple_2", Tache.Mirror.SPECIFIC)
        );
        evacuationCherryBouboule.add(
            new DeleteZone("Libération jaune 2", "east_cake_yellow_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération jaune 2", "west_cake_yellow_2", Tache.Mirror.SPECIFIC)
        );
        evacuationCherryBouboule.add(
            new DeleteZone("Libération brun 2", "east_cake_brown_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération brun 2", "west_cake_brown_2", Tache.Mirror.SPECIFIC)
        );
        evacuationCherryBouboule.add(
            new GoToAstar("Position vidage bouboule", 2800, 1775)
        );
        evacuationCherryBouboule.add(
            new Face("Alignement vidage bouboule", 3000, 1775)
        );
        evacuationCherryBouboule.add(
            new Go("Position finale", 100)
        );
        // todo vidange
        objectifsCouleur0.add(evacuationCherryBouboule.generateObjectif("Violet 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(evacuationCherryBouboule.generateMirrorObjectif("Violet 2", objectifsCouleur3000.size()+1, score, 1));

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

        LoggerFactory.setDefaultLevel(Level.ERROR);
        System.out.println("Test de la strat 0");
        try {
            Table table = new Table("config/2023/table0.tbl");
            table.loadJsonFromFile("config/2023/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(220, 1300, 0);
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
            Position startPoint = new Position(90, 1300, 0);
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
