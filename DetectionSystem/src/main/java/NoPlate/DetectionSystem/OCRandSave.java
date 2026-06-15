package NoPlate.DetectionSystem;
import net.sourceforge.tess4j.Tesseract;
import java.io.File;
import net.sourceforge.tess4j.Tesseract;

public class OCRandSave {

    public static void main(String[] args) 
    		throws Exception {
    	Platepng.detectPlate();

        Tesseract t = new Tesseract();

        t.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");

        File image = new File("D:\\New File\\plate.png");

        String plate = t.doOCR(image);

        plate = plate.replaceAll("[^A-Z0-9]", "");

        System.out.println("Detected Plate : " + plate);

        SavePlate.save(plate);
    }
}