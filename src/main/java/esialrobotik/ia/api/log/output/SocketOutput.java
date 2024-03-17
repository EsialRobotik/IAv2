package esialrobotik.ia.api.log.output;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.DateFormat;

import org.slf4j.event.Level;

public class SocketOutput implements LogOutput {
    
    private Socket sock;
    private Level level;
    private String logFormat;
    private DateFormat dateFormat;
    private String whoami;

    public SocketOutput(String logFormat, DateFormat dateFormat, Level level, String whoami, Socket sock) {
        this.logFormat = logFormat;
        this.dateFormat = dateFormat;
        this.level = level;
        this.sock = sock;
        this.whoami = whoami;
    }

    @Override
    public PrintStream getOutput() {
        try {
            return new PrintStream(sock.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error while getting the socket output...");
            e.printStackTrace();
        }
        return null;
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

    public String getWhoami() {
        return whoami;
    }

    @Override
    public void close() {
        try {
            sock.close();
        } catch (IOException e) {
            System.err.println("Error while closing the socket output...");
            e.printStackTrace();
        }
    }
    
}
