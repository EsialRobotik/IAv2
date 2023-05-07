package utils.ax12.cmds;

import api.ax12.AX12;
import api.ax12.AX12Exception;
import api.ax12.AX12LinkException;
import api.ax12.AX12LinkSerial;
import gnu.io.SerialPort;
import utils.ax12.AX12MainConsole;

public class AX12CmdUarts extends AX12Cmd {
    private String subCommand;
    private String subArg;

    public AX12CmdUarts() {
        this (null, null);
    }

    public AX12CmdUarts(String subCommand) {
        this(subCommand, null);
    }

    public AX12CmdUarts(String subCommand, String subArg) {
        this(subCommand, subArg, true);
    }

    public AX12CmdUarts(String subCommand, String subArg, Boolean combineRxTx) {
        this.subCommand = subCommand == null ? null : subCommand.toLowerCase();
        this.subArg = subArg;
    }

    @Override
    public void executeCmd(AX12MainConsole cli) throws AX12CmdException {
        if (this.subCommand == null || this.subCommand.equals("infos")) {
            AX12LinkSerial asc = cli.getAx12SerialCommunicator();
            if (asc != null) {
                System.out.println("UART courant : "+asc.getUartName()+"@"+asc.getBaudRate()+"bps");
                System.out.println("Usage : "+getUsage());
            } else {
                System.out.println("Aucun UART connecté.");
            }
        } else if (this.subCommand.equals("ls")) {
            String[] ports = AX12LinkSerial.getAvailableSerialPorts();
            if (ports.length == 0) {
                System.out.println("Aucun port disponibles");
            } else {
                for (int i=0; i<ports.length; i++) {
                    System.out.println(" "+i+" : "+ports[i]);
                }
            }
        } else if (this.subCommand.equals("connect")){
            if (subArg == null) {
                throw new AX12CmdException("Il faut indiquer le n° ou le nom du port");
            }

            String[] ports = AX12LinkSerial.getAvailableSerialPorts();
            int pos = -1;
            for (int i=0; i<ports.length; i++) {
                if (ports[i].equals(subArg)) {
                    pos = i;
                    break;
                }
            }

            if (pos == -1) {
                try {
                    pos = Integer.parseInt(subArg);
                } catch (NumberFormatException e) {
                    throw new AX12CmdException("Le n° ou le nom du port n'est pas valide");
                }
            }

            if (pos < 0 || pos >= ports.length) {
                throw new AX12CmdException("Le n° n'est pas valide");
            }

            SerialPort cp = AX12LinkSerial.getSerialPort(ports[pos]);
            if (cp == null) {
                throw new AX12CmdException("Le port n'a pu être ouvert");
            }

            try {
                cli.setSerialCommunicator(new AX12LinkSerial(cp, 1000000));
            } catch (AX12LinkException e) {
                throw new AX12CmdException("Erreur d'ouverture du port : "+e.getMessage(), e);
            }
        } else if (this.subCommand.equals("speed")) {
            if (this.subArg == null) {
                throw new AX12CmdException("Précisez une vitesse de communication en bps");
            } else {
                try {
                    int i = Integer.parseInt(this.subArg);

                    cli.getAx12SerialCommunicator().setBaudRate(i);
                    cli.getCurrentAx12().setBaudRateRaw(i);
                } catch (NumberFormatException e) {
                    throw new AX12CmdException("La vitesse doit être un entier");
                } catch (AX12LinkException e) {
                    throw new AX12CmdException("Erreur de changmeent de la vitesse", e);
                }
            }
        } else if (this.subCommand.equals("resetax12")) {
            System.out.println("Reset du baudrate à 1 000 000 bps...");
            AX12 a = cli.getCurrentAx12();
            double avancement = 10;
            for (int i=2; i<255; i++) {
                a.setBaudRateRaw(2000000/i);
                for (int j=0; j<3; j++) {
                    try {
                        a.writeUartSpeed(AX12.AX12_UART_SPEEDS.SPEED_1000000);
                        Thread.sleep(150);
                    } catch (InterruptedException | AX12LinkException | AX12Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (((double)i/255.)*100 > avancement) {
                    System.out.println(avancement+"%.. ");
                    avancement += 10;
                }
            }
            System.out.println("100%.. \nFini.");
            try {
                cli.getAx12SerialCommunicator().setBaudRate(1000000);
            } catch (AX12LinkException e) {
                e.printStackTrace();
            }
        }  else if (this.subCommand.equals("write")) {
            if (subArg == null) {
                System.out.println("Vitesses supportées ["+getVitessesSupportees()+"] bps");
                return;
            }
            int vitesse = 0;
            try {
                vitesse = Integer.parseInt(subArg);
            } catch (NumberFormatException e) {
                throw new AX12CmdException("la vitesse doit être un entier positif", e);
            }

            AX12.AX12_UART_SPEEDS speed = AX12.AX12_UART_SPEEDS.fromValue(vitesse);
            if (speed == null) {
                throw new AX12CmdException("La vitesse n'est pas dans la liste ["+getVitessesSupportees()+"]");
            }
            try {
                cli.getCurrentAx12().writeUartSpeed(speed);
                cli.getAx12SerialCommunicator().setBaudRate(speed.intVal);
            } catch (AX12LinkException | AX12Exception e) {
                throw new AX12CmdException("Erreur d'écriture de la vitesse", e);
            }
        } else {
            System.out.println("Usage :"+getUsage());
        }
    }

    private String getVitessesSupportees() {
        StringBuffer sb = new StringBuffer();
        for (AX12.AX12_UART_SPEEDS sp : AX12.AX12_UART_SPEEDS.values()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(sp.intVal);
        }
        return sb.toString();
    }

    @Override
    public String getUsage() {
        return "\n - speed [vitesse] : change le baudrate de l'uart\n - ls : liste des uarts\n - connect [numero]: connexion à l'uart [numero] de la liste\n - resetax12 : reset le baudrate de l'ax12 courant (opération longue)\n - write [vitesse] : écrit la vitesse sur l'ax12. Si pas de vitesse, indique les vitesses supportées";
    }

}
