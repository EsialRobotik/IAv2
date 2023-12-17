package esialrobotik.ia.api.gpio;

import esialrobotik.ia.api.Pi4JContext;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder;
import com.pi4j.io.gpio.digital.PullResistance;

/**
 * Raspberry GPIO Input wrapper
 * Crée un GPIO en entrée
 */
public class GpioInput extends Gpio {

    /**
     * Constructeur
     * @param gpioPin Numéro du GPIO (gpio, pas numéro de la pin : <a href="http://pi4j.com/pins/model-3b-rev1.html">Mapping des pins</a>
     * @param pullUp true pour un GPIO en pull up, false pour du pull in
     */
    public GpioInput(int gpioPin, boolean pullUp) {
        Context pi4j = Pi4JContext.getInstance();
        DigitalInputConfigBuilder config = DigitalInput.newConfigBuilder(pi4j)
                .id("input" + gpioPin)
                .name("input" + gpioPin)
                .address(gpioPin)
                .pull(pullUp ? PullResistance.PULL_UP : PullResistance.PULL_DOWN)
                .debounce(0L);
        gpioPinDigital= pi4j.create(config);
    }

    public static void main(String args[]) throws InterruptedException {
        GpioInput input = new GpioInput(4, false); // Tirette
        GpioInput input2 = new GpioInput(5, false); // Capteur couleur
        while (true) {
            System.out.println("Tirette : " + (input.isHigh() ? "HIGH" : "LOW")
                + "   Couleur : " + (input2.isHigh() ? "HIGH" : "LOW"));
            Thread.sleep(1000);
        }
    }

}
