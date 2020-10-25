package com.relit.health_care_app.feeling;

import com.relit.health_care_app.models.Date;

import java.util.HashMap;
import java.util.List;

public class FeelingModel {
    private int id;
    private Date date;
    private HashMap<String, Boolean> aches;
    private int feeling;
    private int stress;
    private String comments;

    public FeelingModel(int id, Date date, HashMap<String, Boolean> aches, int feeling, int stress, String comments) {
        this.id = id;
        this.date = date;
        this.aches = aches;
        this.feeling = feeling;
        this.stress = stress;
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public int getStress() {
        return stress;
    }

    public void setStress(int stress) {
        this.stress = stress;
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

    public HashMap<String, Boolean> getAches() {
        return aches;
    }

    public void setAches(HashMap<String, Boolean> aches) {
        this.aches = aches;
    }
}
