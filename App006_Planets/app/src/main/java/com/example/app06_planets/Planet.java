package com.example.app06_planets;

public class Planet {
    private String name;
    private int numberOfMoons;
    private int imageRes;

    public Planet(String name, int numberOfMoons, int imageRes) {
        this.name = name;
        this.numberOfMoons = numberOfMoons;
        this.imageRes = imageRes;
    }

    // Getters
    public String getName() { return name; }

    public int getNumberOfMoons() {
        return numberOfMoons;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getStringNumberOfMoons() {
        return numberOfMoons + " Moon(s)";
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfMoons(int numberOfMoons) {
        this.numberOfMoons = numberOfMoons;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}