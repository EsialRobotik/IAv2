package esialrobotik.ia.api.log.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;

public class FileOutput implements LogOutput {
    
    private FileOutputStream file;
    private String logFormat;
    private DateFormat dateFormat;

    public FileOutput(String logFormat, DateFormat dateFormat, String fileFormat) throws FileNotFoundException {
        this.logFormat = logFormat;
        this.dateFormat = dateFormat;

        String now = dateFormat.format(System.currentTimeMillis());
        String filepath = fileFormat.replace("%d", now);
        File f = new File(filepath);
        f.getParentFile().mkdirs();
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("Error while creating the file output...");
                e.printStackTrace();
            }
        }
        file = new FileOutputStream(filepath);
    }

    @Override
    public PrintStream getOutput() {
        return new PrintStream(file);
    }

    @Override
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    @Override
    public String getLogFormat() {
        return logFormat;
    }

    @Override
    public void close() {
        try {
            file.close();
        } catch (IOException e) {
            System.err.println("Error while closing the file output...");
            e.printStackTrace();
        }
    }
    
}
