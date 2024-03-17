package esialrobotik.ia.api.log;

import java.io.PrintStream;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.AbstractLogger;
import org.slf4j.helpers.MessageFormatter;

import esialrobotik.ia.api.log.output.LogOutput;

public class NetworkLogger extends AbstractLogger {

    protected Level level;
    protected LogOutput[] outputs;

    private final static String SP = " ";
    private final static long START_TIME = System.currentTimeMillis();

    public NetworkLogger(String name, Level level, LogOutput[] outputs) {
        this.name = name;
        this.level = level;
        this.outputs = outputs;
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

    void write(StringBuilder buf, Throwable t, PrintStream targetStream) {
        targetStream.println(buf.toString());
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
        for (LogOutput output : outputs) {
            StringBuilder buf = new StringBuilder(128);
            // For each char in the log format
            // if it is a '%', and our flag is true, we add % to the buffer, otherwise we
            // set the flag to true
            // otherwise if the flag is true, we add the corresponding value to the buffer
            // otherwise we add the char to the buffer
            String logFormat = output.getLogFormat();
            boolean flag = false;
            for (int i = 0; i < logFormat.length(); i++) {
                char c = logFormat.charAt(i);
                if (c == '%') {
                    if (flag) {
                        buf.append('%');
                        flag = false;
                    } else {
                        flag = true;
                    }
                } else {
                    if (flag) {
                        switch (c) {
                            case 'd':
                                buf.append(output.getDateFormat().format(System.currentTimeMillis()));
                                break;
                            case 't':
                                buf.append(System.currentTimeMillis() - START_TIME);
                                break;
                            case 'l':
                                buf.append(level.toString());
                                break;
                            case 'n':
                                buf.append(name);
                                break;
                            case 'm':
                                buf.append(MessageFormatter.arrayFormat(messagePattern, arguments).getMessage());
                                break;
                            case 'T':
                                buf.append(Thread.currentThread().getName());
                                break;
                            case 'i':
                                buf.append(Thread.currentThread().getId());
                                break;
                            case 'M':
                                if (marker != null) {
                                    buf.append(marker.getName());
                                } else {
                                    buf.append("none");
                                }
                                break;
                            case 'W':
                                if (output instanceof esialrobotik.ia.api.log.output.SocketOutput) {
                                    buf.append(((esialrobotik.ia.api.log.output.SocketOutput) output).getWhoami());
                                } else {
                                    buf.append("unknown");
                                }
                                break;
                            default:
                                buf.append(c);
                        }
                        flag = false;
                    } else {
                        buf.append(c);
                    }
                }
            }
            write(buf, throwable, output.getOutput());
        }
    }
}
