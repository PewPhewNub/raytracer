package main.java.math;

public class Color {
    public final double r;
    public final double g;
    public final double b;
    public final double a;

    public Color(double r, double g) {
        this(r, g, 0.0, 1.0);
    }

    public Color(double r, double g, double b) {
        this(r, g, b, 1.0);
    }

    public Color(double r, double g, double b, double a) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
        this.a = clamp(a);
    }

    private static double clamp(double v) {
        if (v <= 0.0) return 0.0;
        if (v >= 1.0) return 1.0;
        return v;
    }

    public int toARGB() {
        int A = (int) Math.round(a * 255.0) & 0xFF;
        int R = (int) Math.round(r * 255.0) & 0xFF;
        int G = (int) Math.round(g * 255.0) & 0xFF;
        int B = (int) Math.round(b * 255.0) & 0xFF;
        return (A << 24) | (R << 16) | (G << 8) | B;
    }

    public static Color fromARGB(int argb) {
        int a = (argb >>> 24) & 0xFF;
        int r = (argb >>> 16) & 0xFF;
        int g = (argb >>> 8) & 0xFF;
        int b = argb & 0xFF;
        return new Color(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
    }

    @Override
    public String toString() {
        return String.format("Color(%.3f, %.3f, %.3f, %.3f)", r, g, b, a);
    }
}
