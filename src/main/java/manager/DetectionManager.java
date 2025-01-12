package manager;

import asserv.Position;
import detection.Lidar;
import detection.SrfDetectionNetworkInterface;
import pathfinding.table.Point;

import java.util.List;

/**
 * Created by icule on 12/05/17.
 */
public class DetectionManager {
    private UltraSoundManager ultraSoundManager;
    private SrfDetectionNetworkInterface detectionInterface;
    private Lidar lidar;
    
    public DetectionManager(
        SrfDetectionNetworkInterface detectionInterface,
        UltraSoundManager ultraSoundManager,
        Lidar lidar
    ) {
        this.ultraSoundManager = ultraSoundManager;
        this.detectionInterface = detectionInterface;
        this.lidar = lidar;
    }

    public void startDetection() {
        this.ultraSoundManager.start();
    }

    public void stopDetection() {
        this.ultraSoundManager.stop();
    }

    public void startDetectionDebug() {
        this.ultraSoundManager.startDebug();
    }

    public boolean emergencyDetection() {
        return this.ultraSoundManager.hasBeenDetected();
    }

    public boolean[] getEmergencyDetectionMap() {
        return this.ultraSoundManager.getDetectionResult();
    }

    public Position[] getEmergencyDetectionPositions() {
        return this.ultraSoundManager.getDetectionPosition();
    }

    public boolean isEmergencyDetectionFront()
    {
        boolean[] detected = this.getEmergencyDetectionMap();
        return detected[0] || detected[1] || detected[2];
    }

    public boolean isEmergencyDetectionBack()
    {
        boolean[] detected = this.getEmergencyDetectionMap();
        return detected[3];
    }

    public List<Point> getLongRangeDetection()
    {
        return this.lidar.getDetectedPoints();
    }
}
