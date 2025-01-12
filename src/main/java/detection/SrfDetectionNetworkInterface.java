package detection;

import detection.ultrasound.SRFInterface;

/**
 * Created by franc on 10/02/2017.
 */
public interface SrfDetectionNetworkInterface {
    void startDetection();

    //We never know right
    void stopDetection();

    int getUltraSoundSensorCount();

    long[] ultraSoundDetection();

    SRFInterface getUltrasoundFrontLeft();
    SRFInterface getUltrasoundFront();
    SRFInterface getUltrasoundFrontRight();
    SRFInterface getUltrasoundBack();
}
