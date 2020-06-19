package com.myproject.vietnam.gymapp;

import java.io.Serializable;

public class cart_attribute implements Serializable {
    private int id;
    private String image;
    private String name;
    private String flavor;
    private String price;
    private String pricesale;
    private float rating;

    public cart_attribute(int id, String image, String name, String flavor, String price, String pricesale, float rating) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.flavor = flavor;
        this.price = price;
        this.pricesale = pricesale;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPricesale() {
        return pricesale;
    }

    public void setPricesale(String pricesale) {
        this.pricesale = pricesale;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
