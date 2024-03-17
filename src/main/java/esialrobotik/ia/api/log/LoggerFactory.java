package esialrobotik.ia.api.log;

import java.io.IOException;
import java.io.Reader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import esialrobotik.ia.api.log.output.FileOutput;
import esialrobotik.ia.api.log.output.LogOutput;
import esialrobotik.ia.api.log.output.SocketOutput;
import esialrobotik.ia.api.log.output.StdoutOutput;
import esialrobotik.ia.core.Main;

public class LoggerFactory implements ILoggerFactory {
    private ConcurrentHashMap<String, Logger> loggerMap;
    private Level default_Level;
    private LogOutput[] outputs;
    public final static LoggerFactory INSTANCE = new LoggerFactory();

    private LoggerFactory() {
        loggerMap = new ConcurrentHashMap<>();
        this.default_Level = Level.DEBUG;

        String default_dateFormat = "yyyy-MM-dd HH:mm:ss";
        String default_logFormat = "%d [%T/%i] [%l] [%M] %m";

        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(Paths.get(Main.configFilePath))) {
            JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);
            JsonObject logConfig = configRootNode.getAsJsonObject("log");
            if (logConfig == null) {
                DateFormat dateFormat = new SimpleDateFormat(default_dateFormat);
                this.outputs = new LogOutput[] { new StdoutOutput("%d [%t/%i] [%l] [%M] %m", dateFormat) };
                return;
            }
            JsonObject logStdout = logConfig.getAsJsonObject("stdout");
            JsonObject logFile = logConfig.getAsJsonObject("file");
            JsonObject logSocket = logConfig.getAsJsonObject("socket");
            
            List<LogOutput> outputs = new ArrayList<>();
            if (logStdout != null && logStdout.get("active").getAsBoolean()) {
                String logFormat = logStdout.get("logFormat") != null ?
                    logStdout.get("logFormat").getAsString() : default_logFormat;
                String dateFormat_str = logStdout.get("dateFormat") != null ?
                    logStdout.get("dateFormat").getAsString() : default_dateFormat;
                DateFormat dateFormat = new SimpleDateFormat(dateFormat_str);
                outputs.add(new StdoutOutput(logFormat, dateFormat));
            }
            if (logFile != null && logFile.get("active").getAsBoolean()) {
                String logFormat = logStdout.get("logFormat") != null ?
                    logStdout.get("logFormat").getAsString() : default_logFormat;
                String dateFormat_str = logStdout.get("dateFormat") != null ?
                    logStdout.get("dateFormat").getAsString() : default_dateFormat;
                String fileFormat = logFile.get("fileFormat") != null ?
                    logFile.get("fileFormat").getAsString() : "logs/%d.log";
                DateFormat dateFormat = new SimpleDateFormat(dateFormat_str);
                outputs.add(new FileOutput(logFormat, dateFormat, fileFormat));
            }
            if (logSocket != null && logSocket.get("active").getAsBoolean()) {
                String logFormat = logStdout.get("logFormat") != null ?
                    logStdout.get("logFormat").getAsString() : default_logFormat;
                String dateFormat_str = logStdout.get("dateFormat") != null ?
                    logStdout.get("dateFormat").getAsString() : default_dateFormat;
                String host = logSocket.get("host") != null ?
                    logSocket.get("host").getAsString() : "localhost";
                int port = logSocket.get("port") != null ?
                    logSocket.get("port").getAsInt() : 12345;
                String whoami = logSocket.get("whoami") != null ?
                    logSocket.get("whoami").getAsString() : "unknown";
                DateFormat dateFormat = new SimpleDateFormat(dateFormat_str);
                Socket sock = new Socket(host, port);
                outputs.add(new SocketOutput(logFormat, dateFormat, whoami, sock));
            }
            this.outputs = outputs.toArray(new LogOutput[0]);
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            e.printStackTrace();
            DateFormat dateFormat = new SimpleDateFormat(default_dateFormat);
            this.outputs = new LogOutput[] { new StdoutOutput("%d [%t/%i] [%l] [%M] %m", dateFormat) };
        }
    }

    @Override
    public Logger getLogger(String name) {
        if (loggerMap.contains(name)) {
            return loggerMap.get(name);
        } else {
            NetworkLogger logger = new NetworkLogger(name, default_Level, outputs);
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
        for (LogOutput output : INSTANCE.outputs) {
            output.close();
        }
    }
}
