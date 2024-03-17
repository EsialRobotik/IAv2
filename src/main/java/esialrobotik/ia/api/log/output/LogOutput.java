package esialrobotik.ia.api.log.output;

import java.io.PrintStream;
import java.text.DateFormat;

/*
 * Interface for log output
 */
public interface LogOutput {
    // Get the output stream for the log
    PrintStream getOutput();
    
    // Get the date format for the log
    DateFormat getDateFormat();

    // Get the log format to use for this output
    String getLogFormat();

    // Close the output
    // This helps to close the output when the logger is closed,
    // and therefore to release the resources that are used by the output
    void close();
}