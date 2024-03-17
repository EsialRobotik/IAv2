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
        String default_logFormat = "%d [%t/%i] [%l] [%M] %m";
        String default_lvl = "DEBUG";

        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(Paths.get(Main.configFilePath))) {
            JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);
            JsonObject logConfig = configRootNode.getAsJsonObject("log");
            if (logConfig == null) {
                DateFormat dateFormat = new SimpleDateFormat(default_dateFormat);
                this.outputs = new LogOutput[] { new StdoutOutput("%d [%t/%i] [%l] [%M] %m", dateFormat, default_Level) };
                return;
            }
            JsonObject logStdout = logConfig.get("stdout").getAsJsonObject();
            JsonObject logFile = logConfig.get("file").getAsJsonObject();
            JsonObject logSocket = logConfig.get("socket").getAsJsonObject();
            
            List<LogOutput> outputs = new ArrayList<>();
            if (logStdout != null && logStdout.get("active").getAsBoolean()) {
                String logFormat = logStdout.get("logFormat").getAsString();
                String dateFormat_str = logStdout.get("dateFormat").getAsString();
                String level = logStdout.get("level").getAsString();
                if (level == null) {
                    level = default_lvl;
                }
                if (logFormat == null) {
                    logFormat = default_logFormat;
                }
                if (dateFormat_str == null) {
                    dateFormat_str = default_dateFormat;
                }
                DateFormat dateFormat = new SimpleDateFormat(dateFormat_str);
                outputs.add(new StdoutOutput(logFormat, dateFormat, Level.valueOf(level)));
            }
            if (logFile != null && logFile.get("active").getAsBoolean()) {
                String logFormat = logFile.get("logFormat").getAsString();
                String fileFormat = logFile.get("fileFormat").getAsString();
                String dateFormat_str = logFile.get("dateFormat").getAsString();
                String level = logFile.get("level").getAsString();
                if (level == null) {
                    level = default_lvl;
                }
                if (logFormat == null) {
                    logFormat = default_logFormat;
                }
                if (fileFormat == null) {
                    fileFormat = "log.txt";
                }
                if (dateFormat_str == null) {
                    dateFormat_str = default_dateFormat;
                }
                DateFormat dateFormat = new SimpleDateFormat(dateFormat_str);
                outputs.add(new FileOutput(logFormat, dateFormat, default_Level, fileFormat));
            }
            if (logSocket != null && logSocket.get("active").getAsBoolean()) {
                String logFormat = logSocket.get("logFormat").getAsString();
                String dateFormat_str = logSocket.get("dateFormat").getAsString();
                String level = logSocket.get("level").getAsString();
                String whoami = logSocket.get("whoami").getAsString();
                String host = logSocket.get("host").getAsString();
                int port = logSocket.get("port").getAsInt();
                if (level == null) {
                    level = default_lvl;
                }
                if (logFormat == null) {
                    logFormat = default_logFormat;
                }
                if (dateFormat_str == null) {
                    dateFormat_str = default_dateFormat;
                }
                if (whoami == null) {
                    whoami = "unknown";
                }
                if (host == null) {
                    host = "localhost";
                }
                if (port == 0) {
                    port = 4269;
                }
                DateFormat dateFormat = new SimpleDateFormat(dateFormat_str);
                Socket sock = new Socket(host, port);
                outputs.add(new SocketOutput(logFormat, dateFormat, Level.valueOf(level), whoami, sock));
            }
            this.outputs = outputs.toArray(new LogOutput[0]);
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            e.printStackTrace();
            DateFormat dateFormat = new SimpleDateFormat(default_dateFormat);
            this.outputs = new LogOutput[] { new StdoutOutput("%d [%t/%i] [%l] [%M] %m", dateFormat, default_Level) };
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
