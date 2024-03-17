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

public class Main2021 {

    //    public static void mainStratPrincipal(String... arg) throws Exception {
    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Jaune
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /*
         * On va vider le distributeur Sud
         * Score = 0
         */
        TaskList recuperationRecifSud =  new TaskList();
        recuperationRecifSud.add(new Go("Step de départ bizarre", 1));
        recuperationRecifSud.add(new GoTo("Sortie départ", 750, 670));
        recuperationRecifSud.add(new Manipulation("Preparer ramassage recif sud", ActionFileBinder.ActionFile.PREPARER_RAMASSAGE.ordinal()));
        recuperationRecifSud.add(new GoToAstar("Placement recif sud", 1600, 230));
        recuperationRecifSud.add(new GoTo("Alignement recif sud", 1600, 130));
        recuperationRecifSud.add(new Go("Plaquage rammassage recif sud", 130, 500));
        recuperationRecifSud.add(new Manipulation("Ramassage recif sud", ActionFileBinder.ActionFile.TOUT_RAMASSER.ordinal()));
        recuperationRecifSud.add(new Manipulation("Libération ramassage recif sud", ActionFileBinder.ActionFile.LEVER_GOBELETS.ordinal()));
        recuperationRecifSud.add(new Go("Sortie recif sud", -210));
        Objectif objectifRecuperationRecifSud0 = new Objectif("Recif Sud", objectifsCouleur0.size()+1, 0, 1, recuperationRecifSud);
        Objectif objectifRecuperationRecifSud3000 = new Objectif("Recif Sud", objectifsCouleur3000.size()+1, 0, 1, null);
        try {
            objectifRecuperationRecifSud3000.generateMirror(objectifRecuperationRecifSud0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationRecifSud0);
        objectifsCouleur3000.add(objectifRecuperationRecifSud3000);

        /*
         * On va taper les manches à air
         * Score = 15 pour les 2
         */
        int score = 15;
        TaskList manches =  new TaskList();
        TaskList manches3000 =  new TaskList();
        manches.add(new GoToAstar("Placement manche à air", 1700, 210));
        manches.add(new GoToBack("Ajustement placement manche à air", 1800, 210));
        manches.add(new Face("Alignement manche à air", 1800, 3000));
        manches.add(new Go("Callage manche à air", -120, 500));
        int sortieBras = manches.size()+1;
        manches.add(new Manipulation("Sortie bras droit", ActionFileBinder.ActionFile.BAISSER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        manches3000.add(new Manipulation("Sortie bras gauche", ActionFileBinder.ActionFile.BAISSER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), sortieBras);
        manches.add(new SetSpeed("Réduction de la vitesse", 50));
        manches.add(new GoTo("Taper la manche 1", 1800, 290));
        manches.add(new GoTo("Taper la manche 2", 1800, 700));
        manches.add(new SetSpeed("Vitesse normale", 100));
        int rentrerBras = manches.size()+1;
        manches.add(new Manipulation("Rentrer bras droit", ActionFileBinder.ActionFile.LEVER_BRAS_DROIT.ordinal(), Tache.Mirror.SPECIFIC));
        manches3000.add(new Manipulation("Rentrer bras gauche", ActionFileBinder.ActionFile.LEVER_BRAS_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC), rentrerBras);
        manches.add(new Go("On quitte la zone", -70));
        Objectif objectifManches0 = new Objectif("Manches à air", objectifsCouleur0.size()+1, score, 1, manches);
        Objectif objectifManches3000 = new Objectif("Manches à air", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifManches3000.generateMirror(objectifManches0.taches, manches3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifManches0);
        objectifsCouleur3000.add(objectifManches3000);

        /*
         * On prend la boussole en photo
         * Score = 0
         */
        score = 0;
        TaskList photo =  new TaskList();
        photo.add(new Face("Alignement boussole", 0, 1500));
        photo.add(new Manipulation("Photo", ActionFileBinder.ActionFile.ARUCO_CAM.ordinal()));
        Objectif objectifPhoto0 = new Objectif("Photo", objectifsCouleur0.size()+1, score, 1, photo);
        Objectif objectifPhoto3000 = new Objectif("Photo", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifPhoto3000.generateMirror(objectifPhoto0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifPhoto0);
        objectifsCouleur3000.add(objectifPhoto3000);

        /*
         * On marque la bouée 4
         * Score = 2
         *  - 1 point par bouée dans le port => 1
         *  - 1 point par bouée dans le bon chenal => 1
         */
        score = 2;
        TaskList recuperationBouees4 =  new TaskList();
        recuperationBouees4.add(new GoToAstar("Mise en position bouée 4", 1090, 700));
        recuperationBouees4.add(new Face("Alignement bouée 4", 1090, 3000));
        recuperationBouees4.add(new SetSpeed("Vitesse réduite", 25));
        recuperationBouees4.add(new GoToBack("Marquage bouée 4", 1090, 500));
        recuperationBouees4.add(new GoToBack("Marquage bouée 4", 1090, 400));
        recuperationBouees4.add(new GoToBack("Marquage bouée 4", 1090, 300));
        recuperationBouees4.add(new GoToBack("Marquage bouée 4", 1090, 270));
        recuperationBouees4.add(new SetSpeed("Vitesse normale", 100));
        recuperationBouees4.add(new GoTo("Sortie de la zone", 1090, 700));
        recuperationBouees4.add(new DeleteZone("Suppression zone bouée 4", "bouee4"));
        recuperationBouees4.add(new AddZone("Blocage du chenal Sud", "chenal_depart_s"));
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
         * Largage des bouées sud
         * Score = 6
         *  - 1 point par bouée dans le port => 3
         *  - 1 point par bouée dans le bon chenal => 3
         */
        score = 6;
        TaskList largageSud = new TaskList();
        largageSud.add(new GoToAstar("Déplacement largage sud", 1420, 260));
        largageSud.add(new Face("Alignement largage sud", 0, 260));
        largageSud.add(new Go("Placement largage sud", 155));
        largageSud.add(new Manipulation("Préparer largage recif sud", ActionFileBinder.ActionFile.PREPARER_LARGAGE.ordinal()));
        largageSud.add(new Manipulation("Largage impaire recif sud", ActionFileBinder.ActionFile.LARGUER_DOIGTS_IMPAIRE.ordinal()));
        largageSud.add(new Go("Sortie largage sud", -255));
        largageSud.add(new GoTo("Esquive chenal", 1520, 700));
        Objectif objectifLargageSud0 = new Objectif("Largage sud", objectifsCouleur0.size()+1, score, 1, largageSud);
        Objectif objectifLargageSud3000 = new Objectif("Largage sud", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifLargageSud3000.generateMirror(objectifLargageSud0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifLargageSud0);
        objectifsCouleur3000.add(objectifLargageSud3000);

        /*
         * On marque la bouée 3, on allume le phare, on récupère la bouée 5
         * Score = 19
         *  - 1 point par bouée dans le port => 1
         *  - 1 point par bouée dans le bon chenal => 1
         *  - une paire dans les chenaux => 2
         */
        score = 4;
        TaskList recuperationBouees3 =  new TaskList();
        TaskList recuperationBouees3_3000 =  new TaskList();
        recuperationBouees3.add(new GoToAstar("Mise en position bouée 3", 520, 700));
        recuperationBouees3.add(new Face("Alignement bouée 3", 520, 3000));
        recuperationBouees3.add(new SetSpeed("Vitesse réduite", 25));
        recuperationBouees3.add(new GoToBack("Marquage bouée 3", 520, 500));
        recuperationBouees3.add(new GoToBack("Marquage bouée 3", 520, 400));
        recuperationBouees3.add(new GoToBack("Marquage bouée 3", 520, 300));
        recuperationBouees3.add(new GoToBack("Marquage bouée 3", 520, 270));
        recuperationBouees3.add(new SetSpeed("Vitesse normale", 100));
        recuperationBouees3.add(new GoTo("Sortie de la zone", 520, 700));
        recuperationBouees3.add(new DeleteZone("Suppression zone bouée 3", "bouee3"));
        recuperationBouees3.add(new AddZone("Blocage du chenal Nord", "chenal_depart_n"));
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
         * Largage des bouées nord
         * Score = 8
         *  - 1 point par bouée dans le port => 2
         *  - 1 point par bouée dans le bon chenal => 2
         *  - 2 point par paire => 4
         */
        score = 8;
        TaskList largageNord = new TaskList();
        largageNord.add(new GoToAstar("Placement largage nord", 280, 260));
        largageNord.add(new Face("Alignement largage nord", 2000, 260));
        largageNord.add(new GoTo("Placement largage nord", 320, 260));
        largageNord.add(new Manipulation("Largage impaire nord", ActionFileBinder.ActionFile.LARGUER_DOIGTS_PAIRE.ordinal()));
        largageNord.add(new GoToBack("Sortie largage nord", 230, 260));
        largageNord.add(new Manipulation("On remet tout en place", ActionFileBinder.ActionFile.INIT.ordinal()));
        Objectif objectifRecifLargageN0 = new Objectif("Largage nord", objectifsCouleur0.size()+1, score, 1, largageNord);
        Objectif objectifRecifLargageN3000 = new Objectif("Largage nord", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecifLargageN3000.generateMirror(objectifRecifLargageN0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecifLargageN0);
        objectifsCouleur3000.add(objectifRecifLargageN3000);

        /*
         * On va vider le distributeur Nord
         * Score = 0
         */
        TaskList recuperationRecifNord =  new TaskList();
        recuperationRecifNord.add(new Manipulation("Preparer ramassage recif nord", ActionFileBinder.ActionFile.PREPARER_RAMASSAGE.ordinal()));
        recuperationRecifNord.add(new GoToAstar("Placement recif nord", 230, 850));
        recuperationRecifNord.add(new Face("Alignement recif nord", 0, 850));
        recuperationRecifNord.add(new GoTo("Mise en position rammassage recif nord", 130, 850));
        recuperationRecifNord.add(new Go("Plaquage rammassage recif nord", 130, 500));
        recuperationRecifNord.add(new Manipulation("Ramassage recif nord", ActionFileBinder.ActionFile.TOUT_RAMASSER.ordinal()));
        recuperationRecifNord.add(new Manipulation("Libération ramassage recif nord", ActionFileBinder.ActionFile.LEVER_GOBELETS.ordinal()));
        recuperationRecifNord.add(new GoToBack("Sortie recif nord", 230, 850));
        Objectif objectifRecuperationRecifNord0 = new Objectif("Recif nord", objectifsCouleur0.size()+1, 0, 1, recuperationRecifNord);
        Objectif objectifRecuperationRecifNord3000 = new Objectif("Recif nord", objectifsCouleur3000.size()+1, 0, 1, null);
        try {
            objectifRecuperationRecifNord3000.generateMirror(objectifRecuperationRecifNord0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecuperationRecifNord0);
        objectifsCouleur3000.add(objectifRecuperationRecifNord3000);

        /*
         * Largage distributeur Nord
         * Score = 5
         *  - 1 point par bouée dans le port => 5
         */
        int mirrorId = 0;
        score = 5;
        TaskList largageRecifNord = new TaskList();
        TaskList largageRecifNord_3000 = new TaskList();
        largageRecifNord.add(new GoToAstar("Placement dans le grand port", 800, 450));
        largageRecifNord.add(new Face("Alignement dans le grand port", 800, 0));
        largageRecifNord.add(new Manipulation("Préparer largage grand port", ActionFileBinder.ActionFile.PREPARER_LARGAGE.ordinal()));
//        mirrorId = largageRecifNord.size() + 1;
        largageRecifNord.add(new Manipulation("Largage grand port", ActionFileBinder.ActionFile.OUVRIR_DOIGTS_1A5.ordinal()));
//        largageRecifNord_3000.add(new Manipulation("Largage grand port", ActionFileBinder.ActionFile.OUVRIR_DOIGTS_2A5.ordinal()), mirrorId);
        largageRecifNord.add(new GoToBack("Sortie grand port", 800, 800));
        Objectif objectifLargageRecifNord0 = new Objectif("Largage recif nord", objectifsCouleur0.size()+1, score, 1, largageRecifNord);
        Objectif objectifLargageRecifNord3000 = new Objectif("Largage recif nord", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifLargageRecifNord3000.generateMirror(objectifLargageRecifNord0.taches, largageRecifNord_3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifLargageRecifNord0);
        objectifsCouleur3000.add(objectifLargageRecifNord3000);

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/2021/configCollection.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Ajout du clean de picrate pour les tests
        TaskList fakeZoneForSimu = new TaskList();
        fakeZoneForSimu.add(new DeleteZone("Suppression zone bouée 1", "bouee1"));
        fakeZoneForSimu.add(new DeleteZone("Suppression zone bouée 2", "bouee2"));
        fakeZoneForSimu.add(new DeleteZone("Suppression zone bouée 3", "bouee3"));
        fakeZoneForSimu.add(new DeleteZone("Suppression zone bouée 5", "bouee5"));
        fakeZoneForSimu.add(new DeleteZone("Suppression zone bouée 6", "bouee6"));
        fakeZoneForSimu.add(new AddZone("Blocage du chenal Sud", "chenal_depart_s"));
        fakeZoneForSimu.add(new AddZone("Blocage du chenal Nord", "chenal_depart_n"));
        photo.addAll(fakeZoneForSimu);

        Strategie startBoussole = Main2021.mainBoussole();
        strat.couleur0.add(startBoussole.couleur0.get(0)); // Nord
//        strat.couleur0.add(startBoussole.couleur0.get(1)); // Sud

        System.out.println("Test de la strat");
        try {
            LoggerFactory.setDefaultLevel(Level.ERROR);
            Table table = new Table("config/2021/table0.tbl");
            table.loadJsonFromFile("config/2021/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(750, 200, Math.PI / 2);
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
            try (PrintWriter stratFile = new PrintWriter("config/2021/strat_simu.json")) {
                stratFile.println(stratSimu);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Strategie mainBoussole() throws Exception {
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
        tachesPortNord.add(new GoTo("On se gare", 200, 200));
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

        try (PrintWriter jsonFile = new PrintWriter("config/2021/configCollectionBoussole.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return strat;
    }

}
