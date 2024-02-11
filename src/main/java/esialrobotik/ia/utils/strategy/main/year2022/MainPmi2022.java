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

public class MainPmi2022 {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        // Pompes à 90 et 235mm du bord
        // 300 * 160

        // Liste des objectifs de chaque côté
        // 0 = Jaune, 3000 = Violet
        List<Objectif> objectifsCouleur0 = new ArrayList<>();
        List<Objectif> objectifsCouleur3000 = new ArrayList<>();

        /**
         * Swap statuette
         * - 2 points pour la dépose de la statuette sur le piédestal pendant le temps de préparation;
         * - 5 points si la statuette n’est plus présente en fin de match sur le piédestal;
         * - 10 points si la réplique est présente en fin de match sur le piédestal;
         * - 2 points si l’équipe dépose une vitrine pendant le temps de préparation;
         * Score = 19
         */
        int score = 19;
        TaskList swapStatuette0 = new TaskList();
        TaskList swapStatuette3000 = new TaskList();
        swapStatuette0.setMirrorTaskList(swapStatuette3000);
        swapStatuette0.add(
            new Go("Step de départ bizarre", 1)
        );
        swapStatuette0.add(
            new GoToAstar("Placement chantier récupération statuette", 1600, 500, Tache.Mirror.SPECIFIC),
            new GoToAstar("Placement chantier récupération statuette", 1500, 2600, Tache.Mirror.SPECIFIC)
        );
        swapStatuette0.add(
            new GoTo("Placement chantier récupération statuette", 1720, 420, Tache.Mirror.SPECIFIC),
            new GoTo("Placement chantier récupération statuette", 1580, 2730, Tache.Mirror.SPECIFIC)
        );
        swapStatuette0.add(
            new Face("Alignement récupération statuette", 1800, 342, Tache.Mirror.SPECIFIC),
            new Face("Alignement récupération statuette", 1840, 3000, Tache.Mirror.SPECIFIC)
        );
        swapStatuette0.add(
            new Manipulation("Récupération statuette", ActionFileBinder.ActionFile.PASSPASS_GET_STATUE.ordinal())
        );
        swapStatuette0.add(
            new Go("Manoeuvre libération réplique", -300)
        );
        swapStatuette0.add(
            new GoTo("Placement chantier libération réplique", 1500, 450, Tache.Mirror.SPECIFIC),
            new GoTo("Placement chantier libération réplique", 1500, 2450, Tache.Mirror.SPECIFIC)
        );
        swapStatuette0.add(
            new GoTo("Placement chantier libération réplique", 1640, 280, Tache.Mirror.SPECIFIC),
            new GoTo("Placement chantier libération réplique", 1720, 2620, Tache.Mirror.SPECIFIC)
        );
        swapStatuette0.add(
            new Face("Alignement libération réplique", 1930, 0, Tache.Mirror.SPECIFIC),
            new Face("Alignement libération réplique", 2000, 2935, Tache.Mirror.SPECIFIC)
        );
        swapStatuette0.add(
            new Go("Placement final libération réplique", 40, 200)
        );
        swapStatuette0.add(
            new Manipulation("Libération réplique", ActionFileBinder.ActionFile.PASSPASS_PUT_FAKE_STATUE.ordinal())
        );
        swapStatuette0.add(
            new Go("Libération chantier", -200)
        );
        Objectif objectifSwapStatuette0 = new Objectif("Distributeur central", objectifsCouleur0.size() + 1, score, 1, swapStatuette0);
        Objectif objectifSwapStatuette3000 = new Objectif("Distributeur central", objectifsCouleur3000.size() + 1, score, 1, null);
        try {
            objectifSwapStatuette3000.generateMirror(objectifSwapStatuette0.taches, swapStatuette3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifSwapStatuette0);
        objectifsCouleur3000.add(objectifSwapStatuette3000);

        /**
         * Dépose statuette dans la vitrine
         * - 15 points si la statuette est présente en fin de match dans la vitrine;
         * - 5 points supplémentaires si la vitrine est activée;
         * Score = 20
         */
        score = 20;
        TaskList deposeStatuette0 = new TaskList();
        TaskList deposeStatuette3000 = new TaskList();
        deposeStatuette0.setMirrorTaskList(deposeStatuette3000);
        deposeStatuette0.add(
            new GoToAstar("Déplacement exposition", 1750, 1340)
        );
        deposeStatuette0.add(
            new GoTo("Déplacement exposition", 1000, 1340)
        );
        deposeStatuette0.add(
            new GoToAstar("Déplacement exposition", 270, 315, Tache.Mirror.SPECIFIC),
            new GoToAstar("Déplacement exposition", 270, 2535, Tache.Mirror.SPECIFIC)
        );
        deposeStatuette0.add(
            new GoTo("Placement exposition", 90, 315, Tache.Mirror.SPECIFIC),
            new GoTo("Placement exposition", 90, 2535, Tache.Mirror.SPECIFIC)
        );
        deposeStatuette0.add(
            new Face("Alignement exposition", 0, 315, Tache.Mirror.SPECIFIC),
            new Face("Alignement exposition", 0, 2535, Tache.Mirror.SPECIFIC)
        );
        deposeStatuette0.add(
                new Manipulation("Dépose statuette", ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_POSE_OUT.ordinal())
        );
        deposeStatuette0.add(
            new Go("Collage statuette", 10, 200)
        );
        deposeStatuette0.add(
            new Manipulation("Dépose statuette", ActionFileBinder.ActionFile.PASSPASS_PUT_RELEASE.ordinal())
        );
        deposeStatuette0.add(
            new Go("Sortie exposition", -190)
        );
        Objectif objectifDeposeStatuette0 = new Objectif("Depose Statuette", objectifsCouleur0.size() + 1, score, 1, deposeStatuette0);
        Objectif objectifDeposeStatuette3000 = new Objectif("Depose Statuette", objectifsCouleur3000.size() + 1, score, 1, null);
        try {
            objectifDeposeStatuette3000.generateMirror(objectifDeposeStatuette0.taches, deposeStatuette3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifDeposeStatuette0);
        objectifsCouleur3000.add(objectifDeposeStatuette3000);

        /**
         * Ramassage des échantillons au sol
         * Score = 0
         */
        score = 0;
        TaskList ramassageEchantillon0 = new TaskList();
        TaskList ramassageEchantillon3000 = new TaskList();
        ramassageEchantillon0.setMirrorTaskList(ramassageEchantillon3000);
        ramassageEchantillon0.add(
            new GoToAstar("Position echantillon bleu", 300, 840, Tache.Mirror.SPECIFIC),
            new GoToAstar("Position echantillon bleu", 300, 2040, Tache.Mirror.SPECIFIC)
        );
        ramassageEchantillon0.add(
            new GoTo("Position echantillon bleu", 400, 840, Tache.Mirror.SPECIFIC),
            new GoTo("Position echantillon bleu", 400, 2040, Tache.Mirror.SPECIFIC)
        );
        ramassageEchantillon0.add(
                new Manipulation("Récupération bleu", ActionFileBinder.ActionFile.PASSPASS_TAKE.ordinal())
        );
        ramassageEchantillon0.add(
                new Manipulation("Stockage bleu", ActionFileBinder.ActionFile.PASSPASS_STORE.ordinal())
        );
        ramassageEchantillon0.add(
                new DeleteZone("Suppression zone bleu", "SampleBlue")
        );
        ramassageEchantillon0.add(
                new GoTo("Position echantillon vert", 400, 770, Tache.Mirror.SPECIFIC),
                new GoTo("Position echantillon vert", 400, 2110, Tache.Mirror.SPECIFIC)
        );
        ramassageEchantillon0.add(
                new GoTo("Position echantillon vert", 520, 770, Tache.Mirror.SPECIFIC),
                new GoTo("Position echantillon vert", 520, 2110, Tache.Mirror.SPECIFIC)
        );
        ramassageEchantillon0.add(
                new Manipulation("Récupération vert", ActionFileBinder.ActionFile.PASSPASS_TAKE.ordinal())
        );
        ramassageEchantillon0.add(
                new Manipulation("Switch vert", ActionFileBinder.ActionFile.PASSPASS_SWITCH.ordinal())
        );
        ramassageEchantillon0.add(
                new DeleteZone("Suppression zone vert", "SampleGreen")
        );
        ramassageEchantillon0.add(
                new GoTo("Position echantillon rouge", 520, 840, Tache.Mirror.SPECIFIC),
                new GoTo("Position echantillon rouge", 520, 2040, Tache.Mirror.SPECIFIC)
        );
        ramassageEchantillon0.add(
                new GoTo("Position echantillon rouge", 640, 840, Tache.Mirror.SPECIFIC),
                new GoTo("Position echantillon rouge", 640, 2040, Tache.Mirror.SPECIFIC)
        );
        ramassageEchantillon0.add(
                new Manipulation("Récupération rouge", ActionFileBinder.ActionFile.PASSPASS_TAKE.ordinal())
        );
        ramassageEchantillon0.add(
                new DeleteZone("Suppression zone rouge", "SampleRed")
        );
        Objectif objectifRamassageEchantillon0 = new Objectif("Ramassage Echantillon", objectifsCouleur0.size() + 1, score, 1, ramassageEchantillon0);
        Objectif objectifRamassageEchantillon3000 = new Objectif("Ramassage Echantillon", objectifsCouleur3000.size() + 1, score, 1, null);
        try {
            objectifRamassageEchantillon3000.generateMirror(objectifRamassageEchantillon0.taches, ramassageEchantillon3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRamassageEchantillon0);
        objectifsCouleur3000.add(objectifRamassageEchantillon3000);

        /**
         * Dépose échantillon vert dans la gallerie
         * - 3 points par échantillon sur la galerie;
         * - 3 points supplémentaires par échantillon face trésor et trié dans sa période historique;
         */
        score = 6;
        TaskList deposeEchantillonVert0 = new TaskList();
        TaskList deposeEchantillonVert3000 = new TaskList();
        deposeEchantillonVert0.setMirrorTaskList(deposeEchantillonVert3000);
        deposeEchantillonVert0.add(
            new GoToAstar("Position dépose vert", 300, 710, Tache.Mirror.SPECIFIC),
            new GoToAstar("Position dépose vert", 300, 2290, Tache.Mirror.SPECIFIC)
        );
        deposeEchantillonVert0.add(
                new Face("Position dépose vert", 0, 710, Tache.Mirror.SPECIFIC),
                new Face("Position dépose vert", 0, 2290, Tache.Mirror.SPECIFIC)
        );
        deposeEchantillonVert0.add(
                new Manipulation("Depose vert", ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_POSE_OUT.ordinal())
        );
        deposeEchantillonVert0.add(
            new Go("Mise en place dépose vert", 100, 1000)
        );
        deposeEchantillonVert0.add(
                new Manipulation("Lacher vert", ActionFileBinder.ActionFile.PASSPASS_PUT_RELEASE.ordinal())
        );
        deposeEchantillonVert0.add(
                new Go("Sortie dépose vert", -100)
        );
        deposeEchantillonVert0.add(
                new Manipulation("Depose vert", ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_POSE_IN.ordinal())
        );
        deposeEchantillonVert0.add(
                new Manipulation("Switch pour rouge", ActionFileBinder.ActionFile.PASSPASS_SWITCH.ordinal())
        );
        Objectif objectifDeposeEchantillonVert0 = new Objectif("Dépose échantillon vert", objectifsCouleur0.size() + 1, score, 1, deposeEchantillonVert0);
        Objectif objectifDeposeEchantillonVert3000 = new Objectif("Dépose échantillon vert", objectifsCouleur3000.size() + 1, score, 1, null);
        try {
            objectifDeposeEchantillonVert3000.generateMirror(objectifDeposeEchantillonVert0.taches, deposeEchantillonVert3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifDeposeEchantillonVert0);
        objectifsCouleur3000.add(objectifDeposeEchantillonVert3000);

        /**
         * Dépose échantillon rouge dans la gallerie
         * - 3 points par échantillon sur la galerie;
         * - 3 points supplémentaires par échantillon face trésor et trié dans sa période historique;
         */
        score = 6;
        TaskList deposeEchantillonRouge0 = new TaskList();
        TaskList deposeEchantillonRouge3000 = new TaskList();
        deposeEchantillonRouge0.setMirrorTaskList(deposeEchantillonRouge3000);
        deposeEchantillonRouge0.add(
                new GoToAstar("Position dépose Rouge", 300, 950, Tache.Mirror.SPECIFIC),
                new GoToAstar("Position dépose Rouge", 300, 2050, Tache.Mirror.SPECIFIC)
        );
        deposeEchantillonRouge0.add(
                new Face("Position dépose Rouge", 0, 950, Tache.Mirror.SPECIFIC),
                new Face("Position dépose Rouge", 0, 2050, Tache.Mirror.SPECIFIC)
        );
        deposeEchantillonRouge0.add(
                new Manipulation("Depose Rouge", ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_POSE_OUT.ordinal())
        );
        deposeEchantillonRouge0.add(
                new Go("Mise en place dépose Rouge", 100, 1000)
        );
        deposeEchantillonRouge0.add(
                new Manipulation("Lacher Rouge", ActionFileBinder.ActionFile.PASSPASS_PUT_RELEASE.ordinal())
        );
        deposeEchantillonRouge0.add(
                new Go("Sortie dépose Rouge", -100)
        );
        deposeEchantillonRouge0.add(
                new Manipulation("Depose Rouge", ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_POSE_IN.ordinal())
        );
        deposeEchantillonRouge0.add(
                new Manipulation("Switch pour bleu", ActionFileBinder.ActionFile.PASSPASS_UNSTORE.ordinal())
        );
        deposeEchantillonRouge0.add(
                new Manipulation("Switch pour bleu", ActionFileBinder.ActionFile.PASSPASS_SWITCH.ordinal())
        );
        Objectif objectifDeposeEchantillonRouge0 = new Objectif("Dépose échantillon Rouge", objectifsCouleur0.size() + 1, score, 1, deposeEchantillonRouge0);
        Objectif objectifDeposeEchantillonRouge3000 = new Objectif("Dépose échantillon Rouge", objectifsCouleur3000.size() + 1, score, 1, null);
        try {
            objectifDeposeEchantillonRouge3000.generateMirror(objectifDeposeEchantillonRouge0.taches, deposeEchantillonRouge3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifDeposeEchantillonRouge0);
        objectifsCouleur3000.add(objectifDeposeEchantillonRouge3000);

        /**
         * Dépose échantillon bleu dans la gallerie
         * - 3 points par échantillon sur la galerie;
         * - 3 points supplémentaires par échantillon face trésor et trié dans sa période historique;
         * 570 / 810 / 1050, décallage 100
         */
        score = 6;
        TaskList deposeEchantillonBleu0 = new TaskList();
        TaskList deposeEchantillonBleu3000 = new TaskList();
        deposeEchantillonBleu0.setMirrorTaskList(deposeEchantillonBleu3000);
        deposeEchantillonBleu0.add(
                new GoToAstar("Position dépose Bleu", 300, 470, Tache.Mirror.SPECIFIC),
                new GoToAstar("Position dépose Bleu", 300, 2530, Tache.Mirror.SPECIFIC)
        );
        deposeEchantillonBleu0.add(
                new Face("Position dépose Bleu", 0, 470, Tache.Mirror.SPECIFIC),
                new Face("Position dépose Bleu", 0, 2530, Tache.Mirror.SPECIFIC)
        );
        deposeEchantillonBleu0.add(
                new Manipulation("Depose Bleu", ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_STORE_OUT.ordinal())
        );
        deposeEchantillonBleu0.add(
                new Go("Mise en place dépose Bleu", 100, 1000)
        );
        deposeEchantillonBleu0.add(
                new Manipulation("Lacher Bleu", ActionFileBinder.ActionFile.PASSPASS_PUT_RELEASE.ordinal())
        );
        deposeEchantillonBleu0.add(
                new Go("Sortie dépose Bleu", -100)
        );
        deposeEchantillonBleu0.add(
                new Manipulation("Depose Bleu", ActionFileBinder.ActionFile.PASSPASS_AX_BRAS_STORE_IN.ordinal())
        );
        Objectif objectifDeposeEchantillonBleu0 = new Objectif("Dépose échantillon Bleu", objectifsCouleur0.size() + 1, score, 1, deposeEchantillonBleu0);
        Objectif objectifDeposeEchantillonBleu3000 = new Objectif("Dépose échantillon Bleu", objectifsCouleur3000.size() + 1, score, 1, null);
        try {
            objectifDeposeEchantillonBleu3000.generateMirror(objectifDeposeEchantillonBleu0.taches, deposeEchantillonBleu3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifDeposeEchantillonBleu0);
        objectifsCouleur3000.add(objectifDeposeEchantillonBleu3000);

        /**
         * Rangement
         * - 20 points si tous les robots de l’équipe sont dans le campement ou la zone de fouille;
         * Score = 0 ==> Fenwick compte ça
         */
        score = 0;
        TaskList rangement0 =  new TaskList();
        rangement0.add(
                new GoToAstar("Mise en place rangement zone de fouille", 1150, 1350)
        );
        rangement0.add(
                new GoTo("Mise en place rangement zone de fouille", 1150, 1000)
        );
        Objectif objectifRangement0 = new Objectif("Rangement", objectifsCouleur0.size()+1, score, 1, rangement0);
        Objectif objectifRangement3000 = new Objectif("Rangement", objectifsCouleur0.size()+1, score, 1, null);
        try {
            objectifRangement3000.generateMirror(objectifRangement0.taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objectifsCouleur0.add(objectifRangement0);
        objectifsCouleur3000.add(objectifRangement3000);

        // Création de la stratégie complète
        Strategie strat = new Strategie();
        strat.couleur0 = objectifsCouleur0;
        strat.couleur3000 = objectifsCouleur3000;

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        System.out.println("#########################");
        System.out.println(gson.toJson(strat));
        System.out.println("#########################");

        try (PrintWriter jsonFile = new PrintWriter("config/2022/configCollectionPmi.json")) {
            jsonFile.println(gson.toJson(strat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LoggerFactory.setDefaultLevel(Level.ERROR);
        System.out.println("Test de la strat 0");
        try {
            Table table = new Table("config/2022/table0.tbl");
            table.loadJsonFromFile("config/2022/table.json");
            PathFinding pathFinding = new PathFinding(new Astar(table));
            Position startPoint = new Position(900, 240, 0);
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
            try (PrintWriter stratFile = new PrintWriter("config/2022/strat_simu_pmi_0.json")) {
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
            Position startPoint = new Position(900, 2760, 0);
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
            try (PrintWriter stratFile = new PrintWriter("config/2022/strat_simu_pmi_3000.json")) {
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
        if (Files.exists(Paths.get("config/2022/strat_simu_0.json"))) {
            jsVariables.append("var strategyBig0 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_0.json"))));
            jsVariables.append(";");
        }
        if (Files.exists(Paths.get("config/2022/strat_simu_3000.json"))) {
            jsVariables.append("var strategyBig3000 = ");
            jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_3000.json"))));
            jsVariables.append(";");
        }
        jsVariables.append("var strategySmall0 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_pmi_0.json"))));
        jsVariables.append(";");
        jsVariables.append("var strategySmall3000 = ");
        jsVariables.append(new String(Files.readAllBytes(Paths.get("config/2022/strat_simu_pmi_3000.json"))));
        jsVariables.append(";");
        try (PrintWriter jsVariablesFile = new PrintWriter("src/main/resources/web/visualisator/2022/variables.js")) {
            jsVariablesFile.println(jsVariables);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
