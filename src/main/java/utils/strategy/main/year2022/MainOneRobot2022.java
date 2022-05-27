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

public class MainOneRobot2022 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Jaune, 3000 = Violet
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /**
         * Swap statuette
         * - 2 points pour la dépose de la statuette sur le piédestal pendant le temps de préparation;
         * - 5 points si la statuette n’est plus présente en fin de match sur le piédestal;
         * - 2 points si l’équipe dépose une vitrine pendant le temps de préparation;
         * Score = 9
         */
        int score = 9;
        TaskList recuperationStatuette0 =  new TaskList();
        recuperationStatuette0.add(
            new Go("Step de départ bizarre", 1)
        );
        recuperationStatuette0.add(
                new GoTo("Sortie de zone interdite", 600, 250)
        );
        recuperationStatuette0.add(
            new GoToAstar("Placement chantier récupération statuette", 1500, 400, Tache.Mirror.SPECIFIC),
            new GoToAstar("Placement chantier récupération statuette", 1500, 2450, Tache.Mirror.SPECIFIC)
        );
        recuperationStatuette0.add(
            new GoTo("Placement chantier récupération statuette", 1560, 350, Tache.Mirror.SPECIFIC),
            new GoTo("Placement chantier récupération statuette", 1650, 2560, Tache.Mirror.SPECIFIC)
        );
        recuperationStatuette0.add(
            new Face("Alignement récupération statuette", 1650, 260, Tache.Mirror.SPECIFIC),
            new Face("Alignement récupération statuette", 1750, 2660, Tache.Mirror.SPECIFIC)
        );
        recuperationStatuette0.add(
            new Manipulation("Prise statuette", ActionFileBinder.ActionFile.FENWICK_SOLO_GET_STATUETTE.ordinal())
        );
        recuperationStatuette0.add(
            new Go("Sortie chantier", -100)
        );
        objectifsCouleur0.add(recuperationStatuette0.generateObjectif("Récupération statuette", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationStatuette0.generateMirrorObjectif("Récupération statuette", objectifsCouleur3000.size()+1, score, 1));

        /**
         * Dépose statuette dans la vitrine
         * - 15 points si la statuette est présente en fin de match dans la vitrine;
         * - 5 points supplémentaires si la vitrine est activée;
         * Score = 20
         */
        score = 20;
        TaskList deposeStatuette0 = new TaskList();
        deposeStatuette0.add(
            new GoToAstar("Déplacement exposition", 400, 260, Tache.Mirror.SPECIFIC),
            new GoToAstar("Déplacement exposition", 400, 2780, Tache.Mirror.SPECIFIC)
        );
        deposeStatuette0.add(
            new Manipulation("On remonte le bras", ActionFileBinder.ActionFile.FENWICK_SOLO_ASCENSCEUR_GET_STATUETTE_TOP.ordinal())
        );
        deposeStatuette0.add(
            new GoTo("Déplacement exposition", 200, 260, Tache.Mirror.SPECIFIC),
            new GoTo("Déplacement exposition", 200, 2780, Tache.Mirror.SPECIFIC)
        );
        deposeStatuette0.add(
            new Face("Déplacement exposition", 0, 260, Tache.Mirror.SPECIFIC),
            new Face("Déplacement exposition", 0, 2780, Tache.Mirror.SPECIFIC)
        );
        deposeStatuette0.add(
            new SetSpeed("On ralentit", 20)
        );
        deposeStatuette0.add(
            new GoTo("Déplacement exposition", 160, 260, Tache.Mirror.SPECIFIC),
            new GoTo("Déplacement exposition", 160, 2780, Tache.Mirror.SPECIFIC)
        );
        deposeStatuette0.add(
            new Manipulation("Pose statuette", ActionFileBinder.ActionFile.FENWICK_SOLO_PUT_STATUETTE.ordinal())
        );
        deposeStatuette0.add(
                new SetSpeed("On remet bien", 100)
        );
        deposeStatuette0.add(
            new Go("Sortie exposition", -200)
        );
        objectifsCouleur0.add(deposeStatuette0.generateObjectif("Dépose statuette", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(deposeStatuette0.generateMirrorObjectif("Dépose statuette", objectifsCouleur3000.size()+1, score, 1));

        /**
         * Swap statuette
         * - 10 points si la réplique est présente en fin de match sur le piédestal;
         * Score = 10
         */
        score = 10;
        TaskList deposeFake0 =  new TaskList();
        deposeFake0.add(
            new GoToAstar("Placement chantier dépose fake", 1520, 420, Tache.Mirror.SPECIFIC),
            new GoToAstar("Placement chantier dépose fake", 1520, 2470, Tache.Mirror.SPECIFIC)
        );
        deposeFake0.add(
            new GoTo("Placement chantier dépose fake", 1580, 370, Tache.Mirror.SPECIFIC),
            new GoTo("Placement chantier dépose fake", 1670, 2580, Tache.Mirror.SPECIFIC)
        );
        deposeFake0.add(
            new Face("Alignement dépose fake", 1670, 280, Tache.Mirror.SPECIFIC),
            new Face("Alignement dépose fake", 1770, 2680, Tache.Mirror.SPECIFIC)
        );
        deposeFake0.add(
            new Manipulation("Dépose fake", ActionFileBinder.ActionFile.FENWICK_SOLO_PUT_FAKE.ordinal())
        );
        deposeFake0.add(
            new Go("Sortie chantier", -100)
        );
        objectifsCouleur0.add(deposeFake0.generateObjectif("Dépose fake", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(deposeFake0.generateMirrorObjectif("Dépose fake", objectifsCouleur3000.size()+1, score, 1));

        /**
         * Carrés de fouilles
         * - 5 points pour chaque carré de fouille basculé à la couleur de l’équipe;
         * - 5 points supplémentaires si au moins un carré de fouille à la couleur de l’équipe est basculé, et que le
         * carré rouge du côté de l’équipe n’est pas basculé;
         * Score = 5 * 4 + 5 = 25
         */
        score = 0;
        TaskList carresFouille1_0 =  new TaskList();
        carresFouille1_0.add(
            new GoTo("Placement Carré 1", 1760, 600)
        );
        carresFouille1_0.add(
            new GoTo("Placement Carré 1", 1760, 1000)
        );
        carresFouille1_0.add(
            new GoToBack("Placement Carré 1", 1800, 640)
        );
        carresFouille1_0.add(
            new Face("Alignement Carré 1", 1800, 3000)
        );
        carresFouille1_0.add(
            new Manipulation("Carré 1", ActionFileBinder.ActionFile.FENWICK_SONDE_DROITE_OUT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 1", ActionFileBinder.ActionFile.FENWICK_SONDE_GAUCHE_OUT.ordinal(), Tache.Mirror.SPECIFIC)
        );
        carresFouille1_0.add(
                new Manipulation("Carré 1", ActionFileBinder.ActionFile.FENWICK_SONDE_DROITE_IN.ordinal(), Tache.Mirror.SPECIFIC),
                new Manipulation("Carré 1", ActionFileBinder.ActionFile.FENWICK_SONDE_GAUCHE_IN.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(carresFouille1_0.generateObjectif("Carré de fouille 1", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(carresFouille1_0.generateMirrorObjectif("Carré de fouille 1", objectifsCouleur3000.size()+1, score, 1));

        score = 10; // On le retourne toujours, donc on compte le bonus ici
        TaskList carresFouille2_0 =  new TaskList();
        carresFouille2_0.add(
            new GoTo("Placement Carré 2", 1800, 820)
        );
        carresFouille2_0.add(
            new Face("Alignement Carré 2", 1800, 3000)
        );
        carresFouille2_0.add(
            new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_OUT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_OUT.ordinal(), Tache.Mirror.SPECIFIC)
        );
        carresFouille2_0.add(
            new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_IN.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_IN.ordinal(), Tache.Mirror.SPECIFIC)
        );
        carresFouille2_0.add(
                new GoTo("Sortie Carré 2", 1760, 1000)
        );
        objectifsCouleur0.add(carresFouille2_0.generateObjectif("Carré de fouille 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(carresFouille2_0.generateMirrorObjectif("Carré de fouille 2", objectifsCouleur3000.size()+1, score, 1));

//        score = 5;
//        TaskList carresFouille3_0 =  new TaskList();
//        carresFouille3_0.add(
//            new GoTo("Placement Carré 3", 1830, 1010)
//        );
//        carresFouille3_0.add(
//            new Face("Placement Carré 3", 1830, 3000)
//        );
//        carresFouille3_0.add(
//            new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_OUT.ordinal(), Tache.Mirror.SPECIFIC),
//            new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_OUT.ordinal(), Tache.Mirror.SPECIFIC)
//        );
//        carresFouille3_0.add(
//            new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_IN.ordinal(), Tache.Mirror.SPECIFIC),
//            new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_IN.ordinal(), Tache.Mirror.SPECIFIC)
//        );
//        carresFouille3_0.add(
//            new GoTo("Sortie Carré 3", 1780, 1200)
//        );
//        objectifsCouleur0.add(carresFouille3_0.generateObjectif("Carré de fouille 3", objectifsCouleur0.size()+1, score, 1, "fouille1OK"));
//        objectifsCouleur3000.add(carresFouille3_0.generateMirrorObjectif("Carré de fouille 3", objectifsCouleur3000.size()+1, score, 1, "fouille1OK"));

        /**
         * Rangement
         * - 20 points si tous les robots de l’équipe sont dans le campement ou la zone de fouille;
         * Score = 20
         */
        score = 20;
        TaskList rangement0 =  new TaskList();
        rangement0.add(
            new GoTo("Mise en place rangement zone de fouille", 1500, 950)
        );
        objectifsCouleur0.add(rangement0.generateObjectif("Rangement", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(rangement0.generateMirrorObjectif("Rangement", objectifsCouleur3000.size()+1, score, 1));

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/2022/configCollection.json")) {
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
