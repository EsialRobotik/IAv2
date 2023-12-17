package esialrobotik.ia.api.qik;

import esialrobotik.ia.api.communication.SerialDevice;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.asserv.Asserv;
import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Communication avec une Qik2s9v1
 * Code complètement volé de <a href="https://github.com/Nancyborg/Nancyborg/blob/master/informatique/ia-raspberry/src/api/controllers/qik/Qik2s9v1.java">Nancyborg</a>
 */
public class Qik {

    private static final int QIK_GET_FIRMWARE_VERSION = 0x81;
    private static final int QIK_GET_ERROR_BYTE = 0x82;
    private static final int QIK_GET_CONFIGURATION_PARAMETER = 0x83;
    private static final int QIK_SET_CONFIGURATION_PARAMETER = 0x84;

    private static final int QIK_MOTOR_M0_FORWARD = 0x88;
    private static final int QIK_MOTOR_M0_FORWARD_8_BIT = 0x89;
    private static final int QIK_MOTOR_M0_REVERSE = 0x8A;
    private static final int QIK_MOTOR_M0_REVERSE_8_BIT = 0x8B;
    private static final int QIK_MOTOR_M1_FORWARD = 0x8C;
    private static final int QIK_MOTOR_M1_FORWARD_8_BIT = 0x8D;
    private static final int QIK_MOTOR_M1_REVERSE = 0x8E;
    private static final int QIK_MOTOR_M1_REVERSE_8_BIT = 0x8F;

    // 2s9v1 only
    private static final int QIK_2S9V1_MOTOR_M0_COAST = 0x86;
    private static final int QIK_2S9V1_MOTOR_M1_COAST = 0x87;

    /**
     * Identificateur utilisé pour adresser plusieurs Qik sur le même port série
     * <p>
     * <dl>
     * <dt>Valeur par défaut :</dt>
     * <dd>9</dd>
     * </dl>
     */
    public static final byte QIK_CONFIG_DEVICE_ID = 0;

    /**
     * Paramètrage du PWM
     * <p>
     * <blockquote>
     * <table border="1">
     * <tr>
     * <th>Valeur</th>
     * <th>Résolution</th>
     * <th>Fréquence PWM</th>
     * </tr>
     * <tr>
     * <td>0</td>
     * <td>7 bits</td>
     * <td>31.5 KHz (inaudible)</td>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>8 bits</td>
     * <td>15.7 KHz</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>7 bits</td>
     * <td>7.8 KHz</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>8 bits</td>
     * <td>3.9 KHz</td>
     * </tr>
     * </table>
     * </blockquote>
     * <dl>
     * <dt>Valeur par défaut :</dt>
     * <dd>0</dd>
     * </dl>
     */
    public static final byte QIK_CONFIG_PWM_PARAMETER = 1;

    /**
     * Indique si la Qik doit arrêter (<b>1</b>) ou non (<b>0</b>) les moteurs
     * en cas d'erreur.
     * <dl>
     * <dt>Valeur par défaut :</dt>
     * <dd>1</dd>
     * </dl>
     */
    public static final byte QIK_CONFIG_SHUT_DOWN_MOTORS_ON_ERROR = 2;

    /**
     * Durée après laquelle la Qik déclenche une erreur de timeout en l'absence
     * de réception d'un paquet (ceci permet par exemple d'arrêter les moteurs
     * si on perd la connexion avec la Qik !)
     * <p>
     * <b>Pour une valeur de 0, la fonctionnalité est désactivée</b>
     * <p>
     * Sinon, la formule donnant le timeout est : <blockquote>timeout = 0.262
     * secondes * <b>x</b> * 2 <sup><b>y</b></sup></blockquote> où <b>x</b>
     * désigne les 4 bits de poids faible de la valeur du registre et <b>y</b>
     * les 3 bits de poids fort.
     * <p>
     * Ainsi, si le registre a la valeur 5E (01011110 en binaire), on aura :
     * <blockquote>
     * <p>
     * x = 1110 = 14
     * <p>
     * y = 101 = 5
     * <p>
     * timeout = 0.262s * 14 * 2<sup>5</sup> = <b>117 secondes</b>.
     * </blockquote>
     * <p>
     * La durée peut varier entre 262 ms et 8.32 minutes
     *
     * <dl>
     * <dt>Valeur par défaut :</dt>
     * <dd>0 (désactivé)</dd>
     * </dl>
     */
    public static final byte QIK_CONFIG_SERIAL_TIMEOUT = 3;

    /**
     * Serial link
     */
    protected SerialDevice serialDevice;

    /**
     * Logger
     */
    protected Logger logger = null;

    public Qik(JsonObject config) {
        this.logger = LoggerFactory.getLogger(Asserv.class);

        String serialPort = config.get("serie").getAsString();
        Baud baudRate = Baud.getInstance(config.get("baud").getAsInt());

        this.logger.info("Initialisation de la liaison série Qik, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        this.serialDevice = new SerialDevice(serialPort, baudRate);
        this.serialDevice.write(0xAA); // permet à la Qik de détecter la vitesse de communication entre 1200 et 38400 bps
    }

    /**
     * Renvoie un caractère correspondant à la version du firmware (normalement
     * '1' ou '2')
     */
    public char getFirmwareVersion() throws IOException {
        serialDevice.write(QIK_GET_FIRMWARE_VERSION);
        return (char) serialDevice.getInputStream().read();
    }

    /**
     * Retourne les erreurs en cours sous forme de "masque" (chaque bit à 1
     * indique un type d'erreur)
     * <p>
     * <blockquote>
     * <table border="1">
     * <tr>
     * <th>Bits</th>
     * <th>Erreur</th>
     * <th>Commentaire</th>
     * </tr>
     * <tr>
     * <td>0-2</td>
     * <td>-</td>
     * <td>Non utilisé</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>Dépassement de tampon</td>
     * <td></td>
     * </tr>
     * <tr>
     * <td>4</td>
     * <td>Trame</td>
     * <td>Peut se produire si on communique à la mauvaise vitesse</td>
     * </tr>
     * <tr>
     * <td>5</td>
     * <td>CRC</td>
     * <td></td>
     * </tr>
     * <tr>
     * <td>6</td>
     * <td>Format</td>
     * <td>Un paquet invalide a été reçu</td>
     * </tr>
     * <tr>
     * <td>7</td>
     * <td>Timeout</td>
     * <td></td>
     * </tr>
     * </table>
     * </blockquote>
     * <dl>
     * <dt>Valeur par défaut</dt>
     * <dd>0 (pas d'erreur)</dd>
     * </dl>
     *
     * @return
     * @throws IOException
     */
    public int getErrors() throws IOException {
        serialDevice.write(QIK_GET_ERROR_BYTE);
        return serialDevice.getInputStream().read();
    }

    /**
     * Récupère la valeur d'un paramètre de configuration
     *
     * @param parameter numéro du paramètre (constantes QIK_CONFIG_*)
     * @throws IOException
     */
    public int getConfigurationParameter(int parameter) throws IOException {
        serialDevice.write(QIK_GET_CONFIGURATION_PARAMETER);
        serialDevice.write(parameter);
        return serialDevice.getInputStream().read();
    }

    /**
     * Change la valeur d'un paramètre de configuration
     *
     * @param parameter numéro du paramètre (constantes QIK_CONFIG_*)
     * @param value nouvelle valeur
     * @throws IOException
     */
    public int setConfigurationParameter(int parameter, int value) throws IOException {
        serialDevice.write(QIK_SET_CONFIGURATION_PARAMETER);
        serialDevice.write(parameter);
        serialDevice.write(value);
        serialDevice.write(0x55);
        serialDevice.write(0x2A);
        return serialDevice.getInputStream().read();
    }

    /**
     * Change la consigne de vitesse de la voie M0
     *
     * @param speed la nouvelle vitesse (entre -127 et 127 en mode 7 bits et
     *            entre -255 et 255 en mode 8 bits)
     * @throws IOException
     */
    public void setM0Speed(int speed) throws IOException {
        boolean reverse = false;

        if (speed < 0) {
            speed = -speed; // make speed a positive quantity
            reverse = true; // preserve the direction
        }

        if (speed > 255) {
            speed = 255;
        }

        if (speed > 127) {
            // 8-bit mode: actual speed is (speed + 128)
            serialDevice.write(reverse ? QIK_MOTOR_M0_REVERSE_8_BIT : QIK_MOTOR_M0_FORWARD_8_BIT);
            serialDevice.write(speed - 128);
        } else {
            serialDevice.write(reverse ? QIK_MOTOR_M0_REVERSE : QIK_MOTOR_M0_FORWARD);
            serialDevice.write(speed);
        }
    }

    /**
     * Change la consigne de vitesse de la voie M1
     *
     * @param speed la nouvelle vitesse (entre -127 et 127 en mode 7 bits et
     *            entre -255 et 255 en mode 8 bits)
     * @throws IOException
     */
    public void setM1Speed(int speed) throws IOException {
        boolean reverse = false;

        if (speed < 0) {
            speed = -speed; // make speed a positive quantity
            reverse = true; // preserve the direction
        }

        if (speed > 255) {
            speed = 255;
        }

        if (speed > 127) {
            // 8-bit mode: actual speed is (speed + 128)
            serialDevice.write(reverse ? QIK_MOTOR_M1_REVERSE_8_BIT : QIK_MOTOR_M1_FORWARD_8_BIT);
            serialDevice.write(speed - 128);
        } else {
            serialDevice.write(reverse ? QIK_MOTOR_M1_REVERSE : QIK_MOTOR_M1_FORWARD);
            serialDevice.write(speed);
        }
    }

    /**
     * Change la consigne des deux voies M0 et M1
     *
     * @param m0Speed nouvelle consigne de la voie M0
     * @param m1Speed nouvelle consigne de la voie M1
     * @see #setM0Speed(int)
     * @see #setM1Speed(int)
     * @throws IOException
     */
    public void setSpeeds(int m0Speed, int m1Speed) throws IOException {
        setM0Speed(m0Speed);
        setM1Speed(m1Speed);
    }

    // 2s9v1

    /**
     * Active le mode roue-libre sur la voie M0
     * <p>
     * Les sorties sont alors mises en haute impédance (alors qu'une consigne de
     * 0 les met en court-circuit)
     *
     * @throws IOException
     */
    public void setM0Coast() throws IOException {
        serialDevice.write(QIK_2S9V1_MOTOR_M0_COAST);
    }

    /**
     * Active le mode roue-libre sur la voie M1
     * <p>
     * Les sorties sont alors mises en haute impédance (alors qu'une consigne de
     * 0 les met en court-circuit)
     *
     * @throws IOException
     */
    public void setM1Coast() throws IOException {
        serialDevice.write(QIK_2S9V1_MOTOR_M1_COAST);
    }

    /**
     * Active le mode roue-libre sur les voies M0 et M1
     * <p>
     * Les sorties sont alors mises en haute impédance (alors qu'une consigne de
     * 0 les met en court-circuit)
     *
     * @throws IOException
     * @see #setM0Coast()
     * @see #setM1Coast()
     */
    public void setCoasts() throws IOException {
        setM0Coast();
        setM1Coast();
    }
}
