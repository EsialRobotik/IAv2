package api.lcd.seed.constants;

public enum LcdSettingMode {
    LOAD_TO_RAM (0x00),
    LOAD_TO_EEPROM (0x80);

    public int value;

    LcdSettingMode(int value) {
        this.value = value;
    }
}
