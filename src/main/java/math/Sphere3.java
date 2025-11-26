package main.java.math;

public class Sphere3 {
    private final Vector3 center;
    private final double radius;

    public Sphere3(Vector3 center, double radius) {
        if (radius < 0.0) throw new IllegalArgumentException("Radius cannot be negative");
        this.center = center;
        this.radius = radius;
    }

    public Vector3 getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public Sphere3 setTransform(Matrix4 matrix) {
        Vector3 newCenter = matrix.transformPoint(center);
        // Assuming uniform scaling for radius
        Vector3 scaledPoint = matrix.transformPoint(center.add(new Vector3(radius, 0, 0)));
        double newRadius = scaledPoint.subtract(newCenter).magnitude();
        return new Sphere3(newCenter, newRadius);
    }

    public Vector3 normalAt(Vector3 point){
        return (point.subtract(center)).normalize();
    }
}
