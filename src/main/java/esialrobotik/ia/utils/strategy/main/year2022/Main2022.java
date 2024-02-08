package esialrobotik.ia.utils.strategy.main.year2022;

import esialrobotik.ia.actions.a2022.ActionFileBinder;
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
        recuperationDistributeurCentral0.add(
            new Go("Step de départ bizarre", 1)
        );
        recuperationDistributeurCentral0.add(
            new GoToAstar("Placement distributeur central", 300, 1290, Tache.Mirror.NONE)
        );
        recuperationDistributeurCentral0.add(
            new Face("Placement distributeur central", 0, 1290, Tache.Mirror.NONE)
        );
        recuperationDistributeurCentral0.add(
            new Manipulation("Preparer ramassage distributeur central", ActionFileBinder.ActionFile.FENWICK_OUT.ordinal())
        );
        recuperationDistributeurCentral0.add(
            new GoTo("Ramassage distributeur central", 230, 1290, Tache.Mirror.NONE)
        );
        recuperationDistributeurCentral0.add(
            new Go("Ramassage distributeur central", 30, 200)
        );
        recuperationDistributeurCentral0.add(
            new Manipulation("Ramassage distributeur central", ActionFileBinder.ActionFile.FENWICK_IN.ordinal())
        );
        recuperationDistributeurCentral0.add(
            new Go("Sortie distributeur central", -100)
        );
        objectifsCouleur0.add(recuperationDistributeurCentral0.generateObjectif("Distributeur central", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationDistributeurCentral0.generateMirrorObjectif("Distributeur central", objectifsCouleur3000.size()+1, score, 1));

        /**
         * Largage des échantillons du fenwick dans le campement
         * - 1 point par échantillon dans le campement;
         * - 1 point supplémentaire par échantillon face trésor et trié dans le campement;
         * Score = 6
         */
        score = 6;
        TaskList largageDistributeurCentral0 =  new TaskList();
        largageDistributeurCentral0.add(
            new GoToAstar("Placement campement", 900, 450, Tache.Mirror.SPECIFIC),
            new GoToAstar("Placement campement", 850, 2550, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurCentral0.add(
            new GoTo("Placement campement largage rouge", 900, 300, Tache.Mirror.SPECIFIC), 
            new GoTo("Placement campement largage rouge", 850, 2700, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurCentral0.add(
            new Manipulation("Largage campement rouge", ActionFileBinder.ActionFile.FENWICK_RED.ordinal())
        );
        largageDistributeurCentral0.add(
            new AddZone("Echantillon central déposé rouge", "0_SampleCentralRed", Tache.Mirror.SPECIFIC), 
            new AddZone("Echantillon central déposé rouge", "3000_SampleCentralRed", Tache.Mirror.SPECIFIC)
        );
        largageDistributeurCentral0.add(
            new GoToBack("Placement campement largage vert", 900, 450, Tache.Mirror.SPECIFIC), 
            new GoToBack("Placement campement largage vert", 850, 2550, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurCentral0.add(
            new Manipulation("Largage campement vert", ActionFileBinder.ActionFile.FENWICK_GREEN.ordinal())
        );
        largageDistributeurCentral0.add(
            new AddZone("Echantillon central déposé vert", "0_SampleCentralGreen", Tache.Mirror.SPECIFIC), 
            new AddZone("Echantillon central déposé vert", "3000_SampleCentralGreen", Tache.Mirror.SPECIFIC)
        );
        largageDistributeurCentral0.add(
            new GoToBack("Placement campement largage bleu", 900, 600, Tache.Mirror.SPECIFIC), 
            new GoToBack("Placement campement largage bleu", 850, 2400, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurCentral0.add(
            new Manipulation("Largage campement bleu", ActionFileBinder.ActionFile.FENWICK_BLUE.ordinal())
        );
        largageDistributeurCentral0.add(
            new AddZone("Echantillon central déposé bleu", "0_SampleCentralBlue", Tache.Mirror.SPECIFIC),
            new AddZone("Echantillon central déposé bleu", "3000_SampleCentralBlue", Tache.Mirror.SPECIFIC)
        );
        largageDistributeurCentral0.add(
            new GoToBack("Sortie campement", 900, 650, Tache.Mirror.SPECIFIC), 
            new GoToBack("Sortie campement", 850, 2350, Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(largageDistributeurCentral0.generateObjectif("Largage Distributeur central", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(largageDistributeurCentral0.generateMirrorObjectif("Largage Distributeur central", objectifsCouleur3000.size()+1, score, 1));

        /**
         * On va vider le distributeur latéral
         * - 1 point pour chaque échantillon enlevé d’un distributeur du côté de l’équipe (incluant le distributeur
         * partagé et l’abri de chantier);
         * Score = 3
         */
        score = 3;
        TaskList recuperationDistributeurLateral0 =  new TaskList();
        recuperationDistributeurLateral0.add(
            new GoToAstar("Placement distributeur latéral", 1300, 400, Tache.Mirror.SPECIFIC), 
            new GoToAstar("Placement distributeur latéral", 1200, 2600, Tache.Mirror.SPECIFIC)
        );
        recuperationDistributeurLateral0.add(
            new Face("Alignement distributeur latéral", 1300, 0, Tache.Mirror.SPECIFIC), 
            new Face("Alignement distributeur latéral", 1200, 3000, Tache.Mirror.SPECIFIC)
        );
        recuperationDistributeurLateral0.add(
            new Manipulation("Preparer ramassage distributeur latéral", ActionFileBinder.ActionFile.FENWICK_OUT.ordinal())
        );
        recuperationDistributeurLateral0.add(
            new GoTo("Ramassage distributeur latéral", 1300, 230, Tache.Mirror.SPECIFIC), 
            new GoTo("Ramassage distributeur latéral", 1200, 2770, Tache.Mirror.SPECIFIC)
        );
        recuperationDistributeurLateral0.add(
            new Go("Ramassage distributeur latéral", 30, 200)
        );
        recuperationDistributeurLateral0.add(
            new Manipulation("Ramassage distributeur latéral", ActionFileBinder.ActionFile.FENWICK_IN.ordinal())
        );
        recuperationDistributeurLateral0.add(
            new Go("Sortie distributeur latéral", -200)
        );
        objectifsCouleur0.add(recuperationDistributeurLateral0.generateObjectif("Distributeur latéral", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(recuperationDistributeurLateral0.generateMirrorObjectif("Distributeur latéral", objectifsCouleur3000.size()+1, score, 1));

        /**
         * Largage des échantillons du fenwick dans le campement
         * - 1 point par échantillon dans le campement;
         * - 1 point supplémentaire par échantillon face trésor et trié dans le campement;
         * Score = 6
         */
        score = 6;
        TaskList largageDistributeurLateral0 =  new TaskList();
        largageDistributeurLateral0.add(
            new GoTo("Placement campement", 1200, 600)
        );
        largageDistributeurLateral0.add(
            new GoTo("Placement campement", 720, 600, Tache.Mirror.SPECIFIC), 
            new GoTo("Placement campement", 1030, 2400, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurLateral0.add(
            new GoTo("Placement campement largage rouge", 720, 300, Tache.Mirror.SPECIFIC), 
            new GoTo("Placement campement largage rouge", 1030, 2700, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurLateral0.add(
            new Manipulation("Largage campement rouge", ActionFileBinder.ActionFile.FENWICK_RED.ordinal())
        );
        largageDistributeurLateral0.add(
            new AddZone("Echantillon Lateral déposé rouge", "0_SampleLateralRed", Tache.Mirror.SPECIFIC), 
            new AddZone("Echantillon Lateral déposé rouge", "3000_SampleLateralRed", Tache.Mirror.SPECIFIC)
        );
        largageDistributeurLateral0.add(
            new GoToBack("Placement campement largage vert", 720, 450, Tache.Mirror.SPECIFIC), 
            new GoToBack("Placement campement largage vert", 1030, 2550, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurLateral0.add(
            new Manipulation("Largage campement vert", ActionFileBinder.ActionFile.FENWICK_GREEN.ordinal())
        );
        largageDistributeurLateral0.add(
            new AddZone("Echantillon Lateral déposé vert", "0_SampleLateralGreen", Tache.Mirror.SPECIFIC), 
            new AddZone("Echantillon Lateral déposé vert", "3000_SampleLateralGreen", Tache.Mirror.SPECIFIC)
        );
        largageDistributeurLateral0.add(
            new GoToBack("Placement campement largage bleu", 720, 600, Tache.Mirror.SPECIFIC), 
            new GoToBack("Placement campement largage bleu", 1030, 2400, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurLateral0.add(
            new Manipulation("Largage campement bleu", ActionFileBinder.ActionFile.FENWICK_BLUE.ordinal())
        );
        largageDistributeurLateral0.add(
            new AddZone("Echantillon Lateral déposé bleu", "0_SampleLateralBlue", Tache.Mirror.SPECIFIC), 
            new AddZone("Echantillon Lateral déposé bleu", "3000_SampleLateralBlue", Tache.Mirror.SPECIFIC)
        );
        largageDistributeurLateral0.add(
            new GoToBack("Sortie campement", 720, 630, Tache.Mirror.SPECIFIC), 
            new GoToBack("Sortie campement", 1030, 2370, Tache.Mirror.SPECIFIC)
        );
        largageDistributeurLateral0.add(
            new GoTo("Sortie campement", 900, 650, Tache.Mirror.SPECIFIC), 
            new GoTo("Sortie campement", 1030, 2370, Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(largageDistributeurLateral0.generateObjectif("Largage Distributeur central", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(largageDistributeurLateral0.generateMirrorObjectif("Largage Distributeur central", objectifsCouleur3000.size()+1, score, 1));

        /**
         * Carrés de fouilles
         * - 5 points pour chaque carré de fouille basculé à la couleur de l’équipe;
         * - 5 points supplémentaires si au moins un carré de fouille à la couleur de l’équipe est basculé, et que le
         * carré rouge du côté de l’équipe n’est pas basculé;
         * Score = 5 * 4 + 5 = 25
         */
        score = 5;
        TaskList carresFouille1_0 =  new TaskList();
        carresFouille1_0.add(
            new GoToAstar("Placement Carré 1", 1750, 670)
        );
        carresFouille1_0.add(
            new Face("Alignement Carré 1", 1750, 3000)
        );
        carresFouille1_0.add(
            new Manipulation("Carré 1", ActionFileBinder.ActionFile.FENWICK_FOUILLE_DROITE_1.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 1", ActionFileBinder.ActionFile.FENWICK_FOUILLE_GAUCHE_1.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(carresFouille1_0.generateObjectif("Carré de fouille 1", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(carresFouille1_0.generateMirrorObjectif("Carré de fouille 1", objectifsCouleur3000.size()+1, score, 1));

        score = 10; // On le retourne toujours, donc on compte le bonus ici
        TaskList carresFouille2_0 =  new TaskList();
        carresFouille2_0.add(
            new GoToAstar("Placement Carré 2", 1750, 850)
        );
        carresFouille2_0.add(
            new Face("Alignement Carré 2", 1750, 3000)
        );
        carresFouille2_0.add(
            new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_OUT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_OUT.ordinal(), Tache.Mirror.SPECIFIC)
        );
        carresFouille2_0.add(
            new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_IN.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 2", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_IN.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(carresFouille2_0.generateObjectif("Carré de fouille 2", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(carresFouille2_0.generateMirrorObjectif("Carré de fouille 2", objectifsCouleur3000.size()+1, score, 1));

        score = 5;
        TaskList carresFouille3_0 =  new TaskList();
        carresFouille3_0.add(
            new GoToAstar("Placement Carré 3", 1750, 1040)
        );
        carresFouille3_0.add(
            new Face("Placement Carré 3", 1750, 3000)
        );
        carresFouille3_0.add(
            new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_OUT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_OUT.ordinal(), Tache.Mirror.SPECIFIC)
        );
        carresFouille3_0.add(
            new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_IN.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 3", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_IN.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(carresFouille3_0.generateObjectif("Carré de fouille 3", objectifsCouleur0.size()+1, score, 1, "fouille1OK"));
        objectifsCouleur3000.add(carresFouille3_0.generateMirrorObjectif("Carré de fouille 3", objectifsCouleur3000.size()+1, score, 1, "fouille1OK"));

        TaskList carresFouille4_0 =  new TaskList();
        carresFouille4_0.add(
            new GoToAstar("Placement Carré 4", 1750, 1220)
        );
        carresFouille4_0.add(
            new Face("Placement Carré 4", 1750, 3000)
        );
        carresFouille4_0.add(
            new Manipulation("Carré 4", ActionFileBinder.ActionFile.FENWICK_FOUILLE_DROITE_4.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 4", ActionFileBinder.ActionFile.FENWICK_FOUILLE_GAUCHE_4.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(carresFouille4_0.generateObjectif("Carré de fouille 4", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(carresFouille4_0.generateMirrorObjectif("Carré de fouille 4", objectifsCouleur3000.size()+1, score, 1));

        TaskList carresFouille5_0 =  new TaskList();
        carresFouille5_0.add(
            new GoToAstar("Placement Carré 5", 1750, 1410)
        );
        carresFouille5_0.add(
            new Face("Placement Carré 5", 1750, 3000)
        );
        carresFouille5_0.add(
            new Manipulation("Carré 5", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_OUT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 5", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_OUT.ordinal(), Tache.Mirror.SPECIFIC)
        );
        carresFouille5_0.add(
            new Manipulation("Carré 5", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_IN.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 5", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_IN.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(carresFouille5_0.generateObjectif("Carré de fouille 5", objectifsCouleur0.size()+1, score, 1, "fouille4OK"));
        objectifsCouleur3000.add(carresFouille5_0.generateMirrorObjectif("Carré de fouille 5", objectifsCouleur3000.size()+1, score, 1, "fouille4OK"));

        TaskList carresFouille6_0 =  new TaskList();
        carresFouille6_0.add(
            new GoToAstar("Placement Carré 6", 1750, 1590)
        );
        carresFouille6_0.add(
            new Face("Placement Carré 6", 1750, 3000)
        );
        carresFouille6_0.add(
            new Manipulation("Carré 6", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_OUT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 6", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_OUT.ordinal(), Tache.Mirror.SPECIFIC)
        );
        carresFouille6_0.add(
            new Manipulation("Carré 6", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_IN.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 6", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_IN.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(carresFouille6_0.generateObjectif("Carré de fouille 6", objectifsCouleur0.size()+1, score, 1, "fouille4OK"));
        objectifsCouleur3000.add(carresFouille6_0.generateMirrorObjectif("Carré de fouille 6", objectifsCouleur3000.size()+1, score, 1, "fouille4OK"));

        TaskList carresFouille7_0 =  new TaskList();
        carresFouille7_0.add(
            new GoToAstar("Placement Carré 7", 1750, 1780)
        );
        carresFouille7_0.add(
            new Face("Placement Carré 7", 1750, 3000)
        );
        carresFouille7_0.add(
            new Manipulation("Carré 7", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_OUT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 7", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_OUT.ordinal(), Tache.Mirror.SPECIFIC)
        );
        carresFouille7_0.add(
            new Manipulation("Carré 7", ActionFileBinder.ActionFile.FENWICK_BRAS_DROIT_IN.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Carré 7", ActionFileBinder.ActionFile.FENWICK_BRAS_GAUCHE_IN.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(carresFouille7_0.generateObjectif("Carré de fouille 7", objectifsCouleur0.size()+1, score, 1, "fouille4KO"));
        objectifsCouleur3000.add(carresFouille7_0.generateMirrorObjectif("Carré de fouille 7", objectifsCouleur3000.size()+1, score, 1, "fouille4KO"));

        /**
         * Rangement
         * - 20 points si tous les robots de l’équipe sont dans le campement ou la zone de fouille;
         * Score = 20
         */
        score = 20;
        TaskList rangement0 =  new TaskList();
        rangement0.add(
            new GoToAstar("Mise en place rangement zone de fouille", 1600, 1350)
        );
        rangement0.add(
            new GoTo("Mise en place rangement zone de fouille", 1600, 1000)
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

        LoggerFactory.init(Level.ERROR);
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
