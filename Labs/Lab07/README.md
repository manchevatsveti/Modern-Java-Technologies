# Photo Edge Detector 🖼️  

This week, our task is to develop modules for an image processing library. The library will support images in various formats (JPEG, PNG, BMP) and apply different transformations to them. For the first version, the client has requested functionality to convert a color image to grayscale and detect edges in an image.  

## Example Output  

![Example car output](car-output.png)

---

## Library Overview  

The library consists of two main components:  

1. **Image Processing Algorithms**  
2. **File System Manager** for loading and saving images.  

---

## Key Interfaces and Classes  

### `ImageAlgorithm`  
The `ImageAlgorithm` interface represents an image processing algorithm.  

```java
package bg.sofia.uni.fmi.mjt.imagekit.algorithm;

import java.awt.image.BufferedImage;

/**
 * Represents an algorithm that processes images.
 */
public interface ImageAlgorithm {

    /**
     * Applies the image processing algorithm to the given image.
     *
     * @param image the image to be processed
     * @return BufferedImage the processed image of type (TYPE_INT_RGB)
     */
    BufferedImage process(BufferedImage image);
}
```

**Note:** Explore the `BufferedImage` class documentation as it will be required for creating instances and using its API. Always create instances using `BufferedImage(int width, int height, int imageType)` with `BufferedImage.TYPE_INT_RGB` as the third argument.  

---

### `GrayscaleAlgorithm`  
A marker interface for algorithms that convert an image to grayscale.  

```java
package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

public interface GrayscaleAlgorithm extends ImageAlgorithm {
}
```

---

### `EdgeDetectionAlgorithm`  
A marker interface for algorithms that detect edges.  

```java
package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

public interface EdgeDetectionAlgorithm extends ImageAlgorithm {
}
```

---

### `FileSystemImageManager`  
Manages loading and saving images from the file system.  

```java
package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * An interface for loading images from the file system.
 * The supported image formats are JPEG, PNG, and BMP.
 */
public interface FileSystemImageManager {

    BufferedImage loadImage(File imageFile) throws IOException;

    List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException;

    void saveImage(BufferedImage image, File imageFile) throws IOException;
}
```

---

## Implementations  

### `LocalFileSystemImageManager`  
A class with a public default constructor implementing `FileSystemImageManager`. It provides methods to load and save images using the `javax.imageio.ImageIO` class.  

---

### `LuminosityGrayscale`  
A class implementing `GrayscaleAlgorithm` using the **Luminance Method** to convert images to grayscale.  

Formula: `0.21 * R + 0.72 * G + 0.07 * B`  

---

### `SobelEdgeDetection`  
A class implementing `EdgeDetectionAlgorithm`. It applies the **Sobel Operator** to detect edges in an image. The constructor requires a `GrayscaleAlgorithm` instance.  

---

## Example Usage  

```java
FileSystemImageManager fsImageManager = new LocalFileSystemImageManager();

BufferedImage image = fsImageManager.loadImage(new File("kitten.png"));

ImageAlgorithm grayscaleAlgorithm = new LuminosityGrayscale();
BufferedImage grayscaleImage = grayscaleAlgorithm.process(image);

ImageAlgorithm sobelEdgeDetection = new SobelEdgeDetection(grayscaleAlgorithm);
BufferedImage edgeDetectedImage = sobelEdgeDetection.process(image);

fsImageManager.saveImage(grayscaleImage, new File("kitten-grayscale.png"));
fsImageManager.saveImage(edgeDetectedImage, new File("kitten-edge-detected.png"));
```

---

## Testing  

1. Test the library locally with sample images or your own.  
2. Create appropriate unit tests to verify the basic functionality.  
3. Focus on testing rather than achieving full code coverage.  

---

## Package Structure  

```
src
└── bg.sofia.uni.fmi.mjt.imagekit
    ├── algorithm
    │   ├── detection
    │   │   ├── EdgeDetectionAlgorithm.java
    │   │   └── SobelEdgeDetection.java
    │   ├── grayscale
    │   │   ├── GrayscaleAlgorithm.java
    │   │   └── LuminosityGrayscale.java
    │   ├── ImageAlgorithm.java
    ├── filesystem
    │   ├── FileSystemImageManager.java
    │   ├── LocalFileSystemImageManager.java
    └── (...)
test
└── bg.sofia.uni.fmi.mjt.imagekit
     └── (...)
```  

