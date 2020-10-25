package com.relit.health_care_app.weight;

import com.relit.health_care_app.models.Date;

public class WeightModel {
    private int id;
    private Date date;
    private float weight;

    public WeightModel(int id, Date date, float weight) {
        this.id = id;
        this.date = date;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "WeightModel{" +
                "id=" + id +
                ", date=" + date +
                ", weight=" + weight +
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
