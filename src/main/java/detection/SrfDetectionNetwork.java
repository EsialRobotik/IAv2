package detection;

import api.gpio.GPioPair;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import detection.ultrasound.SRF04;
import detection.ultrasound.SRF08;
import detection.ultrasound.SRF08Config;
import detection.ultrasound.SRFInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icule on 12/05/17.
 */
public class SrfDetectionNetwork implements SrfDetectionNetworkInterface {

    private List<SRFInterface> srfList;

    public SrfDetectionNetwork(JsonObject ultrasoundObject) {
        srfList = new ArrayList<>();
        if (ultrasoundObject.get("type").getAsString().equals("srf04")) {
            JsonArray gpioPairArray = ultrasoundObject.getAsJsonArray("gpioList");
            for (JsonElement e : gpioPairArray) {
                JsonObject temp = e.getAsJsonObject();
                GPioPair pair = new GPioPair(temp.get("in").getAsInt(), temp.get("out").getAsInt());
                srfList.add(new SRF04(pair, temp.get("x").getAsInt(), temp.get("y").getAsInt(), temp.get("angle").getAsInt(), temp.get("threshold").getAsInt()));
            }
            System.out.println("Creation done : SRF04");

        } else if (ultrasoundObject.get("type").getAsString().equals("srf08")) {
            System.out.println("SRF08");
            List<SRF08Config> srf08ConfList = new ArrayList<>();
            JsonArray srf08ConfArray = ultrasoundObject.getAsJsonArray("i2cConfigList");
            for (JsonElement e : srf08ConfArray) {
                JsonObject temp = e.getAsJsonObject();
                srf08ConfList.add(new SRF08Config(
                        temp.get("address").getAsInt(),
                        temp.get("maxAnalogGain").getAsInt(),
                        temp.get("range").getAsInt(),
                        temp.get("desc").getAsString(),
                        temp.get("x").getAsInt(),
                        temp.get("y").getAsInt(),
                        temp.get("angle").getAsInt(),
                        temp.get("threshold").getAsInt()
                ));
            }
            for (SRF08Config config : srf08ConfList) {
                srfList.add(new SRF08(config));
            }
            System.out.println("Creation done : SRF08");
        }
    }

    @Override
    public SRFInterface getUltrasoundFrontLeft() {
        return this.srfList.get(0);
    }

    @Override
    public SRFInterface getUltrasoundFront() {
        return this.srfList.get(1);
    }

    @Override
    public SRFInterface getUltrasoundFrontRight() {
        return this.srfList.get(srfList.size() == 4 ? 2 : 1);
    }

    @Override
    public SRFInterface getUltrasoundBack() {
        return this.srfList.get(srfList.size() == 4 ? 3 : 2);
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
}
