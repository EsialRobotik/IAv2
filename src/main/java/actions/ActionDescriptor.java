package actions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Guillaume on 18/05/2017.
 */
public class ActionDescriptor {

    private ActionCollection actionCollection;

    private String desc;
    private int objectiveId;
    private int points;
    private int priority;
    private String skipFlag;
    private String neededFlag;

    private List<Step> stepList;
    private int stepIndex;

    private boolean stepByStep;
    private Scanner scanner;

    public ActionDescriptor(JsonObject object, boolean stepByStep) {
        stepList = new ArrayList<>();
        stepIndex = -1;
        this.stepByStep = stepByStep;

        desc = object.get("desc").getAsString();
        objectiveId = object.get("id").getAsInt();
        points = object.get("points").getAsInt();
        priority = object.get("priorite").getAsInt();
        for (JsonElement elt : object.get("taches").getAsJsonArray()) {
            stepList.add(new Step(elt.getAsJsonObject()));
        }
        if (object.has("skipFlag")) {
            skipFlag = object.get("skipFlag").getAsString();
        }
        if (object.has("neededFlag")) {
            neededFlag = object.get("neededFlag").getAsString();
        }
    }

    public String toString() {
        String res = "\n\nid : " + objectiveId;
        res += "\ndesc : " + desc;
        res += "\npoints: " + points;
        res += "\npriority : " + priority;
        res += "\nskipFlag : " + skipFlag;
        res += "\nneededFlag : " + neededFlag;
        return res;
    }

    public boolean isActionStarted() {
        return stepIndex < 0;
    }

    public boolean hasNextStep() {
        return this.stepIndex < stepList.size() - 1;
    }

    public Step getNextStep() {
        if (this.stepByStep) {
            System.out.println("STEP BY STEP getNextStep : Enter to continue");
            scanner.nextLine();
        }

        ++stepIndex;
        Step step = this.stepList.get(stepIndex);

        System.out.println("Check next step flags - skip = " + step.getSkipFlag() + " - need = " + step.getNeededFlag());
        System.out.println("Actions flags : " + actionCollection.getActionFlags().toString());

        while (
            (step.getSkipFlag() != null && this.actionCollection.getActionFlags().contains(step.getSkipFlag()))
            || (step.getNeededFlag() != null && !this.actionCollection.getActionFlags().contains(step.getNeededFlag()))
        ) {
            ++stepIndex;
            if (!this.hasNextStep()) {
                return null;
            }
            step = this.stepList.get(stepIndex);
        }

        return step;
    }

    public Step getNextStepReal() {
        if (stepIndex + 1 < this.stepList.size()) {
            return this.stepList.get(stepIndex + 1);
        } else {
            return null;
        }
    }

    public Step getCurrentStep() {
        return stepList.get(stepIndex);
    }

    public String getDesc() {
        return desc;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getObjectiveId() {
        return objectiveId;
    }

    public String getSkipFlag() {
        return skipFlag;
    }

    public ActionDescriptor setActionCollection(ActionCollection actionCollection) {
        this.actionCollection = actionCollection;
        return this;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ActionCollection actionCollection = new ActionCollection("actionHandler/configCollection.json");
        ActionDescriptor actionDescriptor;
        boolean positive = false;
        while ((actionDescriptor = actionCollection.getNextActionToPerform()) != null) {
            System.out.println(actionDescriptor);
            while (actionDescriptor.hasNextStep()) {
                Step step = actionDescriptor.getNextStep();
                if ((step != null && step.isyPositiveExclusive() && !positive)
                        || (step != null && step.isyNegativeExclusive() && positive)) {
                    System.out.println("################################### step qui saute : " + step.getDesc());
                    step = actionDescriptor.getNextStep();
                }
                System.out.println(step);
            }
        }
    }
}
