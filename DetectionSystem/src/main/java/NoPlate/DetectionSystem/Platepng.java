package NoPlate.DetectionSystem;
import java.util.ArrayList;
import java.util.List;

import nu.pattern.OpenCV;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Platepng {
    public static void detectPlate() {
 
        OpenCV.loadLocally();

        // Image Read
        Mat image = Imgcodecs.imread("D:\\IDE\\DetectionSystem\\car.png.png");

        if (image.empty()) {
            System.out.println("Image Not Found");
            return;
        }

        // Convert to Gray
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        // Blur
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Edge Detection
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 100, 200);

        // Morphological Close
        Mat kernel = Imgproc.getStructuringElement(
                Imgproc.MORPH_RECT,
                new Size(17, 3));

        Imgproc.morphologyEx(
                edges,
                edges,
                Imgproc.MORPH_CLOSE,
                kernel);

        // Find Contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(
                edges,
                contours,
                hierarchy,
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE);

        System.out.println("Contours Found : " + contours.size());

        Rect plateRect = null;

        for (MatOfPoint contour : contours) {

            Rect rect = Imgproc.boundingRect(contour);

            System.out.println(
                    "X=" + rect.x +
                    " Y=" + rect.y +
                    " W=" + rect.width +
                    " H=" + rect.height);

            double ratio =
                    (double) rect.width / rect.height;

            // Number Plate Conditions
            if (rect.width > 250 &&
                rect.width < 500 &&
                rect.height > 50 &&
                rect.height < 120 &&
                ratio > 3 &&
                ratio < 6) {

                plateRect = rect;

                System.out.println("\nPlate Found");
                System.out.println("X = " + rect.x);
                System.out.println("Y = " + rect.y);
                System.out.println("Width = " + rect.width);
                System.out.println("Height = " + rect.height);

                break;
            }
        }

        if (plateRect != null) {

            // Draw Rectangle
            Imgproc.rectangle(
                    image,
                    new Point(plateRect.x, plateRect.y),
                    new Point(
                            plateRect.x + plateRect.width,
                            plateRect.y + plateRect.height),
                    new Scalar(0, 255, 0),
                    3);

            // Save Image With Rectangle
            Imgcodecs.imwrite(
                    "D:\\New File\\detected_plate.png",
                    image);

            // Crop Plate
            Mat plate =
                    new Mat(image, plateRect);

            Imgcodecs.imwrite(
                    "D:\\New File\\plate.png",
                    plate);

            System.out.println("Plate Saved Successfully");
        }
        else {
            System.out.println("Number Plate Not Found");
        }
    }
}