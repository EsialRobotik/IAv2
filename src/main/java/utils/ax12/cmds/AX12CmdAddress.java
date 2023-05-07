package utils.ax12.cmds;

import api.ax12.AX12Exception;
import api.ax12.AX12LinkException;
import utils.ax12.AX12MainConsole;

public class AX12CmdAddress extends AX12Cmd {

    private Integer address;
    private String cmd;

    public AX12CmdAddress() {
        this(null, null);
    }

    public AX12CmdAddress(String cmd, Integer address) {
        this.cmd = cmd;
        this.address = address;
    }

    public AX12CmdAddress(Integer address) {
        this(null, address);
    }

    @Override
    public void executeCmd(AX12MainConsole cli) throws AX12CmdException {
        this.thowsNoAx12Exception(cli);
        if (cmd == null) {
            if (address == null) {
                System.out.println("Adresse courante : "+cli.getCurrentAx12().getAddress());
                System.out.println("Usage :"+getUsage());
            } else {
                try {
                    cli.getCurrentAx12().setAddress(address);
                } catch (IllegalArgumentException e) {
                    throw new AX12CmdException("Erreur de changement d'adresse", e);
                }
            }
        } else {
            if (cmd.equals("set")) {
                if (address == null) {
                    throw new AX12CmdException("L'adresse n'a pas été fournie");
                }
                try {
                    cli.getCurrentAx12().writeAddress(address);
                } catch (AX12LinkException | IllegalArgumentException | AX12Exception e) {
                    throw new AX12CmdException("Erreur d'écriture d'adresse", e);
                }
            } else {
                throw new AX12CmdException("Mauvais argument");
            }
        }
    }

    @Override
    public String getUsage() {
        return "\n - <a> désigne l'AX12 à utiliser\n - set <a> change l'adresse sur l'AX12";
    }
}
