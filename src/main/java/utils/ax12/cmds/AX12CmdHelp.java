package utils.ax12.cmds;

import utils.ax12.AX12MainConsole;

public class AX12CmdHelp extends AX12Cmd {

    public AX12CmdHelp() {
    }

    @Override
    public void executeCmd(AX12MainConsole cli) throws AX12CmdException {
        StringBuffer sb = new StringBuffer("Liste des commandes :\n");
        for (AX12CmdDescription desc : AX12Cmd.getAvailableCommands()) {
            sb.append(" - ");
            sb.append(desc.nom);
            sb.append(" : ");
            sb.append(desc.description);
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }

    @Override
    public String getUsage() {
        return null;
    }

}
