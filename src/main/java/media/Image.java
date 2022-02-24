package media;

import javafx.scene.image.PixelReader;

import java.util.Objects;

public class Image {
    private final int width;
    private final int height;
    private final int[] pixels;

    public Image(String path) {
        javafx.scene.image.Image image = new javafx.scene.image.Image(Objects.requireNonNull(Image.class.getResourceAsStream(path)));
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        pixels = new int[width * height];

        PixelReader pixelReader = image.getPixelReader();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pixels[(x % width) + (y * width)] = pixelReader.getArgb(x, y);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }
}
