package main.java.core;

import java.util.Random;
public class Vector3{
    public final double x; public final double y; public final double z;
    private static final double EPS = 1e-12;
    private static final Random random = new Random();
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public static final Vector3 zero = new Vector3(0.0, 0.0, 0.0);
    public Vector3 add(Vector3 v){
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }
    public Vector3 subtract(Vector3 v){
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }
    public double dot(Vector3 v){
        return (v.x*this.x) + (v.y*this.y) + (v.z*this.z);
    }
    public Vector3 scale(double scalar){
        return new Vector3(this.x*scalar, this.y*scalar, this.z*scalar);
    }
    public Vector3 cross(Vector3 v) {
        return new Vector3(
            this.y * v.z - this.z * v.y,
            this.z * v.x - this.x * v.z,
            this.x * v.y - this.y * v.x
        );
    }
    public static Vector3 random() {
        // Use a loop instead of recursion; retry if generated vector is (near) zero.
        while (true) {
            double x = random.nextGaussian();
            double y = random.nextGaussian();
            double z = random.nextGaussian();
            double length = Math.sqrt(x*x + y*y + z*z);
            if (length > EPS) {
                return (new Vector3(x, y, z)).normalize();
            }
            // otherwise retry
        }
    }
    public double magnitude(){
        return Math.sqrt(x*x + y*y + z*z);
    }
    public double magnitudeSquared(){
        return (x*x + y*y + z*z);
    }
    // REVISED Vector3.java
    public Vector3 normalize(){
        double mag = magnitude();
        
        // CRITICAL: Check if the magnitude is zero (or near zero, for floating point safety)
        if (mag < EPS) { // small epsilon for double precision
            return new Vector3(0.0, 0.0, 0.0); // Return the zero vector
        }
        
        // Original logic: If magnitude is valid, normalize it
        return scale(1.0 / mag);
    }
    public Vector3 projectOnto(Vector3 other) {
        double denom = other.magnitudeSquared();
        if (denom < EPS) return new Vector3(0.0, 0.0, 0.0);
        double scalar = this.dot(other) / denom;
        return new Vector3(other.x * scalar, other.y * scalar, other.z * scalar);
    }
    public boolean isNormalized(){
        return Math.abs(magnitude() - 1.0) <= 1E-12;
    }
    public String toString() {
        return String.format("Vector3(%.6f, %.6f, %.6f)", x, y, z);
    }
    public boolean equals(Object o) {
        if (!(o instanceof Vector3)) return false;
        Vector3 v = (Vector3) o;
        return Math.abs(this.x - v.x) < 1e-9 &&
               Math.abs(this.y - v.y) < 1e-9 &&
               Math.abs(this.z - v.z) < 1e-9;
    }
    public Vector3 copy(){
        return new Vector3(x, y, z);
    }
    public Vector3 clone(){
        return new Vector3(x, y, z);
    }
    public int hashCode() {
        // mix hashes for slightly better distribution
        int hx = Double.hashCode(x);
        int hy = Double.hashCode(y);
        int hz = Double.hashCode(z);
        return (hx * 31) ^ (hy * 17) ^ hz;
    }
    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
    }
    // Additional utility methods useful for ray tracing
    public double distance(Vector3 v) {
        return Math.sqrt((x - v.x)*(x - v.x) + (y - v.y)*(y - v.y) + (z - v.z)*(z - v.z));
    }
    public Vector3 reflect(Vector3 normal) {
        // reflect this vector around normal (assumes normal is normalized)
        double d = this.dot(normal);
        return this.subtract(normal.scale(2.0 * d));
    }
    public Vector3 lerp(Vector3 to, double t) {
        return new Vector3(x + (to.x - x)*t, y + (to.y - y)*t, z + (to.z - z)*t);
    }
    public double angleBetween(Vector3 v) {
        double denom = this.magnitude() * v.magnitude();
        if (denom < EPS) return 0.0;
        double cos = Math.max(-1.0, Math.min(1.0, this.dot(v) / denom));
        return Math.acos(cos);
    }
    public Vector3 refract(Vector3 normal, double eta) {
        // Snell's law refraction. normal should be normalized.
        double cosi = Math.max(-1.0, Math.min(1.0, this.dot(normal)));
        double etai = 1.0, etat = eta;
        Vector3 n = normal;
        if (cosi < 0) {
            cosi = -cosi;
        } else {
            // entering from inside to outside: swap
            double tmp = etai; etai = etat; etat = tmp;
            n = normal.negate();
        }
        double etaRatio = etai / etat;
        double k = 1.0 - etaRatio*etaRatio*(1.0 - cosi*cosi);
        if (k < 0) {
            return new Vector3(0.0, 0.0, 0.0); // total internal reflection (caller can detect)
        }
        return this.scale(etaRatio).add(n.scale(etaRatio * cosi - Math.sqrt(k)));
    }
}