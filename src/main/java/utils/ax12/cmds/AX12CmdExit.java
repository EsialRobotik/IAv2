package utils.ax12.cmds;

import utils.ax12.Ax12MainConsole;

public class AX12CmdExit extends AX12Cmd {

    @Override
    public void executeCmd(Ax12MainConsole cli) throws AX12CmdException {
        cli.requestStopMainLoop();
        System.out.println("Bye bye o/");
    }

    @Override
    public String getUsage() {
        return null;
    }

}
