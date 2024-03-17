package esialrobotik.ia.asserv;

/**
 * Created by franc on 10/02/2017.
 */
public interface AsservInterface {


    enum AsservStatus {
        STATUS_IDLE,
        STATUS_RUNNING,
        STATUS_HALTED,
        STATUS_BLOCKED
    }

    enum MovementDirection {
        BACKWARD,
        FORWARD,
        NONE
    }

    /***********************************************************************
     * Commande basique
     ************************************************************************/

    /**
     * Démarre l'asservissement
     */
    void initialize();

    /**
     * Arrête l'asservissement
     */
    void stop();

    /**
     * Arrêt d'urgence de l'esialrobotik.ia.asserv, défini la consigne comme étant la position courante du robot
     */
    void emergencyStop();

    /**
     * Annule l'état d'arrêt d'urgence.
     * Tout les commandes seront ignorées en mode Arrêt d'urgence avant l'appel à cette méthode
     */
    void emergencyReset();

    /**
     * Déplace le robot en ligne droite
     * @param dist distance en mm
     */
    void go(int dist);

    /**
     * Fait tourner le robot
     * @param degree angle en degrés
     */
    void turn(int degree);

    /***********************************************************************
     * Commande GoTo
     ************************************************************************/

    /**
     * Envoie le robot à une position définie en marche avant
     * @param position position en (x,y) à atteindre, l'angle n'est pas pris en compte
     */
    void goTo(Position position);

    /**
     * Envoie le robot à une position définie en marche avant avec enchainement des GoTo
     * @param position position en (x,y) à atteindre, l'angle n'est pas pris en compte
     */
    void goToChain(Position position);

    /**
     * Envoie le robot à une position définie en marche arrière
     * @param position position en (x,y) à atteindre, l'angle n'est pas pris en compte
     */
    void goToReverse(Position position);

    /**
     * Aligne le robot avec une position
     * @param position position en (x,y) pour l'alignement
     */
    void face(Position position);

    /***********************************************************************
     * Commande odométrie
     ************************************************************************/

    /**
     * Définiela position complète du robot
     * @param x X en mm
     * @param y Y en mm
     * @param theta Angle theta en radian
     */
    void setOdometrie(int x, int y, double theta);

    /***********************************************************************
     * Commande régulateur
     ************************************************************************/

    /**
     * Active ou désactive le mode low speed
     * @param enable true pour activer, false pour désactiver
     */
    void enableLowSpeed(boolean enable);

    /**
     * Définie la vitesse maximum en %
     * @param pct pourcentage de la vitesse max
     */
    void setSpeed(int pct);

    /**
     * Active ou désactive le régulateur d'angle
     * @param enable true pour activer, false pour désactiver
     */
    void enableRegulatorAngle(boolean enable);

    /**
     * Active ou désactive le régulateur de distance
     * @param enable true pour activer, false pour désactiver
     */
    void enableRegulatorDistance(boolean enable);

    /**
     * Active ou désactive les moteurs
     * @param enable true pour activer les moteurs, false pour les couper
     */
    void enableMotors(boolean enable);

    /***********************************************************************
     * Statut de l'asservissement
     ************************************************************************/

    /**
     * Retourne le statue de la dernière commande de l'asservissement
     * @return AsservStatus
     */
    AsservStatus getAsservStatus();

    /**
     * Retourne la taille de file de commandes
     * @return Taille de la file de commandes
     */
    int getQueueSize();

    /**
     * Renvoit la position du robot stockée par l'API
     * @return position stockée du robot
     */
    Position getPosition();

    /**
     * Renvoit la dernière direction du mouvement lu sur la connection série
     * @return Direction d movement
     */
    MovementDirection getMovementDirection();

    void goStart(boolean isColor0) throws Exception;
}
