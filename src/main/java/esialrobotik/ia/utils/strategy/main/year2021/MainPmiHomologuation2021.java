package esialrobotik.ia.utils.strategy.main.year2021;

import esialrobotik.ia.actions.a2020.ActionFileBinder;
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
import java.util.ArrayList;
import java.util.List;

public class MainPmiHomologuation2021 {

    //    public static void mainStratPrincipal(String... arg) throws Exception {
    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Jaune
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /*
         * On attrape et on marque les bouée 1 et 2
         * Score = 6
         *  - 1 point par bouée dans le port => 2
         *  - 1 point par bouée dans le bon chenal => 2
         *  - une paire dans les chenaux => 2
         */
        int score = 6;
        TaskList recuperationBouees1_2 =  new TaskList();
        TaskList recuperationBouees1_2_3000 =  new TaskList();
        recuperationBouees1_2.add(new Go("Step de départ bizarre", 1));
        recuperationBouees1_2.add(new GoTo("Mise en position bouée 1", 600, 240));
        recuperationBouees1_2.add(new Face("Alignement bouée 1", 0, 240));
        int mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Preparer ramassage bouée 1", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Preparer ramassage bouée 1", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees1_2.add(new GoTo("Mise en position récupération bouée 1", 516, 240));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Ramassage bouée 1 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Ramassage bouée 1 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Ramassage bouée 1 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Ramassage bouée 1 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Ramassage bouée 1 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Ramassage bouée 1 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees1_2.add(new Go("Retour au centre", -300));
        recuperationBouees1_2.add(new DeleteZone("Suppression zone bouée 1", "bouee1"));
        recuperationBouees1_2.add(new GoTo("Mise en position bouée 2", 900, 240));
        recuperationBouees1_2.add(new Face("Alignement bouée 2", 2000, 240));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Preparer ramassage bouée 2", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Preparer ramassage bouée 2", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees1_2.add(new GoTo("Mise en position récupération bouée 2", 1084, 240));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Ramassage bouée 2 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Ramassage bouée 2 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Ramassage bouée 2 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Ramassage bouée 2 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Ramassage bouée 2 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Ramassage bouée 2 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees1_2.add(new GoToBack("Mise en position largage bouée 1", 980, 240));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Largage bouée 1 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Largage bouée 1 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees1_2.add(new Go("Retour au centre", -250));
        recuperationBouees1_2.add(new DeleteZone("Suppression zone bouée 2", "bouee2"));
        recuperationBouees1_2.add(new GoTo("Mise en position largage bouée 2", 620, 240));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Largage bouée 2 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Largage bouée 2 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees1_2.add(new GoToBack("Retour au centre", 750, 240));
        recuperationBouees1_2.add(new AddZone("Blocage du chenal Sud", "chenal_depart_s"));
        recuperationBouees1_2.add(new AddZone("Blocage du chenal Nord", "chenal_depart_n"));
        Objectif objectifRecuperationBouees1_2_0 = new Objectif("Bouées 1 et 2", objectifsCouleur0.size()+1, score, 1, recuperationBouees1_2);
        Objectif objectifRecuperationBouees1_2_3000 = new Objectif("Bouées 1 et 2", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationBouees1_2_3000.generateMirror(objectifRecuperationBouees1_2_0.taches, recuperationBouees1_2_3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationBouees1_2_0);
        objectifsCouleur3000.add(objectifRecuperationBouees1_2_3000);

        /*
         * On marque la bouée 3, on allume le phare, on récupère la bouée 5
         * Score = 19
         *  - 1 point par bouée dans le port => 1
         *  - 1 point par bouée dans le bon chenal => 1
         *  - une paire dans les chenaux => 2
         *  - phare => 15
         */
        score = 19;
        TaskList recuperationBouees3 =  new TaskList();
        TaskList recuperationBouees3_3000 =  new TaskList();
        recuperationBouees3.add(new GoToAstar("Mise en position bouée 3", 520, 700));
        recuperationBouees3.add(new SetSpeed("Vitesse réduite", 25));
        recuperationBouees3.add(new GoToBack("Marquage bouée 3", 520, 500));
        recuperationBouees3.add(new GoToBack("Marquage bouée 3", 520, 220));
        recuperationBouees3.add(new SetSpeed("Vitesse normale", 100));
        recuperationBouees3.add(new GoTo("Sortie de la zone", 520, 500));
        recuperationBouees3.add(new DeleteZone("Suppression zone bouée 3", "bouee3"));
        recuperationBouees3.add(new Manipulation("Bras en position d'allumage du phare", ActionFileBinder.ActionFile.PMI_ALLUMER_PHARE.ordinal()));
        recuperationBouees3.add(new GoTo("Direction le phare", 200, 290));
        recuperationBouees3.add(new Face("Alignement phare", 0, 290));
        recuperationBouees3.add(new GoTo("Placement phare", 120, 290));
        recuperationBouees3.add(new SetSpeed("Vitesse réduite", 50));
        recuperationBouees3.add(new Go("Allumage phare", 20, 500));
        recuperationBouees3.add(new SetSpeed("Vitesse normale", 100));
        recuperationBouees3.add(new GoToBack("Retour phare", 200, 290));
        recuperationBouees3.add(new GoTo("Position d'attente", 650, 900));
        recuperationBouees3.add(new Face("Position d'attente", 650, 3000));
        recuperationBouees3.add(new WaitChrono("Attente", 80));
        Objectif objectifRecuperationBouees3_0 = new Objectif("Bouée 3 + Phare + Bouée 5", objectifsCouleur0.size()+1, score, 1, recuperationBouees3);
        Objectif objectifRecuperationBouees3_3000 = new Objectif("Bouée 3 + Phare + Bouée 5", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationBouees3_3000.generateMirror(objectifRecuperationBouees3_0.taches, recuperationBouees3_3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationBouees3_0);
        objectifsCouleur3000.add(objectifRecuperationBouees3_3000);

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/2021/configCollectionPmi.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Strategie startBoussole = MainPmiHomologuation2021.mainBoussole();
//        strat.couleur0.add(startBoussole.couleur0.get(0)); // Nord
        strat.couleur0.add(startBoussole.couleur0.get(1)); // Sud

        // Ajout du clean de princess pour les tests
        TaskList fakeZoneForSimu = new TaskList();
        fakeZoneForSimu.add(new DeleteZone("Suppression zone bouée 4", "bouee4"));
        recuperationBouees3.addAll(fakeZoneForSimu);

        System.out.println("Test de la strat");
        try {
            LoggerFactory.setDefaultLevel(Level.ERROR);
            Table table = new Table("config/2021/table0.tbl");
            table.loadJsonFromFile("config/2021/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(1020, 240, Math.PI);
            StringBuilder stratSimu = new StringBuilder("[");
            stratSimu.append("{ \"task\":\"Position de départ\",\"command\":\"start\",\"position\":" + startPoint.toJson() + "},");
            for (Objectif objectif : strat.couleur0) {
                for (Tache task: objectif.taches) {
                    task.pathFinding = pathFinding;
                    String execution = task.execute(startPoint);
                    System.out.println(execution);
                    if (!fakeZoneForSimu.contains(task)) {
                        stratSimu.append(execution);
                    }
                    startPoint = task.getEndPoint();
                }
            }
            stratSimu.deleteCharAt(stratSimu.length()-1);
            stratSimu.append("]");
            try (PrintWriter stratFile = new PrintWriter("config/2021/strat_simu_pmi.json")) {
                stratFile.println(stratSimu);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Strategie mainBoussole() {
        System.out.println("Génération de l'action pour arriver à bon port");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Jaune
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /**
         * On se positionne entre les deux port et on va dans le bon
         * Score = 10
         */
        int score = 10;
        TaskList tachesPortNord =  new TaskList();
        tachesPortNord.add(new GoToAstar("On se gare", 300, 700));
        tachesPortNord.add(new GoTo("On se gare", 300, 400));
        Objectif objectifPortN0 = new Objectif("Port Nord", objectifsCouleur0.size()+1, score, 1, tachesPortNord);
        Objectif objectifPortN3000 = new Objectif("Port Nord", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifPortN3000.generateMirror(objectifPortN0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifPortN0);
        objectifsCouleur3000.add(objectifPortN3000);

        /**
         * On se positionne entre les deux port et on va dans le bon
         * Score = 10
         */
        score = 10;
        TaskList tachesPortSud =  new TaskList();
        tachesPortSud.add(new GoToAstar("On se gare", 1300, 700));
        tachesPortSud.add(new GoTo("On se gare", 1300, 400));
        Objectif objectifPortS0 = new Objectif("Port Sud", objectifsCouleur0.size()+1, score, 1, tachesPortSud);
        Objectif objectifPortS3000 = new Objectif("Port Sud", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifPortS3000.generateMirror(objectifPortS0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifPortS0);
        objectifsCouleur3000.add(objectifPortS3000);

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        System.out.println(strat.toString());

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));

        try (PrintWriter jsonFile = new PrintWriter("config/2021/configCollectionBoussolePmiHomologuation.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return strat;
    }

}
