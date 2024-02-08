package esialrobotik.ia.api.communication;

import esialrobotik.ia.api.ax12.AX12LinkSerial;
import esialrobotik.ia.api.log.LoggerFactory;
import gnu.io.*;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.Enumeration;

/**
 * Communication serie avec RXTX
 */
public class SerialRxTx {

    /**
     * RXTX Serial
     */
    protected SerialPort serial;

    protected OutputStream outputestream;

    /**
     * Port série
     */
    protected String serialPortName;

    /**
     * Logger
     */
    protected Logger logger = null;

    /**
     * Constructeur
     * @param serialPortName Nom du port série (ex : /dev/ttyUSB0 ou /dev/ttyAMA0
     * @param baudRate Baud rate
     */
    public SerialRxTx(String serialPortName, int baudRate) {
        logger = LoggerFactory.getLogger(SerialRxTx.class);

        logger.info("init serial port " + serialPortName + " at baud " + baudRate);
        this.serialPortName = serialPortName;
        try {
            this.serial = SerialRxTx.getSerialPort(serialPortName);

            if (serial == null) {
                throw new IOException(serialPortName+" not found or in use");
            }
            this.serial.setDTR(false);
            this.serial.setRTS(false);
            this.serial.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

            this.serial.setSerialPortParams(baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE );
            this.serial.enableReceiveTimeout(50);
            this.outputestream = serial.getOutputStream();
        } catch (IOException | UnsupportedCommOperationException e) {
            logger.error(SerialRxTx.class + " " + serialPortName + " init fail at baud " + baudRate + " : " + e.getMessage());
        }
    }

    public static SerialPort getSerialPort(String name) {
        @SuppressWarnings("unchecked")
        Enumeration<CommPortIdentifier> p = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier cpi;
        CommPort cp;

        String realName = null;
        try {
            realName = (new File(name)).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(p.hasMoreElements()){
            try {
                cpi = p.nextElement();
                System.out.println(cpi.getName());
                if(cpi != null && !cpi.isCurrentlyOwned() && (realName == null || cpi.getName().equals(realName) || cpi.getName().equals(name))) {
                    cp = cpi.open(AX12LinkSerial.class.getName(), 500);
                    if(cp instanceof gnu.io.SerialPort){
                        return (SerialPort) cp;
                    }
                }
            } catch (PortInUseException e) {
            }
        }

        return null;
    }

    /**
     * Envoie une string sur la liaison série
     * @param string String à envoyer
     */
    public void write(String string) {
        try {
            logger.info(SerialRxTx.class + " " + serialPortName + " write : " + string);
            this.outputestream.write(string.getBytes());
            this.outputestream.flush();
        } catch (IOException e) {
            logger.error(SerialRxTx.class + " " + serialPortName + " write fail : " + e.getMessage());
        }
    }

    /**
     * Envoie une string sur la liaison série
     * @param bytes bytes à envoyer
     */
    public void write(byte[] bytes) {
        try {
            logger.info(SerialRxTx.class + " " + serialPortName + " write "+bytes.length+" byte(s)");
            this.outputestream.write(bytes);
            this.outputestream.flush();
        } catch (IOException e) {
            logger.error(SerialRxTx.class + " " + serialPortName + " write fail : " + e.getMessage());
        }
    }

    public void write(int value) {
        try {
            this.outputestream.write(intToUnsignedByte(value));
            this.outputestream.flush();
        } catch (IOException e) {
            logger.error(SerialRxTx.class + " " + serialPortName + " write fail : " + e.getMessage());
        }
    }

    public void setDTR(boolean b) {
        try {
            serial.setDTR(b);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream() throws IOException {
        return serial.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return serial.getOutputStream();
    }

    public void close() {
        try {
            serial.close();
        } catch (IllegalStateException e) {
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
