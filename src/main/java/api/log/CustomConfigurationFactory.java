package api.log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.Main;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.net.Protocol;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@Plugin(name = "DebugConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class CustomConfigurationFactory extends ConfigurationFactory {

    public static final String rollingFilename =  "log.log";
    public static final String rollingFilenameArchive =  "log-%d{MM-dd-yy HH:mm}.log";
    private Level level;

    //We need it to make log4j happy (it uses introspection at one point)
    public CustomConfigurationFactory(){
        super();
    }

    public CustomConfigurationFactory(Level level){
        super();
        this.level = level;
    }

    private Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) throws IOException {
        if (this.level == null) { //The first call to this thing is issue by this motherfucker of log4j and so if this things is null, it crash.
            // but if the factory is redeclare after with a different log level it works O.o
            this.level = Level.DEBUG;
        }
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.ERROR);

        builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).
                addAttribute("level", level));

        AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").
                addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d [%t][%c{1}] %-5level: %msg%n%throwable"));
        appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
                Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));
        builder.add(appenderBuilder);

        builder.add(builder.newLogger("org.apache.logging.log4j", level).
                add(builder.newAppenderRef("Stdout")).
                addAttribute("additivity", false));

        JsonObject loggerSocketConfig = null;
        if (!this.level.equals(Level.OFF)) {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(Main.configFilePath));
            JsonObject configRootNode = gson.fromJson(reader, JsonObject.class);
            loggerSocketConfig = configRootNode.getAsJsonObject("loggerSocket");
            if (loggerSocketConfig != null && loggerSocketConfig.get("active").getAsBoolean()) {
                appenderBuilder = builder.newAppender("Socket", "Socket")
                        .addAttribute("host", loggerSocketConfig.get("host").getAsString())
                        .addAttribute("protocol", Protocol.TCP)
                        .addAttribute("port", loggerSocketConfig.get("logPort").getAsInt())
                        .addAttribute("reconnectDelayMillis", -1)
                        .addAttribute("immediateFail", false);
                appenderBuilder.add(builder.newLayout("PatternLayout").
                        addAttribute("pattern", "%d [" + loggerSocketConfig.get("who").getAsString() + "][%t][%c{1}] %-5level: %msg%n%throwable"));
                appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
                        Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));
                builder.add(appenderBuilder);
                System.out.println("Socket logger added");
            }
        }

        LayoutComponentBuilder layoutBuilder;
        layoutBuilder = builder.newLayout("PatternLayout")
                .addAttribute("pattern", "%d [%t][%c{1}] %-5level: %msg%n");

        ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));

        appenderBuilder = builder.newAppender("rolling", "RollingFile")
                .addAttribute("fileName", rollingFilename)
                .addAttribute("filePattern", "archive/" + rollingFilenameArchive)
                .add(layoutBuilder)
                .addComponent(triggeringPolicy);
        builder.add(appenderBuilder);

        builder.add( builder.newLogger( "rolling", level )
                .add( builder.newAppenderRef( "rolling" ) )
                .addAttribute( "additivity", false ) );

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(level)
                .add(builder.newAppenderRef("Stdout"))
                .add(builder.newAppenderRef("rolling"));
        if (loggerSocketConfig != null && loggerSocketConfig.get("active").getAsBoolean()) {
            rootLogger.add(builder.newAppenderRef("Socket"));
        }
        builder.add(rootLogger);
        return builder.build();
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        try {
            return createConfiguration(name, builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
