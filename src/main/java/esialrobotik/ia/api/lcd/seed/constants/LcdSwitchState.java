package esialrobotik.ia.api.lcd.seed.constants;

public enum LcdSwitchState {
    OFF (0x00),
    ON (0x01);

    public int value;

    LcdSwitchState(int value) {
        this.value = value;
    }
}
