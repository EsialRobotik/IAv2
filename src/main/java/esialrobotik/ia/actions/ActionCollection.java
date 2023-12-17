package esialrobotik.ia.actions;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Guillaume on 18/05/2017.
 */
public class ActionCollection {
    private List<ActionDescriptor> actionList;
    private Map<Integer, Boolean> actionFinished;
    private List<String> actionFlags;
    private int currentIndex;
    private JsonElement jsonElement;
    private boolean stepByStep = false;
    private Scanner scanner;
    private boolean isColor0;

    public ActionCollection(String filepath) throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        jsonElement = parser.parse(new JsonReader(new FileReader(filepath)));

        currentIndex = 0;
    }

    public void setStepByStepMode(boolean stepByStep) {
        this.stepByStep = stepByStep;
    }

    public void prepareActionList(boolean isColor0) {
        actionList = new ArrayList<>();
        actionFinished = new HashMap<>();
        actionFlags = new ArrayList<>();
        this.isColor0 = isColor0;
        for (JsonElement element : jsonElement.getAsJsonObject().getAsJsonArray(isColor0 ? "couleur0" : "couleur3000")) {
            ActionDescriptor action = new ActionDescriptor(element.getAsJsonObject(), this.stepByStep);
            if (this.stepByStep) {
                action.setScanner(this.scanner);
            }
            actionList.add(action);
            actionFinished.put(action.getObjectiveId(), false);
        }
    }

    public String toString() {
        String res = "";
        for (ActionDescriptor descriptor : actionList) {
            res += actionList + "\n";
        }
        return res;
    }

    public ActionDescriptor getNextActionToPerform() {
        // TODO devenir inteligent ici...
        // - Ajouter une liste de tag de pré-requis comme les skip
        // - Trier les esialrobotik.ia.actions par ID puis Prio
        // - Parcourir les esialrobotik.ia.actions tant que tout n'est pas fait
        // - Utiliser le lidar intelligemment pour vérifier que les esialrobotik.ia.actions sont atteignables

        if (currentIndex >= actionList.size()) {
            return null;
        }

        if (this.stepByStep) {
            System.out.println("STEP BY STEP getNextActionToPerform : Enter to continue");
            scanner.nextLine();
        }

        // Skip finished action
        ActionDescriptor nextAction = actionList.get(currentIndex++);
        if (nextAction.getSkipFlag() != null && actionFlags.contains(nextAction.getSkipFlag())) {
            actionFinished.put(nextAction.getObjectiveId(), true);
        }
        while (actionFinished.get(nextAction.getObjectiveId())) {
            if (currentIndex >= actionList.size()) {
                return null;
            }
            nextAction = actionList.get(currentIndex++);
            if (nextAction.getSkipFlag() != null && actionFlags.contains(nextAction.getSkipFlag())) {
                actionFinished.put(nextAction.getObjectiveId(), true);
            }
        }
        return nextAction;
    }

    public List<ActionDescriptor> getActionList() {
        return actionList;
    }

    public void setActionList(List<ActionDescriptor> actionList) {
        this.actionList = actionList;
    }

    public boolean isStepByStep() {
        return this.stepByStep;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean isColor0() {
        return isColor0;
    }

    public void addAction(ActionDescriptor action) {
        actionList.add(action);
        actionFinished.put(action.getObjectiveId(), false);
    }

    public void addActionFlag(String flag) {
        actionFlags.add(flag);
    }

    public static void main(String args[]) throws FileNotFoundException {
//        ActionCollection collection = new ActionCollection("actionHandler/configCollection.json");
//        System.out.println(collection);
        Scanner scanner = new Scanner(System.in);
        System.out.println("STEP BY STEP getNextActionToPerform : Enter 0 to continue");
        while (scanner.hasNextLine()) {
            if (scanner.nextInt() == 0) {
                scanner.close();
                break;
            }
        }
    }
}
