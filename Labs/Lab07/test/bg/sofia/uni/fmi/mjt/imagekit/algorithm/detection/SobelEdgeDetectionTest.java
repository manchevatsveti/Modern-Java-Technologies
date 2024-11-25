package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.GrayscaleAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class SobelEdgeDetectionTest {

    private GrayscaleAlgorithm mockGrayscaleAlgorithm;
    private SobelEdgeDetection sobelEdgeDetection;

    @BeforeEach
    void setUp() {
        mockGrayscaleAlgorithm = Mockito.mock(GrayscaleAlgorithm.class);
        sobelEdgeDetection = new SobelEdgeDetection(mockGrayscaleAlgorithm);
    }

    @Test
    void testSobelEdgeDetectionOnBlankImage() {
        BufferedImage blankGrayscaleImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
        Mockito.when(mockGrayscaleAlgorithm.process(any(BufferedImage.class))).thenReturn(blankGrayscaleImage);

        BufferedImage edgeImage = sobelEdgeDetection.process(blankGrayscaleImage);

        assertNotNull(edgeImage, "Edge image should not be null");
        for (int y = 0; y < edgeImage.getHeight(); y++) {
            for (int x = 0; x < edgeImage.getWidth(); x++) {
                int color = new Color(edgeImage.getRGB(x, y)).getRed();
                assertEquals(0, color, "Edge detection on a blank image should produce a black image");
            }
        }
    }
}
