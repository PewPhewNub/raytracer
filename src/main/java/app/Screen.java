package main.java.app;

import main.java.core.Color;

public interface Screen {
    int getWidth();
    int getHeight();
    Color getPixel(int x, int y);
    void setPixel(int x, int y, Color color);
    void clear(Color color);
    /** Row-major ARGB int buffer (length = width*height). */
    int[] getIntBuffer();
    /** Returns a newly allocated RGBA byte[] (r,g,b,a) suitable for GL uploads. */
    byte[] getRGBABytes();
}

/* Package-private simple pixel buffer implementation */
class PixelBufferScreen implements Screen {
    private final int width;
    private final int height;
    private final int[] buffer; // ARGB

    public PixelBufferScreen(int width, int height) {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("Invalid dimensions");
        this.width = width;
        this.height = height;
        this.buffer = new int[width * height];
    }

    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }

    private void checkBounds(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IndexOutOfBoundsException("Pixel out of bounds: " + x + "," + y);
    }

    @Override
    public Color getPixel(int x, int y) {
        checkBounds(x, y);
        int argb = buffer[y * width + x];
        return Color.fromARGB(argb);
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        checkBounds(x, y);
        buffer[y * width + x] = (color == null) ? 0 : color.toARGB();
    }

    @Override
    public void clear(Color color) {
        int fill = (color == null) ? 0 : color.toARGB();
        for (int i = 0; i < buffer.length; i++) buffer[i] = fill;
    }

    @Override
    public int[] getIntBuffer() { return buffer; }

    @Override
    public byte[] getRGBABytes() {
        byte[] out = new byte[width * height * 4];
        int dst = 0;
        for (int i = 0; i < buffer.length; i++) {
            int argb = buffer[i];
            int a = (argb >>> 24) & 0xFF;
            int r = (argb >>> 16) & 0xFF;
            int g = (argb >>> 8) & 0xFF;
            int b = argb & 0xFF;
            out[dst++] = (byte) r;
            out[dst++] = (byte) g;
            out[dst++] = (byte) b;
            out[dst++] = (byte) a;
        }
        return out;
    }
}
