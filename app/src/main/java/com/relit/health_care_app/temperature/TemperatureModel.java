package com.relit.health_care_app.temperature;

import com.relit.health_care_app.models.Date;

public class TemperatureModel {
    private int id;
    private Date date;
    private float temperature;

    public TemperatureModel(int id, Date date, float temperature) {
        this.id = id;
        this.date = date;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "TemperatureModel{" +
                "id=" + id +
                ", date=" + date.toString() +
                ", temperature=" + temperature +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
