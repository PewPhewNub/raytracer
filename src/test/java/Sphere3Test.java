package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import main.java.core.Matrix4;
import main.java.core.Vector3;
import main.java.world.Sphere3;

public class Sphere3Test {
    @Test
    public void testSphereTransformation(){
        Sphere3 sphere = new Sphere3(new Vector3(1,2,3), 4);

        assertEquals(new Vector3(1,2,3), sphere.getCenter());
        assertEquals(4, sphere.getRadius(), 1e-6);

        Matrix4 transform = new Matrix4(
            new double[]{
                4,0,0,0,
                0,3,0,0,
                0,0,2,0,
                0,0,0,1
            }
        );
        sphere.setTransform(transform);
        assertEquals(new Vector3(1,2,3), sphere.getCenter());
        assertEquals(4, sphere.getRadius(), 1e-6);
    }

    public void testNormals(){
        
    }
}
