package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.math.Sphere3;
import main.java.math.Ray3;
import main.java.math.Vector3;

public class Ray3Test {
    @Test
    public void testRayCreation() {
        Sphere3 sphere = new Sphere3(new Vector3(0, 0, 0), 1);
        Ray3 ray1 = new Ray3(new Vector3(0, 0, -5), new Vector3(0, 0, 1));
        Ray3 ray2 = new Ray3(new Vector3(0, 1, -5), new Vector3(0, 0, 1));
        Ray3 ray3 = new Ray3(new Vector3(0, 2, -5), new Vector3(0, 0, 1));
        List<Vector3> intersections1 = ray1.intersectSphere(sphere);
        List<Vector3> intersections2 = ray2.intersectSphere(sphere);
        List<Vector3> intersections3 = ray3.intersectSphere(sphere);

        assertEquals(intersections1.get(0), new Vector3(0, 0, -1));
        assertEquals(intersections1.get(1), new Vector3(0, 0, 1));
        assertEquals(intersections2.get(0), new Vector3(0, 1, 0));
        assertEquals(intersections2.get(1), new Vector3(0, 1, 0));
        assertTrue(intersections3.isEmpty());
    }
}
