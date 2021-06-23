package detection.ultrasound;

import api.communication.I2C;
import api.log.LoggerFactory;
import asserv.Position;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * Created by goldensgui on 15/03/2020.
 * Télémètre ultrason SRF08 analogique
 * @see <a href="https://www.mil.ufl.edu/projects/koolio/Koolio/Datasheets/SRF08%20Ultra%20sonic%20range%20finder.htm">Documentation</a>
 */
public class SRF08 implements UltraSoundInterface {

    private static long TIMEOUT = 65; // If no change after 65ms, it's a timeout

    private I2C i2cDevice;
    private Logger logger;
    private SRF08Config currentConfig;

    // Default address
    private static final int DEFAULT_I2C_ADDRESS = 0x70;

    //Commands
    private static final int COMMAND_RANGING_INCHES         = 80 ;  //0x50 	Ranging Mode - Result in inches
    private static final int COMMAND_RANGING_CM             = 81 ;  //0x51 	Ranging Mode - Result in centimeters
    private static final int COMMAND_RANGING_MICRO_SEC      = 82 ;  //0x52 	Ranging Mode - Result in micro-seconds
    private static final int COMMAND_ANN_INCHES             = 83 ;  //0x53 	ANN (Artificial Neural Network) Mode - Result in inches
    private static final int COMMAND_ANN_MM                 = 84 ;  //0x54 	ANN Mode - Result in centimeters
    private static final int COMMAND_ANN_MICRO_SEC          = 85 ;  //0x55 	ANN Mode - Result in micro-seconds
    private static final int COMMAND_CHANGE_I2C_ADDR_SEQ1   = 160;  //0xA0 	1st in sequence to change I2C address
    private static final int COMMAND_CHANGE_I2C_ADDR_SEQ2   = 165;  //0xA5 	3rd in sequence to change I2C address
    private static final int COMMAND_CHANGE_I2C_ADDR_SEQ3   = 170;  //0xAA 	2nd in sequence to change I2C address

    //Read Registers
    private static final int REGISTER_SOFTWARE_REV   = 0 ; //Software Revision
    private static final int REGISTER_LIGHT_SENSOR   = 1 ; //Light Sensor
    private static final int REGISTER_HI_BYTE_01     = 2  ; //01st Echo High Byte
    private static final int REGISTER_LO_BYTE_01     = 3  ; //01st Echo Low Byte
    private static final int REGISTER_HI_BYTE_02     = 4  ; //02nd Echo High Byte
    private static final int REGISTER_LO_BYTE_02     = 5  ; //02nd Echo Low Byte
    private static final int REGISTER_HI_BYTE_03     = 6  ; //03rd Echo High Byte
    private static final int REGISTER_LO_BYTE_03     = 7  ; //03rd Echo Low Byte
    private static final int REGISTER_HI_BYTE_04     = 8  ; //04th Echo High Byte
    private static final int REGISTER_LO_BYTE_04     = 9  ; //04th Echo Low Byte
    private static final int REGISTER_HI_BYTE_05     = 10 ; //05th Echo High Byte
    private static final int REGISTER_LO_BYTE_05     = 11 ; //05th Echo Low Byte
    private static final int REGISTER_HI_BYTE_06     = 12 ; //06th Echo High Byte
    private static final int REGISTER_LO_BYTE_06     = 13 ; //06th Echo Low Byte
    private static final int REGISTER_HI_BYTE_07     = 14 ; //07th Echo High Byte
    private static final int REGISTER_LO_BYTE_07     = 15 ; //07th Echo Low Byte
    private static final int REGISTER_HI_BYTE_08     = 16 ; //08th Echo High Byte
    private static final int REGISTER_LO_BYTE_08     = 17 ; //08th Echo Low Byte
    private static final int REGISTER_HI_BYTE_09     = 18 ; //09th Echo High Byte
    private static final int REGISTER_LO_BYTE_09     = 19 ; //09th Echo Low Byte
    private static final int REGISTER_HI_BYTE_10     = 20 ; //10th Echo High Byte
    private static final int REGISTER_LO_BYTE_10     = 21 ; //10th Echo Low Byte
    private static final int REGISTER_HI_BYTE_11     = 22 ; //11th Echo High Byte
    private static final int REGISTER_LO_BYTE_11     = 23 ; //11th Echo Low Byte
    private static final int REGISTER_HI_BYTE_12     = 24 ; //12th Echo High Byte
    private static final int REGISTER_LO_BYTE_12     = 25 ; //12th Echo Low Byte
    private static final int REGISTER_HI_BYTE_13     = 26 ; //13th Echo High Byte
    private static final int REGISTER_LO_BYTE_13     = 27 ; //13th Echo Low Byte
    private static final int REGISTER_HI_BYTE_14     = 28 ; //14th Echo High Byte
    private static final int REGISTER_LO_BYTE_14     = 29 ; //14th Echo Low Byte
    private static final int REGISTER_HI_BYTE_15     = 30 ; //15th Echo High Byte
    private static final int REGISTER_LO_BYTE_15     = 31 ; //15th Echo Low Byte
    private static final int REGISTER_HI_BYTE_16     = 32 ; //16th Echo High Byte
    private static final int REGISTER_LO_BYTE_16     = 33 ; //16th Echo Low Byte
    private static final int REGISTER_HI_BYTE_17     = 34 ; //17th Echo High Byte
    private static final int REGISTER_LO_BYTE_17     = 35 ; //17th Echo Low Byte

    //Write Registers
    private static final int REGISTER_COMMAND   = 0 ; //Command Register
    private static final int REGISTER_MAX_GAIN  = 1 ; //Max Gain Register (default 31)
    private static final int REGISTER_RANGE     = 2 ; //Range Register (default 255)

    //Range Register values
    private static final int RANGE_VALUE_43MM = 0 ;
    private static final int RANGE_VALUE_86MM = 1 ;
    private static final int RANGE_VALUE_129MM = 2 ;
    private static final int RANGE_VALUE_172MM = 3 ;
    private static final int RANGE_VALUE_215MM = 4 ;
    private static final int RANGE_VALUE_258MM = 5 ;
    private static final int RANGE_VALUE_301MM = 6 ;
    private static final int RANGE_VALUE_344MM = 7 ;
    private static final int RANGE_VALUE_387MM = 8 ;
    private static final int RANGE_VALUE_430MM = 9 ;
    private static final int RANGE_VALUE_473MM = 10 ;
    private static final int RANGE_VALUE_516MM = 11 ;
    private static final int RANGE_VALUE_559MM = 12 ;
    private static final int RANGE_VALUE_602MM = 13 ;
    private static final int RANGE_VALUE_645MM = 14 ;
    private static final int RANGE_VALUE_688MM = 15 ;
    private static final int RANGE_VALUE_731MM = 16 ;
    private static final int RANGE_VALUE_774MM = 17 ;
    private static final int RANGE_VALUE_817MM = 18 ;
    private static final int RANGE_VALUE_860MM = 19 ;
    private static final int RANGE_VALUE_903MM = 20 ;
    private static final int RANGE_VALUE_946MM = 21 ;
    private static final int RANGE_VALUE_989MM = 22 ;
    private static final int RANGE_VALUE_1032MM = 23 ;
    private static final int RANGE_VALUE_1075MM = 24 ;
    private static final int RANGE_VALUE_1118MM = 25 ;
    //...
    private static final int RANGE_VALUE_11008MM = 255 ;

    //Gain Register values
    private static final int MAX_ANALOG_GAIN_94     = 0 ; //0x00 	Set Maximum Analogue Gain to 94
    private static final int MAX_ANALOG_GAIN_97     = 1 ; //0x01 	Set Maximum Analogue Gain to 97
    private static final int MAX_ANALOG_GAIN_100    = 2 ; //0x02 	Set Maximum Analogue Gain to 100
    private static final int MAX_ANALOG_GAIN_103    = 3 ; //0x03 	Set Maximum Analogue Gain to 103
    private static final int MAX_ANALOG_GAIN_107    = 4 ; //0x04 	Set Maximum Analogue Gain to 107
    private static final int MAX_ANALOG_GAIN_110    = 5 ; //0x05 	Set Maximum Analogue Gain to 110
    private static final int MAX_ANALOG_GAIN_114    = 6 ; //0x06 	Set Maximum Analogue Gain to 114
    private static final int MAX_ANALOG_GAIN_118    = 7 ; //0x07 	Set Maximum Analogue Gain to 118
    private static final int MAX_ANALOG_GAIN_123    = 8 ; //0x08 	Set Maximum Analogue Gain to 123
    private static final int MAX_ANALOG_GAIN_128    = 9 ; //0x09 	Set Maximum Analogue Gain to 128
    private static final int MAX_ANALOG_GAIN_133    = 10; //0x0A 	Set Maximum Analogue Gain to 133
    private static final int MAX_ANALOG_GAIN_139    = 11; //0x0B 	Set Maximum Analogue Gain to 139
    private static final int MAX_ANALOG_GAIN_145    = 12; //0x0C 	Set Maximum Analogue Gain to 145
    private static final int MAX_ANALOG_GAIN_152    = 13; //0x0D 	Set Maximum Analogue Gain to 152
    private static final int MAX_ANALOG_GAIN_159    = 14; //0x0E 	Set Maximum Analogue Gain to 159
    private static final int MAX_ANALOG_GAIN_168    = 15; //0x0F 	Set Maximum Analogue Gain to 168
    private static final int MAX_ANALOG_GAIN_177    = 16; //0x10 	Set Maximum Analogue Gain to 177
    private static final int MAX_ANALOG_GAIN_187    = 17; //0x11 	Set Maximum Analogue Gain to 187
    private static final int MAX_ANALOG_GAIN_199    = 18; //0x12 	Set Maximum Analogue Gain to 199
    private static final int MAX_ANALOG_GAIN_212    = 19; //0x13 	Set Maximum Analogue Gain to 212
    private static final int MAX_ANALOG_GAIN_227    = 20; //0x14 	Set Maximum Analogue Gain to 227
    private static final int MAX_ANALOG_GAIN_245    = 21; //0x15 	Set Maximum Analogue Gain to 245
    private static final int MAX_ANALOG_GAIN_265    = 22; //0x16 	Set Maximum Analogue Gain to 265
    private static final int MAX_ANALOG_GAIN_288    = 23; //0x17 	Set Maximum Analogue Gain to 288
    private static final int MAX_ANALOG_GAIN_317    = 24; //0x18 	Set Maximum Analogue Gain to 317
    private static final int MAX_ANALOG_GAIN_352    = 25; //0x19 	Set Maximum Analogue Gain to 352
    private static final int MAX_ANALOG_GAIN_395    = 26; //0x1A 	Set Maximum Analogue Gain to 395
    private static final int MAX_ANALOG_GAIN_450    = 27; //0x1B 	Set Maximum Analogue Gain to 450
    private static final int MAX_ANALOG_GAIN_524    = 28; //0x1C 	Set Maximum Analogue Gain to 524
    private static final int MAX_ANALOG_GAIN_626    = 29; //0x1D 	Set Maximum Analogue Gain to 626
    private static final int MAX_ANALOG_GAIN_777    = 30; //0x1E 	Set Maximum Analogue Gain to 777
    private static final int MAX_ANALOG_GAIN_1025   = 31; //0x1F 	Set Maximum Analogue Gain to 1025

    public SRF08() {
        //use default value for default object
        this(new SRF08Config(DEFAULT_I2C_ADDRESS, MAX_ANALOG_GAIN_1025, RANGE_VALUE_11008MM, "dummy", 0, 0, 0, 0));
    }

    public SRF08(SRF08Config config) {

        this.logger = LoggerFactory.getLogger(SRF08.class);

        //init with default values
        currentConfig = new SRF08Config(DEFAULT_I2C_ADDRESS, MAX_ANALOG_GAIN_1025, RANGE_VALUE_11008MM, "default", config.x, config.y, config.angle, config.threshold);

        currentConfig.description = config.description;


        if(config.i2cAddress > 0x77 || config.i2cAddress < 0x70) {
            logger.error("Given I2C address is not within given range [0x70 : 0x77] : " + config.range + ". Default value used instead : " + DEFAULT_I2C_ADDRESS);
        }else {
            currentConfig.i2cAddress = config.i2cAddress;
        }

        if(config.maxAnalogGain > 31) {
            logger.error("Given analog Gain is too high : " + config.maxAnalogGain + ". Dafault value used instead : MAX_ANALOG_GAIN_1025 = " + MAX_ANALOG_GAIN_1025);
        }else{
            currentConfig.maxAnalogGain = config.maxAnalogGain;
        }

        if(config.range > 255) {
            logger.error("Given range is too high : " + config.range + ". Default value used instead : RANGE_VALUE_11008MM = " + RANGE_VALUE_11008MM);
            currentConfig.range = RANGE_VALUE_11008MM;
        }else{
            currentConfig.range = config.range;
        }
        logger.info("Initializing SRF08 on I2C address " + String.format("0x%X", currentConfig.i2cAddress) +
                    " with maxAnalogGain = "    + currentConfig.maxAnalogGain   +
                    " and range = "             + currentConfig.range           +
                    " used as : "               + currentConfig.description);

        this.i2cDevice = new I2C(currentConfig.i2cAddress);
        this.init();
    }

    @Override
    public Position getPosition() {
        return new Position(currentConfig.x, currentConfig.y, Math.toRadians(currentConfig.angle));
    }

    @Override
    public int getThreshold() {
        return currentConfig.threshold;
    }

    /**
     * Initialize the sensor to selected value.
     * This function will set the Max Analog gain and the measure ranging of the sensor.
     */
    public void init() {

        logger.info("SRF08 init : try init");
        int res = this.i2cDevice.read((byte) REGISTER_SOFTWARE_REV); //read the
        if (res == 0){
            logger.error("SRF08 init : Error while trying to get Software Rev.");
        }
        else {
            logger.info("SRF08 init : Sensor has the Software Rev. : " + res);
            setSensorMaxGain(currentConfig.maxAnalogGain);
            setSensorRange(currentConfig.range);
            logger.info("SRF08 init : init done");
        }

    }

    /**
     * Set the sensor range value
     * The maximum range of the SRF08 is set by an internal timer. By default, this is 65mS or the equivalent of 11 metres
     *     of range. This is much further than the 6 metres the SRF08  is actually capable of. It is possible to reduce the
     *     time the SRF08 listens for an echo, and hence the range, by writing to the range register at location 2. The range
     *     can be set in steps of about 43 mm (0.043 m or 1.68 inches) up to 11 metres.
     *     The range is ((Range Register x 43 mm) + 43 mm) so setting the Range Register to
     *         0 (0x00) gives a maximum range of 43 mm.
     *         1 (0x01) gives a maximum range of 86 mm. More usefully,
     *         24 (0x18) gives a range of 1 metre
     *         140 (0x8C) is 6 metres
     *         255 (0xFF) gives the original 11 metres (255 x 43 + 43 is 11008 mm).
     *     There are two reasons you may wish to reduce the range.
     *         1. To get at the range information quicker
     *         2. To be able to fire the SRF08 at a faster rate.
     *     If you only wish to get at the range information a bit sooner and will continue to fire the SRF08 at 65ms of slower,
     *     then all will be well. However, if you wish to fire the SRF08 at a faster rate than 65mS, you will definitely need to
     *     reduce the gain - see next section.
     *     The range is set to maximum every time the SRF08 is powered-up. If you need a different range, change it once as
     *     part of your system initialization code.
     * @param range The Range ID [0x0, 0xFF] to be set.
     */
    public void setSensorRange(int range){

        this.i2cDevice.write((byte) REGISTER_RANGE, (byte) range);
        this.logger.info("SRF08 : Sensor Range was set to " + range + " (" + (range*43+43) + "mm)");

    }

    /**
     * Set the Maximum Analog Gain Value
     * The analog gain register sets the Maximum gain of the analogue stages.
     * To set the maximum gain, just write one of these values to the gain register at location 1.
     * During a ranging, the analogue gain starts off at its minimum value of 94.
     * This is increased at approx. 70uS intervals up to the maximum gain setting, set by register 1.
     * Maximum possible gain is reached after about 390 mm of range.
     * The purpose of providing a limit to the maximum gain is to allow you to fire the sonar more rapidly than 65mS.
     * Since the ranging can be very short, a new ranging can be initiated as soon as the previous range data has been
     * read. A potential hazard with this is that the second ranging may pick up a distant echo returning from the
     * previous "ping", give a false result of a close by object when there is none. To reduce this possibility, the
     * maximum gain can be reduced to limit the modules' sensitivity to the weaker distant echo, whilst still able to
     * detect close by objects. The maximum gain setting is stored only in the CPU's RAM and is initialized to maximum
     * on power-up, so if you only want do a ranging every 65mS, or longer, you can ignore the Range and Gain Registers.
     *
     * Note - Effective in Ranging Mode only, in ANN mode, gain is controlled automatically.
     *
     * Note that the relationship between the Gain Register setting, and the actual gain is not a linear one.
     * Also there is no magic formula to say "use this gain setting with that range setting". It depends on the size,
     * shape and material of the object and what else is around in the room. Try playing with different settings until
     * you get the result you want. If you appear to get false readings, it may be echo's from previous "pings", try
     * going back to firing the SRF08 every 65mS or longer (slower).
     * If you are in any doubt about the Range and Gain Registers, remember they are automatically set by the SRF08 to
     * their default values when it is powered-up. You can ignore and forget about them, and the SRF08 will work fine,
     * detecting objects up to 6 metres away every 65mS or slower.
     * @param gain The maximum analog gain ID [0x00, 0x1F] to be set.
     */
    public void setSensorMaxGain(int gain){

        this.i2cDevice.write((byte) REGISTER_MAX_GAIN, (byte) gain);
        this.logger.error("Sensor max analog gain was set to " + gain);

    }

    /**
     * WARNING !!! Attendre 12ms entre 2 mesures, même sur des capteurs différents pour ne pas capter des echos foireux
     * @return mesure du télémètre en mm
     */
    public long getMeasure() {

        logger.info("SRF08 : Asking for sensor measure by sending " + String.format("0x%X",COMMAND_RANGING_CM) +
                    " to register " + String.format("0x%X",REGISTER_COMMAND));

        byte buffer[] = {REGISTER_COMMAND,COMMAND_RANGING_CM,0,0};

        this.i2cDevice.write(buffer, 0, 2);

        long checkoutTimeout = System.currentTimeMillis();

        /*
        You do not have to use a timer on your own controller to wait for ranging to finish. You can take advantage of
        the fact that the SRF08 will not respond to any I2C activity whilst ranging. Therefore, if you try to read from
        the SRF08 (we use the software revision number a location 0) then you will get 255 (0xFF) whilst ranging. This
        is because the I2C data line (SDA) is pulled high if nothing is driving it. As soon as the ranging is complete
        the SRF08 will again respond to the I2C bus, so just keep reading the register until its not 255 (0xFF) anymore.
        You can then read the sonar data. Your controller can take advantage of this to perform other tasks while the
        SRF08 is ranging.
         */
        int distance = 10000;
        buffer[0] = 0;
        try {
            Thread.sleep(75, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.i2cDevice.write(buffer, 0, 1);
        logger.info("SRF08 : Getting measure.");

        this.i2cDevice.read(buffer,0,4);

        int hi_byte = buffer[2];
        int lo_byte = buffer[3];
        distance = (int)(((byte)(hi_byte << 8)) + lo_byte);
        logger.info("SRF08 : Measurement done : " + distance + " cm (low byte:" +
                    lo_byte + String.format(" (0x%X)", lo_byte) + " high byte :" +
                    hi_byte + String.format(" (0x%X)", hi_byte));


        return distance;
    }

    public static void main(String args[]) throws InterruptedException {
        LoggerFactory.init(Level.INFO);
        System.out.println("Hello SRF08");
        SRF08 srf08FrontRight   = new SRF08(new SRF08Config(0x71, MAX_ANALOG_GAIN_1025, RANGE_VALUE_602MM,"FRight", 0, 0, 0, 0)); // Avant droit
        SRF08 srf08FrontMiddle  = new SRF08(new SRF08Config(0x72, MAX_ANALOG_GAIN_1025, RANGE_VALUE_602MM,"FMid", 0, 0, 0, 0)); // Avant milieu
        SRF08 srf08FrontLeft    = new SRF08(new SRF08Config(0x73, MAX_ANALOG_GAIN_1025, RANGE_VALUE_602MM,"FLeft", 0, 0, 0, 0)); // Avant gauche
        SRF08 srf08Rear         = new SRF08(new SRF08Config(0x74, MAX_ANALOG_GAIN_1025, RANGE_VALUE_602MM,"Back", 0, 0, 0, 0)); // Arriere

        long measureFrontRight, measureFrontMiddle, measureFrontLeft, measureRear;
        while (true) {
            measureFrontRight = srf08FrontRight.getMeasure();
            Thread.sleep(12);
            measureFrontMiddle = srf08FrontMiddle.getMeasure();
            Thread.sleep(12);
            measureFrontLeft = srf08FrontLeft.getMeasure();
            Thread.sleep(12);
            measureRear = srf08Rear.getMeasure();
            System.out.println("measureFrontRight=" + measureFrontRight + "  measureFrontMiddle=" + measureFrontMiddle + "  measureFrontLeft=" + measureFrontLeft + "  measureRear=" + measureRear);
            Thread.sleep(12);
        }

    }

}
