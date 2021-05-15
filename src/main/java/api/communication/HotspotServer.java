package api.communication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HotspotServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = null;
        Socket socket = null;
        System.out.println("Server start");

        try {
            serverSocket = new ServerSocket(4269);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Connexion");
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new EchoThread(socket).start();
        }
    }

    private static class EchoThread extends Thread {
        protected Socket socket;

        public EchoThread(Socket clientSocket) {
            this.socket = clientSocket;
        }

        public void run() {
            try {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String line;
                while (true) {
                        line = reader.readLine();
                        if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                            socket.close();
                            return;
                        } else {
                            writer.println(line + " from server");
                            System.out.println(line);
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
