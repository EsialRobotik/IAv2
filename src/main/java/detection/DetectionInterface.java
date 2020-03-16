package detection;

import detection.lidar.LidarPoint;

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

    List<LidarPoint> getLidarDetection();
}
