package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm  {

    private static final int[][] SOBEL_KERNEL_X = {
        {-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}
    };

    private static final int[][] SOBEL_KERNEL_Y = {
        {-1, -2, -1},
        {0,  0,  0},
        {1,  2,  1}
    };
    private static final int MAX_COLOR_INTENSITY = 255;

    private final ImageAlgorithm grayscaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage grayscaleImage = grayscaleAlgorithm.process(image);
        int width = grayscaleImage.getWidth();
        int height = grayscaleImage.getHeight();
        BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int gX = calculateGradient(grayscaleImage, x, y, SOBEL_KERNEL_X);
                int gY = calculateGradient(grayscaleImage, x, y, SOBEL_KERNEL_Y);
                int edgeStrength = calculateEdgeStrength(gX, gY);
                applyEdgeStrength(edgeImage, x, y, edgeStrength);
            }
        }

        return edgeImage;
    }

    private int calculateGradient(BufferedImage image, int x, int y, int[][] kernel) {
        int gradient = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int grayValue = new Color(image.getRGB(x + j, y + i)).getRed();
                gradient += kernel[i + 1][j + 1] * grayValue;
            }
        }
        return gradient;
    }

    private int calculateEdgeStrength(int gX, int gY) {
        return Math.min(MAX_COLOR_INTENSITY, (int) Math.sqrt(gX * gX + gY * gY));
    }

    private void applyEdgeStrength(BufferedImage edgeImage, int x, int y, int edgeStrength) {
        Color edgeColor = new Color(edgeStrength, edgeStrength, edgeStrength);
        edgeImage.setRGB(x, y, edgeColor.getRGB());
    }

}
