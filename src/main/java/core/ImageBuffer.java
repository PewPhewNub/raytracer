package main.java.core;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageBuffer {
    private final int width;
    private final int height;
    private final WritableImage image;
    private final PixelWriter writer;

    public ImageBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new WritableImage(width, height);
        this.writer = image.getPixelWriter();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public WritableImage getImage() { return image; }

    // color values expected 0–1
    public void setPixel(int x, int y, double r, double g, double b) {
        r = clamp(r);
        g = clamp(g);
        b = clamp(b);
        writer.setColor(x, y, new Color(r, g, b, 1.0));
    }

    private double clamp(double v) {
        return Math.max(0.0, Math.min(1.0, v));
    }
}
