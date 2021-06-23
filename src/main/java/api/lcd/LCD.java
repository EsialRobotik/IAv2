package api.lcd;

public interface LCD {

    /**
     * Affiche un texte sur l'écran LCD sur une nouvelle ligne
     * @param str Le texte à afficher
     */
    void println(String str);

    /**
     * Efface l'écran LCD
     */
    void clear();

    /**
     * Affichage du score sur le LCD
     */
    void score(int score);

}
