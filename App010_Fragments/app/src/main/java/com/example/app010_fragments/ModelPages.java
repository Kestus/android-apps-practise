package com.example.app010_fragments;

public enum ModelPages {

    RED(R.string.red, R.layout.view_red),
    BLUE(R.string.blue, R.layout.view_blue),
    GREEN(R.string.green, R.layout.view_green);

    private int titleID;
    private int layoutID;

    ModelPages(int titleID, int layoutID) {
        this.titleID = titleID;
        this.layoutID = layoutID;
    }

    public int getTitleID() {
        return titleID;
    }

    public void setTitleID(int titleID) {
        this.titleID = titleID;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }
}
