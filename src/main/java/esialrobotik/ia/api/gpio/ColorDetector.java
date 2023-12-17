package esialrobotik.ia.api.gpio;

/**
 * Created by icule on 20/05/17.
 */
public class ColorDetector {

    private GpioInput colorDetector;

    public ColorDetector(int gpioPin) {
        this.colorDetector = new GpioInput(gpioPin, false);
    }

    public boolean isColor0() {
        return this.colorDetector.isLow();
    }

}
