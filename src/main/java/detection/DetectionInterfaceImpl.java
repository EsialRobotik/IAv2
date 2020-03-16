package detection;

import api.gpio.GPioPair;
import detection.lidar.LidarPoint;
import detection.ultrasound.SRF04;
import detection.ultrasound.UltraSoundInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icule on 12/05/17.
 */
public class DetectionInterfaceImpl implements DetectionInterface{

    private List<UltraSoundInterface> srfList;

    public DetectionInterfaceImpl(List<GPioPair> gPioPairList, String srfType) {
        srfList = new ArrayList<UltraSoundInterface>();
        for(GPioPair pair : gPioPairList) {
            if (srfType.equals("srf04")) {
                srfList.add(new SRF04(pair.gpio_in, pair.gpio_out));
            }
        }
    }

    public void startDetection() {

    }

    public void stopDetection() {

    }

    public int getUltraSoundSensorCount() {
        return srfList.size();
    }

    public long[] ultraSoundDetection() {
        long[] res = new long[srfList.size()];
        for(int i = 0; i < srfList.size(); ++i) {
            res[i] = srfList.get(i).getMeasure();
            try {
                Thread.sleep(12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public List<LidarPoint> getLidarDetection() {
        return null;
    }
}
