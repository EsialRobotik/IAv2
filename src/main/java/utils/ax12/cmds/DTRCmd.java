package utils.ax12.cmds;

import utils.ax12.Ax12MainConsole;

public class DTRCmd extends AX12Cmd {

    protected Boolean allumer;

    public DTRCmd(String enable) {
        allumer = enable != null && enable.equals("1");
    }

    public DTRCmd() {
        allumer = null;
    }
    @Override
    public void executeCmd(Ax12MainConsole cli) throws AX12CmdException {
        if (allumer == null) {
            System.out.println("DTR : "+(cli.getAx12SerialCommunicator().isDtrEnabled() ? "1" : "0"));
        } else {
            cli.getAx12SerialCommunicator().enableDtr(allumer);
        }
    }

    @Override
    public String getUsage() {
        return "Lit ou écrit la valeur du signal DTR";
    }
}
