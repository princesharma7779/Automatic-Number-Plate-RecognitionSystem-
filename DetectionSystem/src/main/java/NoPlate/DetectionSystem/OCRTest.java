package NoPlate.DetectionSystem;
import java.io.File;
import net.sourceforge.tess4j.Tesseract;
 public class OCRTest{
    public static void main(String[] args) {
        try {
            Tesseract t = new Tesseract();

            t.setDatapath( "C:\\Program Files\\Tesseract-OCR\\tessdata"  );

            t.setLanguage("eng");
            t.setPageSegMode(7);
            t.setTessVariable( "tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
            File image =new File("D:\\New File\\plate.png");
            String text = t.doOCR(image);

            text = text.replaceAll("[^A-Z0-9]", "");

            System.out.println(
                "Detected Plate : " + text);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
