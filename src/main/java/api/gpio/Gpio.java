package api.gpio;

import com.pi4j.io.gpio.digital.Digital;

/**
 * Gpio abstract class
 * Représentation des GPIOs pour une gpio pi via la libraire Pi4J
 * Attention, le mapping de PI4J ne correspond pas à ce que l'on trouve habituellement en cherche le pinout d'une gpio !
 * @see <a href="https://pi4j.com/documentation/build-io/">Pi4J Usage</a>
 * @see <a href="https://pi4j.com/getting-started/understanding-the-pins/">Pin numbering</a>
 */
public abstract class Gpio {

    /**
     * Pi4J GPIO
     */
    protected Digital gpioPinDigital;

    /**
     * Vérifie si l'état du GPIO est haut
     * @return true if GPIO is high
     */
    public boolean isHigh() {
        return gpioPinDigital.isHigh();
    }

    /**
     * Vérifie si l'état du GPIO est bas
     * @return true if GPIO is low
     */
    public boolean isLow() {
        return gpioPinDigital.isLow();
    }
}
