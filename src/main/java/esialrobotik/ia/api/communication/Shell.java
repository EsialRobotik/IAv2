package esialrobotik.ia.api.communication;

import java.io.*;

public class Shell {

    protected String command;
    protected Process shell;

    private BufferedReader reader;
    private BufferedWriter writer;

    public Shell(String command) {
        this.command = command;
    }

    public void start() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", this.command);
        this.shell = processBuilder.start();
        this.reader = new BufferedReader(new InputStreamReader(this.shell.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(this.shell.getOutputStream()));
    }

    public void send(String command) throws IOException {
        writer.write(command);
        writer.close();
    }

    public String read() throws IOException {
        String line;
        StringBuilder res = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            res.append(line+ "\n");
        }
        return res.toString();
    }
}
