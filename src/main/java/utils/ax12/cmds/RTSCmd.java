package utils.ax12.cmds;

import utils.ax12.AX12MainConsole;

public class RTSCmd extends AX12Cmd {

    protected Boolean allumer;

    public RTSCmd(String enable) {
        allumer = enable != null && enable.equals("1");
    }

    public RTSCmd() {
        allumer = null;
    }
    @Override
    public void executeCmd(AX12MainConsole cli) throws AX12CmdException {
        if (allumer == null) {
            System.out.println("DTR : "+(cli.getAx12SerialCommunicator().isRtsEnabled() ? "1" : "0"));
        } else {
            cli.getAx12SerialCommunicator().enableRts(allumer);
        }
    }

    @Override
    public String getUsage() {
        return "Lit ou écrit la valeur du signal RTS";
    }
}
