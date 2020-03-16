package detection.lidar;

import java.util.List;

public interface LidarInterface {
    interface LidarInterfaceFactory {
        LidarInterface create(String lidarPort);
    }

    void init();
    void stop();

    List<LidarPoint> getMeasures();
}
