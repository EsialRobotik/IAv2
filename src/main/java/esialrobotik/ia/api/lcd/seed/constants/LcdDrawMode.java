package esialrobotik.ia.api.lcd.seed.constants;

public enum LcdDrawMode {
    WHITE_NO_FILL (0x00),
    WHITE_FILL (0x01),
    BLACK_NO_FILL (0x02),
    BLACK_FILL (0x03);

    public int value;

    LcdDrawMode(int value) {
        this.value = value;
    }
}
