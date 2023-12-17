package esialrobotik.ia.utils.ax12.cmds;

import esialrobotik.ia.api.ax12.AX12Exception;
import esialrobotik.ia.api.ax12.AX12LinkException;
import esialrobotik.ia.api.ax12.value.AX12Position;
import esialrobotik.ia.utils.ax12.Ax12MainConsole;

/**
 * Lit ou écrit la position de l'AX12
 */
public class AX12CmdGoal extends AX12Cmd {
    private final Double goal;


    public AX12CmdGoal() {
        this(null);
    }
    public AX12CmdGoal(Double goal) {
        this.goal = goal;
    }

    @Override
    public void executeCmd(Ax12MainConsole cli) throws AX12CmdException {
        try {
            if (goal == null) {
                AX12Position position = cli.getCurrentAx12().readServoPosition();
                System.out.println("Position : "+position.getAngleAsDegrees()+"° / "+position.getRawAngle()+" raw");
            } else {
                cli.getCurrentAx12().setServoPosition(AX12Position.buildFromDegrees(this.goal));
            }
        } catch (IllegalArgumentException | AX12LinkException | AX12Exception e) {
            throw new AX12CmdException("Erreur avec l'AX12 : "+e.getMessage(), e);
        }
    }

    @Override
    public String getUsage() {
        return "Usage : indiquer l'angle en degrés dans la plage [0; 300] ou rien pour lire";
    }
}
