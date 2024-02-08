package esialrobotik.ia.api.log;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public class LoggerFactory {
    private static boolean hasBeenInit = false;

    public static void init(final Level level) {
        hasBeenInit = true;
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", level.toString());
    }


    public static Logger getLogger(Class<?> clazz){
        if(!hasBeenInit){
            throw new RuntimeException("Log module hasn't been initialize.");
        }
        return org.slf4j.LoggerFactory.getLogger(clazz.getName());
    }

    public static void shutdown() {
        // no-op
    }
}
