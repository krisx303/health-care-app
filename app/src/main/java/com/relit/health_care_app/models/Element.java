package com.relit.health_care_app.models;

public class Element {
    private String name;
    private String title;
    private String description;
    private int imageId;
    private boolean isSelected;

    public Element(String name, String title, String description, int imageId) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getImageId() {
        return imageId;
    }
}
