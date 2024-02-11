package esialrobotik.ia.api.log;

import java.io.PrintStream;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.AbstractLogger;

public class NetworkLogger extends AbstractLogger {

    protected Level level;

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
        // TODO
    }

    protected void writeThrowable(Throwable t, PrintStream targetStream) {
        if (t != null) {
            t.printStackTrace(targetStream);
        }
    }

    @Override
    protected void handleNormalizedLoggingCall(Level level, Marker marker, String messagePattern, Object[] arguments,
            Throwable throwable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleNormalizedLoggingCall'");
    }

    
}
