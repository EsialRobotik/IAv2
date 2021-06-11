package api.lcd.seed.constants;

public enum LcdRegAddress {
    FontModeRegAddr (1),
    CharXPosRegAddr (2),
    CharYPosRegAddr (3),

    CursorConfigRegAddr (16),
    CursorXPosRegAddr (17),
    CursorYPosRegAddr (18),
    CursorWidthRegAddr (19),
    CursorHeightRegAddr (20), 	//8

    DisRAMAddr (32),				//9
    ReadRAM_XPosRegAddr (33),
    ReadRAM_YPosRegAddr (34),
    WriteRAM_XPosRegAddr (35),
    WriteRAM_YPosRegAddr (36),


    DrawDotXPosRegAddr (64), 		//14
    DrawDotYPosRegAddr (65),

    DrawLineStartXRegAddr (66),
    DrawLineEndXRegAddr (67),
    DrawLineStartYRegAddr (68),
    DrawLineEndYRegAddr (69),

    DrawRectangleXPosRegAddr (70),	//20
    DrawRectangleYPosRegAddr (71),
    DrawRectangleWidthRegAddr (72),
    DrawRectangleHeightRegAddr (73),
    DrawRectangleModeRegAddr (74),

    DrawCircleXPosRegAddr (75),
    DrawCircleYPosRegAddr (76),
    DrawCircleRRegAddr (77),
    DrawCircleModeRegAddr (78),

    DrawBitmapXPosRegAddr (79),
    DrawBitmapYPosRegAddr (80),
    DrawBitmapWidthRegAddr (81),
    DrawBitmapHeightRegAddr (82), 	//31


    DisplayConfigRegAddr (128),		//32
    WorkingModeRegAddr (129),
    BackLightConfigRegAddr (130),
    ContrastConfigRegAddr (131),
    DeviceAddressRegAddr (132);

    public byte address;

    LcdRegAddress(int address) {
        this.address = (byte)address;
    }
}
