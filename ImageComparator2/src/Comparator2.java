import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by marcinja on 24/11/2014.
 */
public class Comparator2 {
    public static void main(String args[])
    {
        boolean result = compareImages("8.jpg", "9.jpg", 4.0);
        System.out.print(result);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
//METODY


    public static boolean compareImages(String filePath1, String filePath2, double margin) {

        BufferedImage loadedImage1 = null;
        BufferedImage loadedImage2 = null;
        try {
            loadedImage1 = loadImage(filePath1);
            loadedImage2 = loadImage(filePath2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double difference = calculateImageDifferenceRatio(loadedImage1, loadedImage2);

        if (difference > margin) return false;
        else {
            return true;
        }
    }


    private static BufferedImage loadImage(String filepath) throws IOException {
        return ImageIO.read(new File(filepath));
    }

    private static double calculateImageDifferenceRatio(BufferedImage img1, BufferedImage img2) {

        long width1 = img1.getWidth();
        long width2 = img2.getWidth();


        long height1 = img1.getHeight();
        long height2 = img2.getHeight();

        if(isTheSameDim(width1, width2, height1, height2)) {
            double diff = 0;
            for (int i = 0; i < width1; i++) {
                for (int j = 0; j < height1; j++) {
                    int rgb1 = img1.getRGB(i, j);
                    int rgb2 = img2.getRGB(i, j);
                    int r1 = (rgb1 >> 16) & 0xff;
                    int g1 = (rgb1 >> 8) & 0xff;
                    int b1 = (rgb1) & 0xff;
                    int r2 = (rgb2 >> 16) & 0xff;
                    int g2 = (rgb2 >> 8) & 0xff;
                    int b2 = (rgb2) & 0xff;
                    diff += Math.abs(r1 - r2);
                    diff += Math.abs(g1 - g2);
                    diff += Math.abs(b1 - b2);
                }
            }
            double n = width1 * height1 * 3;
            double p = diff / n / 255.0;

            System.out.println("diff percent: " + (p * 100.0));
            return p * 100.0;
        }else{
            System.out.print("Dimensions mismatch ");
            return 300;
        }
    }

    private static boolean isTheSameDim(long width1, long width2, long height1, long height2) {
        return !((width1 != width2) || (height1 != height2));
    }

}
