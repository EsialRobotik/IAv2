package esialrobotik.ia.detection;

import esialrobotik.ia.detection.lidar.LidarPoint;
import esialrobotik.ia.detection.ultrasound.UltraSoundInterface;

import java.util.List;

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

    List<LidarPoint> getLidarDetection();
}
