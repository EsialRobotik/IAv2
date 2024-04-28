package manager;

import asserv.Position;
import detection.DetectionInterface;

/**
 * Created by icule on 12/05/17.
 */
public class DetectionManager {
    private UltraSoundManager ultraSoundManager;
    private DetectionInterface detectionInterface;
    
    public DetectionManager(DetectionInterface detectionInterface, UltraSoundManager ultraSoundManager) {
        this.ultraSoundManager = ultraSoundManager;
        this.detectionInterface = detectionInterface;
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
}
