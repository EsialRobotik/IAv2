package api.communication;

import api.log.LoggerFactory;
import com.pi4j.io.serial.*;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;

/**
 * Communication serie
 *
 * Communication série pour une communication pi via Pi4J
 * @see <a href="http://pi4j.com/example/serial.html">Pi4J Serial Example</a>
 */
public class Serial {

    /**
     * Pi4J Serial
     */
    protected com.pi4j.io.serial.Serial serial;

    /**
     * Port série
     */
    protected String serialPort;

    /**
     * Logger
     */
    protected Logger logger = null;

    /**
     * Constructeur
     * @param serialPort Nom du port série (ex : /dev/ttyUSB0 ou /dev/ttyAMA0
     * @param baudRate Baud rate
     */
    public Serial(String serialPort, Baud baudRate) {
        logger = LoggerFactory.getLogger(Serial.class);

        logger.info("Serial " + serialPort + " init at baud " + baudRate.getValue());
        this.serialPort = serialPort;
        SerialConfig serialConfig = new SerialConfig();
        serialConfig.device(serialPort)
                .baud(baudRate)
                .dataBits(DataBits._8)
                .parity(Parity.NONE)
                .stopBits(StopBits._1)
                .flowControl(FlowControl.NONE);

        serial = SerialFactory.createInstance();
        try {
            serial.open(serialConfig);
        } catch (IOException e) {
            logger.error("Serial " + serialPort + " init fail at baud " + baudRate.getValue() + " : " + e.getMessage());
        }
    }

    /**
     * Constructeur
     * @param serialPort Nom du port série (ex : /dev/ttyUSB0 ou /dev/ttyAMA0
     * @param baudRate Baud rate
     */
    public Serial(String serialPort, int baudRate) {
        logger = LoggerFactory.getLogger(Serial.class);

        logger.info("Serial " + serialPort + " init at baud " + baudRate);
        this.serialPort = serialPort;
        serial = SerialFactory.createInstance();
        try {
            serial.open(serialPort, baudRate);
        } catch (IOException e) {
            logger.error("Serial " + serialPort + " init fail at baud " + baudRate + " : " + e.getMessage());
        }
    }

    /**
     * Envoie une string sur la liaison série
     * @param string String à envoyer
     */
    public void write(String string) {
        try {
            logger.info("Serial " + serialPort + " write : " + string);
            serial.writeln(string);
        } catch (IOException e) {
            logger.error("Serial " + serialPort + " write fail : " + e.getMessage());
        }
    }

    /**
     * Envoie une string sur la liaison série
     * @param bytes bytes à envoyer
     */
    public void write(byte[] bytes) {
        try {
            logger.info("Serial " + serialPort + " write "+bytes.length+" byte(s)");
            serial.write(bytes);
            serial.flush();
        } catch (IOException e) {
            logger.error("Serial " + serialPort + " write fail : " + e.getMessage());
        }
    }

    public void write(int value) {
        try {
            serial.write(intToUnsignedByte(value));
            serial.flush();
        } catch (IOException e) {
            logger.error("Serial " + serialPort + " write fail : " + e.getMessage());
        }
    }
    
    /**
     * Ajoute des listeners écoutant la liaison série
     * @param serialDataEventListeners Listeners écoutant la liaison série
     * @see <a href="http://pi4j.com/example/serial.html">Pi4J Serial Example</a>
     */
    public void addReaderListeners(SerialDataEventListener... serialDataEventListeners) {
        serial.addListener(serialDataEventListeners);
    }

    /**
     * Supprime des listeners
     * @param serialDataEventListeners Listeners à supprimer
     */
    public void removeReaderListeners(SerialDataEventListener... serialDataEventListeners) {
        serial.removeListener(serialDataEventListeners);
    }

    public void setDTR(boolean b) {
        try {
            serial.setDTR(b);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream() {
        return serial.getInputStream();
    }

    public OutputStream getOutputStream() {
        return serial.getOutputStream();
    }

    public void close() {
        try {
            serial.close();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param val
     * @return
     * @throws IllegalArgumentException Si la valeur donnée n'est pas comprise entre 0 et 255
     */
    public static byte intToUnsignedByte(int val) throws IllegalArgumentException {
        if (val < 0 || val > 255) {
            throw new IllegalArgumentException("La valeur doit être comprise entre 0 et 255 : "+val);
        }

        if(val == 0) {
            return 0;
        }
        BitSet bs = new BitSet(8);
        int vals[] = new int[]{1, 2, 4, 8, 16, 32, 64, 128};

        for(int i=vals.length-1; i>=0; i--) {
            if (val >= vals[i]) {
                val -= vals[i];
                bs.set(i, true);
            } else {
                bs.set(i, false);
            }
        }

        return bs.toByteArray()[0];
    }
}
