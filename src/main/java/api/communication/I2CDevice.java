package api.communication;

import api.Pi4JContext;
import api.log.LoggerFactory;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Communication I2C
 * Communication I2C pour une communication pi via Pi4J
 * @see <a href='https://pi4j.com/documentation/io-examples/i2c/'>Pi4J I2C</a>
 */
public class I2CDevice {

    /**
     * Pi4J I2C device
     */
    protected I2C i2CDevice;

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
    public I2CDevice(int deviceAddress) {
        logger = LoggerFactory.getLogger(I2CDevice.class);
        this.deviceAddress = deviceAddress;
        Context pi4j = Pi4JContext.getInstance();
        I2CProvider i2CProvider = pi4j.provider("pigpio-i2c");
        I2CConfig i2cConfig = I2C
            .newConfigBuilder(pi4j)
            .id("" + deviceAddress)
            .bus(1)
            .device(deviceAddress)
            .build();

        try {
            logger.info(String.format("I2C 0x%02X init", deviceAddress));
            i2CDevice = i2CProvider.create(i2cConfig);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X init fail : " + e.getMessage(), deviceAddress));
        }
    }

    /**
     * Constructeur
     * @param deviceAddress Adresse du device (ex : 0x39)
     */
    public I2CDevice(byte deviceAddress) {
        this((int) deviceAddress);
    }

    /**
     * Lit la valeur d'un registre du device via l'I2C
     * @param register Registre à lire
     * @return Valeur du registre
     */
    public int read(byte register) {
        try {
            return i2CDevice.readRegister(register);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X read register 0x%02X fail : " + e.getMessage(), deviceAddress, register));
            return 0;
        }
    }

    public int read(byte register, byte[] buffer, int bufferOffset, int size) {
        try {

            return i2CDevice.readRegister(register, buffer, bufferOffset,size);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X read register 0x%02X fail : " + e.getMessage(), deviceAddress, register));
            return 0;
        }
    }

    public int read(byte[] buffer, int bufferOffset, int size) {
        try {

            return i2CDevice.read(buffer, bufferOffset, size);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X read register fail : " + e.getMessage(), deviceAddress));
            return 0;
        }
    }
    public int read(int address, byte[] buffer, int bufferOffset, int size) {
        try {

            return i2CDevice.readRegister(address, buffer, bufferOffset, size);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X read register fail : " + e.getMessage(), deviceAddress));
            return 0;
        }
    }

    public void write(byte register, byte value) {
        try {
            i2CDevice.write(register, value);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X write register 0x%02X with value 0x%02X fail : " + e.getMessage(), deviceAddress, register , value));
        }
    }

    public void write(byte register, byte[] data){
        try {
            i2CDevice.writeRegister(register, data);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X write register 0x%02X with value 0x%02X fail : " + e.getMessage(), deviceAddress, register , data[0]));
        }
    }

    public void write(byte[] data, int offset, int size){
        try {
            i2CDevice.write(data, offset, size);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X write register 0x%02X with value 0x%02X fail : " + e.getMessage(), deviceAddress, data[0], data[1]));
        }
    }

    public void write(byte value) {
        try {
            i2CDevice.write(value);
        } catch (Exception e) {
            logger.error(String.format("I2C 0x%02X write value 0x%02X fail : " + e.getMessage(), deviceAddress, value));
        }
    }

}
