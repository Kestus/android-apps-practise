package com.kes.app025_advancedrecyclerviews.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.kes.app025_advancedrecyclerviews.models.Planet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class JsonRepository {

    private final Context context;
    private final List<Planet> planets;

    public JsonRepository(Context context) {
        this.context = context;
        planets = loadPlanets();
    }

    private List<Planet> loadPlanets() {
        String json = readAssetFile("planets.json");
        Gson reader = new Gson();
        Planet[] result = reader.fromJson(json, Planet[].class);
        return Arrays.asList(result);
    }

    public String readAssetFile(String fileName) {
        try (InputStream inputStream = context.getAssets().open(fileName)) {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            int readSize = inputStream.read(buffer);
            if (readSize != size) {
                throw new IOException("could not read file");
            }

            return new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Planet> getPlanets() {
        return planets;
    }

}
