package esialrobotik.ia.utils.ax12;

import esialrobotik.ia.api.ax12.AX12;
import esialrobotik.ia.api.ax12.AX12LinkSerial;
import esialrobotik.ia.utils.ax12.cmds.AX12Cmd;
import esialrobotik.ia.utils.ax12.cmds.AX12CmdException;

import java.util.Scanner;

/**
 * Pilotage des AX12 par la console
 * @author gryttix
 *
 */
public class Ax12MainConsole {

    private AX12 ax12;
    private boolean continueMainLoop;
    private AX12LinkSerial sc;

    public Ax12MainConsole(AX12LinkSerial ax12link) {
        sc = ax12link;
        ax12 = new AX12(1, sc);
    }

    /**
     * Boucle principale de la ligne de commande
     * La fonction bloque jusqu'à la fin du programme
     */
    public void mainLoop() {
        this.continueMainLoop = true;
        Scanner scan = new Scanner(System.in);
        String line;

        System.out.println("Démarré, tapez help pour la liste des commandes");
        while (this.continueMainLoop) {
            System.out.print(getInfos());
            line = scan.nextLine();
            if (line != null) {
                line = line.trim();
            }
            if (line == null || line.equals("")) {
                System.out.println("Tapez help pour la liste des commandes");
            } else {
                try {
                    AX12Cmd cmd = AX12Cmd.buildAx12CmdFromParamettersString(line);
                    if (cmd != null) {
                        cmd.executeCmd(this);
                    }
                } catch (AX12CmdException e) {
                    System.err.println("Erreur : "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        scan.close();
    }

    private String getInfos() {
        if (sc == null || ax12 == null) {
            return "non-conecté>";
        } else {
            return sc.getUartName()+"@"+sc.getBaudRate()+"bps-addr"+ax12.getAddress()+">";
        }
    }

    public AX12 getCurrentAx12() {
        return this.ax12;
    }

    public AX12LinkSerial getAx12SerialCommunicator() {
        return this.sc;
    }

    public void requestStopMainLoop() {
        this.continueMainLoop = false;
    }

    public void setSerialCommunicator(AX12LinkSerial sc) {
        this.sc = sc;
    }


}
