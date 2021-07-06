package api.communication;

import api.log.LoggerFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class HotspotSocket extends WebSocketClient {

    private String hostname;
    private int port;
    private String who;
    private Logger logger;
    private String lastMessage;

    public HotspotSocket(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public HotspotSocket(URI serverURI) {
        super(serverURI);
    }

    public HotspotSocket(String hostname, int port, String who) throws URISyntaxException, InterruptedException {
        super(new URI("ws://" + hostname + ":" + port));
        this.hostname = hostname;
        this.port = port;
        this.who = who;
        this.logger = LoggerFactory.getLogger(HotspotSocket.class);
        this.connectBlocking();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send(this.who);
        this.logger.info("New connection opened to " + this.hostname + " for " + this.who);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        this.logger.info("Closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        this.logger.info("Received message: " + message);
        this.lastMessage = message;
    }

    @Override
    public void onMessage(ByteBuffer message) {
        this.logger.info("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        this.logger.error("An error occurred:" + ex);
    }

    public String read() {
        String res = this.lastMessage;
        this.lastMessage = null;
        return res;
    }

    public static void main(String[] args) throws Exception {
        LoggerFactory.init(Level.TRACE);
        Logger logger = LoggerFactory.getLogger(HotspotSocket.class);
        logger.info("init logger");

        String hostname = "localhost";
        int port = 4269;
        String who = "robot";
//        String who = "loggerListener";
        HotspotSocket socket = new HotspotSocket(hostname, port, who);
        HotspotSocket socket2 = new HotspotSocket(hostname, port, who);
        Thread.sleep(500);
        int i = 0;
        while (true) {
            socket.send("Coucou from socket 1 : " + i);
            Thread.sleep(500);
            System.out.println("socket 2 : " + socket2.read());
            socket2.send("Coucou from socket 2 : " + i);
            Thread.sleep(500);
            System.out.println("socket 1 : " + socket.read());
            i++;
        }
    }
}
