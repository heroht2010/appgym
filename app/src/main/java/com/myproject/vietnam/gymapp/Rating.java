package com.myproject.vietnam.gymapp;

import java.io.Serializable;

public class Rating implements Serializable {
    private int id;
    private String user;
    private String descri;
    private float rating;

    public Rating(int id, String user, String descri, float rating) {
        this.id = id;
        this.user = user;
        this.descri = descri;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}
