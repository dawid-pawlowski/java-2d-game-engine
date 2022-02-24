package core;

import media.Image;

import java.util.concurrent.ThreadLocalRandom;

public class Renderer {
    private final int width;
    private final int height;
    private final int[] pixels;
    private int fillColor;

    public Renderer(int width, int height, int[] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public void drawRect(int offsetX, int offsetY, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setPixel(x + offsetX, y + offsetY, fillColor);
            }
        }
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public void drawImage(Image image, int offsetX, int offsetY, int x0, int x1, int y0, int y1) {
        if (x0 < 0 || x1 > image.getWidth() || y0 < 0 || y0 > image.getHeight()) return;
        if (offsetX < -image.getWidth() || offsetX >= width) return;
        if (offsetY < -image.getHeight() || offsetY >= height) return;

        int startX = x0;
        int startY = y0;
        int endX = x1;
        int endY = y1;

        if (offsetX < 0) startX -= offsetX;
        if (offsetY < 0) startY -= offsetY;

        if (endX + offsetX >= width) endX -= endX + offsetX - width;
        if (endY + offsetY >= height) endY -= endY + offsetY - height;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                setPixel(x + offsetX, y + offsetY, image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }

    private void setPixel(int x, int y, int value) {
        if (x < 0 || x >= width || y < 0 || y >= height || ((value >> 24) & 0xFF) == 0) {
            return;
        }
        pixels[x + y * width] = value;
    }

    private void setPixel(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        pixels[x + y * width] = fillColor;
    }

    public int getRandomColor() {
        return getArbgColor(255, ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256));
    }

    private int getArbgColor(int alpha, int red, int green, int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xFFFFFF;
        }
    }
}
