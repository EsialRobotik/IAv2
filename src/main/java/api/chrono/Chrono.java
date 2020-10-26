package api.chrono;

import core.MasterLoop;

import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by icule on 20/05/17.
 */
public class Chrono {
    private Timer timer;
    private int matchDuration;
    private double timestampStart;

    public Chrono(int matchDuration) {
        timer = new Timer();
        this.matchDuration = matchDuration;
    }

    public String toString(){
        Timestamp t = new Timestamp(System.currentTimeMillis());
        int chrono = matchDuration - (int)((t.getTime() - timestampStart)/1000);
        return ""+chrono;
    }

    //We should find a way to do other wise, but, well
    public void startMatch(final MasterLoop masterLoop) {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        timestampStart = t.getTime();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                masterLoop.matchEnd();
            }
        }, matchDuration * 1000); //Delay in milliseconds
    }
}
