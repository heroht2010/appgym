package com.myproject.vietnam.gymapp;

import java.io.Serializable;

public class Status_product_tab implements Serializable {
    private int id;
    private String image;
    private String name;
    private String flavor;
    private String price;
    private String pricesum;
    private int count;

    public Status_product_tab(int id, String image, String name, String flavor, String price, String pricesum, int count) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.flavor = flavor;
        this.price = price;
        this.pricesum = pricesum;
        this.count = count;
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

    public String getPricesum() {
        return pricesum;
    }

    public void setPricesum(String pricesum) {
        this.pricesum = pricesum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
