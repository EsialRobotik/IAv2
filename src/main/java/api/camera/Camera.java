package api.camera;

import api.log.LoggerFactory;
import org.apache.logging.log4j.Logger;
import uk.co.caprica.picam.*;
import uk.co.caprica.picam.enums.AutomaticWhiteBalanceMode;
import uk.co.caprica.picam.enums.Encoding;

import java.io.File;

public class Camera {

    /**
     * PiCamera
     */
    private uk.co.caprica.picam.Camera camera;

    /**
     * Logger
     */
    protected Logger logger = null;

    public Camera() {
        logger = LoggerFactory.getLogger(Camera.class);
        logger.info("Initialize camera");
        try {
            PicamNativeLibrary.installLibrary("/home/pi/picam");
            logger.info("Camera library loaded");
            this.camera = new uk.co.caprica.picam.Camera(CameraConfiguration.cameraConfiguration()
                    .width(1920)
                    .height(1080)
                    .automaticWhiteBalance(AutomaticWhiteBalanceMode.SUNLIGHT)
                    .encoding(Encoding.JPEG)
                    .quality(85));
            logger.info("Camera is ready");
        } catch (NativeLibraryException | CameraException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Take a picture with the camera
     * @param fileName Image file name, must end with jpg
     * @return Picture file
     */
    public File takePicture(String fileName) {
        try {
            logger.info("Take picture in " + fileName);
            FilePictureCaptureHandler handler = new FilePictureCaptureHandler(new File(fileName));
            logger.info("Create handler");
            camera.takePicture(handler);
            logger.info("camera.takePicture");
            return handler.result();
        } catch (CaptureFailedException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
