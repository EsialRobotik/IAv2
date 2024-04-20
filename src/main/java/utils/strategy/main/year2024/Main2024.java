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

        generateStrategy();
    }

    public static void panneauSolaire() {
        TaskList taskList =  new TaskList(3000);
        taskList.add(
            new Manipulation("Init doigt solaire", ActionFileBinder.ActionFile.MAMA_DOIGT_SOLAIRE_OUT_DROIT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Init doigt solaire", ActionFileBinder.ActionFile.MAMA_DOIGT_SOLAIRE_OUT_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC)
        );
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
            new Manipulation("Ranger doigt solaire", ActionFileBinder.ActionFile.MAMA_DOIGT_SOLAIRE_IN_DROIT.ordinal(), Tache.Mirror.SPECIFIC),
            new Manipulation("Ranger doigt solaire", ActionFileBinder.ActionFile.MAMA_DOIGT_SOLAIRE_IN_GAUCHE.ordinal(), Tache.Mirror.SPECIFIC)
        );
        objectifsCouleur0.add(taskList.generateObjectif("Panneaux solaire", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(taskList.generateMirrorObjectif("Panneaux solaire", objectifsCouleur3000.size()+1, score, 1));
    }

    public static void plante1() {
        TaskList taskList =  new TaskList(3000);
        taskList.add(
            new GoToAstar("Position plante", 1300, 650)
        );
        taskList.add(
            new Face("Alignement plante", 1300, 3000)
        );
        taskList.add(
            new Manipulation("Ramasse plante", ActionFileBinder.ActionFile.MAMA_RAMASSE_PLANTE.ordinal())
        );
        taskList.add(
            new GoToBack("Sortie plante", 1300, 650)
        );
        taskList.add(
            new GoTo("Position pot", 1375, 450)
        );
        taskList.add(
            new Face("Position pot", 1375, 0)
        );
        taskList.add(
            new Manipulation("Depose plante", ActionFileBinder.ActionFile.MAMA_DEPOSE_PLANTE.ordinal())
        );
        score += 5;
        taskList.add(
            new GoToBack("Position pot", 1375, 650)
        );
        objectifsCouleur0.add(taskList.generateObjectif("Panneaux solaire", objectifsCouleur0.size()+1, score, 1));
        objectifsCouleur3000.add(taskList.generateMirrorObjectif("Panneaux solaire", objectifsCouleur3000.size()+1, score, 1));
    }

    public static void rechargeBatterie() {
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
