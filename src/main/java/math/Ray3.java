package main.java.math;

import java.util.List;

public class Ray3 {
    Vector3 origin;
    Vector3 direction;

    Ray3(Vector3 origin, Vector3 direction) {
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

    public List<Vector3> intersectSphere(Sphere3 sphere) {
        Vector3 L = sphere.getCenter().subtract(origin);
        double tca = L.dot(direction);
        double d2 = L.dot(L) - tca * tca;
        double radius2 = sphere.getRadius() * sphere.getRadius();
        if (d2 > radius2) {
            return List.of(); // No intersection
        }
        double thc = Math.sqrt(radius2 - d2);
        double t0 = tca - thc;
        double t1 = tca + thc;

        if (t0 < 0 && t1 < 0) {
            return List.of(); // Both intersections are behind the ray
        }

        return List.of(getPoint(t0), getPoint(t1)); // Two intersections
    }
}
