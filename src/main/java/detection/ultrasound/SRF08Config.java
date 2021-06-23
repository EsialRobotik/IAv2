package detection.ultrasound;

public class SRF08Config {

    public int range;
    public int maxAnalogGain;
    public int i2cAddress;
    public String description;
    public int x, y, angle, threshold;

    public SRF08Config (int i2cAddress, int maxAnalogGain, int range, String description, int x, int y, int angle, int threshold) {
        this.i2cAddress = i2cAddress;
        this.maxAnalogGain = maxAnalogGain;
        this.range = range;
        this.description = description;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.threshold = threshold;
    }

}
