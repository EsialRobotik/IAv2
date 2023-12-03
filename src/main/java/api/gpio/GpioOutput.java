package api.gpio;

import api.Pi4JContext;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;

/**
 * Raspberry GPIO Output wrapper
 * Crée un GPIO en sortie
 */
public class GpioOutput extends Gpio {

    /**
     * Constructeur
     * @param gpioPin Numéro du GPIO (gpio, pas numéro de la pin : <a href="http://pi4j.com/pins/model-3b-rev1.html">Mapping des pins</a>
     * @param initialLow Etat initiale de la pin, true pour bas, false pour haut
     */
    public GpioOutput(int gpioPin, boolean initialLow) {
        Context pi4j = Pi4JContext.getInstance();
        DigitalOutputConfigBuilder config = DigitalOutput.newConfigBuilder(pi4j)
                .id("output" + gpioPin)
                .name("output" + gpioPin)
                .address(gpioPin)
                .shutdown(initialLow ? DigitalState.LOW : DigitalState.HIGH)
                .initial(initialLow ? DigitalState.LOW : DigitalState.HIGH)
                .provider("pigpio-digital-output");
        gpioPinDigital = pi4j.create(config);
    }

    /**
     * Met la pin à haut
     */
    public void setHigh() {
        ((DigitalOutput)gpioPinDigital).high();
    }

    /**
     * Met la pin à bas
     */
    public void setLow() {
        ((DigitalOutput)gpioPinDigital).low();
    }

}
