package esialrobotik.ia.utils.strategy.main.year2023;

import esialrobotik.ia.actions.a2023.ActionFileBinder;
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
        TaskList recuperationGateauViolet2 =  new TaskList(2000);
        recuperationGateauViolet2.add(
            new DeleteZone("Libération assiette 2", "start0_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération assiette 2", "start3000_2", Tache.Mirror.SPECIFIC)
        );
        recuperationGateauViolet2.add(
            new Go("Step de départ bizarre", 1)
        );
        recuperationGateauViolet2.add(
            new Manipulation("Init pince", ActionFileBinder.ActionFile.ROB_PINCE_MOBILE_RELACHER_LARGE.ordinal())
        );
        recuperationGateauViolet2.add(
            new Manipulation("Init ascenceur", ActionFileBinder.ActionFile.ROB_ASCENSEUR_INIT.ordinal())
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
            new GoTo("Récupération jaune 2", 2430, 1775)
        );
        recuperationGateauJaune2.add(
            new Manipulation("Prise jaune 2", ActionFileBinder.ActionFile.ROB_EMPILER_GATEAU_SUIVANT.ordinal())
        );
        recuperationGateauJaune2.add(
            new DeleteZone("Libération jaune 2", "east_cake_yellow_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération jaune 2", "west_cake_yellow_2", Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(recuperationGateauJaune2.generateObjectif("Jaune 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationGateauJaune2.generateMirrorObjectif("Jaune 2", objectifsCouleur3000.size()+1, score, 1));

        // Récupération gateaux Brun 2
        TaskList recuperationGateauBrun2 =  new TaskList(2000);
        recuperationGateauBrun2.add(
                new GoToAstar("Récupération brun 2", 2200, 1280)
        );
        recuperationGateauBrun2.add(
                new GoTo("Récupération brun 2", 2200, 1275)
        );
        recuperationGateauBrun2.add(
                new Face("Récupération brun 2", 0, 1275)
        );
        recuperationGateauBrun2.add(
                new GoTo("Récupération brun 2", 2080, 1275)
        );
        recuperationGateauBrun2.add(
                new Manipulation("Prise brun 2", ActionFileBinder.ActionFile.ROB_EMPILER_GATEAU_SUIVANT.ordinal())
        );
        recuperationGateauBrun2.add(
            new DeleteZone("Libération brun 2", "east_cake_brown_2", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération brun 2", "west_cake_brown_2", Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(recuperationGateauBrun2.generateObjectif("Brun 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationGateauBrun2.generateMirrorObjectif("Brun 2", objectifsCouleur3000.size()+1, score, 1));

        // Dépose gateaux ménage assiette 2
        score = 0;
        TaskList deposeGateauxAssiette2Trash =  new TaskList(2000);
        deposeGateauxAssiette2Trash.add(
            new GoToAstar("Récupération brun 1", 1400, 1280)
        );
        deposeGateauxAssiette2Trash.add(
            new DeleteZone("Libération brun 1", "east_cake_brown_1", Tache.Mirror.SPECIFIC),
            new DeleteZone("Libération brun 1", "west_cake_brown_1", Tache.Mirror.SPECIFIC)
        );
        deposeGateauxAssiette2Trash.add(
            new GoTo("Récupération brun 1", 1100, 1280)
        );
        deposeGateauxAssiette2Trash.add(
            new GoToAstar("Go assiette 2", 800, 1450)
        );
        deposeGateauxAssiette2Trash.add(
            new Face("Alignement assiette 2", 0, 1450)
        );
        deposeGateauxAssiette2Trash.add(
            new GoTo("Position largage 1", 600, 1450)
        );
        deposeGateauxAssiette2Trash.add(
            new GoToBack("Libération assiette 2", 800, 1450)
        );
        objectifsCouleur0.add(deposeGateauxAssiette2Trash.generateObjectif("Assiette 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(deposeGateauxAssiette2Trash.generateMirrorObjectif("Assiette 2", objectifsCouleur3000.size()+1, score, 1));

        // Dépose gateaux assiette 2
        // Score : (tranche (1) + cerise (3) = 4) * 3
        score = (1 + 3) * 3;
        TaskList deposeGateauxAssiette2 =  new TaskList(2000);
        deposeGateauxAssiette2.add(
            new GoToAstar("Go assiette 2", 500, 1200)
        );
        deposeGateauxAssiette2.add(
            new Face("Alignement assiette 2", 0, 1200)
        );
        deposeGateauxAssiette2.add(
            new GoTo("Position largage 1", 250, 1200)
        );
        deposeGateauxAssiette2.add(
            new Manipulation("Depilement 1", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette2.add(
            new GoToBack("Position largage 2", 350, 1200)
        );
        deposeGateauxAssiette2.add(
            new Manipulation("Depilement 2", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette2.add(
            new GoToBack("Position largage 3", 450, 1200)
        );
        deposeGateauxAssiette2.add(
            new Manipulation("Depilement 3", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette2.add(
            new GoToBack("Libération assiette 2", 650, 1200)
        );
        objectifsCouleur0.add(deposeGateauxAssiette2.generateObjectif("Assiette 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(deposeGateauxAssiette2.generateMirrorObjectif("Assiette 2", objectifsCouleur3000.size()+1, score, 1));

        // Dépose gateaux assiette 3
        // Score : (tranche (1) + cerise (3) = 4) * 3
        score = (1 + 3) * 3;
        TaskList deposeGateauxAssiette3 =  new TaskList(2000);
        deposeGateauxAssiette3.add(
            new GoToAstar("Go assiette 3", 1000, 1550)
        );
        deposeGateauxAssiette3.add(
            new Face("Alignement assiette 3", 1000, 2000)
        );
        deposeGateauxAssiette3.add(
            new GoTo("Position largage 1", 1000, 1750)
        );
        deposeGateauxAssiette3.add(
            new Manipulation("Depilement 1", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette3.add(
            new GoToBack("Position largage 2", 1000, 1650)
        );
        deposeGateauxAssiette3.add(
            new Manipulation("Depilement 2", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette3.add(
            new GoToBack("Position largage 3", 1000, 1550)
        );
        deposeGateauxAssiette3.add(
            new Manipulation("Depilement 3", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette3.add(
            new GoToBack("Libération assiette 3", 1000, 1450)
        );
        objectifsCouleur0.add(deposeGateauxAssiette3.generateObjectif("assiette 3", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(deposeGateauxAssiette3.generateMirrorObjectif("assiette 3", objectifsCouleur3000.size()+1, score, 1));

        // Dépose gateaux assiette 4
        // Score : (tranche (1) + cerise (3) = 4) * 3
        score = (1 + 3) * 3;
        TaskList deposeGateauxAssiette4 =  new TaskList(2000);
        deposeGateauxAssiette4.add(
            new GoToAstar("Go assiette 4", 1750, 450)
        );
        deposeGateauxAssiette4.add(
            new Face("Alignement assiette 4", 1750, 0)
        );
        deposeGateauxAssiette4.add(
            new GoTo("Position largage 1", 1750, 250)
        );
        deposeGateauxAssiette4.add(
            new Manipulation("Depilement 1", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette4.add(
            new GoToBack("Position largage 2", 1750, 350)
        );
        deposeGateauxAssiette4.add(
            new Manipulation("Depilement 2", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette4.add(
            new GoToBack("Position largage 3", 1750, 450)
        );
        deposeGateauxAssiette4.add(
            new Manipulation("Depilement 3", ActionFileBinder.ActionFile.ROB_DEPILER_TRANCHE.ordinal())
        );
        deposeGateauxAssiette4.add(
            new GoToBack("Libération assiette 4", 1750, 600)
        );
        deposeGateauxAssiette4.add(
            new GoTo("Libération assiette 4", 1600, 600)
        );
        objectifsCouleur0.add(deposeGateauxAssiette4.generateObjectif("assiette 4", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(deposeGateauxAssiette4.generateMirrorObjectif("assiette 4", objectifsCouleur3000.size()+1, score, 1));

        // Les roues dans le plat 1
        // Score = 15 (pour les 2 robots)
        score = 15;
        TaskList finirPlat1 =  new TaskList(2000);
        finirPlat1.add(
            new GoToAstar("Go assiette 1", 2500, 1500)
        );
        objectifsCouleur0.add(finirPlat1.generateObjectif("roues dans le plats", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(finirPlat1.generateMirrorObjectif("roues dans le plats", objectifsCouleur3000.size()+1, score, 1));

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

        LoggerFactory.setDefaultLevel(Level.ERROR);
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