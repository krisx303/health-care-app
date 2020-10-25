package com.relit.health_care_app.pressure;

import com.relit.health_care_app.models.Date;

public class PressureModel {
    private int id;
    private Date date;
    private int systolic;
    private int diastolic;

    public PressureModel(int id, Date date, int systolic, int diastolic) {
        this.id = id;
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    @Override
    public String toString() {
        return "PressureModel{" +
                "id=" + id +
                ", date=" + date +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
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

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }
}
