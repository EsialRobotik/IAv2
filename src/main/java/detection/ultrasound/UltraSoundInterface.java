package detection.ultrasound;

import asserv.Position;

/**
 * Created by Guillaume on 14/05/2017.
 */
public interface UltraSoundInterface {

    void init();

    long getMeasure();

    Position getPosition();

    int getThreshold();
}
