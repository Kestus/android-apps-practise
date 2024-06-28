package com.example.app018_countrynames.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Country {
    @SerializedName("name")
    public Name name;
    @SerializedName("capital")
    private List<String> capital;
    @SerializedName("region")
    private String region;

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
