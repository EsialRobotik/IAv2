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
import java.util.ArrayList;
import java.util.List;

public class Main2022 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Jaune, 3000 = Violet
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /**
         * On va vider le distributeur central
         * - 1 point pour chaque échantillon enlevé d’un distributeur du côté de l’équipe (incluant le distributeur
         * partagé et l’abri de chantier);
         * Score = 3
         */
        int score = 3;
        TaskList recuperationDistributeurCentral0 =  new TaskList();
        recuperationDistributeurCentral0.add(new Go("Step de départ bizarre", 1));
        recuperationDistributeurCentral0.add(new GoToAstar("Placement distributeur central", 300, 1290, Tache.Mirror.NONE));
        recuperationDistributeurCentral0.add(new Face("Placement distributeur central", 0, 1290, Tache.Mirror.NONE));
        recuperationDistributeurCentral0.add(new Manipulation("Preparer ramassage distributeur central", ActionFileBinder.ActionFile.FENWICK_OUT.ordinal()));
        recuperationDistributeurCentral0.add(new GoTo("Ramassage distributeur central", 230, 1290, Tache.Mirror.NONE));
        recuperationDistributeurCentral0.add(new Go("Ramassage distributeur central", 30, 200));
        recuperationDistributeurCentral0.add(new Manipulation("Ramassage distributeur central", ActionFileBinder.ActionFile.FENWICK_IN.ordinal()));
        recuperationDistributeurCentral0.add(new Go("Sortie distributeur central", -100));
        Objectif objectifRecuperationDistributeurCentral0 = new Objectif("Distributeur central", objectifsCouleur0.size()+1, score, 1, recuperationDistributeurCentral0);
        Objectif objectifRecuperationDistributeurCentral3000 = new Objectif("Distributeur central", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationDistributeurCentral3000.generateMirror(objectifRecuperationDistributeurCentral0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationDistributeurCentral0);
        objectifsCouleur3000.add(objectifRecuperationDistributeurCentral3000);

        /**
         * Largage des échantillons du fenwick dans le campement
         * - 1 point par échantillon dans le campement;
         * - 1 point supplémentaire par échantillon face trésor et trié dans le campement;
         * Score = 6
         */
        score = 6;
        TaskList largageDistributeurCentral0 =  new TaskList();
        TaskList largageDistributeurCentral3000 =  new TaskList();
        largageDistributeurCentral0.add(new GoToAstar("Placement campement", 900, 450, Tache.Mirror.SPECIFIC));
        largageDistributeurCentral3000.add(new GoToAstar("Placement campement", 850, 2550, Tache.Mirror.SPECIFIC), largageDistributeurCentral0.size());
        largageDistributeurCentral0.add(new GoTo("Placement campement largage rouge", 900, 300, Tache.Mirror.SPECIFIC));
        largageDistributeurCentral3000.add(new GoTo("Placement campement largage rouge", 850, 2700, Tache.Mirror.SPECIFIC), largageDistributeurCentral0.size());
        largageDistributeurCentral0.add(new Manipulation("Largage campement rouge", ActionFileBinder.ActionFile.FENWICK_RED.ordinal()));
        largageDistributeurCentral0.add(new AddZone("Echantillon central déposé rouge", "0_SampleCentralRed", Tache.Mirror.SPECIFIC));
        largageDistributeurCentral3000.add(new AddZone("Echantillon central déposé rouge", "3000_SampleCentralRed", Tache.Mirror.SPECIFIC), largageDistributeurCentral0.size());
        largageDistributeurCentral0.add(new GoToBack("Placement campement largage vert", 900, 450, Tache.Mirror.SPECIFIC));
        largageDistributeurCentral3000.add(new GoToBack("Placement campement largage vert", 850, 2550, Tache.Mirror.SPECIFIC), largageDistributeurCentral0.size());
        largageDistributeurCentral0.add(new Manipulation("Largage campement vert", ActionFileBinder.ActionFile.FENWICK_GREEN.ordinal()));
        largageDistributeurCentral0.add(new AddZone("Echantillon central déposé vert", "0_SampleCentralGreen", Tache.Mirror.SPECIFIC));
        largageDistributeurCentral3000.add(new AddZone("Echantillon central déposé vert", "3000_SampleCentralGreen", Tache.Mirror.SPECIFIC), largageDistributeurCentral0.size());
        largageDistributeurCentral0.add(new GoToBack("Placement campement largage bleu", 900, 600, Tache.Mirror.SPECIFIC));
        largageDistributeurCentral3000.add(new GoToBack("Placement campement largage bleu", 850, 2400, Tache.Mirror.SPECIFIC), largageDistributeurCentral0.size());
        largageDistributeurCentral0.add(new Manipulation("Largage campement bleu", ActionFileBinder.ActionFile.FENWICK_BLUE.ordinal()));
        largageDistributeurCentral0.add(new AddZone("Echantillon central déposé bleu", "0_SampleCentralBlue", Tache.Mirror.SPECIFIC));
        largageDistributeurCentral3000.add(new AddZone("Echantillon central déposé bleu", "3000_SampleCentralBlue", Tache.Mirror.SPECIFIC), largageDistributeurCentral0.size());
        largageDistributeurCentral0.add(new GoToBack("Sortie campement", 900, 650, Tache.Mirror.SPECIFIC));
        largageDistributeurCentral3000.add(new GoToBack("Sortie campement", 850, 2350, Tache.Mirror.SPECIFIC), largageDistributeurCentral0.size());
        Objectif objectifLargageDistributeurCentral0 = new Objectif("Largage Distributeur central", objectifsCouleur0.size()+1, score, 1, largageDistributeurCentral0);
        Objectif objectifLargageDistributeurCentral3000 = new Objectif("Largage Distributeur central", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifLargageDistributeurCentral3000.generateMirror(objectifLargageDistributeurCentral0.taches, largageDistributeurCentral3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifLargageDistributeurCentral0);
        objectifsCouleur3000.add(objectifLargageDistributeurCentral3000);

        /**
         * On va vider le distributeur latéral
         * - 1 point pour chaque échantillon enlevé d’un distributeur du côté de l’équipe (incluant le distributeur
         * partagé et l’abri de chantier);
         * Score = 3
         */
        score = 3;
        TaskList recuperationDistributeurLateral0 =  new TaskList();
        TaskList recuperationDistributeurLateral3000 =  new TaskList();
        recuperationDistributeurLateral0.add(new GoToAstar("Placement distributeur latéral", 1300, 400, Tache.Mirror.SPECIFIC));
        recuperationDistributeurLateral3000.add(new GoToAstar("Placement distributeur latéral", 1200, 2600, Tache.Mirror.SPECIFIC), recuperationDistributeurLateral0.size());
        recuperationDistributeurLateral0.add(new Face("Alignement distributeur latéral", 1300, 0, Tache.Mirror.SPECIFIC));
        recuperationDistributeurLateral3000.add(new Face("Alignement distributeur latéral", 1200, 3000, Tache.Mirror.SPECIFIC), recuperationDistributeurLateral0.size());
        recuperationDistributeurLateral0.add(new Manipulation("Preparer ramassage distributeur latéral", ActionFileBinder.ActionFile.FENWICK_OUT.ordinal()));
        recuperationDistributeurLateral0.add(new GoTo("Ramassage distributeur latéral", 1300, 230, Tache.Mirror.SPECIFIC));
        recuperationDistributeurLateral3000.add(new GoTo("Ramassage distributeur latéral", 1200, 2770, Tache.Mirror.SPECIFIC), recuperationDistributeurLateral0.size());
        recuperationDistributeurLateral0.add(new Go("Ramassage distributeur latéral", 30, 200));
        recuperationDistributeurLateral0.add(new Manipulation("Ramassage distributeur latéral", ActionFileBinder.ActionFile.FENWICK_IN.ordinal()));
        recuperationDistributeurLateral0.add(new Go("Sortie distributeur latéral", -200));
        Objectif objectifRecuperationDistributeurLateral0 = new Objectif("Distributeur latéral", objectifsCouleur0.size()+1, score, 1, recuperationDistributeurLateral0);
        Objectif objectifRecuperationDistributeurLateral3000 = new Objectif("Distributeur latéral", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationDistributeurLateral3000.generateMirror(objectifRecuperationDistributeurLateral0.taches, recuperationDistributeurLateral3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationDistributeurLateral0);
        objectifsCouleur3000.add(objectifRecuperationDistributeurLateral3000);

        /**
         * Largage des échantillons du fenwick dans le campement
         * - 1 point par échantillon dans le campement;
         * - 1 point supplémentaire par échantillon face trésor et trié dans le campement;
         * Score = 6
         */
        score = 6;
        TaskList largageDistributeurLateral0 =  new TaskList();
        TaskList largageDistributeurLateral3000 =  new TaskList();
        largageDistributeurLateral0.add(new GoTo("Placement campement", 1200, 600));
        largageDistributeurLateral0.add(new GoTo("Placement campement", 720, 600, Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new GoTo("Placement campement", 1030, 2400, Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        largageDistributeurLateral0.add(new GoTo("Placement campement largage rouge", 720, 300, Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new GoTo("Placement campement largage rouge", 1030, 2700, Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        largageDistributeurLateral0.add(new Manipulation("Largage campement rouge", ActionFileBinder.ActionFile.FENWICK_RED.ordinal()));
        largageDistributeurLateral0.add(new AddZone("Echantillon Lateral déposé rouge", "0_SampleLateralRed", Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new AddZone("Echantillon Lateral déposé rouge", "3000_SampleLateralRed", Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        largageDistributeurLateral0.add(new GoToBack("Placement campement largage vert", 720, 450, Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new GoToBack("Placement campement largage vert", 1030, 2550, Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        largageDistributeurLateral0.add(new Manipulation("Largage campement vert", ActionFileBinder.ActionFile.FENWICK_GREEN.ordinal()));
        largageDistributeurLateral0.add(new AddZone("Echantillon Lateral déposé vert", "0_SampleLateralGreen", Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new AddZone("Echantillon Lateral déposé vert", "3000_SampleLateralGreen", Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        largageDistributeurLateral0.add(new GoToBack("Placement campement largage bleu", 720, 600, Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new GoToBack("Placement campement largage bleu", 1030, 2400, Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        largageDistributeurLateral0.add(new Manipulation("Largage campement bleu", ActionFileBinder.ActionFile.FENWICK_BLUE.ordinal()));
        largageDistributeurLateral0.add(new AddZone("Echantillon Lateral déposé bleu", "0_SampleLateralBlue", Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new AddZone("Echantillon Lateral déposé bleu", "3000_SampleLateralBlue", Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        largageDistributeurLateral0.add(new GoToBack("Sortie campement", 720, 630, Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new GoToBack("Sortie campement", 1030, 2370, Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        largageDistributeurLateral0.add(new GoTo("Sortie campement", 900, 650, Tache.Mirror.SPECIFIC));
        largageDistributeurLateral3000.add(new GoTo("Sortie campement", 1030, 2370, Tache.Mirror.SPECIFIC), largageDistributeurLateral0.size());
        Objectif objectifLargageDistributeurLateral0 = new Objectif("Largage Distributeur central", objectifsCouleur0.size()+1, score, 1, largageDistributeurLateral0);
        Objectif objectifLargageDistributeurLateral3000 = new Objectif("Largage Distributeur central", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifLargageDistributeurLateral3000.generateMirror(objectifLargageDistributeurLateral0.taches, largageDistributeurLateral3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifLargageDistributeurLateral0);
        objectifsCouleur3000.add(objectifLargageDistributeurLateral3000);

        /**
         * Carrés de fouilles
         * - 5 points pour chaque carré de fouille basculé à la couleur de l’équipe;
         * - 5 points supplémentaires si au moins un carré de fouille à la couleur de l’équipe est basculé, et que le
         * carré rouge du côté de l’équipe n’est pas basculé;
         * Score = 5 * 4 + 5 = 25
         */
        score = 25;
        TaskList carresFouille0 =  new TaskList();
        TaskList carresFouille3000 =  new TaskList();
        // TODO Probablement découper ça et avoir un putain de if, c'est trop dangereux là !!!
        carresFouille0.add(new GoToAstar("Placemnt Carré 1", 1750, 670));
        carresFouille0.add(new Face("Alignement Carré 1", 1750, 3000));
        carresFouille0.add(new Manipulation("Carré 1", ActionFileBinder.ActionFile.FENWICK_RIGTH_ARMS.ordinal(), Tache.Mirror.SPECIFIC));
        carresFouille3000.add(new Manipulation("Carré 1", ActionFileBinder.ActionFile.FENWICK_LEFT_ARMS.ordinal(), Tache.Mirror.SPECIFIC), carresFouille0.size());
        carresFouille0.add(new GoTo("Placemnt Carré 2", 1750, 850));
        carresFouille0.add(new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_RIGTH_ARMS.ordinal(), Tache.Mirror.SPECIFIC));
        carresFouille3000.add(new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_LEFT_ARMS.ordinal(), Tache.Mirror.SPECIFIC), carresFouille0.size());
        carresFouille0.add(new GoTo("Placemnt Carré 3", 1750, 1040));
        carresFouille0.add(new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_RIGTH_ARMS.ordinal(), Tache.Mirror.SPECIFIC));
        carresFouille3000.add(new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_LEFT_ARMS.ordinal(), Tache.Mirror.SPECIFIC), carresFouille0.size());
        carresFouille0.add(new GoTo("Placemnt Carré 4", 1750, 1220));
        carresFouille0.add(new Manipulation("Carré 4", ActionFileBinder.ActionFile.FENWICK_RIGTH_ARMS.ordinal(), Tache.Mirror.SPECIFIC));
        carresFouille3000.add(new Manipulation("Carré 4", ActionFileBinder.ActionFile.FENWICK_LEFT_ARMS.ordinal(), Tache.Mirror.SPECIFIC), carresFouille0.size());
        carresFouille0.add(new GoTo("Placemnt Carré 5", 1750, 1410));
        carresFouille0.add(new Manipulation("Carré 5", ActionFileBinder.ActionFile.FENWICK_RIGTH_ARMS.ordinal(), Tache.Mirror.SPECIFIC));
        carresFouille3000.add(new Manipulation("Carré 5", ActionFileBinder.ActionFile.FENWICK_LEFT_ARMS.ordinal(), Tache.Mirror.SPECIFIC), carresFouille0.size());
        carresFouille0.add(new GoTo("Placemnt Carré 6", 1750, 1590));
        carresFouille0.add(new Manipulation("Carré 6", ActionFileBinder.ActionFile.FENWICK_RIGTH_ARMS.ordinal(), Tache.Mirror.SPECIFIC));
        carresFouille3000.add(new Manipulation("Carré 6", ActionFileBinder.ActionFile.FENWICK_LEFT_ARMS.ordinal(), Tache.Mirror.SPECIFIC), carresFouille0.size());
        carresFouille0.add(new GoTo("Placemnt Carré 7", 1750, 1780));
        carresFouille0.add(new Manipulation("Carré 7", ActionFileBinder.ActionFile.FENWICK_RIGTH_ARMS.ordinal(), Tache.Mirror.SPECIFIC));
        carresFouille3000.add(new Manipulation("Carré 7", ActionFileBinder.ActionFile.FENWICK_LEFT_ARMS.ordinal(), Tache.Mirror.SPECIFIC), carresFouille0.size());
        Objectif objectifCarresFouille0 = new Objectif("Découverte carrés de fouilles", objectifsCouleur0.size()+1, score, 1, carresFouille0);
        Objectif objectifCarresFouille3000 = new Objectif("Découverte carrés de fouilles", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifCarresFouille3000.generateMirror(objectifCarresFouille0.taches, carresFouille3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifCarresFouille0);
        objectifsCouleur3000.add(objectifCarresFouille3000);

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
    }

}
