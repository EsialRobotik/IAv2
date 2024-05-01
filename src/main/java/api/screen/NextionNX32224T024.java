package api.screen;

import api.communication.Serial;
import api.log.LoggerFactory;
import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import org.apache.logging.log4j.Logger;
import pathfinding.table.TableColor;

import java.io.IOException;

public class NextionNX32224T024 {

    /**
     * Serial port of the screen
     */
    protected Serial serialDevice;

    /**
     * Logger
     */
    protected Logger logger = null;

    private String status = "";
    private String color = "";
    private boolean calibrationStarted = false;

    public NextionNX32224T024(String serialPort, Baud baudRate) {
        this.logger = LoggerFactory.getLogger(NextionNX32224T024.class);

        logger.info("Initialisation de la liason série du Nextion, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        serialDevice = new Serial(serialPort, baudRate);
        startSerialReader();
    }

    public NextionNX32224T024(JsonObject config) {
        this.logger = LoggerFactory.getLogger(NextionNX32224T024.class);
        String serialPort = config.get("serie").getAsString();
        Baud baudRate = Baud.getInstance(config.get("baud").getAsInt());

        logger.info("Initialisation de la liason série du Nextion, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        serialDevice = new Serial(serialPort, baudRate);
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
        serialDevice.addReaderListeners(serialDataEvent -> {
            try {
                String serialBuffer = serialDataEvent.getAsciiString();
                logger.trace("Nextion reading : " + serialBuffer);
                parseLine(serialBuffer.replaceAll("@", ""));
            } catch (IOException e) {
                logger.error("Echec du parsing de la ligne : " + e.getMessage());
            }
        });
    }

    private void parseLine(String line) {
        if (line.startsWith("gopage")) {
            String page = line.split(" ")[1];
            gotoPage(page);
            if (page.equals("calibration")) {
                displayCalibrationStatus("Debut calibration");
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
