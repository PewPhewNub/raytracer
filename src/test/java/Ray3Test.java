package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.math.Sphere3;
import main.java.logic.Intersection;
import main.java.logic.Intersections;
import main.java.math.Matrix4;
import main.java.math.Ray3;
import main.java.math.Vector3;

public class Ray3Test {
    @Test
    public void testRayIntersection() {
        Sphere3 sphere = new Sphere3(new Vector3(0, 0, 0), 1);
        Ray3 ray1 = new Ray3(new Vector3(0, 0, -5), new Vector3(0, 0, 1));
        Ray3 ray2 = new Ray3(new Vector3(0, 1, -5), new Vector3(0, 0, 1));
        Ray3 ray3 = new Ray3(new Vector3(0, 2, -5), new Vector3(0, 0, 1));
        Ray3 ray4 = new Ray3(new Vector3(0, 0, 0), new Vector3(0, 0, 1));
        Intersections intersections1 = ray1.intersectSphere(sphere);
        Intersections intersections2 = ray2.intersectSphere(sphere);
        Intersections intersections3 = ray3.intersectSphere(sphere);
        Intersections intersections4 = ray4.intersectSphere(sphere);    

        assertEquals(intersections1.list.get(0).t, 4.0, 1e-6);
        assertEquals(intersections1.list.get(1).t, 6.0, 1e-6);
        assertEquals(intersections2.list.get(0).t, 5.0, 1e-6);
        assertEquals(intersections2.list.get(1).t, 5.0, 1e-6);
        assertTrue(Double.isNaN(intersections3.list.get(0).t));
        assertTrue(Double.isNaN(intersections3.list.get(1).t));
        assertEquals(intersections4.list.get(0).t, -1.0, 1e-6);
        assertEquals(intersections4.list.get(1).t, 1.0, 1e-6);
    }
    @Test
    public void testRayTransformations(){
        Ray3 ray = new Ray3(new Vector3(1,2,3), new Vector3(0,1,0));
        Matrix4 translation = new Matrix4(new double[]{
            1,0,0,3,
            0,1,0,4,
            0,0,1,5,
            0,0,0,1
        });

        Matrix4 scaling = new Matrix4(new double[]{
            2,0,0,0,
            0,3,0,0,
            0,0,4,0,
            0,0,0,1
        });

        Ray3 rayTranslated = ray.transform(translation);
        Ray3 rayScaled = ray.transform(scaling);

        assertEquals(new Vector3(4,6,8), rayTranslated.getOrigin());
        assertEquals(new Vector3(0,1,0), rayTranslated.getDirection());
        assertEquals(new Vector3(2,6,12), rayScaled.getOrigin());
        assertEquals(new Vector3(0,1,0), rayScaled.getDirection());
    }

    @Test
    public void testTransformIntersections(){
        Ray3 ray = new Ray3(new Vector3(0, 0, -5), new Vector3(0, 0, 1));
        Sphere3 sphere = new Sphere3(new Vector3(0, 0, 0), 1);
    }
}
