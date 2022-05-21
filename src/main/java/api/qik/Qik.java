package api.qik;

import api.communication.Serial;
import api.log.LoggerFactory;
import asserv.Asserv;
import com.google.gson.JsonObject;
import com.pi4j.io.serial.Baud;
import org.apache.logging.log4j.Logger;

public class Qik {

    /**
     * Serial link
     */
    protected Serial serial;

    /**
     * Logger
     */
    protected Logger logger = null;

    public Qik(JsonObject config) {
        this.logger = LoggerFactory.getLogger(Asserv.class);

        String serialPort = config.get("serie").getAsString();
        Baud baudRate = Baud.getInstance(config.get("baud").getAsInt());

        this.logger.info("Initialisation de la liaison série Qik, port =  " + serialPort + ", baudRate = " + baudRate.getValue());
        this.serial = new Serial(serialPort, baudRate);
    }
}
