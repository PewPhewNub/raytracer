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
}
