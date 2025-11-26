package test.java;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import main.java.math.Vector3;
public class Vector3Test {
    @Test
    public void VectorOperations(){
        Vector3 vec1 = new Vector3(1,2,3);
        Vector3 vec2 = new Vector3(3,2,1);
        assertEquals(Math.sqrt(14), vec1.magnitude(), 1e-6);
        assertEquals(new Vector3(4,4,4), vec1.add(vec2));
        assertEquals(new Vector3(-2,0,2), vec1.subtract(vec2));
        assertEquals(10, vec1.dot(vec2));
        assertEquals(new Vector3(-4,8,-4), vec1.cross(vec2));
        assertFalse(vec1.isNormalized());
        assertEquals((vec1.normalize()).magnitude(),1,1e-6);
    }
}
