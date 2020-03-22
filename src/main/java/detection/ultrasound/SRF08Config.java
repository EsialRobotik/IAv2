package detection.ultrasound;

public class SRF08Config {

    public int range;
    public int maxAnalogGain;
    public int i2cAddress;
    public String description;

    public SRF08Config (int i2cAddress, int maxAnalogGain, int range, String description) {
        this.i2cAddress = i2cAddress;
        this.maxAnalogGain = maxAnalogGain;
        this.range = range;
        this.description = description;
    }

}
