package com.myproject.vietnam.gymapp;

import java.io.Serializable;

public class Slider_Item_Detailproduct implements Serializable {
    private int id;
    private String image;

    public Slider_Item_Detailproduct(int id, String image) {
        this.id = id;
        this.image = image;
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
}
