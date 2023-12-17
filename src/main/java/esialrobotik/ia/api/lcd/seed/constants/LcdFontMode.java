package esialrobotik.ia.api.lcd.seed.constants;

public enum LcdFontMode {
    FM_ANL_AAA (0x00),	//FM_AutoNewLine_AutoAddrAdd
    FM_ANL_MAA (0x10),	//FM_AanualNewLine_ManualAddrAdd
    FM_MNL_MAA (0x30),	//FM_ManualNewLine_ManualAddrAdd
    FM_MNL_AAA (0x20); 	//FM_ManualNewLine_AutoAddrAdd

    public int value;

    LcdFontMode(int value) {
        this.value = value;
    }
}
