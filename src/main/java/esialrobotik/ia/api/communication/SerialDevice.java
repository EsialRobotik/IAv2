package esialrobotik.ia.api.communication;

import esialrobotik.ia.api.Pi4JContext;
import esialrobotik.ia.api.log.LoggerFactory;
import com.pi4j.context.Context;
import com.pi4j.io.serial.*;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;

/**
 * Communication serie
 * Communication série pour une communication pi via Pi4J
 * @see <a href="https://pi4j.com/documentation/io-examples/serial/">Pi4J Serial</a>
 */
public class SerialDevice {

    /**
     * Pi4J Serial
     */
    protected Serial serial;

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
    public SerialDevice(String serialPort, Baud baudRate) {
        logger = LoggerFactory.getLogger(SerialDevice.class);
        logger.info("Serial " + serialPort + " init at baud " + baudRate.getValue());
        this.serialPort = serialPort;
        Context pi4j = Pi4JContext.getInstance();
        this.serial = pi4j.create(
            Serial.newConfigBuilder(pi4j)
                .baud(baudRate)
                .dataBits_8()
                .parity(Parity.NONE)
                .stopBits(StopBits._1)
                .flowControl(FlowControl.NONE)
                .id(serialPort)
                .device(serialPort)
                .provider("pigpio-serial")
                .build()
        );
        try {
            serial.open();
        } catch (Exception e) {
            logger.error("Serial " + serialPort + " init fail at baud " + baudRate.getValue() + " : " + e.getMessage());
        }
    }

    /**
     * Constructeur
     * @param serialPort Nom du port série (ex : /dev/ttyUSB0 ou /dev/ttyAMA0
     * @param baudRate Baud rate
     */
    public SerialDevice(String serialPort, int baudRate) {
        logger = LoggerFactory.getLogger(SerialDevice.class);

        logger.info("Serial " + serialPort + " init at baud " + baudRate);
        this.serialPort = serialPort;
        Context pi4j = Pi4JContext.getInstance();
        this.serial = pi4j.create(
            Serial.newConfigBuilder(pi4j)
                .baud(baudRate)
                .dataBits_8()
                .parity(Parity.NONE)
                .stopBits(StopBits._1)
                .flowControl(FlowControl.NONE)
                .id(serialPort)
                .device(serialPort)
                .provider("pigpio-serial")
                .build()
        );
        try {
            serial.open();
        } catch (Exception e) {
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
            serial.write(string + "\r\n");
            // writeln not exists anymire, \r\n could be wrong
        } catch (Exception e) {
            logger.error("Serial " + serialPort + " write fail : " + e.getMessage());
        }
    }

    public void write(int value) {
        try {
            serial.write(intToUnsignedByte(value));
            // flush not exist anymore, can be an issue
        } catch (Exception e) {
            logger.error("Serial " + serialPort + " write fail : " + e.getMessage());
        }
    }

    public Serial getSerial() {
        return serial;
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
        } catch (Exception e) {
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
