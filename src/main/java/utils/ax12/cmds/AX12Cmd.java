package utils.ax12.cmds;

import utils.ax12.AX12MainConsole;

import java.lang.reflect.Constructor;
import java.util.*;

public abstract class AX12Cmd {

    private static final Map<String, AX12CmdDescription> commandes;
    static {
        commandes = new HashMap<String, AX12CmdDescription>();
        commandes.put("help", new AX12CmdDescription(AX12CmdHelp.class, "help", "Affiche la liste des commandes disponibles"));
        commandes.put("uart", new AX12CmdDescription(AX12CmdUarts.class, "uart", "Manipulation de l'UART"));
        commandes.put("dtr", new AX12CmdDescription(DTRCmd.class, "dtr", "Lit ou écrit le DTR de l'UART"));
        commandes.put("rts", new AX12CmdDescription(RTSCmd.class, "rts", "Lit ou écrit le RTS de l'UART"));
        commandes.put("a", new AX12CmdDescription(AX12CmdAddress.class, "a", "Affiche l'adresse d'Ax12 en cours ou en définit une"));
        commandes.put("s", new AX12CmdDescription(Ax12CmdScan.class, "s", "Scanne la liaison à la recherche d'ax12"));
        commandes.put("l", new AX12CmdDescription(AX12CmdLed.class, "l", "Allume ou éteint la led de l'AX12"));
        commandes.put("g", new AX12CmdDescription(AX12CmdGoal.class, "g", "Définit la position à tenir de l'AX12 en degrés dans [0 ~300]"));
        commandes.put("exit", new AX12CmdDescription(AX12CmdExit.class, "exit", "Quitter le programme"));
    }


    public static AX12Cmd buildAx12CmdFromParamettersString(String in) throws AX12CmdException {
        List<String> cmds = AX12Cmd.getCmds(in);
        if (cmds.isEmpty()) {
            return null;
        }

        String cmd = cmds.remove(0).toLowerCase();
        if (commandes.containsKey(cmd)) {
            AX12CmdDescription desc = commandes.get(cmd);
            int args_length = cmds.size();
            Constructor<?>[] constructeurs = desc.classe.getConstructors();
            if (constructeurs.length > 0) {
                // On recherche un constructeur qui a exactement le bon nombre de paramètres et un autre qui s'en rapproche
                int index_exact = -1;
                int index_presque = -1;
                int min_params = -1;
                for (int i=0; i<constructeurs.length; i++) {
                    if (min_params == -1 || constructeurs[i].getParameterCount() < min_params) {
                        min_params = constructeurs[i].getParameterCount();
                    }
                    if (constructeurs[i].getParameterCount() == args_length) {
                        index_exact = i;
                        break;
                    } else if (constructeurs[i].getParameterCount() < args_length) {
                        // Si le constructeur trouvé a plus d'arguments qu'un autre constructeur déjà trouvé
                        if (index_presque == -1 || constructeurs[i].getParameterCount() > constructeurs[index_presque].getParameterCount()) {
                            index_presque = i;
                        }
                    }
                }

                int index_a_utiliser = Math.max(index_exact, index_presque);
                if (index_a_utiliser != -1) {
                    Constructor<?> construct = constructeurs[index_a_utiliser];
                    Class<?>[] paramsClasses = construct.getParameterTypes();
                    Object[] params = new Object[paramsClasses.length];
                    int i = 0;

                    for (i=0; i<paramsClasses.length; i++) {
                        Class<?> c = paramsClasses[i];
                        if (c == String.class) {
                            params[i] = cmds.get(i);
                        } else if (c == Double.class) {
                            try {
                                params[i] = Double.parseDouble(cmds.get(i));
                            } catch (NumberFormatException e) {
                                throw new AX12CmdException("L'argument "+(i+1)+" doit être un nombre décimal");
                            }
                        } else if (c == Integer.class) {
                            try {
                                params[i] = Integer.parseInt(cmds.get(i));
                            } catch (NumberFormatException e) {
                                throw new AX12CmdException("L'argument "+(i+1)+" doit être un nombre entier");
                            }
                        } else if (c == Boolean.class) {
                            String b = cmds.get(i).toLowerCase();
                            if (b.equals("true") || b.equals("1")) {
                                params[i] = true;
                            } else if (b.equals("false") || b.equals("0")) {
                                params[i] = false;
                            } else {
                                throw new AX12CmdException("L'argument "+(i+1)+" doit être une valeur booléenne : true / false / 1 / 0");
                            }
                        } else {
                            throw new AX12CmdException("Mauvaise implémentation de la commande");
                        }
                    }

                    try {
                        return (AX12Cmd)construct.newInstance(params);
                    } catch (Exception e) {
                        throw new AX12CmdException("La commande n'est pas correctement implémentée", e);
                    }

                } else {
                    throw new AX12CmdException("Minimum de paramètres attendus : "+min_params);
                }
            } else {
                throw new AX12CmdException("La commande n'est pas correctement implémentée");
            }
        } else {
            throw new AX12CmdException("Commande non trouvée.");
        }
    }

    /**
     * Exécute la commande
     * @param cli
     * @throws AX12CmdException
     */
    public abstract void executeCmd(AX12MainConsole cli) throws AX12CmdException;

    /**
     * Indique commande utiliser la commande
     * @return null s'il n'y a pas d'usage particulier
     */
    public abstract String getUsage();

    /**
     * Découpe une chaine de caractères en une liste d'arguments en ne conservant pas les espaces
     * @param in
     * @return retourne la commande et la liste de ses paramètres
     */
    private static List<String> getCmds(String in) {
        ArrayList<String> cmds = new ArrayList<>();
        String[] rawCmds = in.split(" ");
        for (String s : rawCmds) {
            if (!s.trim().equals("")) {
                cmds.add(s);
            }
        }
        return cmds;
    }

    public static Collection<AX12CmdDescription> getAvailableCommands() {
        return commandes.values();
    }

    /**
     * Renvoit la description associée à une commande
     * @param cmd
     * @return null si la commande n'existe pas
     */
    public static AX12CmdDescription getCmd(String cmd) {
        return commandes.get(cmd);
    }

    protected void thowsNoAx12Exception(AX12MainConsole cli) throws AX12CmdException {
        if (cli.getCurrentAx12() == null) {
            throw new AX12CmdException("Aucun AX12 n'est défini");
        }
    }

}
