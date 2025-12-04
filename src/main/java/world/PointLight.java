package main.java.world;

import main.java.core.Color;
import main.java.core.Vector3;

public class PointLight {
    private final Vector3 position;
    private final Color intensity;

    public PointLight(Vector3 position, Color intensity) {
        this.position = position;
        this.intensity = intensity;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Color getIntensity() {
        return intensity;
    }
}
