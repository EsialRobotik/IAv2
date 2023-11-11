package utils.ax12.cmds;

import utils.ax12.Ax12MainConsole;

public class Ax12CmdHelp extends AX12Cmd {

    public Ax12CmdHelp() {
    }

    @Override
    public void executeCmd(Ax12MainConsole cli) throws AX12CmdException {
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
