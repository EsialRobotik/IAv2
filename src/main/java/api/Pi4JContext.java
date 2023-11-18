package api;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;

public final class Pi4JContext {
    private static Context instance;
    public String value;

    private Pi4JContext(String value) {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.value = value;
    }

    public static Context getInstance() {
        if (instance == null) {
            instance = Pi4J.newAutoContext();
        }
        return instance;
    }
}
