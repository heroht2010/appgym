package com.myproject.vietnam.gymapp;

import java.io.Serializable;

public class SliderItem implements Serializable {
    private int id;
    private int imageSlider;

    public SliderItem(int id, int imageSlider) {
        this.id = id;
        this.imageSlider = imageSlider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageSlider() {
        return imageSlider;
    }

    public void setImageSlider(int imageSlider) {
        this.imageSlider = imageSlider;
    }
}
