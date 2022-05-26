package actions;

import manager.CommunicationManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ActionHttp implements ActionExecutor {

    protected URL url;

    public ActionHttp(URL url) {
        this.url = url;
    }

    @Override
    public void execute() {
        try {
            InputStream is = url.openStream();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public void resetActionState() {

    }

    @Override
    public void setData(String data) {

    }

    @Override
    public void setCommunicationManager(CommunicationManager communicationManager) {

    }

    @Override
    public String getActionFlag() {
        return null;
    }
}
