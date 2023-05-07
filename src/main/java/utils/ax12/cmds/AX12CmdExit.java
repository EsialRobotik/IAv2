package utils.ax12.cmds;

import utils.ax12.AX12MainConsole;

public class AX12CmdExit extends AX12Cmd {

    @Override
    public void executeCmd(AX12MainConsole cli) throws AX12CmdException {
        cli.requestStopMainLoop();
        System.out.println("Bye bye o/");
    }

    @Override
    public String getUsage() {
        return null;
    }

}
