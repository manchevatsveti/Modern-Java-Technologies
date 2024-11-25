package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LuminosityGrayscale implements GrayscaleAlgorithm {

    private static final double RED_LUMINOSITY_FACTOR = 0.21;
    private static final double GREEN_LUMINOSITY_FACTOR = 0.72;
    private static final double BLUE_LUMINOSITY_FACTOR = 0.07;

    public LuminosityGrayscale() {
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int gray = (int) ( RED_LUMINOSITY_FACTOR * color.getRed() + GREEN_LUMINOSITY_FACTOR * color.getGreen() + BLUE_LUMINOSITY_FACTOR* color.getBlue());
                Color grayColor = new Color(gray, gray, gray);
                grayscaleImage.setRGB(x, y, grayColor.getRGB());
            }
        }

        return grayscaleImage;
    }
}
