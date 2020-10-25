package com.relit.health_care_app.sugar;

import com.relit.health_care_app.models.Date;

public class SugarModel {
    private int id;
    private Date date;
    private int value;

    public SugarModel(int id, Date date, int value) {
        this.id = id;
        this.date = date;
        this.value = value;
    }

    @Override
    public String toString() {
        return "SugarModel{" +
                "id=" + id +
                ", date=" + date +
                ", value=" + value +
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
