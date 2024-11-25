package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalFileSystemImageManagerTest {

    private LocalFileSystemImageManager imageManager;

    @BeforeEach
    void setUp() {
        imageManager = new LocalFileSystemImageManager();
    }

    @Test
    void testLoadImageWithNullFile() {
        assertThrows(IllegalArgumentException.class, () -> imageManager.loadImage(null),
            "Loading image with null file should throw an exception");
    }

    @Test
    void testLoadImageWithInvalidFile() {
        File invalidFile = new File("nonexistent.jpg");

        assertThrows(IllegalArgumentException.class, () -> imageManager.loadImage(invalidFile),
            "Loading image with a nonexistent file should throw an exception");
    }

    @Test
    void testLoadImagesFromDirectoryWithValidDirectory() throws IOException {
        File tempDir = Files.createTempDirectory("images").toFile();
        File image1 = createTempImage("image1.jpg", tempDir);
        File image2 = createTempImage("image2.png", tempDir);

        List<BufferedImage> images = imageManager.loadImagesFromDirectory(tempDir);

        assertNotNull(images, "Loaded images list should not be null");
        assertEquals(2, images.size(), "Directory should contain 2 valid images");
    }

    private File createTempImage(String fileName) throws IOException {
        File tempImage = File.createTempFile(fileName, null);
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(image, "jpg", tempImage);
        return tempImage;
    }

    private File createTempImage(String fileName, File directory) throws IOException {
        File tempImage = new File(directory, fileName);
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(image, "jpg", tempImage);
        return tempImage;
    }

    @Test
    void testSaveImageWithNullImage() {
        File tempImage = new File("output.png");

        assertThrows(IllegalArgumentException.class, () -> imageManager.saveImage(null, tempImage),
            "Saving a null image should throw an exception");
    }

    @Test
    void testSaveImageWithNullFile() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

        assertThrows(IllegalArgumentException.class, () -> imageManager.saveImage(image, null),
            "Saving an image to a null file should throw an exception");
    }

    @Test
    void testLoadImagesFromDirectoryWithInvalidDirectory() {
        assertThrows(IllegalArgumentException.class, () -> imageManager.loadImagesFromDirectory(null),
            "Loading images from a null directory should throw an exception");

        File invalidDir = new File("nonexistentDirectory");

        assertThrows(IllegalArgumentException.class, () -> imageManager.loadImagesFromDirectory(invalidDir),
            "Loading images from a nonexistent directory should throw an exception");
    }

    @Test
    void testSaveImageWithValidData() throws IOException {
        File tempImage = File.createTempFile("savedImage", ".png");
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

        imageManager.saveImage(image, tempImage);

        assertTrue(tempImage.exists(), "Saved image file should exist");
    }

    @Test
    void testLoadImageWithValidFile() throws IOException {
        File tempImage = createTempImage("test.jpg");

        BufferedImage image = imageManager.loadImage(tempImage);

        assertNotNull(image, "Loaded image should not be null");
    }
}
