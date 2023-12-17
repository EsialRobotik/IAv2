package esialrobotik.ia.utils.ax12.cmds;

import esialrobotik.ia.api.ax12.AX12Exception;
import esialrobotik.ia.api.ax12.AX12LinkException;
import esialrobotik.ia.utils.ax12.Ax12MainConsole;

public class AX12CmdLed extends AX12Cmd {

    private boolean allumer;

    public AX12CmdLed(Boolean allumer) {
        this.allumer = allumer;
    }

    @Override
    public void executeCmd(Ax12MainConsole cli) throws AX12CmdException {
        this.thowsNoAx12Exception(cli);
        try {
            cli.getCurrentAx12().setLed(allumer);
        } catch (AX12LinkException | AX12Exception e) {
            throw new AX12CmdException("Erreur avec l'AX12 : "+e.getMessage(), e);
        }
    }

    @Override
    public String getUsage() {
        return "1 ou 0 / true / false pour allumer/éteindre la led";
    }

}
