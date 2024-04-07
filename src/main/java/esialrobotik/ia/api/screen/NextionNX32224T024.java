package esialrobotik.ia.api.screen;

import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.Serial;
import esialrobotik.ia.api.communication.SerialDevice;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.pathfinding.table.TableColor;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Nextion NX32224T024 screen
 * See https://github.com/EsialRobotik/Affichage_Nextion for screen usage
 */
public class NextionNX32224T024 {

    /**
     * Serial port of the screen
     */
    protected SerialDevice serialDevice;

    /**
     * Logger
     */
    protected Logger logger = null;

    private String status = "";
    private String color = "";
    private boolean calibrationStarted = false;

    public NextionNX32224T024(String serialPort, Baud baudRate) {
        logger = LoggerFactory.getLogger(NextionNX32224T024.class);

        logger.info("Initialisation de la liason série du Nextion, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        serialDevice = new SerialDevice(serialPort, baudRate, "nextion");
        startSerialReader();
    }

    public NextionNX32224T024(JsonObject config) {
        this.logger = LoggerFactory.getLogger(NextionNX32224T024.class);
        String serialPort = config.get("serie").getAsString();
        Baud baudRate = Baud.getInstance(config.get("baud").getAsInt());

        logger.info("Initialisation de la liason série du Nextion, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        serialDevice = new SerialDevice(serialPort, baudRate, "nextion");
        startSerialReader();
    }

    protected void sendInstruction(String instruction) {
        byte[] endOfLine = new byte[]{(byte)255,(byte)255,(byte)255};
        serialDevice.write(instruction, endOfLine);
    }

    public void gotoPage(String pageName) {
        String page = "unknow";
        switch (pageName) {
            case "init":
                page = "page0";
                break;
            case "color":
                page = "page1";
                break;
            case "calibration":
                page = "page2";
                break;
            case "ready":
                page = "page3";
                break;
            case "score":
                page = "page4";
                break;
        }
        logger.info("Go to page " + page);
        sendInstruction("page " + page);
    }

    public void displayColor(String color) {
        sendInstruction("robotcolor.txt=\"" + color.toUpperCase() + "\"");
    }

    public void displayCalibrationStatus(String status) {
        this.status += "\r\n"+status;
        sendInstruction("status.txt=\"" + this.status + "\"");
    }

    public void displayScore(int score) {
        sendInstruction("score.val=" + score);
    }

    private void startSerialReader() {
        Thread serialReaderThread = new Thread(() -> {
            // We use a buffered reader to handle the data received from the serial port
            BufferedReader br = new BufferedReader(new InputStreamReader(this.serialDevice.getInputStream()));
            Serial serial = this.serialDevice.getSerial();

            try {
                // Data from the esialrobotik.ia.asserv is recieved in lines
                String line = "";

                // Read data
                while (true) {
                    // First we need to check if there is data available to read.
                    // The read() command for pigpio-serial is a NON-BLOCKING call,
                    // in contrast to typical java input streams.
                    int available = serial.available();
                    if (available > 0) {
                        for (int i = 0; i < available; i++) {
                            char b = (char) br.read();
                            if (b == '@') {
                                // All non-string bytes are handled as line breaks
                                if (!line.isEmpty()) {
                                    this.logger.trace("Read : " + line);
                                    parseLine(line);
                                    line = "";
                                }
                            } else {
                                line += b;
                            }
                        }
                    } else {
                        Thread.sleep(10);
                    }
                }
            } catch (Exception e) {
                this.logger.error("Error reading data from serial: " + e.getMessage());
            }
        }, "SerialReader");
        serialReaderThread.setDaemon(true);
        serialReaderThread.start();
    }

    private void parseLine(String line) {
        if (line.startsWith("gopage")) {
            String page = line.split(" ")[1];
            gotoPage(page);
            if (page.equals("calibration")) {
                displayCalibrationStatus("Début calibration");
                calibrationStarted = true;
            }
        } else if (line.startsWith("color")) {
            displayColor(line.split(" ")[1]);
            this.color = line.split(" ")[1];
        }
    }

    public boolean isColor0() {
        return this.color.equalsIgnoreCase(TableColor.COLOR_0.toString());
    }

    public void waitForCalibration() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (calibrationStarted) {
                return;
            }
        }
    }
}
