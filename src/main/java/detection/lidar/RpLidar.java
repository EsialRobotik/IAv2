package detection.lidar;

//import ev3dev.sensors.slamtec.RPLidarA1;
//import ev3dev.sensors.slamtec.RPLidarA1ServiceException;
//import ev3dev.sensors.slamtec.model.Scan;

import java.util.ArrayList;
import java.util.List;

public class RpLidar implements LidarInterface {

//    private RPLidarA1 lidar;

    public RpLidar(String lidarPort) {
//        this.lidar = new RPLidarA1(lidarPort);
//        this.init();
    }

    @Override
    public void init() {
//        try {
//            this.lidar.init();
//        } catch (RPLidarA1ServiceException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void stop() {
//        try {
//            this.lidar.close();
//        } catch (RPLidarA1ServiceException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public List<LidarPoint> getMeasures() {
//        final Scan scan;
        List<LidarPoint> points = new ArrayList<>();
//        try {
//            scan = lidar.scan();
//            scan.getDistances()
//                    .stream()
//                    .filter((measure) -> measure.getQuality() > 5)
//                    .filter((measure) -> measure.getDistance() > 0 && measure.getDistance() <= 100)
//                    .forEach((measure) -> {
//                        points.add(new LidarPoint(Math.toRadians(measure.getAngle()), (int)measure.getDistance()*10));
//                    });
//        } catch (RPLidarA1ServiceException e) {
//            e.printStackTrace();
//        }
        return points;
    }

//    public static void main(String[] args) throws InterruptedException, RPLidarA1ServiceException {
//        RpLidar lidar = new RpLidar("/dev/ttyUSB0");
//        while (true) {
//            List<LidarPoint> points = lidar.getMeasures();
//            for (LidarPoint point : points) {
//                System.out.println("Angle : " + Math.toDegrees(point.angle) + " - Distance en mm : " + point.distance);
//            }
//            System.out.println("###############################");
//        }
//    }
}
