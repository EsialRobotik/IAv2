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
        this.fontModeConf((byte) LcdFontSort.Font_6x8.value, (byte) LcdFontMode.FM_ANL_AAA.value, (byte) LcdCharMode.BLACK_BAC.value);
        this.contrastConf((byte) LcdSettingMode.LOAD_TO_RAM.value, 35);
        this.charGotoXY(0, 0);
    }

    @Override
    public void println(String str) {
        logger.info("Print : " + str);
        int Y_Present = i2cDevice.read(LcdRegAddress.CharYPosRegAddr.address);
        int fontIndex = i2cDevice.read(LcdRegAddress.FontModeRegAddr.address);
        fontIndex = fontIndex & 0x0f;
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
        this.fontModeConf((byte) LcdFontSort.Font_16x32.value, (byte) LcdFontMode.FM_ANL_AAA.value, (byte) LcdCharMode.BLACK_BAC.value);
        this.dispStringAt("" + score,0, 0);
    }

    private void charGotoXY(int x, int y)
    {
        byte[] buf = new byte[2];
        buf[0] = (byte) x;
        buf[1] = (byte) y;
        i2cDevice.write(LcdRegAddress.CharXPosRegAddr.address, buf);
    }

    private void readRAMGotoXY(int x, int y)
    {
        byte[] buf = new byte[2];
        buf[0] = (byte) x;
        buf[1] = (byte) y;
        i2cDevice.write(LcdRegAddress.ReadRAM_XPosRegAddr.address, buf);
    }

    private void writeRAMGotoXY(int x, int y)
    {
        byte[] buf = new byte[2];
        buf[0] = (byte) x;
        buf[1] = (byte) y;
        i2cDevice.write(LcdRegAddress.WriteRAM_XPosRegAddr.address, buf);
    }

    private void sendBitmapData(int[] buf, int length)
    {
        int i;
        int circleTimes, circleCounter, transBytesNum;

        circleTimes = length / I2C_LCD_TRANS_ONCE_BYTE_MAX + 1;

        for (circleCounter = 0; circleCounter < circleTimes; circleCounter ++) {
            if (circleCounter + 1 >= circleTimes) {
                transBytesNum = length % I2C_LCD_TRANS_ONCE_BYTE_MAX;
            } else {
                transBytesNum = I2C_LCD_TRANS_ONCE_BYTE_MAX;
            }

            byte[] data = new byte[transBytesNum];
            for (i = 0; i < transBytesNum; i ++) {
                data[i] = (byte) buf[i];
            }
            i2cDevice.write(LcdRegAddress.DisRAMAddr.address, data);
        }
    }

    private void fontModeConf(byte font, byte mode, byte cMode)
    {
        i2cDevice.write(LcdRegAddress.FontModeRegAddr.address,(byte) (cMode|mode|font));
    }

    private void dispCharAt(char buf, int x, int y)
    {
        charGotoXY(x,y);
        i2cDevice.write(LcdRegAddress.DisRAMAddr.address, (byte) buf);
    }

    private void dispStringAt(String buf, int x, int y)
    {
        charGotoXY(x,y);
        i2cDevice.write(LcdRegAddress.DisRAMAddr.address, buf.getBytes());
    }

    private int write(int value)
    {
        int Y_Present, Y_New, Fontsize_Y, fontIndex;
        switch (value) {
            case 0x0d: break;
            case 0x0a:
                Y_Present = i2cDevice.read(LcdRegAddress.CharYPosRegAddr.address);
                fontIndex = i2cDevice.read(LcdRegAddress.FontModeRegAddr.address) & 0x0f;

                if (Y_Present + 2 * fontYsizeTab[fontIndex] <= I2C_LCD_Y_SIZE_MAX) {
                    Y_New = fontYsizeTab[fontIndex] + Y_Present;
                    charGotoXY(0, Y_New);
                } else {
                    charGotoXY(0, 0);
                }
                break;
            case 0x09:
                i2cDevice.write(LcdRegAddress.DisRAMAddr.address, "  ".getBytes());
                break;
            default:
                i2cDevice.write(LcdRegAddress.DisRAMAddr.address, (byte) value);
        }
        return 1; // assume sucess
    }

    private void cursorConf(byte swi, int freq)
    {
        i2cDevice.write(LcdRegAddress.CursorConfigRegAddr.address,(byte) ((swi<<7) | freq));
    }

    private void cursorGotoXY(int x, int y, int width, int height)
    {
        byte[] buf = new byte[4];
        buf[0] = (byte) x;
        buf[1] = (byte) y;
        buf[2] = (byte) width;
        buf[3] = (byte) height;
        i2cDevice.write(LcdRegAddress.CursorXPosRegAddr.address, buf);
    }

    private void drawDotAt(int x, int y, byte color)
    {
        byte[] buf = new byte[2];
        if (x < 128 && y < 64) {
            buf[0] = (byte)x;
            buf[1] = (byte) ((color<<7)|y);
            i2cDevice.write(LcdRegAddress.DrawDotXPosRegAddr.address, buf);
        }
    }

    private void drawHLineAt(int startX, int endX, int y, byte color)
    {
        drawLineAt(startX, endX, y, y, color);
    }

    private void drawVLineAt(int startY, int endY, int x, byte color)
    {
        drawLineAt(x, x, startY, endY, color);
    }

    private void drawLineAt(int startX, int endX, int startY, int endY, byte color)
    {
        byte[] buf = new byte[4];
        if(endY < 64) {
            buf[0] = (byte) startX;
            buf[1] = (byte) endX;
            buf[2] = (byte) startY;
            buf[3] = (byte) ((color<<7)|endY);
            i2cDevice.write(LcdRegAddress.DrawLineStartXRegAddr.address, buf);
        }
    }

    private void drawRectangleAt(int x, int y, int width, int height, byte mode)
    {
        byte[] buf = new byte[5];
        buf[0] = (byte) x;
        buf[1] = (byte) y;
        buf[2] = (byte) width;
        buf[3] = (byte) height;
        buf[4] = (byte) mode;
        i2cDevice.write(LcdRegAddress.DrawRectangleXPosRegAddr.address, buf);
    }

    private void drawCircleAt(int x, int y, int r, byte mode) {
        byte[] buf = new byte[4];
        if (x < 128 && y < 64 && r < 64)
        {
            buf[0] = (byte) x;
            buf[1] = (byte) y;
            buf[2] = (byte) r;
            buf[3] = (byte) mode;
            i2cDevice.write(LcdRegAddress.DrawCircleXPosRegAddr.address, buf);
        }
    }

    /*private void drawScreenAreaAt(GUI_Bitmap_t *bitmap, int x, int y)
    {
        TODO
        int regBuf[4];
        int16_t byteMax;
        int i,counter;
        const int *buf = bitmap->pData;
        if(y<64 && x<128)
        {
            regBuf[0] = x;
            regBuf[1] = y;
            regBuf[2] = bitmap->XSize;
            regBuf[3] = bitmap->YSize;
            WriteSeriesToReg(DrawBitmapXPosRegAddr, regBuf, 4);
            byteMax = regBuf[3]*bitmap->BytesPerLine;
            counter = byteMax/31;
            if(counter)
                for(i=0; i<counter; i++,buf+=31)
                    SendBitmapData(buf, 31);
            counter = byteMax%31;
            if(counter)
                SendBitmapData(buf, counter);
        }
    }*/

    private void drawFullScreen(byte[] buf)
    {
        int i;
        writeRAMGotoXY(0,0);
        for ( i = 0; i < 1024; i++) {
            // TODO envoyer tout d'un coup ?
            i2cDevice.write(LcdRegAddress.DisRAMAddr.address, buf[i]);
        }
    }

    private int readByteDispRAM(int x, int y)
    {
        readRAMGotoXY(x,y);
        return i2cDevice.read(LcdRegAddress.DisRAMAddr.address);
    }

    private int readSeriesDispRAM(byte[] buf, int length, int x, int y)
    {
        readRAMGotoXY(x, y);
        return i2cDevice.read(LcdRegAddress.DisRAMAddr.address, buf, 0, length);
    }

    private void writeByteDispRAM(int buf, int x, int y)
    {
        writeRAMGotoXY(x,y);
        i2cDevice.write(LcdRegAddress.DisRAMAddr.address, (byte) buf);
    }

    private void writeSeriesDispRAM(byte[] buf, int length, int x, int y)
    {
        writeRAMGotoXY(x,y);
        i2cDevice.write(LcdRegAddress.DisRAMAddr.address, buf);
    }

    private void displayConf(byte mode)
    {
        i2cDevice.write(LcdRegAddress.DisplayConfigRegAddr.address, mode);
    }

    private void workingModeConf(byte logoSwi, byte backLightSwi, byte mode)
    {
        i2cDevice.write(LcdRegAddress.WorkingModeRegAddr.address,(byte)(0x50|(int)(logoSwi<<3)|(int)(backLightSwi<<2)|mode));
    }

    private void backlightConf(byte mode, int buf)
    {
        if (buf>0x7f) {
            buf = 0x7f;
        }
        i2cDevice.write(LcdRegAddress.BackLightConfigRegAddr.address,(byte) (mode|buf));
    }

    private void contrastConf(byte mode, int buf)
    {
        if (buf>0x3f) {
            buf = 0x3f;
        }
        i2cDevice.write(LcdRegAddress.ContrastConfigRegAddr.address, (byte) (mode|buf));
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
            i2cDevice.write(LcdRegAddress.DisplayConfigRegAddr.address, (byte) ((buf | 0x40) & 0xdf));
        } else {
            i2cDevice.write(LcdRegAddress.DisplayConfigRegAddr.address, (byte) (buf | 0x60));
        }
    }
}
