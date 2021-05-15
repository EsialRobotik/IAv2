package api.communication;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class HotspotSocket {

    public static void main(String[] args) throws Exception {
        String hostname = "localhost";
        int port = 4269;
        try (Socket socket = new java.net.Socket(hostname, port)) {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            System.out.println("Socket start");

            while (true) {
                writer.println("Coucou");

                String data = reader.readLine();
                System.out.println(data);

                Thread.sleep(1000);
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

}
