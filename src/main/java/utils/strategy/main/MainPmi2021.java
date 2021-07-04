package utils.strategy.main;

import actions.a2020.ActionFileBinder;
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

public class MainPmi2021 {

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
         * On marque la bouée 4
         * Score = 2
         *  - 1 point par bouée dans le port => 1
         *  - 1 point par bouée dans le bon chenal => 1
         */
        score = 2;
        TaskList recuperationBouees4 =  new TaskList();
        recuperationBouees4.add(new GoToAstar("Mise en position bouée 4", 1090, 700));
        recuperationBouees4.add(new SetSpeed("Vitesse réduite", 25));
        recuperationBouees4.add(new GoToBack("Marquage bouée 4", 1090, 500));
        recuperationBouees4.add(new GoToBack("Marquage bouée 4", 1090, 220));
        recuperationBouees4.add(new SetSpeed("Vitesse normale", 100));
        recuperationBouees4.add(new GoTo("Sortie de la zone", 1090, 700));
        recuperationBouees4.add(new DeleteZone("Suppression zone bouée 4", "bouee4"));
        Objectif objectifRecuperationBouees4_0 = new Objectif("Bouée 4", objectifsCouleur0.size()+1, score, 1, recuperationBouees4);
        Objectif objectifRecuperationBouees4_3000 = new Objectif("Bouée 4", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationBouees4_3000.generateMirror(objectifRecuperationBouees4_0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationBouees4_0);
        objectifsCouleur3000.add(objectifRecuperationBouees4_3000);

        /*
         * On marque la bouée 3
         * Score = 4
         *  - 1 point par bouée dans le port => 1
         *  - 1 point par bouée dans le bon chenal => 1
         *  - une paire dans les chenaux => 2
         */
        score = 4;
        TaskList recuperationBouees3 =  new TaskList();
        recuperationBouees3.add(new GoToAstar("Mise en position bouée 3", 520, 700));
        recuperationBouees3.add(new SetSpeed("Vitesse réduite", 25));
        recuperationBouees3.add(new GoToBack("Marquage bouée 3", 520, 500));
        recuperationBouees3.add(new GoToBack("Marquage bouée 3", 520, 220));
        recuperationBouees3.add(new SetSpeed("Vitesse normale", 100));
        recuperationBouees3.add(new GoTo("Sortie de la zone", 520, 700));
        recuperationBouees3.add(new DeleteZone("Suppression zone bouée 3", "bouee3"));
        Objectif objectifRecuperationBouees3_0 = new Objectif("Bouée 3", objectifsCouleur0.size()+1, score, 1, recuperationBouees3);
        Objectif objectifRecuperationBouees3_3000 = new Objectif("Bouée 3", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationBouees3_3000.generateMirror(objectifRecuperationBouees3_0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationBouees3_0);
        objectifsCouleur3000.add(objectifRecuperationBouees3_3000);

        /*
         * Ramassage bouées 5
         * Score = 0
         */
        score = 0;
        TaskList recuperationBouees5 =  new TaskList();
        TaskList recuperationBouees5_3000 =  new TaskList();
        recuperationBouees5.add(new GoToAstar("Mise en position bouée 5", 350, 700));
        recuperationBouees5.add(new GoTo("Mise en position bouée 5", 250, 730));
        recuperationBouees5.add(new Face("Alignement bouée 5", 0, 730));
        recuperationBouees5.add(new GoTo("Mise en position ramassage bouée 5", 216, 730));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees5.add(new Manipulation("Ramassage bouée 5 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees5_3000.add(new Manipulation("Ramassage bouée 5 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees5.add(new Manipulation("Ramassage bouée 5 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees5_3000.add(new Manipulation("Ramassage bouée 5 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees5.add(new Manipulation("Ramassage bouée 5 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees5_3000.add(new Manipulation("Ramassage bouée 5 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees5.add(new DeleteZone("Suppression zone bouée 5", "bouee5"));
        Objectif objectifRecuperationBouees5_0 = new Objectif("Récupération Bouée 5", objectifsCouleur0.size()+1, score, 1, recuperationBouees5);
        Objectif objectifRecuperationBouees5_3000 = new Objectif("Récupération Bouée 5", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationBouees5_3000.generateMirror(objectifRecuperationBouees5_0.taches, recuperationBouees5_3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationBouees5_0);
        objectifsCouleur3000.add(objectifRecuperationBouees5_3000);

        /*
         * Ramassage bouées 6
         * Score = 0
         */
        score = 0;
        TaskList recuperationBouees6 =  new TaskList();
        TaskList recuperationBouees6_3000 =  new TaskList();
        recuperationBouees6.add(new GoToAstar("Mise en position bouée 6", 340, 670));
        recuperationBouees6.add(new GoTo("Mise en position bouée 6", 340, 800));
        recuperationBouees6.add(new Face("Alignement bouée 6", 340, 3000));
        recuperationBouees6.add(new GoTo("Mise en position ramassage bouée 6", 340, 840));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees6.add(new Manipulation("Ramassage bouée 6 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees6_3000.add(new Manipulation("Ramassage bouée 6 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees6.add(new Manipulation("Ramassage bouée 6 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees6_3000.add(new Manipulation("Ramassage bouée 6 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees6.add(new Manipulation("Ramassage bouée 6 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees6_3000.add(new Manipulation("Ramassage bouée 6 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees6.add(new DeleteZone("Suppression zone bouée 6", "bouee6"));
        recuperationBouees6.add(new GoTo("Placement pour forcer le chemin de Astar", 500, 1100));
        Objectif objectifRecuperationBouees6_0 = new Objectif("Récupération Bouée 6", objectifsCouleur0.size()+1, score, 1, recuperationBouees6);
        Objectif objectifRecuperationBouees6_3000 = new Objectif("Récupération Bouée 6", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationBouees6_3000.generateMirror(objectifRecuperationBouees6_0.taches, recuperationBouees6_3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationBouees6_0);
        objectifsCouleur3000.add(objectifRecuperationBouees6_3000);

        /*
         * On place les bouées du petit port et on vide les bouées stockées
         * Score = 12
         *  - 1 point par bouée dans le port => 4
         *  - 1 point par bouée dans le bon chenal => 4
         *  - une paire dans les chenaux => 4
         */
        score = 12;
        TaskList petitPort = new TaskList();
        petitPort.add(new GoToAstar("Déplacement petit port", 1450, 1800));
        petitPort.add(new Face("Alignement petit port", 0, 1800));
        petitPort.add(new SetSpeed("Vitesse réduite", 50));
        petitPort.add(new GoToBack("Marquage bouées petit port", 1600, 1800));
        petitPort.add(new GoToBack("Marquage bouées petit port", 1800, 1800));
        petitPort.add(new SetSpeed("Vitesse normale", 100));
        petitPort.add(new DeleteZone("Suppression zone bouée 8", "bouee8"));
        petitPort.add(new DeleteZone("Suppression zone bouée 11", "bouee11"));
        petitPort.add(new GoTo("Placement rotation largage bouée 5 et 6", 1600, 1800));
        petitPort.add(new Face("Alignement largage bouée 5 et 6", 2000, 1800));
        petitPort.add(new GoTo("Placement largage bouée 5 et 6", 1700, 1800));
        petitPort.add(new Manipulation("Largage bouée 5 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_DROIT.ordinal()));
        petitPort.add(new Manipulation("Largage bouée 6 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_GAUCHE.ordinal()));
        petitPort.add(new GoToBack("Sortie petit port", 1450, 1800));
        Objectif objectifPetitPort0 = new Objectif("Petit port", objectifsCouleur0.size()+1, score, 1, petitPort);
        Objectif objectifPetitPort3000 = new Objectif("Petit port", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifPetitPort3000.generateMirror(objectifPetitPort0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifPetitPort0);
        objectifsCouleur3000.add(objectifPetitPort3000);

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("configCollectionPmi.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Strategie startBoussole = MainPmi2021.mainBoussole();
        strat.couleur0.add(startBoussole.couleur0.get(0)); // Nord
//        strat.couleur0.add(startBoussole.couleur0.get(1)); // Sud

        System.out.println("Test de la strat");
        try {
            LoggerFactory.init(Level.OFF);
            Table table = new Table("table0.tbl");
            table.loadJsonFromFile("table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(1020, 240, Math.PI);
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
            try (PrintWriter stratFile = new PrintWriter("strat_simu_pmi.json")) {
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
        tachesPortNord.add(new GoToAstar("On se gare", 200, 500));
        tachesPortNord.add(new GoTo("On se gare", 160, 200));
        tachesPortNord.add(new GoTo("On se gare", 160, 90));
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
        tachesPortSud.add(new GoToAstar("On se gare", 1400, 500));
        tachesPortSud.add(new GoTo("On se gare", 1440, 200));
        tachesPortSud.add(new GoTo("On se gare", 1440, 90));
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

        try (PrintWriter jsonFile = new PrintWriter("configCollectionBoussolePmi.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return strat;
    }

}
