package com.relit.health_care_app.models;

public class Choice {
    private String name;
    private boolean isSelected;
    private boolean hasData;
    private String lastDataString;
    private String lastDateString;

    public Choice(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
        this.lastDataString = "";
        this.lastDateString = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public String getLastDataString() {
        return lastDataString;
    }

    public void setLastDataString(String lastDataString) {
        this.lastDataString = lastDataString;
    }

    public String getLastDateString() {
        return lastDateString;
    }

    public void setLastDateString(String lastDateString) {
        this.lastDateString = lastDateString;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "name='" + name + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
