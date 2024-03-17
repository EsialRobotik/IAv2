package esialrobotik.ia.api.log;

import java.io.PrintStream;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.AbstractLogger;
import org.slf4j.helpers.MessageFormatter;

public class NetworkLogger extends AbstractLogger {

    protected Level level;

    private final static String SP = " ";
    private final static long START_TIME = System.currentTimeMillis();

    public NetworkLogger(String name, Level level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public boolean isTraceEnabled() {
        return level.toInt() <= Level.TRACE.toInt();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return level.toInt() <= Level.DEBUG.toInt();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return level.toInt() <= Level.INFO.toInt();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return level.toInt() <= Level.WARN.toInt();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return level.toInt() <= Level.ERROR.toInt();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return isErrorEnabled();
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return null;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    void write(StringBuilder buf, Throwable t) {
        System.err.println(buf.toString());
        if (t != null) {
            writeThrowable(t, System.err);
        }
    }

    protected void writeThrowable(Throwable t, PrintStream targetStream) {
        if (t != null) {
            t.printStackTrace(targetStream);
        }
    }

    @Override
    protected void handleNormalizedLoggingCall(Level level, Marker marker, String messagePattern, Object[] arguments,
            Throwable throwable) {
        StringBuilder buf = new StringBuilder(32);

        // append date-time
        buf.append(System.currentTimeMillis() - START_TIME);
        buf.append(SP);

        // append thread name
        buf.append("[");
        buf.append(Thread.currentThread().getName());
        buf.append("/");
        buf.append(Thread.currentThread().getId());
        buf.append("]");
        buf.append(SP);

        // append log level
        buf.append("[");
        buf.append(level.name());
        buf.append("]");
        buf.append(SP);

        // append marker
        if (marker != null) {
            buf.append("[");
            buf.append(marker.getName());
            buf.append("]");
            buf.append(SP);
        }

        // append message
        String formattedMessage = MessageFormatter.basicArrayFormat(messagePattern, arguments);
        buf.append(formattedMessage);

        write(buf, throwable);
    }
}
