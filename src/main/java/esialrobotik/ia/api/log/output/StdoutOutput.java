package esialrobotik.ia.api.log.output;

import java.io.PrintStream;
import java.text.DateFormat;

public class StdoutOutput implements LogOutput {
    
    private String logFormat;
    private DateFormat dateFormat;

    public StdoutOutput(String logFormat, DateFormat dateFormat) {
        this.logFormat = logFormat;
        this.dateFormat = dateFormat;
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
    public String getLogFormat() {
        return logFormat;
    }

    @Override
    public void close() {
        // nothing to do
    }
    
}
