package actions;

import api.communication.Serial;
import api.log.LoggerFactory;
import com.pi4j.io.serial.SerialDataEventListener;
import manager.CommunicationManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ActionSerial implements ActionExecutor {

    private Serial serial;
    private String serialCommand;
    private SerialDataEventListener serialDataEventListener;
    protected boolean finished;
    protected Logger logger;

    public ActionSerial(Serial serial, String serialCommand) {
        logger = LoggerFactory.getLogger(ActionSerial.class);
        logger.info("ActionSerial init for command : " + serialCommand);
        this.serial = serial;
        this.serialCommand = serialCommand;
        serialDataEventListener = serialDataEvent -> {
            try {
                String serialBuffer = serialDataEvent.getAsciiString();
                if (serialBuffer != null && serialBuffer.trim().equals("ok")) {
                    finished = true;
                    serial.removeReaderListeners(serialDataEventListener);
                    logger.info("ActionSerial command " + serialCommand + " finished");
                }
            } catch (IOException e) {
                logger.error("Echec de ActionSerial : " + e.getMessage());
            }
        };
        this.finished = false;
    }

    @Override
    public void execute() {
        if (finished) {
            logger.info("ActionSerial finished command : " + serialCommand);
            return;
        }
        logger.info("ActionSerial execute command : " + serialCommand);
        this.serial.addReaderListeners(serialDataEventListener);

        new Thread(new Runnable() {
            @Override
            public void run() {
                serial.write(serialCommand);
            }
        }).start();
    }

    @Override
    public boolean finished() {
        return this.finished;
    }

    @Override
    public void resetActionState() {
        this.finished = false;
    }

    @Override
    public void setData(String data) {}

    @Override
    public void setCommunicationManager(CommunicationManager communicationManager) {}

    @Override
    public String getActionFlag() {
        return null;
    }
}
