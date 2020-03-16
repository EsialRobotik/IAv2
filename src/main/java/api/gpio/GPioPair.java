package api.gpio;

public class GPioPair {

    public int gpio_in;
    public int gpio_out;

    public GPioPair (int gpio_in, int gpio_out) {
        this.gpio_in = gpio_in;
        this.gpio_out = gpio_out;
    }
}
