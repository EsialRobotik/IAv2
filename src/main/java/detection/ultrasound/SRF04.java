package detection.ultrasound;

import api.gpio.GPioPair;
import api.gpio.GpioInput;
import api.gpio.GpioOutput;
import asserv.Position;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by franc on 18/05/2017.
 * Télémètre ultrason SRF04 analogique
 * @see <a href="https://www.robot-electronics.co.uk/htm/srf04tech.htm">Documentation</a>
 */
public class SRF04 implements UltraSoundInterface {

    private static long TIMEOUT = 3; // If no change after 3ms, there is nothing below 1m

    private GpioInput gpioInput;
    private GpioOutput gpioOutput;
    private int x, y, angle, threshold;

    public SRF04(int gpioInput, int gpioOutput) {
        this.gpioInput = new GpioInput(gpioInput, false); // Echo
        this.gpioOutput = new GpioOutput(gpioOutput, true); // Trigger
        this.init();
    }

    public SRF04(GPioPair pair, int x, int y, int angle, int threshold) {
        this.gpioInput = new GpioInput(pair.gpio_in, false); // Echo
        this.gpioOutput = new GpioOutput(pair.gpio_out, true); // Trigger
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.threshold = threshold;
        this.init();
    }

    public void init() {
        this.gpioOutput.setLow();
    }

    @Override
    public Position getPosition() {
        return new Position(this.x, this.y, Math.toRadians(this.angle));
    }

    @Override
    public int getThreshold() {
        return this.threshold;
    }

    /**
     * WARNING !!! Attendre 12ms entre 2 mesures, même sur des capteurs différents pour ne pas capter des echos foireux
     * @return mesure du télémètre en mm
     */
    public long getMeasure() {
        /*
         * Pour faire la mesure, il faut:
         * - envoyer une impulsion d'au moins 10 us sur la GPIO out,
         * - attendre la réponse sur la GPIO in
         * Le capteur répond en mettant à 1 le GPIO pendant une certaine durée, c'est cette durée qui donne la mesure de distance.
         * C'est la mesure brute, en nanosecondes. D'après la doc, il faut diviser par 5800 pour avoir une valeur en mm.
         */
        this.gpioOutput.setLow();
        LockSupport.parkNanos(10000);
        this.gpioOutput.setHigh();
        final long[] time = new long[2];

        long checkoutTimeout = System.currentTimeMillis();
        while (gpioInput.isLow()){
            if (System.currentTimeMillis() - checkoutTimeout > TIMEOUT) {
                return 10000;
            }
        }
        time[0] = System.nanoTime();
        checkoutTimeout = System.currentTimeMillis();
        while (gpioInput.isHigh()) {
            if (System.currentTimeMillis() - checkoutTimeout > TIMEOUT) {
                return 20000;
            }
        }
        time[1] = System.nanoTime();
        return (time[1] - time[0]) / 5800;
    }

    public static void main(String args[]) throws InterruptedException {
        SRF04 srf04AvantDroit = new SRF04(24, 25); // Avant droit
        SRF04 srf04AvantMilieu = new SRF04(2, 0); // Avant milieu
        SRF04 srf04AvantGauche = new SRF04(22, 21); // Avant gauche
        SRF04 srf04Arriere = new SRF04(12, 13); // Arriere

        long measureAvantDroit, measureAvantMilieu, measureAvantGauche, measureArriere;
        while (true) {
            measureAvantDroit = srf04AvantDroit.getMeasure();
            Thread.sleep(20);
            measureAvantMilieu = srf04AvantMilieu.getMeasure();
            Thread.sleep(20);
            measureAvantGauche = srf04AvantGauche.getMeasure();
            Thread.sleep(20);
            measureArriere = srf04Arriere.getMeasure();
            System.out.println("measureAvantDroit=" + measureAvantDroit + "  measureAvantMilieu=" + measureAvantMilieu + "  measureAvantGauche=" + measureAvantGauche + "  measureArriere=" + measureArriere);
            Thread.sleep(200);
        }

//        GpioOutput out0 = new GpioOutput(0, true);
//        GpioOutput out2 = new GpioOutput(2, true);
//        GpioOutput out12 = new GpioOutput(12, true);
//        GpioOutput out13 = new GpioOutput(13, true);
//        GpioOutput out21 = new GpioOutput(21, true);
//        GpioOutput out22 = new GpioOutput(22, true);
//        GpioOutput out24 = new GpioOutput(24, true);
//        GpioOutput out25 = new GpioOutput(25, true);
//
//        out0.setLow();
//        out2.setLow();
//        out12.setLow();
//        out13.setLow();
//        out21.setLow();
//        out22.setLow();
//        out24.setLow();
//        out25.setLow();
//
//        System.out.println("init");
//        Thread.sleep(2000);
//        while (true) {
//            System.out.println("up");
//            out0.setHigh();
//            out2.setHigh();
//            Thread.sleep(2000);
//            System.out.println("down");
//            out0.setLow();
//            out2.setLow();
//            Thread.sleep(2000);
//        }
    }

}
