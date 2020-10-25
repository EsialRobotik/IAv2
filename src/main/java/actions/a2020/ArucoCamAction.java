package actions.a2020;

import actions.ActionCollection;
import actions.ActionDescriptor;
import actions.ActionExecutor;
import actions.ax12.Action;
import api.communication.Shell;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ArucoCamAction implements ActionExecutor {

    protected boolean finished = false;
    protected Shell shell;
    protected ActionCollection actionCollection;

    protected ActionDescriptor nord0, sud0, nord3000, sud3000;

    public ArucoCamAction(Shell shell, ActionCollection actionCollection) {
        this.shell = shell;
        this.actionCollection = actionCollection;

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(
            "{\"desc\":\"Port Nord\",\"id\":6,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":120,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
            JsonObject.class
        );
        this.nord0 = new ActionDescriptor(json, false);
        json = gson.fromJson(
                "{\"desc\":\"Port Sud\",\"id\":6,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":120,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
        );
        this.sud0 = new ActionDescriptor(json, false);
        json = gson.fromJson(
                "{\"desc\":\"Port Nord\",\"id\":6,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":2880,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
        );
        this.nord3000 = new ActionDescriptor(json, false);
        json = gson.fromJson(
                "{\"desc\":\"Port Sud\",\"id\":6,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":2880,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
        );
        this.sud3000 = new ActionDescriptor(json, false);
    }

    @Override
    public void execute() {
        if (finished) {
            return;
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    shell.send("a");
                    String result = shell.read();
                    if (result.contains("#NORD#")) {
                        System.out.println("NORD");
                        actionCollection.addAction(actionCollection.isColor0() ? nord0 : nord3000);
                    } else if (result.contains("#SUD#")) {
                        System.out.println("SUD");
                        actionCollection.addAction(actionCollection.isColor0() ? sud0 : sud3000);
                    } else {
                        System.out.println("JE SAIS PAS !!! MAIS JAMAIS AU NORD !!!");
                        actionCollection.addAction(actionCollection.isColor0() ? sud0 : sud3000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finished = true;
            }
        }).start();
    }

    @Override
    public boolean finished() {
        return this.finished;
    }

    @Override
    public void resetActionState() {
        this.finished = false;
    }

    public static void main(String args[]) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(
                "{\"desc\":\"Port Nord\",\"id\":6,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":120,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
        );
        System.out.println((new ActionDescriptor(json, false)).toString());
        json = gson.fromJson(
                "{\"desc\":\"Port Sud\",\"id\":6,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":670,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":120,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
        );
        System.out.println((new ActionDescriptor(json, false)).toString());
        json = gson.fromJson(
                "{\"desc\":\"Port Nord\",\"id\":6,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":2880,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
        );
        System.out.println((new ActionDescriptor(json, false)).toString());
        json = gson.fromJson(
                "{\"desc\":\"Port Sud\",\"id\":6,\"points\":10,\"priorite\":1,\"taches\":[{\"desc\":\"Placement entre les ports\",\"id\":1,\"positionX\":800,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On va au nord\",\"id\":2,\"positionX\":300,\"positionY\":2330,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1},{\"desc\":\"On se gare\",\"id\":3,\"positionX\":300,\"positionY\":2880,\"dist\":0,\"type\":\"deplacement\",\"subtype\":\"goto\",\"actionId\":-1,\"mirror\":\"MIRRORY\",\"timeout\":-1}]}",
                JsonObject.class
        );
        System.out.println((new ActionDescriptor(json, false)).toString());
    }
}
