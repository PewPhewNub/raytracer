package main.java.math;

import java.util.Arrays;

public class Matrix4 {
    private final double[] m; // row-major: m[row*4 + col]

    public Matrix4() {
        // identity
        m = new double[16];
        m[0] = m[5] = m[10] = m[15] = 1.0;
    }

    public Matrix4(double[] values) {
        if (values == null || values.length != 16) throw new IllegalArgumentException("Array must be length 16");
        m = Arrays.copyOf(values, 16);
    }

    public static Matrix4 identity() {
        return new Matrix4();
    }

    public double get(int row, int col) {
        return m[row * 4 + col];
    }

    public Matrix4 multiply(Matrix4 other) {
        double[] r = new double[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double sum = 0.0;
                for (int k = 0; k < 4; k++) {
                    sum += this.m[i*4 + k] * other.m[k*4 + j];
                }
                r[i*4 + j] = sum;
            }
        }
        return new Matrix4(r);
    }

    public Matrix4 transpose() {
        double[] r = new double[16];
        for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) r[i*4 + j] = m[j*4 + i];
        return new Matrix4(r);
    }

    public Vector3 transformPoint(Vector3 p) {
        double x = m[0]*p.x + m[1]*p.y + m[2]*p.z + m[3]*1.0;
        double y = m[4]*p.x + m[5]*p.y + m[6]*p.z + m[7]*1.0;
        double z = m[8]*p.x + m[9]*p.y + m[10]*p.z + m[11]*1.0;
        double w = m[12]*p.x + m[13]*p.y + m[14]*p.z + m[15]*1.0;
        if (Math.abs(w - 1.0) > 1e-12 && w != 0.0) {
            return new Vector3(x / w, y / w, z / w);
        }
        return new Vector3(x, y, z);
    }

    public Vector3 transformDirection(Vector3 v) {
        // w = 0 -> translation doesn't affect direction
        double x = m[0]*v.x + m[1]*v.y + m[2]*v.z;
        double y = m[4]*v.x + m[5]*v.y + m[6]*v.z;
        double z = m[8]*v.x + m[9]*v.y + m[10]*v.z;
        return new Vector3(x, y, z);
    }

    public static Matrix4 createTranslation(double tx, double ty, double tz) {
        Matrix4 t = new Matrix4();
        double[] a = t.m;
        a[3] = tx; a[7] = ty; a[11] = tz;
        return new Matrix4(a);
    }

    public static Matrix4 createScale(double sx, double sy, double sz) {
        double[] a = new double[16];
        a[0] = sx; a[5] = sy; a[10] = sz; a[15] = 1.0;
        return new Matrix4(a);
    }

    public static Matrix4 createRotationX(double radians) {
        double c = Math.cos(radians), s = Math.sin(radians);
        double[] a = new double[16];
        a[0] = 1; a[5] = c; a[6] = -s; a[9] = s; a[10] = c; a[15] = 1;
        return new Matrix4(a);
    }

    public static Matrix4 createRotationY(double radians) {
        double c = Math.cos(radians), s = Math.sin(radians);
        double[] a = new double[16];
        a[0] = c; a[2] = s; a[5] = 1; a[8] = -s; a[10] = c; a[15] = 1;
        return new Matrix4(a);
    }

    public static Matrix4 createRotationZ(double radians) {
        double c = Math.cos(radians), s = Math.sin(radians);
        double[] a = new double[16];
        a[0] = c; a[1] = -s; a[4] = s; a[5] = c; a[10] = 1; a[15] = 1;
        return new Matrix4(a);
    }

    public static Matrix4 createRotationAxis(Vector3 axis, double radians) {
        double ux = axis.x, uy = axis.y, uz = axis.z;
        double len = Math.sqrt(ux*ux + uy*uy + uz*uz);
        if (len == 0.0) return identity();
        ux /= len; uy /= len; uz /= len;
        double c = Math.cos(radians), s = Math.sin(radians);
        double oneC = 1.0 - c;
        double[] a = new double[16];
        a[0] = c + ux*ux*oneC;
        a[1] = ux*uy*oneC - uz*s;
        a[2] = ux*uz*oneC + uy*s;
        a[4] = uy*ux*oneC + uz*s;
        a[5] = c + uy*uy*oneC;
        a[6] = uy*uz*oneC - ux*s;
        a[8] = uz*ux*oneC - uy*s;
        a[9] = uz*uy*oneC + ux*s;
        a[10] = c + uz*uz*oneC;
        a[15] = 1.0;
        return new Matrix4(a);
    }

    public static Matrix4 lookAt(Vector3 eye, Vector3 target, Vector3 up) {
        // builds world->camera (view) matrix
        Vector3 z = eye.subtract(target).normalize(); // camera forward
        Vector3 x = up.cross(z).normalize(); // right
        Vector3 y = z.cross(x); // true up
        double[] a = new double[16];
        a[0] = x.x; a[1] = x.y; a[2] = x.z; a[3] = -x.dot(eye);
        a[4] = y.x; a[5] = y.y; a[6] = y.z; a[7] = -y.dot(eye);
        a[8] = z.x; a[9] = z.y; a[10] = z.z; a[11] = -z.dot(eye);
        a[15] = 1.0;
        return new Matrix4(a);
    }

    public static Matrix4 cameraToWorld(Vector3 eye, Vector3 target, Vector3 up) {
        // inverse of lookAt; constructs camera->world transform directly
        Vector3 z = target.subtract(eye).normalize(); // forward
        Vector3 x = up.cross(z).normalize(); // right
        Vector3 y = z.cross(x); // up
        double[] a = new double[16];
        a[0] = x.x; a[1] = y.x; a[2] = z.x; a[3] = eye.x;
        a[4] = x.y; a[5] = y.y; a[6] = z.y; a[7] = eye.y;
        a[8] = x.z; a[9] = y.z; a[10] = z.z; a[11] = eye.z;
        a[15] = 1.0;
        return new Matrix4(a);
    }

    public static Matrix4 perspective(double fovYRadians, double aspect, double near, double far) {
        double f = 1.0 / Math.tan(fovYRadians * 0.5);
        double[] a = new double[16];
        a[0] = f / aspect;
        a[5] = f;
        a[10] = (far + near) / (near - far);
        a[11] = (2 * far * near) / (near - far);
        a[14] = -1.0;
        return new Matrix4(a);
    }

    public Matrix4 inverse() {
        // Gauss-Jordan elimination for 4x4 matrix inversion
        double[][] aug = new double[4][8];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) aug[r][c] = m[r*4 + c];
            for (int c = 0; c < 4; c++) aug[r][4 + c] = (r == c) ? 1.0 : 0.0;
        }
        for (int col = 0; col < 4; col++) {
            // pivot
            int piv = col;
            for (int r = col; r < 4; r++) {
                if (Math.abs(aug[r][col]) > Math.abs(aug[piv][col])) piv = r;
            }
            if (Math.abs(aug[piv][col]) < 1e-15) throw new IllegalStateException("Matrix is singular and cannot be inverted");
            // swap rows
            if (piv != col) {
                double[] tmp = aug[col]; aug[col] = aug[piv]; aug[piv] = tmp;
            }
            // normalize pivot row
            double d = aug[col][col];
            for (int c = 0; c < 8; c++) aug[col][c] /= d;
            // eliminate others
            for (int r = 0; r < 4; r++) {
                if (r == col) continue;
                double factor = aug[r][col];
                if (factor == 0.0) continue;
                for (int c = 0; c < 8; c++) aug[r][c] -= factor * aug[col][c];
            }
        }
        double[] inv = new double[16];
        for (int r = 0; r < 4; r++) for (int c = 0; c < 4; c++) inv[r*4 + c] = aug[r][4 + c];
        return new Matrix4(inv);
    }

    public String toString() {
        return String.format(
            "[% .6f % .6f % .6f % .6f]%n[% .6f % .6f % .6f % .6f]%n[% .6f % .6f % .6f % .6f]%n[% .6f % .6f % .6f % .6f]",
            m[0], m[1], m[2], m[3],
            m[4], m[5], m[6], m[7],
            m[8], m[9], m[10], m[11],
            m[12], m[13], m[14], m[15]
        );
    }

    public double[] toArray() {
        return Arrays.copyOf(m, 16);
    }
}
