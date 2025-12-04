package main.java.world;
import main.java.core.Matrix4;
import main.java.core.Vector3;
import main.java.objects.PhongMaterial;

public class Sphere3 {
    private final Vector3 center;
    private final double radius;
    private Matrix4 transform;
    private PhongMaterial material;

    public Sphere3(Vector3 center, double radius) {
        if (radius < 0.0) throw new IllegalArgumentException("Radius cannot be negative");
        this.center = center;
        this.radius = radius;
        this.transform = Matrix4.identity();
    }

    public Sphere3(Vector3 center, double radius, PhongMaterial material) {
        if (radius < 0.0) throw new IllegalArgumentException("Radius cannot be negative");
        this.center = center;
        this.radius = radius;
        this.transform = Matrix4.identity();
        this.material = material;
    }

    public Vector3 getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public void setTransform(Matrix4 matrix) {
        this.transform = matrix;
    }

    public Vector3 normalAt(Vector3 worldPoint) {
        // object_point ← inverse(sphere.transform) * world_point
        Matrix4 inverseTransform = transform.inverse();
        Vector3 objectPoint = inverseTransform.transformPoint(worldPoint);
        
        // object_normal ← object_point - point(0, 0, 0)
        Vector3 objectNormal = objectPoint.subtract(new Vector3(0, 0, 0));
        
        // world_normal ← transpose(inverse(sphere.transform)) * object_normal
        Matrix4 transposedInverse = inverseTransform.transpose();
        Vector3 worldNormal = transposedInverse.transformDirection(objectNormal);
        
        // return normalize(world_normal)
        return worldNormal.normalize();
    }

    public void setMaterial(PhongMaterial material) {
        this.material = material;
    }

    public PhongMaterial getMaterial() {
        return material;
    }

    public Matrix4 getTransform() {
        return transform;
    }
}
