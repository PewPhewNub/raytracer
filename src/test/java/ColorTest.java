package test.java;

import main.java.math.Color;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {
    @Test
    public void testColorClamping() {
        Color c1 = new Color(-0.5, 0.5, 1.5, 2.0);
        assertEquals(0.0, c1.r, 1e-9);
        assertEquals(0.5, c1.g, 1e-9);
        assertEquals(1.0, c1.b, 1e-9);
        assertEquals(1.0, c1.a, 1e-9);

        Color c2 = new Color(0.2, 0.8);
        assertEquals(0.2, c2.r, 1e-9);
        assertEquals(0.8, c2.g, 1e-9);
        assertEquals(0.0, c2.b, 1e-9);
        assertEquals(1.0, c2.a, 1e-9);
    }

    @Test
    public void testToFromARGB() {
        Color c = new Color(0.1, 0.2, 0.3, 0.4);
        int argb = c.toARGB();
        Color c2 = Color.fromARGB(argb);
        assertEquals(c.r, c2.r, 1e-3);
        assertEquals(c.g, c2.g, 1e-3);
        assertEquals(c.b, c2.b, 1e-3);
        assertEquals(c.a, c2.a, 1e-3);
    }

    @Test
    public void testToString() {
        Color c = new Color(0.123456, 0.654321, 0.111111, 0.999999);
        String s = c.toString();
        assertEquals("Color(0.123, 0.654, 0.111, 1.000)", s);
    }
    
    
}
