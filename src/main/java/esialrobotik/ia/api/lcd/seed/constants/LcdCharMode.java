package esialrobotik.ia.api.lcd.seed.constants;

public enum LcdCharMode {
    WHITE_BAC (0x00),
    WHITE_NO_BAC (0x40),
    BLACK_BAC (0x80),
    BLACK_NO_BAC (0xc0);

    public int value;

    LcdCharMode(int value) {
        this.value = value;
    }
}
