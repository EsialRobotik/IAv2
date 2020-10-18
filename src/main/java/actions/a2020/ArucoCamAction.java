package actions.a2020;

import actions.ActionExecutor;
import api.communication.Shell;

import java.io.IOException;

public class ArucoCamAction implements ActionExecutor {

    protected boolean finished = false;
    protected Shell shell;

    public ArucoCamAction(Shell shell) {
        this.shell = shell;
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
                    } else if (result.contains("#SUD#")) {
                        System.out.println("SUD");
                    } else {
                        System.out.println("JE SAIS PAS !!! MAIS JAMAIS AU NORD !!!");
                    }
                    // TODO faut faire un truc là
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
}
