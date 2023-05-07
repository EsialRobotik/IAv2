package utils.ax12.cmds;

public class AX12CmdDescription {

    public final Class<? extends AX12Cmd> classe;
    public final String description;
    public final String nom;

    public AX12CmdDescription(Class<? extends AX12Cmd> classe, String nom, String description) {
        this.classe = classe;
        this.nom = nom;
        this.description = description;
    }

}

