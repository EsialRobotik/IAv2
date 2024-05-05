package utils.strategy.main.year2024;

import actions.a2023.ActionFileBinder;
import utils.strategy.Tache;
import utils.strategy.TaskList;
import utils.strategy.main.AbstractMain;
import utils.strategy.task.*;

public class Main2024 extends AbstractMain {

    public static void main(String... arg) throws Exception {
        System.out.println("Génération de la stratégie");

        year = 2024;

        startX_0 = 1800;
        startY_0 = 120;
        startTheta_0 = Math.PI / 2;

        startX_3000 = startX_0;
        startY_3000 = 3000 - startY_0;
        startTheta_3000 = -startTheta_0;

        // 0 = Bleu, 3000 = Jaune
        // Départ en x=1800 y=120, theta=Pi/2
        panneauSolaire();
        plante1();
        plante1();
        rechargeBatterie();

        //testActions();

        generateStrategy();
    }

    public static void panneauSolaire() {
        int score = 0;
        TaskList taskList =  new TaskList(3000);
        taskList.add(
            new Manipulation("Sortie doigt solaire droit", ActionFileBinder.ActionFile.MAMMA_DOIGT_SOLAIRE_OUT_DROIT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Sortie doigt solaire gauche", ActionFileBinder.ActionFile.MAMMA_DOIGT_SOLAIRE_OUT_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC)
        );
        taskList.add(new SetSpeed("Pas trop vite !!", 25));
        taskList.add(
            new GoTo("Panneau 1", 1800, 350)
        );
        score += 5;
        taskList.add(
                new Face("Panneau 1", 1800, 3000)
        );
        taskList.add(
            new GoTo("Panneau 2", 1800, 600)
        );
        score += 5;
        taskList.add(
                new Face("Panneau 2", 1800, 3000)
        );
        taskList.add(
            new GoTo("Panneau 2", 1800, 750)
        );
        score += 5;
        taskList.add(
            new Manipulation("Ranger doigt solaire droit", ActionFileBinder.ActionFile.MAMMA_DOIGT_SOLAIRE_IN_DROIT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Ranger doigt solaire gauche", ActionFileBinder.ActionFile.MAMMA_DOIGT_SOLAIRE_IN_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC)
        );
        taskList.add(new SetSpeed("A fond !!!", 100));
        objectifsCouleur0.add(taskList.generateObjectif("Panneaux solaire", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(taskList.generateMirrorObjectif("Panneaux solaire", objectifsCouleur3000.size()+1, score, 1));
    }

    public static void plante1() {
        int score = 0;
        TaskList taskList =  new TaskList(3000);
        taskList.add(
            (new GoToAstar("Position plante", 700, 650)).setSkipFlag("plant_n_ko")
        );
        taskList.add(
            (new GoToAstar("Position plante", 700, 670)).setNeededFlag("plant_n_ko")
        );
        taskList.add(
            new Face("Alignement plante", 700, 3000)
        );
        taskList.add(
            (new Manipulation("Ramasse plante", ActionFileBinder.ActionFile.MAMMA_RAMASSER_PLANTE_NORD_LOIN.ordinal())).setSkipFlag("plant_n_ko")
        );
        taskList.add(
            (new Manipulation("Ramasse plante", ActionFileBinder.ActionFile.MAMMA_RAMASSER_PLANTE.ordinal())).setNeededFlag("plant_n_ko")
        );
        taskList.add(new SetSpeed("Pas trop vite !!", 25));
        taskList.add(
            new GoToBack("Sortie plante", 700, 650)
        );
        taskList.add(
            new GoTo("Position pot", 612, 385)
        );
        taskList.add(
            new Face("Position pot", 612, 0)
        );
        taskList.add(
            new Manipulation("Depose plante", ActionFileBinder.ActionFile.MAMMA_DEPOSER_PLANTE.ordinal())
        );
        taskList.add(
            new GoToBack("On recule un peu", 612, 465)
        );
        taskList.add(
            new Manipulation("On baisse la pince pour ramasser le pot", ActionFileBinder.ActionFile.MAMMA_PREPARER_ACCROCHE_POT.ordinal())
        );
        taskList.add(
            new GoTo("On va chercher le pot", 612, 395)
        );
        taskList.add(
            new GoToBack("On se libère", 612, 500)
        );
        taskList.add(
            new Manipulation("Hail Mars !!!", ActionFileBinder.ActionFile.MAMMA_PINCE_LEVER_RAMI.ordinal())
        );
        taskList.add(
            new GoTo("On va a la jardiniere", 250, 785)
        );
        taskList.add(
            new Face("On va a la jardiniere", 0, 785)
        );
        taskList.add(
            new Manipulation("On baisse les bras", ActionFileBinder.ActionFile.MAMMA_PINCE_LEVER_HORIZONTAL.ordinal())
        );
        taskList.add(
            new Manipulation("On lache tout", ActionFileBinder.ActionFile.MAMMA_AIMANT_DESACTIVER.ordinal())
        );
        taskList.add(
            new Manipulation("On range le bras", ActionFileBinder.ActionFile.MAMMA_PINCE_LEVER_VERTICAL.ordinal())
        );
        taskList.add(
            new GoToBack("On se libère", 350, 785)
        );
        score += 5;
        taskList.add(
            new GoToBack("Position pot", 612, 650)
        );
        taskList.add(new SetSpeed("A fond !!!", 100));
        objectifsCouleur0.add(taskList.generateObjectif("Panneaux solaire", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(taskList.generateMirrorObjectif("Panneaux solaire", objectifsCouleur3000.size()+1, score, 1));
    }

    public static void testActions() {
        int score = 0;
        TaskList taskList = new TaskList(3000);
        taskList.add(
                new Manipulation("Sortie doigt solaire gauche", ActionFileBinder.ActionFile.MAMMA_DOIGT_SOLAIRE_OUT_GAUCHE.ordinal())
        );
        taskList.add(
                new Manipulation("Ranger doigt solaire gauche", ActionFileBinder.ActionFile.MAMMA_DOIGT_SOLAIRE_IN_GAUCHE.ordinal())
        );
        taskList.add(new Wait("wait", 250));
        taskList.add(
                new Manipulation("Sortie doigt solaire droit", ActionFileBinder.ActionFile.MAMMA_DOIGT_SOLAIRE_OUT_DROIT.ordinal())
        );
        taskList.add(
                new Manipulation("Ranger doigt solaire droit", ActionFileBinder.ActionFile.MAMMA_DOIGT_SOLAIRE_IN_DROIT.ordinal())
        );
        objectifsCouleur0.add(taskList.generateObjectif("Test", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(taskList.generateMirrorObjectif("Test", objectifsCouleur3000.size()+1, score, 1));
    }

    public static void rechargeBatterie() {
        int score = 0;
        TaskList taskList = new TaskList(3000);
        taskList.add(
            new GoToAstar("Position plante", 450, 650)
        );
        taskList.add(
                new GoTo("Position plante", 450, 450)
        );
        score += 10;
        objectifsCouleur0.add(taskList.generateObjectif("Panneaux solaire", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(taskList.generateMirrorObjectif("Panneaux solaire", objectifsCouleur3000.size()+1, score, 1));
    }
}
