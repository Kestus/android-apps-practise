package com.example.app018_countrynames.Models;

import com.google.gson.annotations.SerializedName;

public class Name {
    @SerializedName("common")
    private String common;
    @SerializedName("official")
    private String official;

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getOfficial() {
        return official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }
}