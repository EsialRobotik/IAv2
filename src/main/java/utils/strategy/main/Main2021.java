package utils.strategy.main;

import api.log.LoggerFactory;
import asserv.Position;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import pathfinding.PathFinding;
import pathfinding.table.Point;
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
        recuperationRecifSud.add(new GoTo("Sortie départ", 800, 730));
        recuperationRecifSud.add(new Manipulation("Preparer ramassage recif sud", 5));
        recuperationRecifSud.add(new GoToAstar("Placement recif sud", 1590, 230));
        recuperationRecifSud.add(new GoTo("Mise en position rammassage recif sud", 1595, 130));
        recuperationRecifSud.add(new Face("Alignement recif sud", 1595, 0));
        recuperationRecifSud.add(new Go("Plaquage rammassage recif sud", 130, 500));
        recuperationRecifSud.add(new Manipulation("Ramassage recif sud", 6));
        recuperationRecifSud.add(new Manipulation("Libération ramassage recif sud", 7));
        recuperationRecifSud.add(new GoToBack("Sortie recif sud", 1595, 230));
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
        manches.add(new GoToAstar("Placement manche à air", 1780, 210));
        manches.add(new Face("Alignement manche à air", 1780, 3000));
        manches.add(new Go("Callage manche à air", -120, 500));
        int sortieBras = manches.size()+1;
        manches.add(new Manipulation("Sortie bras droit", 1, Tache.Mirror.SPECIFIC));
        manches3000.add(new Manipulation("Sortie bras gauche", 2, Tache.Mirror.SPECIFIC), sortieBras);
        manches.add(new SetSpeed("Réduction de la vitesse", 50));
        manches.add(new GoTo("Taper la manche 1", 1780, 290));
        manches.add(new Face("Réalignement", 1780, 3000));
        manches.add(new GoTo("Taper la manche 2", 1780, 700));
        manches.add(new SetSpeed("Vitesse normale", 100));
        int rentrerBras = manches.size()+1;
        manches.add(new Manipulation("Rentrer bras droit", 3, Tache.Mirror.SPECIFIC));
        manches3000.add(new Manipulation("Rentrer bras gauche", 4, Tache.Mirror.SPECIFIC), rentrerBras);
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
        photo.add(new Manipulation("Photo", 13));
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
         * On place les bouées du petit port
         * Score = 6
         *  - 1 point par bouée dans le port => 2
         *  - 1 point par bouée dans le bon chenal => 2
         *  - une paire dans les chenaux => 2
         */
        score = 6;
        TaskList petitPort = new TaskList();
        petitPort.add(new GoToAstar("Déplacement petit port", 1425, 1800));
        petitPort.add(new Face("Alignement petit port", 0, 1800));
        petitPort.add(new GoToBack("Marquage bouées petit port", 1700, 1800));
        petitPort.add(new DeleteZone("Suppression zone bouée 8", "bouee8"));
        petitPort.add(new DeleteZone("Suppression zone bouée 11", "bouee11"));
        petitPort.add(new GoTo("Sortie petit port", 1425, 1800));

        // TODO Taff de picrate
        petitPort.add(new DeleteZone("Suppression zone bouée 1", "bouee1"));
        petitPort.add(new DeleteZone("Suppression zone bouée 2", "bouee2"));
        petitPort.add(new DeleteZone("Suppression zone bouée 3", "bouee3"));
        petitPort.add(new DeleteZone("Suppression zone bouée 4", "bouee4"));
        petitPort.add(new DeleteZone("Suppression zone bouée 5", "bouee5"));
        petitPort.add(new DeleteZone("Suppression zone bouée 6", "bouee6"));
        petitPort.add(new AddZone("Blocage du chenal Sud", "chenal_depart_s"));
        petitPort.add(new AddZone("Blocage du chenal Nord", "chenal_depart_n"));

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
         * Largage des bouées sud
         * Score = 6
         *  - 1 point par bouée dans le port => 3
         *  - 1 point par bouée dans le bon chenal => 3
         */
        score = 6;
        TaskList largageSud = new TaskList();
        largageSud.add(new GoToAstar("Déplacement largage sud", 1500, 220));
        largageSud.add(new Face("Alignement largage sud", 0, 220));
        largageSud.add(new Go("Placement largage sud", 200));
        largageSud.add(new Manipulation("Préparer largage recif sud", 8));
        largageSud.add(new Manipulation("Largage impaire recif sud", 9));
        largageSud.add(new Go("Sortie largage sud", -200));
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
         * Largage des bouées nord
         * Score = 8
         *  - 1 point par bouée dans le port => 2
         *  - 1 point par bouée dans le bon chenal => 2
         *  - 2 point par paire => 4
         */
        score = 8;
        TaskList largageNord = new TaskList();
        largageNord.add(new GoToAstar("Placement largage nord", 210, 280));
        largageNord.add(new Face("Alignement largage nord", 2000, 280));
        largageNord.add(new GoTo("Placement largage nord", 360, 280));
        largageNord.add(new Manipulation("Largage impaire nord", 10));
        largageNord.add(new GoToBack("Sortie largage nord", 210, 280));
        largageNord.add(new Manipulation("On remet tout en place", 0));
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
        recuperationRecifNord.add(new Manipulation("Preparer ramassage recif nord", 5));
        recuperationRecifNord.add(new GoToAstar("Placement recif nord", 230, 850));
        recuperationRecifNord.add(new Face("Alignement recif nord", 0, 850));
        recuperationRecifNord.add(new GoTo("Mise en position rammassage recif nord", 130, 850));
        recuperationRecifNord.add(new Go("Plaquage rammassage recif nord", 130, 500));
        recuperationRecifNord.add(new Manipulation("Ramassage recif nord", 6));
        recuperationRecifNord.add(new Manipulation("Libération ramassage recif nord", 7));
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
        TaskList largageRecifNord = new TaskList();
        largageRecifNord.add(new GoToAstar("Placement dans le grand port", 800, 300));
        largageRecifNord.add(new Manipulation("Préparer largage grand port", 8));
        largageRecifNord.add(new Manipulation("Largage impaire grand port", 9));
        largageRecifNord.add(new Manipulation("Largage impaire grand port", 10));
        largageRecifNord.add(new GoToBack("Sortie grand port", 800, 730));
        largageRecifNord.add(new Manipulation("On remet tout en place", 0));
        Objectif objectifLargageRecifNord0 = new Objectif("Largage recif nord", objectifsCouleur0.size()+1, 0, 1, largageRecifNord);
        Objectif objectifLargageRecifNord3000 = new Objectif("Largage recif nord", objectifsCouleur3000.size()+1, 0, 1, null);
        try {
            objectifLargageRecifNord3000.generateMirror(objectifLargageRecifNord0.taches);
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

        try (PrintWriter jsonFile = new PrintWriter("configCollection.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Test de la strat");
        try {
            LoggerFactory.init(Level.OFF);
            Table table = new Table("table0.tbl");
            table.loadJsonFromFile("table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(800, 200, Math.PI / 2);
            System.out.println("[");
            System.out.println("{ \"task\":\"Position de départ\",\"command\":\"start\",\"position\":" + startPoint.toJson() + "},");
            for (Objectif objectif : strat.couleur0) {
                for (Tache task: objectif.taches) {
                    task.pathFinding = pathFinding;
                    task.execute(startPoint);
                    startPoint = task.getEndPoint();
                }
            }
            System.out.println("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mainStrat(String... arg) throws Exception {
//    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Liste des objectifs de chaque côté
        // 0 = Bleu, 3000 = Jaune
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /**
         * On va placer la bouée N dans le chenal et on fait de la place avec celle de l'autre couleur
         * Score = Poser le phare (2) + Bouée placée (1) + Chenal (1) + Bouée placée (1) = 5
         */
        int score = 5;
        List<Tache> tachesBoueeNord =  new ArrayList<>();
        tachesBoueeNord.add(new Tache("Step de départ bizarre", tachesBoueeNord.size()+1, 1, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Sortie départ", tachesBoueeNord.size()+1, 800, 730, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_CHAIN, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Placement bouée Nord", tachesBoueeNord.size()+1, 610, 730, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_CHAIN, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Placement bouée Nord", tachesBoueeNord.size()+1, 610, 650, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_CHAIN, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Alignement bouée Nord", tachesBoueeNord.size()+1, 610, 0, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Marquage bouée Nord", tachesBoueeNord.size()+1, 610, 200, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Libération bouée Nord", tachesBoueeNord.size()+1, 610, 700, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_BACK, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Suppression bouée 3", tachesBoueeNord.size()+1, Tache.Type.ELEMENT, Tache.SubType.SUPPRESSION, "bouee3", Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Placement bouée Nord à virer", tachesBoueeNord.size()+1, 190, 415, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_ASTAR, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Placement bouée Nord à virer", tachesBoueeNord.size()+1, 0, 415, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Marquage bouée Nord à virer", tachesBoueeNord.size()+1, -270, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Libération bouée Nord à virer", tachesBoueeNord.size()+1, 170, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Suppression bouée 1", tachesBoueeNord.size()+1, Tache.Type.ELEMENT, Tache.SubType.SUPPRESSION, "bouee1", Tache.Mirror.MIRRORY));
        tachesBoueeNord.add(new Tache("Blocage du chenal Nord", tachesBoueeNord.size()+1, Tache.Type.ELEMENT, Tache.SubType.AJOUT, "chenal_depart_n", Tache.Mirror.MIRRORY));
        Objectif objectifBoueeN0 = new Objectif("Bouées Nord", objectifsCouleur0.size()+1, score, 1, tachesBoueeNord);
        Objectif objectifBoueeN3000 = new Objectif("Bouées Nord", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifBoueeN3000.generateMirror(objectifBoueeN0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifBoueeN0);
        objectifsCouleur3000.add(objectifBoueeN3000);

        /**
         * On allume le phare
         * Score = Allumer le phare (3) + Phare qui fonctionne (10) = 13
         */
        score = 13;
        List<Tache> tachesPhare =  new ArrayList<>();
        tachesPhare.add(new Tache("Placement phare", tachesPhare.size()+1, 280, 225, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO, -1, Tache.Mirror.MIRRORY));
        tachesPhare.add(new Tache("Alignement phare", tachesPhare.size()+1, 0, 225, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesPhare.add(new Tache("Preparation phare", tachesPhare.size()+1, 0, Tache.Type.MANIPULATION, null, 11, Tache.Mirror.MIRRORY));
        tachesPhare.add(new Tache("Allumage phare", tachesPhare.size()+1, 150, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY, 1500));
        tachesPhare.add(new Tache("Sortie zone N", tachesPhare.size()+1, -100, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesPhare.add(new Tache("On remet tout en place", tachesPhare.size()+1, 0, Tache.Type.MANIPULATION, null, 0, Tache.Mirror.MIRRORY, 1500));
        Objectif objectifPhare0 = new Objectif("Phare", objectifsCouleur0.size()+1, score, 1, tachesPhare);
        Objectif objectifPhare3000 = new Objectif("Phare", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifPhare3000.generateMirror(objectifPhare0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifPhare0);
        objectifsCouleur3000.add(objectifPhare3000);


        /**
         * On fait de la place avec la bouée Sud
         * Score = Bouée placée (1) = 1
         */
        score = 1;
        List<Tache> tachesBoueeSud =  new ArrayList<>();
        tachesBoueeSud.add(new Tache("Placement bouée Sud à virer", tachesBoueeSud.size()+1, 1500, 230, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_ASTAR, -1, Tache.Mirror.MIRRORY));
        tachesBoueeSud.add(new Tache("Placement bouée Sud à virer", tachesBoueeSud.size()+1, 0, 230, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesBoueeSud.add(new Tache("Marquage bouée Sud à virer", tachesBoueeSud.size()+1, 250, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesBoueeSud.add(new Tache("Marquage bouée Sud à virer", tachesBoueeSud.size()+1, 140, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesBoueeSud.add(new Tache("Libération bouée Sud à virer", tachesBoueeSud.size()+1, 1400, 230, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_BACK, -1, Tache.Mirror.MIRRORY));
        tachesBoueeSud.add(new Tache("Suppression bouée 2", tachesBoueeSud.size()+1, Tache.Type.ELEMENT, Tache.SubType.SUPPRESSION, "bouee2", Tache.Mirror.MIRRORY));
        Objectif objectifBoueeS0 = new Objectif("Bouée Sud à virer", objectifsCouleur0.size()+1, score, 1, tachesBoueeSud);
        Objectif objectifBoueeS3000 = new Objectif("Bouée Sud à virer", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifBoueeS3000.generateMirror(objectifBoueeS0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifBoueeS0);
        objectifsCouleur3000.add(objectifBoueeS3000);


        /**
         * On va taper les manches à air
         * Score = 15 pour les 2
         */
        score = 15;
        List<Tache> tachesManches =  new ArrayList<>();
        List<Tache> tachesManches3000 =  new ArrayList<>();
        tachesManches.add(new Tache("Placement manche à air", tachesManches.size()+1, 1780, 210, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_ASTAR, -1, Tache.Mirror.MIRRORY));
        tachesManches.add(new Tache("Alignement manche à air", tachesManches.size()+1, 1780, 3000, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesManches.add(new Tache("Alignement manche à air", tachesManches.size()+1, -120, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY, 500));
        int sortieBras = tachesManches.size()+1;
        tachesManches.add(new Tache("Sortie bras droit", sortieBras, 0, Tache.Type.MANIPULATION, null, 1, Tache.Mirror.SPECIFIC));
        tachesManches3000.add(new Tache("Sortie bras gauche", sortieBras, 0, Tache.Type.MANIPULATION, null, 2, Tache.Mirror.SPECIFIC));
        tachesManches.add(new Tache("Réduction de la vitesse", tachesManches.size()+1, 50, Tache.Type.DEPLACEMENT, Tache.SubType.SET_SPEED, -1, Tache.Mirror.MIRRORY));
        tachesManches.add(new Tache("Taper les manches", tachesManches.size()+1, 1780, 290, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO, -1, Tache.Mirror.MIRRORY));
        tachesManches.add(new Tache("Alignement manche à air", tachesManches.size()+1, 1780, 3000, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesManches.add(new Tache("Taper les manches", tachesManches.size()+1, 1780, 700, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO, -1, Tache.Mirror.MIRRORY));
        tachesManches.add(new Tache("Vitesse normale", tachesManches.size()+1, 100, Tache.Type.DEPLACEMENT, Tache.SubType.SET_SPEED, -1, Tache.Mirror.MIRRORY));
        int rentrerBras = tachesManches.size()+1;
        tachesManches.add(new Tache("Rentrer bras droit", rentrerBras, 0, Tache.Type.MANIPULATION, null, 3, Tache.Mirror.SPECIFIC));
        tachesManches3000.add(new Tache("Rentrer bras gauche", rentrerBras, 0, Tache.Type.MANIPULATION, null, 4, Tache.Mirror.SPECIFIC));
        tachesManches.add(new Tache("On quitte la zone", tachesManches.size()+1, -50, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesManches.add(new Tache("Alignement boussole", tachesManches.size()+1, 0, 1500, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesManches.add(new Tache("Photo", tachesManches.size()+1, 0, Tache.Type.MANIPULATION, null, 13, Tache.Mirror.MIRRORY));
        Objectif objectifManches0 = new Objectif("Manches à air", objectifsCouleur0.size()+1, score, 1, tachesManches);
        Objectif objectifManches3000 = new Objectif("Manches à air", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifManches3000.generateMirror(objectifManches0.taches, tachesManches3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifManches0);
        objectifsCouleur3000.add(objectifManches3000);


        /**
         * On vide le récif de notre couleur et on largue les impaire
         * Score = 3 bouées (3) + 3 chenal (3) + 1 paires (2) = 8
         */
        score = 8;
        List<Tache> tachesRecifS =  new ArrayList<>();
        tachesRecifS.add(new Tache("Preparer ramassage recif sud", tachesRecifS.size()+1, 0, Tache.Type.MANIPULATION, null, 5, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Placement recif sud", tachesRecifS.size()+1, 1595, 230, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_ASTAR, -1, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Alignement recif sud", tachesRecifS.size()+1, 1595, 0, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Mise en position rammassage recif sud", tachesRecifS.size()+1, 1595, 130, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO, -1, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Mise en position rammassage recif sud", tachesRecifS.size()+1, 130, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY, 500));
        tachesRecifS.add(new Tache("Ramassage recif sud", tachesRecifS.size()+1, 0, Tache.Type.MANIPULATION, null, 6, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Libération ramassage recif sud", tachesRecifS.size()+1, 0, Tache.Type.MANIPULATION, null, 7, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Sortie recif sud", tachesRecifS.size()+1, 1595, 230, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_BACK, -1, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Placement largage sud", tachesRecifS.size()+1, 1220, 220, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_ASTAR, -1, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Alignement largage sud", tachesRecifS.size()+1, 0, 220, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Préparer largage recif sud", tachesRecifS.size()+1, 0, Tache.Type.MANIPULATION, null, 8, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Largage impaire recif sud", tachesRecifS.size()+1, 0, Tache.Type.MANIPULATION, null, 9, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Sortie largage sud", tachesRecifS.size()+1, -200, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesRecifS.add(new Tache("Blocage du chenal Sud", tachesRecifS.size()+1, Tache.Type.ELEMENT, Tache.SubType.AJOUT, "chenal_depart_s", Tache.Mirror.MIRRORY));

        Objectif objectifRecifS0 = new Objectif("Récif sud", objectifsCouleur0.size()+1, score, 1, tachesRecifS);
        Objectif objectifRecifS3000 = new Objectif("Récif sud", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecifS3000.generateMirror(objectifRecifS0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecifS0);
        objectifsCouleur3000.add(objectifRecifS3000);

        /**
         * On largue les bouées paires
         * Score = 2 bouée (2) + 2 chenal (2) + 2 paires (4) = 8
         */
        score = 8;
        List<Tache> tachesRecifSPair =  new ArrayList<>();
        tachesRecifSPair.add(new Tache("Placement recif nord", tachesRecifSPair.size()+1, 310, 280, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_ASTAR, -1, Tache.Mirror.MIRRORY));
        tachesRecifSPair.add(new Tache("Alignement recif nord", tachesRecifSPair.size()+1, 2000, 280, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
        tachesRecifSPair.add(new Tache("Placement recif nord", tachesRecifSPair.size()+1, 360, 280, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO, -1, Tache.Mirror.MIRRORY));
        tachesRecifSPair.add(new Tache("Largage impaire recif sud", tachesRecifSPair.size()+1, 0, Tache.Type.MANIPULATION, null, 10, Tache.Mirror.MIRRORY));
        tachesRecifSPair.add(new Tache("Sortie largage nord", tachesRecifSPair.size()+1, -150, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
        tachesRecifSPair.add(new Tache("On remet tout en place", tachesRecifSPair.size()+1, 0, Tache.Type.MANIPULATION, null, 0, Tache.Mirror.MIRRORY));
        Objectif objectifRecifSPair0 = new Objectif("Récif sud", objectifsCouleur0.size()+1, score, 1, tachesRecifSPair);
        Objectif objectifRecifSPair3000 = new Objectif("Récif sud", objectifsCouleur3000.size()+1, score, 1, null);
        try {
            objectifRecifSPair3000.generateMirror(objectifRecifSPair0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRecifSPair0);
        objectifsCouleur3000.add(objectifRecifSPair3000);

        /**
         * On marque la bouée Sud dans le chenal
         * Score = Bouée placée (1) + Chenal (1) = 2
         * PAS LE TEMPS !!!
         */
//        score = 2;
//        List<Tache> tachesBoueeSudChenal =  new ArrayList<>();
//        tachesBoueeSudChenal.add(new Tache("Placement bouée Sud", tachesBoueeSudChenal.size()+1, 980, 700, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO, -1, Tache.Mirror.MIRRORY));
//        tachesBoueeSudChenal.add(new Tache("Placement bouée Sud", tachesBoueeSudChenal.size()+1, 980, 650, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO, -1, Tache.Mirror.MIRRORY));
//        tachesBoueeSudChenal.add(new Tache("Alignement bouée Sud", tachesBoueeSudChenal.size()+1, 980, 0, Tache.Type.DEPLACEMENT, Tache.SubType.FACE, -1, Tache.Mirror.MIRRORY));
//        tachesBoueeSudChenal.add(new Tache("Réduction de la vitesse", tachesBoueeSudChenal.size()+1, 50, Tache.Type.DEPLACEMENT, Tache.SubType.SET_SPEED, -1, Tache.Mirror.MIRRORY));
//        tachesBoueeSudChenal.add(new Tache("Marquage bouée Sud", tachesBoueeSudChenal.size()+1, 100, Tache.Type.DEPLACEMENT, Tache.SubType.GO, -1, Tache.Mirror.MIRRORY));
//        tachesBoueeSudChenal.add(new Tache("Remise de la vitesse", tachesBoueeSudChenal.size()+1, 100, Tache.Type.DEPLACEMENT, Tache.SubType.SET_SPEED, -1, Tache.Mirror.MIRRORY));
//        tachesBoueeSudChenal.add(new Tache("Libération bouée Sud", tachesBoueeSudChenal.size()+1, 980, 770, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_BACK, -1, Tache.Mirror.MIRRORY));
//        Objectif objectifBoueeSChenal0 = new Objectif("Bouée Sud", objectifsCouleur0.size()+1, score, 1, tachesBoueeSudChenal);
//        Objectif objectifBoueeSChenal3000 = new Objectif("Bouée Sud", objectifsCouleur3000.size()+1, score, 1, null);
//        try {
//            objectifBoueeSChenal3000.generateMirror(objectifBoueeSChenal0.taches);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        objectifsCouleur0.add(objectifBoueeSChenal0);
//        objectifsCouleur3000.add(objectifBoueeSChenal3000);

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        System.out.println(strat.toString());

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));

        try (PrintWriter jsonFile = new PrintWriter("configCollection.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void mainBoussole(String... arg) throws Exception {
//    public static void main(String... arg) throws Exception {
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
        List<Tache> tachesPortNord =  new ArrayList<>();
        tachesPortNord.add(new Tache("On se gare", tachesPortNord.size()+1, 300, 150, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_ASTAR, -1, Tache.Mirror.MIRRORY));
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
        List<Tache> tachesPortSud =  new ArrayList<>();
        tachesPortSud.add(new Tache("On se gare", tachesPortSud.size()+1, 1300, 150, Tache.Type.DEPLACEMENT, Tache.SubType.GOTO_ASTAR, -1, Tache.Mirror.MIRRORY));
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

        try (PrintWriter jsonFile = new PrintWriter("configCollectionBoussole.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
