package esialrobotik.ia.api.log.output;

import java.io.PrintStream;
import java.text.DateFormat;

import org.slf4j.event.Level;

public class StdoutOutput implements LogOutput {
    
    private Level level;
    private String logFormat;
    private DateFormat dateFormat;

    public StdoutOutput(String logFormat, DateFormat dateFormat, Level level) {
        this.logFormat = logFormat;
        this.dateFormat = dateFormat;
        this.level = level;
    }

    @Override
    public PrintStream getOutput() {
        return System.out;
    }

    @Override
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getLogFormat() {
        return logFormat;
    }

    @Override
    public void close() {
        // nothing to do
    }
    
}
