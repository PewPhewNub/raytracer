package main.java.app;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import main.java.app.PixelBufferScreen;
import main.java.app.Screen;
import main.java.core.Color;
import main.java.core.Vector3;

public class RayTracerApp extends JFrame {
    private final Screen screen;
    private final JLabel canvas;
    private Vector3 sphereCenter;
    private double sphereRadius;
    private Color sphereColor;

    public RayTracerApp(int width, int height) {
        this.screen = new PixelBufferScreen(width, height);
        
        setTitle("Ray Tracer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        canvas = new JLabel();
        canvas.setPreferredSize(new Dimension(width, height));
        add(canvas);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        init();
    }

    private void init() {
        // Initialize a red sphere at center of screen
        sphereCenter = new Vector3(0, 0, 10000);
        sphereRadius = 100;
        sphereColor = new Color(255, 0, 0); // red
    }

    public void render() {
        // Clear screen
        screen.clear(new Color(0, 0, 0)); // black background
        
        // Draw red sphere
        drawSphere();
        
        updateDisplay();
    }

    private void drawSphere() {
        double focalLength = 500; // distance from camera to projection plane
        
        for (int y = 0; y < screen.getHeight(); y++) {
            for (int x = 0; x < screen.getWidth(); x++) {
                // Convert screen pixel to world ray
                double screenX = x - screen.getWidth() / 2.0;
                double screenY = y - screen.getHeight() / 2.0;
                
                // Check intersection with sphere
                // Ray: P = (screenX, screenY, 0) + t*(0, 0, 1)
                double dx = screenX - sphereCenter.x;
                double dy = screenY - sphereCenter.y;
                double dz = -sphereCenter.z; // ray direction z component
                
                // Sphere intersection: (dx + t*0)^2 + (dy + t*0)^2 + (dz + t*1)^2 = r^2
                double a = 1; // t^2 coefficient
                double b = 2 * dz; // t coefficient
                double c = dx * dx + dy * dy + dz * dz - sphereRadius * sphereRadius;
                
                double discriminant = b * b - 4 * a * c;
                if (discriminant >= 0) {
                    screen.setPixel(x, y, sphereColor);
                }
            }
        }
    }

    public void setPixel(int x, int y, Color color) {
        screen.setPixel(x, y, color);
    }

    private void updateDisplay() {
        byte[] rgba = screen.getRGBABytes();
        BufferedImage img = new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        int[] pixels = new int[screen.getWidth() * screen.getHeight()];
        for (int i = 0; i < rgba.length; i += 4) {
            int r = rgba[i] & 0xFF;
            int g = rgba[i + 1] & 0xFF;
            int b = rgba[i + 2] & 0xFF;
            pixels[i / 4] = (r << 16) | (g << 8) | b;
        }
        
        img.setRGB(0, 0, screen.getWidth(), screen.getHeight(), pixels, 0, screen.getWidth());
        canvas.setIcon(new ImageIcon(img));
    }

    public static void main(String[] args) {
        RayTracerApp app = new RayTracerApp(800, 600);
        app.render();
    }
}