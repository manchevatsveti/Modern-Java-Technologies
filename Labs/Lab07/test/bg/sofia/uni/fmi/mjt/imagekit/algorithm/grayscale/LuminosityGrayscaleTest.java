package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LuminosityGrayscaleTest {

    @Test
    void testProcessConvertsToGrayscaleCorrectly() {LuminosityGrayscale grayscaleAlgorithm = new LuminosityGrayscale();

        // Create a small test image
        BufferedImage testImage = new BufferedImage(3, 1, BufferedImage.TYPE_INT_RGB);
        testImage.setRGB(0, 0, new Color(100, 150, 200).getRGB()); // Pixel 1
        testImage.setRGB(1, 0, new Color(50, 100, 150).getRGB());  // Pixel 2
        testImage.setRGB(2, 0, new Color(200, 50, 100).getRGB());  // Pixel 3

        BufferedImage result = grayscaleAlgorithm.process(testImage);

        // Calculate expected values using the same rounding logic
        int expectedGray1 = (int) Math.round(0.21 * 100 + 0.72 * 150 + 0.07 * 200); // 143
        int expectedGray2 = (int) Math.round(0.21 * 50 + 0.72 * 100 + 0.07 * 150);  // 93
        int expectedGray3 = (int) Math.round(0.21 * 200 + 0.72 * 50 + 0.07 * 100);  // 93

        // Validate the grayscale conversion
        assertEquals(expectedGray1, new Color(result.getRGB(0, 0)).getRed());
        assertEquals(expectedGray2, new Color(result.getRGB(1, 0)).getRed());
        assertEquals(expectedGray3, new Color(result.getRGB(2, 0)).getRed());
    }
}
