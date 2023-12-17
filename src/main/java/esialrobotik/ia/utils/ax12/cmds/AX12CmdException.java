package esialrobotik.ia.utils.ax12.cmds;

public class AX12CmdException extends Exception {

    private static final long serialVersionUID = 1L;

    public AX12CmdException(String raison) {
        this(raison, null);
    }

    public AX12CmdException(String raison, Throwable previous) {
        super(raison, previous);
    }

}

