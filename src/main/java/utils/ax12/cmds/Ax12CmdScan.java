package utils.ax12.cmds;

import api.ax12.AX12;
import api.ax12.AX12Exception;
import api.ax12.AX12Link;
import api.ax12.AX12LinkException;
import api.ax12.value.AX12Position;
import utils.ax12.Ax12MainConsole;

public class Ax12CmdScan extends AX12Cmd {

    @Override
    public void executeCmd(Ax12MainConsole cli) throws AX12CmdException {
        AX12Link link = cli.getAx12SerialCommunicator();

        System.out.println("Scan adresse 0 à 63...");
        scan(link, 0, 64);
        System.out.println("Scan adresse 64 à 127...");
        scan(link, 64, 64);
        System.out.println("Scan adresse 128 à 191...");
        scan(link, 128, 64);
        System.out.println("Scan adresse 192 à 253...");
        scan(link, 192, 61);
    }

    protected void scan(AX12Link link, int offset, int length)
    {
        for (int i=offset; i<length; i++) {
            AX12 ax = new AX12(i, link);
            try {
                AX12Position position = ax.readServoPosition();
                System.out.println("Trouvé #"+i+" : "+position.getAngleAsDegrees()+"° / "+position.getRawAngle()+"raw");
            } catch (AX12Exception | AX12LinkException e) {
                // Rien : fail = pas d'ax12 présent à cette adresse
            }
        }
    }

    @Override
    public String getUsage() {
        return "Scanne la liaison courante à la recherche d'AX12";
    }
}
