package esialrobotik.ia.api.lcd.seed.constants;

public enum LcdWorkingMode {
    WM_CharMode (0x00),	   //Common mode, put char or dot
    WM_BitmapMode (0x01),
    WM_RamMode (0x02);	 //8Bit deal mode, deal with 8-bit RAM directly

    public int value;

    LcdWorkingMode(int value) {
        this.value = value;
    }
}
