package com.example.app07_volumeareacalc;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

public class Shape {

    private String shapeName;
    private int shapeImageRes;
    private Class<?> activity;

    public Shape(String shapeName, int shapeImageRes, Class<?> activity) {
        this.shapeName = shapeName;
        this.shapeImageRes = shapeImageRes;
        this.activity = activity;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public int getShapeImageRes() {
        return shapeImageRes;
    }

    public void setShapeImageRes(int shapeImageRes) {
        this.shapeImageRes = shapeImageRes;
    }

    public Class<?> getActivity() {
        return activity;
    }

    public void setActivity(Class<?> activity) {
        this.activity = activity;
    }
}
