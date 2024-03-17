package esialrobotik.ia.actions;


import esialrobotik.ia.api.communication.SerialRxTx;
import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.manager.CommunicationManager;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class ActionSerial implements ActionExecutor {

    private final SerialRxTx serial;
    private final String serialCommand;
    protected boolean finished;
    protected Logger logger;

    public ActionSerial(SerialRxTx serial, String serialCommand) {
        logger = LoggerFactory.getLogger(ActionSerial.class);
        logger.info("ActionSerial init for command : " + serialCommand);
        this.serial = serial;
        this.serialCommand = serialCommand;
        this.finished = false;
    }

    @Override
    public void execute() {
        if (finished) {
            logger.info("ActionSerial finished command : " + serialCommand);
            return;
        }
        logger.info("ActionSerial execute command : " + serialCommand);

        new Thread(new Runnable() {
            @Override
            public void run() {
                serial.write(serialCommand);
                try {
                    StringBuilder sb = new StringBuilder();
                    InputStream is = serial.getInputStream();
                    while (true) {
                        if (is.available() > 0) {
                            char c = (char)is.read();
                            if (c == '\n' || c == '\r') {
                                sb.setLength(0);
                            } else {
                                sb.append(c);
                                String line = sb.toString().trim();
                                if (line.equals("ok") || line.equals("err")) {
                                    logger.info("ActionSerial command " + serialCommand + " finished");
                                    break;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    logger.error("Echec de ActionSerial : " + e.getMessage());
                } finally {
                    finished = true;
                }
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
