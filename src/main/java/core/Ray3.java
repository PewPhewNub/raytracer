package main.java.core;

import java.util.List;

import main.java.world.Sphere3;

public class Ray3 {
    Vector3 origin;
    Vector3 direction;

    public Ray3(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public Vector3 getPoint(double t) {
        return origin.add(direction.scale(t));
    }

    public Intersections intersectSphere(Sphere3 sphere) {
        Vector3 L = sphere.getCenter().subtract(origin);
        double tca = L.dot(direction);
        double d2 = L.dot(L) - tca * tca;
        double radius2 = sphere.getRadius() * sphere.getRadius();
        if (d2 > radius2) {
            return new Intersections(
                new Intersection(Double.NaN), 
                new Intersection(Double.NaN)
            ); // No intersection
        }
        double thc = Math.sqrt(radius2 - d2);
        double t0 = tca - thc;
        double t1 = tca + thc;

        if (t0 < 0 && t1 < 0) {
            return new Intersections(
                new Intersection(Double.NaN), 
                new Intersection(Double.NaN)
            );  // Both intersections are behind the ray
        }

        return new Intersections(
            new Intersection(t0), 
            new Intersection(t1)
        );
    }

    public Ray3 transform(Matrix4 matrix) {
        Vector3 newOrigin = matrix.transformPoint(origin);
        Vector3 newDirection = matrix.transformDirection(direction).normalize();
        return new Ray3(newOrigin, newDirection);
    }

    public Ray3 scale(double scalar) {
        Vector3 newOrigin = origin.scale(scalar);
        Vector3 newDirection = direction; // Direction remains unchanged
        return new Ray3(newOrigin, newDirection);
    }

    public Ray3 reflect(Vector3 normal) {
        Vector3 reflectedDirection = direction.subtract(normal.scale(2 * direction.dot(normal))).normalize();
        return new Ray3(origin, reflectedDirection);
    }
}
