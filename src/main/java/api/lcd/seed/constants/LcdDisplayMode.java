package api.lcd.seed.constants;

public enum LcdDisplayMode {
    AllREV (0x00),
    AllNOR (0x80);

    public int value;

    LcdDisplayMode(int value) {
        this.value = value;
    }
}
