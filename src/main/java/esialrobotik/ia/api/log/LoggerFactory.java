package esialrobotik.ia.api.log;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public class LoggerFactory implements ILoggerFactory {
    private ConcurrentHashMap<String, Logger> loggerMap;
    private Level default_Level;
    public final static LoggerFactory INSTANCE = new LoggerFactory();

    private LoggerFactory() {
        loggerMap = new ConcurrentHashMap<>();
        this.default_Level = Level.DEBUG;
    }

    @Override
    public Logger getLogger(String name) {
        if (loggerMap.contains(name)) {
            return loggerMap.get(name);
        } else {
            NetworkLogger logger = new NetworkLogger(name, default_Level);
            loggerMap.put(name, logger);
            return logger;
        }
    }

    public static<T> Logger getLogger(Class<T> clazz) {
        return INSTANCE.getLogger(clazz.getName());
    }

    /**
     * Set the default level for the future loggers.
     * In particular, this does not set the level for already existing loggers.
     * 
     * @param level: Default logging level.
     */
    public static void setDefaultLevel(Level level) {
        INSTANCE.default_Level = level;
    }

    /**
     * Set the level of all loggers to the given one.
     * 
     * @param level: the wanted logging level.
     * @see setDefaultLevel if you only need to define the level of the future-created loggers.
     */
    public static void setLevel(Level level) {
        INSTANCE.default_Level = level;
       for (Logger l : INSTANCE.loggerMap.values()) {
            NetworkLogger nl = (NetworkLogger)l;
            nl.setLevel(level);
       }
    }

    public static void shutdown() {
        // NO-OP
    }
}
