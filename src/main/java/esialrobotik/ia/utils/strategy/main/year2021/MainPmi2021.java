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

public class MainPmi2021 {

    //    public static void mainStratPrincipal(String... arg) throws Exception {
    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Jaune
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();
        int mirrorId = 0;

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
        mirrorId = recuperationBouees1_2.size() + 1;
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
        recuperationBouees1_2.add(new GoToBack("Mise en position largage bouée 1", 970, 240));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Largage bouée 1 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Largage bouée 1 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees1_2.add(new DeleteZone("Suppression zone bouée 2", "bouee2"));
        recuperationBouees1_2.add(new GoToBack("Mise en position largage bouée 2", 380, 200));
        recuperationBouees1_2.add(new Face("Alignement largage bouée 2", 2000, 200));
        mirrorId = recuperationBouees1_2.size() + 1;
        recuperationBouees1_2.add(new Manipulation("Largage bouée 2 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees1_2_3000.add(new Manipulation("Largage bouée 2 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees1_2.add(new GoToBack("Direction le phare", 200, 180));
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
         *  - phare => 15
         */
        score = 15;
        TaskList recuperationBouees3 =  new TaskList();
        TaskList recuperationBouees3_3000 =  new TaskList();
        recuperationBouees3.add(new GoTo("Direction le phare", 200, 290));
        recuperationBouees3.add(new Face("Alignement phare", 0, 290));
        recuperationBouees3.add(new GoTo("Placement phare", 120, 290));
        recuperationBouees3.add(new SetSpeed("Vitesse réduite", 50));
        recuperationBouees3.add(new Go("Allumage phare", 20, 500));
        recuperationBouees3.add(new SetSpeed("Vitesse normale", 100));
        recuperationBouees3.add(new GoToBack("Retour phare", 200, 290));
        recuperationBouees3.add(new GoTo("Mise en position bouée 5", 320, 730));
        recuperationBouees3.add(new Face("Alignement bouée 5", 0, 730));
        recuperationBouees3.add(new GoTo("Mise en position ramassage bouée 5", 210, 730));
        mirrorId = recuperationBouees3.size() + 1;
        recuperationBouees3.add(new Manipulation("Ramassage bouée 5 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees3_3000.add(new Manipulation("Ramassage bouée 5 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees3.size() + 1;
        recuperationBouees3.add(new Manipulation("Ramassage bouée 5 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees3_3000.add(new Manipulation("Ramassage bouée 5 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees3.size() + 1;
        recuperationBouees3.add(new Manipulation("Ramassage bouée 5 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees3_3000.add(new Manipulation("Ramassage bouée 5 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        recuperationBouees3.add(new DeleteZone("Suppression zone bouée 5", "bouee5"));
        Objectif objectifRecuperationBouees3_0 = new Objectif("Bouée 3 + Phare + Bouée 5", objectifsCouleur0.size()+1, score, 1, recuperationBouees3);
        Objectif objectifRecuperationBouees3_3000 = new Objectif("Bouée 3 + Phare + Bouée 5", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecuperationBouees3_3000.generateMirror(objectifRecuperationBouees3_0.taches, recuperationBouees3_3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationBouees3_0);
        objectifsCouleur3000.add(objectifRecuperationBouees3_3000);

        /*
         * Ramassage bouées 6
         * Score = 0
         */
        score = 0;
        TaskList recuperationBouees6 =  new TaskList();
        TaskList recuperationBouees6_3000 =  new TaskList();
        recuperationBouees6.add(new GoTo("Mise en position bouée 6", 340, 670));
        recuperationBouees6.add(new Face("Alignement bouée 6", 340, 3000));
        recuperationBouees6.add(new GoTo("Mise en position ramassage bouée 6", 340, 840));
        recuperationBouees6.add(new Face("Alignement ramassage bouée 6", 340, 3000));
        mirrorId = recuperationBouees6.size() + 1;
        recuperationBouees6.add(new Manipulation("Ramassage bouée 6 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees6_3000.add(new Manipulation("Ramassage bouée 6 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees6.size() + 1;
        recuperationBouees6.add(new Manipulation("Ramassage bouée 6 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        recuperationBouees6_3000.add(new Manipulation("Ramassage bouée 6 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = recuperationBouees6.size() + 1;
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
        petitPort.add(new GoToAstar("Déplacement petit port", 1420, 1800));
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

        /*
         * On esaie de prendre 2 bouées de plus et les marquer parce qu'on a du temps
         * Score = 6
         *  - 1 point par bouée dans le port => 2
         *  - 1 point par bouée dans le bon chenal => 2
         *  - une paire dans les chenaux => 2
         */
        score = 6;
        TaskList petitPortBis = new TaskList();
        TaskList petitPortBis_3000 = new TaskList();
        petitPortBis.add(new GoToAstar("Déplacement bouée 10", 1260, 1500));
        petitPortBis.add(new GoTo("Placement bouée 10", 1260, 1380));
        petitPortBis.add(new Face("Alignement bouée 10", 1260, 0));
        mirrorId = petitPortBis.size() + 1;
        petitPortBis.add(new Manipulation("Ramassage bouée 10 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        petitPortBis_3000.add(new Manipulation("Ramassage bouée 10 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = petitPortBis.size() + 1;
        petitPortBis.add(new Manipulation("Ramassage bouée 10 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        petitPortBis_3000.add(new Manipulation("Ramassage bouée 10 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = petitPortBis.size() + 1;
        petitPortBis.add(new Manipulation("Ramassage bouée 10 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        petitPortBis_3000.add(new Manipulation("Ramassage bouée 10 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        petitPortBis.add(new DeleteZone("Suppression bouée 10", "bouee10"));
        petitPortBis.add(new GoToAstar("Déplacement bouée 9", 740, 1380));
        petitPortBis.add(new GoTo("Placement bouée 9", 740, 1210));
        petitPortBis.add(new Face("Alignement bouée 9", 740, 0));
        mirrorId = petitPortBis.size() + 1;
        petitPortBis.add(new Manipulation("Ramassage bouée 9 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        petitPortBis_3000.add(new Manipulation("Ramassage bouée 9 - Pompe", ActionFileBinder.ActionFile.PMI_ATTRAPER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = petitPortBis.size() + 1;
        petitPortBis.add(new Manipulation("Ramassage bouée 9 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        petitPortBis_3000.add(new Manipulation("Ramassage bouée 9 - Poser", ActionFileBinder.ActionFile.PMI_POSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        mirrorId = petitPortBis.size() + 1;
        petitPortBis.add(new Manipulation("Ramassage bouée 9 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC));
        petitPortBis_3000.add(new Manipulation("Ramassage bouée 9 - Lever", ActionFileBinder.ActionFile.PMI_SORTIR_LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC), mirrorId);
        petitPortBis.add(new DeleteZone("Suppression bouée 9", "bouee9"));
        petitPortBis.add(new GoToAstar("Déplacement petit port", 1420, 1800));
        petitPortBis.add(new Face("Alignement largage bouée 9 et 10", 2000, 1800));
        petitPortBis.add(new GoTo("Placement largage bouée 9 et 10", 1600, 1800));
        petitPortBis.add(new Manipulation("Largage bouée 9 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_DROIT.ordinal()));
        petitPortBis.add(new Manipulation("Largage bouée 10 - Pompe", ActionFileBinder.ActionFile.PMI_LACHER_BRAS_GAUCHE.ordinal()));
        petitPortBis.add(new GoToBack("Sortie petit port", 1450, 1800));
        Objectif objectifPetitPortBis0 = new Objectif("Petit port bis", objectifsCouleur0.size()+1, score, 1, petitPortBis);
        Objectif objectifPetitPortBis3000 = new Objectif("Petit port bis", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifPetitPortBis3000.generateMirror(objectifPetitPortBis0.taches, petitPortBis_3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifPetitPortBis0);
        objectifsCouleur3000.add(objectifPetitPortBis3000);

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

        // Ajout du clean de princess pour les tests
        TaskList fakeZoneForSimu = new TaskList();
        fakeZoneForSimu.add(new DeleteZone("Suppression zone bouée 3", "bouee3"));
        fakeZoneForSimu.add(new DeleteZone("Suppression zone bouée 4", "bouee4"));
        recuperationBouees6.addAll(fakeZoneForSimu);

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
}
