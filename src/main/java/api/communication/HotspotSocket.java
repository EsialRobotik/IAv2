package api.communication;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class HotspotSocket {

    private String hostname;
    private int port;
    private String who;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;


    public HotspotSocket(String hostname, int port, String who) throws InterruptedException {
        this.hostname = hostname;
        this.port = port;
        this.who = who;

        try {
            socket = new java.net.Socket(this.hostname, this.port);
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            writer.println(this.who);
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    public void write(String message) {
        this.writer.println(message);
    }

    public String read() {
        try {
            return this.reader.ready() ? this.reader.readLine() : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String hostname = "localhost";
        int port = 4269;
        String who = "robot";
//        String who = "loggerListener";
        HotspotSocket socket = new HotspotSocket(hostname, port, who);
        while (true) {
            socket.write("Coucou 2");
            System.out.println(socket.read());
            Thread.sleep(200);
        }
    }
}
