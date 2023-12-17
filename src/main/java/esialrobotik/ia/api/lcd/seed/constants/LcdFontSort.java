package esialrobotik.ia.api.lcd.seed.constants;

public enum LcdFontSort {
    Font_6x8 (0x00),
    Font_6x12 (0x01),
    Font_8x16_1 (0x02),
    Font_8x16_2 (0x03),
    Font_10x20 (0x04),
    Font_12x24 (0x05),
    Font_16x32 (0x06);

    public int value;

    LcdFontSort(int value) {
        this.value = value;
    }
}
