package detection;

import api.gpio.GPioPair;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import detection.lidar.LidarPoint;
import detection.ultrasound.SRF04;
import detection.ultrasound.SRF08;
import detection.ultrasound.SRF08Config;
import detection.ultrasound.UltraSoundInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icule on 12/05/17.
 */
public class DetectionInterfaceImpl implements DetectionInterface{

    private List<UltraSoundInterface> srfList;

    public DetectionInterfaceImpl(JsonObject ultrasoundObject) {
        if (ultrasoundObject.get("type").getAsString().equals("srf04")) {
            List<GPioPair> gPioPairList = new ArrayList<>();
            JsonArray gpioPairArray = ultrasoundObject.getAsJsonArray("gpioList");
            for (JsonElement e : gpioPairArray) {
                JsonObject temp = e.getAsJsonObject();
                gPioPairList.add(new GPioPair(temp.get("in").getAsInt(), temp.get("out").getAsInt()));
            }

            srfList = new ArrayList<UltraSoundInterface>();
            for (GPioPair pair : gPioPairList) {
                srfList.add(new SRF04(pair.gpio_in, pair.gpio_out));
            }

            System.out.println("Creation done : SRF04");

        } else if (ultrasoundObject.get("type").getAsString().equals("srf08")) {
            System.out.println("SRF08");
            List<SRF08Config> srf08ConfList = new ArrayList<>();
            JsonArray srf08ConfArray = ultrasoundObject.getAsJsonArray("i2cConfigList");
            for (JsonElement e : srf08ConfArray) {
                JsonObject temp = e.getAsJsonObject();
                srf08ConfList.add(new SRF08Config(temp.get("address").getAsInt(),temp.get("maxAnalogGain").getAsInt(),temp.get("range").getAsInt(),temp.get("desc").getAsString()));
            }

            srfList = new ArrayList<UltraSoundInterface>();
            for (SRF08Config config : srf08ConfList) {
                srfList.add(new SRF08(config));
            }

            System.out.println("Creation done : SRF08");

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
