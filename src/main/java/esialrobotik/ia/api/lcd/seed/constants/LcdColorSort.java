package esialrobotik.ia.api.lcd.seed.constants;

public enum LcdColorSort {
    WHITE (0x00),
    BLACK (0xff);

    public int value;

    LcdColorSort(int value) {
        this.value = value;
    }
}
