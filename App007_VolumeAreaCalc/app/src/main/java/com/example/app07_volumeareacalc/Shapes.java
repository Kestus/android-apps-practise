package com.example.app07_volumeareacalc;

import java.util.ArrayList;

public class Shapes {
    public static ArrayList<Shape> getShapes() {
        ArrayList<Shape> shapes = new ArrayList<>();

        shapes.add(new Shape(
                "Cylinder",
                R.drawable.cylinder,
                CylinderActivity.class
        ));

        shapes.add(new Shape(
                "Prism",
                R.drawable.prism,
                PrismActivity.class

        ));

        shapes.add(new Shape(
                "Cube",
                R.drawable.cube,
                CubeActivity.class
        ));

        shapes.add(new Shape(
                "Sphere",
                R.drawable.sphere,
                SphereActivity.class
        ));

        return shapes;
    }
}
