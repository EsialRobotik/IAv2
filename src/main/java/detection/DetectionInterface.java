package detection;

import detection.ultrasound.UltraSoundInterface;

/**
 * Created by franc on 10/02/2017.
 */
public interface DetectionInterface {
    void startDetection();

    //We never know right
    void stopDetection();

    int getUltraSoundSensorCount();

    long[] ultraSoundDetection();

    UltraSoundInterface getUltrasoundFrontLeft();
    UltraSoundInterface getUltrasoundFront();
    UltraSoundInterface getUltrasoundFrontRight();
    UltraSoundInterface getUltrasoundBack();
}
