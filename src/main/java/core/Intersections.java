package main.java.core;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Intersections {
    public final List<Intersection> list;

    public Intersections(Intersection... intersections) {
        this.list = Arrays.asList(intersections);
        this.list.sort(Comparator.comparing(i -> i.t));
    }

    public Intersection hit() {
        for (Intersection i : list) {
            if (i.t >= 0) return i;
        }
        return null;  // no visible intersection
    }

    public int count() {
        return list.size();
    }

    public Intersection get(int i) {
        return list.get(i);
    }
}
