import bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection.SobelEdgeDetection;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.LocalFileSystemImageManager;

import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        final String inputFilePath = "kitten.png";
        final String grayscaleOutputPath = "output-grayscale_kitten.png";
        final String edgeDetectedOutputPath = "output-edge-detected_kitten.png";

        LocalFileSystemImageManager fileManager = new LocalFileSystemImageManager();
        LuminosityGrayscale grayscaleAlgorithm = new LuminosityGrayscale();
        SobelEdgeDetection edgeDetectionAlgorithm = new SobelEdgeDetection(grayscaleAlgorithm);

        try {
            System.out.println("Loading image from: " + inputFilePath);
            BufferedImage originalImage = fileManager.loadImage(new File(inputFilePath));

            // Apply the grayscale algorithm
            System.out.println("Converting image to grayscale...");
            BufferedImage grayscaleImage = grayscaleAlgorithm.process(originalImage);
            fileManager.saveImage(grayscaleImage, new File(grayscaleOutputPath));
            System.out.println("Grayscale image saved to: " + grayscaleOutputPath);

            // Apply the edge detection algorithm
            System.out.println("Applying edge detection...");
            BufferedImage edgeDetectedImage = edgeDetectionAlgorithm.process(originalImage);
            fileManager.saveImage(edgeDetectedImage, new File(edgeDetectedOutputPath));
            System.out.println("Edge-detected image saved to: " + edgeDetectedOutputPath);

        } catch (Exception e) {
            System.err.println("An error occurred during image processing: " + e.getMessage());
        }
    }
}
