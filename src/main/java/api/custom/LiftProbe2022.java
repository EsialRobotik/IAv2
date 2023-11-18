package api.custom;

import api.communication.SerialDevice;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Gestion de l'ascenseur et de la sondes des carrés de fouille de la grosse Princesse 2022
 */
public class LiftProbe2022 {

    public enum CARRE_FOUILLE {
        VIDE,
        PIEGE,
        EQUIPE_JAUNE,
        EQUIPE_VIOLETTE,
        NON_RECONNU,
    }

    protected SerialDevice serialDevice;
    protected Scanner inScanner;
    protected OutputStreamWriter outWriter;

    public LiftProbe2022(SerialDevice serialDevice) {
        this.serialDevice = serialDevice;
        this.inScanner = new Scanner(serialDevice.getInputStream());
        this.outWriter = new OutputStreamWriter(serialDevice.getOutputStream());
    }

    protected String sendCommandAndWaitResult(String cmd) throws IOException {
        this.outWriter.write(cmd);
        this.outWriter.write('\n');
        this.outWriter.flush();
        return this.inScanner.nextLine().trim();
    }

    /**
     * Effectue un reset de l'ascenseur
     * Bloquant
     */
    public void makeLiftHome() throws IOException {
        sendCommandAndWaitResult("z");
    }

    /**
     * Définit la hauteur de l'ascenseur en mm
     * Bloquant
     * @param mm
     */
    public void setLiftPosition(int mm) throws IOException {
        sendCommandAndWaitResult("g"+mm);
    }

    /**
     * Renvoie la hauteur courante de l'ascenseur
     * @return
     * @throws IOException
     */
    public int fetchLiftPosition() throws IOException {
        return Integer.parseInt(sendCommandAndWaitResult("a"));
    }

    /**
     * Arrêt d'urgence de l'ascenseur : coupe le courant dans le moteur
     * @throws IOException
     */
    public void liftEmergencyStop() throws IOException {
        sendCommandAndWaitResult("h");
    }

    /**
     * Renvoie le carré de fouille actuellement sondé
     * @return
     */
    public CARRE_FOUILLE probeExcavations() throws IOException
    {
        switch (sendCommandAndWaitResult("s")) {
            case "n":
                return CARRE_FOUILLE.VIDE;
            case "t":
                return CARRE_FOUILLE.PIEGE;
            case "y":
                return CARRE_FOUILLE.EQUIPE_JAUNE;
            case "p":
                return CARRE_FOUILLE.EQUIPE_VIOLETTE;
            case "u":
            default:
                return CARRE_FOUILLE.NON_RECONNU;
        }
    }

}
