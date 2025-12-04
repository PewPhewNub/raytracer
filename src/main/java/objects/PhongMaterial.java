package main.java.objects;

public class PhongMaterial {
    double ambient;
    double diffuse;
    double specular;
    double shininess;

    public PhongMaterial(double ambient, double diffuse, double specular, double shininess) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }
}
