package api.communication;

import api.log.LoggerFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Communication I2C
 *
 * Communication I2C pour une communication pi via Pi4J
 * @see <a href='https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/I2CExample.java'>Pi4J I2C Example</a>
 */
public class I2C {

    /**
     * Pi4J I2C device
     */
    protected I2CDevice i2CDevice;

    /**
     * Adresse du device
     */
    protected int deviceAddress;

    /**
     * Logger
     */
    protected Logger logger = null;

    /**
     * Constructeur
     * @param deviceAddress Adresse du device (ex : 0x39)
     */
    public I2C(int deviceAddress) {
        logger = LoggerFactory.getLogger(I2C.class);
        this.deviceAddress = deviceAddress;

        try {
            logger.info(String.format("I2C 0x%02X init", deviceAddress));
            I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            i2CDevice = i2c.getDevice(deviceAddress);
        } catch (I2CFactory.UnsupportedBusNumberException | IOException e) {
            logger.error(String.format("I2C 0x%02X init fail : " + e.getMessage(), deviceAddress));
        }
    }

    /**
     * Constructeur
     * @param deviceAddress Adresse du device (ex : 0x39)
     */
    public I2C(byte deviceAddress) {
        this((int) deviceAddress);
    }

    /**
     * Lit la valeur d'un registre du device via l'I2C
     * @param register Registre à lire
     * @return Valeur du registre
     */
    public int read(byte register) {
        try {
            return i2CDevice.read(register);
        } catch (IOException e) {
            logger.error(String.format("I2C 0x%02X read register 0x%02X fail : " + e.getMessage(), deviceAddress, register));
            return 0;
        }
    }

    public int read(byte register, byte[] buffer, int bufferOffset, int size) {
        try {

            return i2CDevice.read(register, buffer, bufferOffset,size);
        } catch (IOException e) {
            logger.error(String.format("I2C 0x%02X read register 0x%02X fail : " + e.getMessage(), deviceAddress, register));
            return 0;
        }
    }

    public int read(byte[] buffer, int bufferOffset, int size) {
        try {

            return i2CDevice.read(buffer, bufferOffset,size);
        } catch (IOException e) {
            logger.error(String.format("I2C 0x%02X read register fail : " + e.getMessage(), deviceAddress));
            return 0;
        }
    }
    public int read(int address, byte[] buffer, int bufferOffset, int size) {
        try {

            return i2CDevice.read(address, buffer, bufferOffset,size);
        } catch (IOException e) {
            logger.error(String.format("I2C 0x%02X read register fail : " + e.getMessage(), deviceAddress));
            return 0;
        }
    }

    public void write(byte register, byte value) {
        try {
            i2CDevice.write(register, value);
        } catch (IOException e) {
            logger.error(String.format("I2C 0x%02X write register 0x%02X with value 0x%02X fail : " + e.getMessage(), deviceAddress, register , value));
        }
    }

    public void write(byte register, byte[] data){
        try {
            i2CDevice.write(register, data);
        } catch (IOException e) {
            logger.error(String.format("I2C 0x%02X write register 0x%02X with value 0x%02X fail : " + e.getMessage(), deviceAddress, register , data[0]));
        }
    }

    public void write(byte[] data, int offset, int size){
        try {
            i2CDevice.write(data, offset, size);
        } catch (IOException e) {
            logger.error(String.format("I2C 0x%02X write register 0x%02X with value 0x%02X fail : " + e.getMessage(), deviceAddress, data[0], data[1]));
        }
    }

    public void write(byte value) {
        try {
            i2CDevice.write(value);
        } catch (IOException e) {
            logger.error(String.format("I2C 0x%02X write value 0x%02X fail : " + e.getMessage(),
                    deviceAddress, value));
        }
    }

}
