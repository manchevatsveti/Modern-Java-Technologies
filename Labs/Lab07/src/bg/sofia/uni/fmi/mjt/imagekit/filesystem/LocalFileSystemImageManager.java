package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileSystemImageManager implements FileSystemImageManager {

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        if (imageFile == null || !imageFile.isFile()) {
            throw new IllegalArgumentException("Invalid file!");
        }
        return ImageIO.read(imageFile);
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        if (imagesDirectory == null || !imagesDirectory.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory!");
        }

        List<BufferedImage> images = new ArrayList<>();
        for (File file : imagesDirectory.listFiles()) {
            if (file.isFile() && isSupportedFormat(file)) {
                images.add(ImageIO.read(file));
            }
        }
        return images;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        if (image == null || imageFile == null) {
            throw new IllegalArgumentException("Invalid input!");
        }

        String format = getFileExtension(imageFile.getName());
        ImageIO.write(image, format, imageFile);
    }

    private boolean isSupportedFormat(File file) {
        String extension = getFileExtension(file.getName()).toLowerCase();
        return extension.equals("jpg") || extension.equals("png") || extension.equals("bmp");
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
