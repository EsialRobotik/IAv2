package esialrobotik.ia.api.lcd.seed;

import esialrobotik.ia.api.communication.I2CDevice;
import esialrobotik.ia.api.lcd.LCD;
import esialrobotik.ia.api.lcd.seed.constants.*;
import esialrobotik.ia.api.log.LoggerFactory;
import org.slf4j.Logger;

/**
 * LCD 128x64px de SeedStudio
 *
 * @see <a href="https://wiki.seeedstudio.com/I2C_LCD/">Documentation</a>
 * @see <a href="https://github.com/SparkingStudio/I2C_LCD">Librairie Arduino d'origine</a>
 */
public class LcdI2c implements LCD {

    private I2CDevice i2cDevice;
    private Logger logger;

    public static final int I2C_LCD_ADDRESS = 0x51; //Device address configuration, the default value is 0x51.
    public static final int I2C_LCD_X_SIZE_MAX = 128;
    public static final int I2C_LCD_Y_SIZE_MAX = 64;
    public static final int I2C_LCD_NUM_OF_FONT = 7;
    public static final int I2C_LCD_TRANS_ONCE_BYTE_MAX = 6;
    public static final int[] fontYsizeTab = {8, 12, 16, 16, 20, 24, 32};

    public LcdI2c() {
        this(I2C_LCD_ADDRESS);
    }

    public LcdI2c(int i2cAddress) {
        this.logger = LoggerFactory.getLogger(LcdI2c.class);
        logger.info(String.format("Initializing 128x64px LCD on I2C address 0x%02X", i2cAddress));
        this.i2cDevice = new I2CDevice(i2cAddress);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        this.clear();
        this.fontModeConf(LcdFontSort.Font_6x8.value, LcdFontMode.FM_ANL_AAA.value, LcdCharMode.BLACK_BAC.value);
        this.contrastConf(LcdSettingMode.LOAD_TO_RAM.value, 35);
        this.charGotoXY(0, 0);
    }

    @Override
    public void println(String str) {
        logger.info("Print : " + str);
        int Y_Present = i2cDevice.read(LcdRegAddress.CharYPosRegAddr.address);
        System.out.println("DEBUG 1 : " + Y_Present);
        int fontIndex = i2cDevice.read(LcdRegAddress.FontModeRegAddr.address);
        System.out.println("DEBUG 2 : " + fontIndex);
        fontIndex = fontIndex & 0x0f;
        System.out.println("DEBUG 3 : " + fontIndex);
        if (Y_Present + 2 * fontYsizeTab[fontIndex] <= I2C_LCD_Y_SIZE_MAX) {
            this.dispStringAt(str,0, fontYsizeTab[fontIndex] + Y_Present);
        } else {
            this.clear();
            this.dispStringAt(str,0, 0);
        }
    }

    @Override
    public void clear() {
        logger.info("Clear screen");
        this.cleanAll(LcdColorSort.WHITE.value);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        this.charGotoXY(0, 0);
    }

    @Override
    public void score(int score) {
        logger.info("Print score : " + score);
        this.clear();
        this.fontModeConf(LcdFontSort.Font_16x32.value, LcdFontMode.FM_ANL_AAA.value, LcdCharMode.BLACK_BAC.value);
        this.dispStringAt("" + score,0, 0);
    }

    private void charGotoXY(int x, int y)
    {
        byte[] buf = new byte[2];
        buf[0] = (byte) x;
        buf[1] = (byte) y;
        i2cDevice.write(LcdRegAddress.CharXPosRegAddr.address, buf);
    }

    private void fontModeConf(int font, int mode, int cMode)
    {
        i2cDevice.write(LcdRegAddress.FontModeRegAddr.address, cMode|mode|font);
    }

    private void dispStringAt(String buf, int x, int y)
    {
        charGotoXY(x,y);
        i2cDevice.write(LcdRegAddress.DisRAMAddr.address, buf.getBytes());
    }

    private void contrastConf(int mode, int buf)
    {
        if (buf>0x3f) {
            buf = 0x3f;
        }
        i2cDevice.write(LcdRegAddress.ContrastConfigRegAddr.address, mode|buf);
    }

    public void deviceAddrEdit(int newAddr)
    {
        byte[] buf = new byte[2];
        buf[0] = (byte) 0x80;
        buf[1] = (byte) newAddr;
        i2cDevice.write(LcdRegAddress.DeviceAddressRegAddr.address, buf);
    }

    private void cleanAll(int color)
    {
        int buf;
        buf = i2cDevice.read(LcdRegAddress.DisplayConfigRegAddr.address);
        if (color == LcdColorSort.WHITE.value) {
            i2cDevice.write(LcdRegAddress.DisplayConfigRegAddr.address, (buf | 0x40) & 0xdf);
        } else {
            i2cDevice.write(LcdRegAddress.DisplayConfigRegAddr.address, buf | 0x60);
        }
    }
}
