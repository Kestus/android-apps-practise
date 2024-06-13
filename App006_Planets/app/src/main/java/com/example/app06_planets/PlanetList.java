package com.example.app06_planets;

import java.util.ArrayList;

public class PlanetList {
    public static ArrayList<Planet> getPlanets() {
        ArrayList<Planet> planets = new ArrayList<Planet>();
        planets.add(new Planet(
                "Mercury",
                0,
                R.drawable.mercury
        ));

        planets.add(new Planet(
                "Venus",
                0,
                R.drawable.venus
        ));

        planets.add(new Planet(
                "Earth",
                1,
                R.drawable.earth
        ));

        planets.add(new Planet(
                "Mars",
                2,
                R.drawable.mars
        ));

        planets.add(new Planet(
                "Jupiter",
                95,
                R.drawable.jupiter
        ));

        planets.add(new Planet(
                "Saturn",
                146,
                R.drawable.saturn
        ));

        planets.add(new Planet(
                "Uranus",
                28,
                R.drawable.uranus
        ));

        planets.add(new Planet(
                "Neptune",
                16,
                R.drawable.neptune
        ));

        return planets;
    }
}