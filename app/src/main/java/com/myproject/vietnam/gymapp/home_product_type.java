package com.myproject.vietnam.gymapp;

import android.widget.TextView;

import java.io.Serializable;

public class home_product_type implements Serializable {
    private int id;
    private String imageProductType;
    private String iconProductType;
    private String txtProductType;

    public home_product_type(int id, String imageProductType, String iconProductType, String txtProductType) {
        this.id = id;
        this.imageProductType = imageProductType;
        this.iconProductType = iconProductType;
        this.txtProductType = txtProductType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageProductType() {
        return imageProductType;
    }

    public void setImageProductType(String imageProductType) {
        this.imageProductType = imageProductType;
    }

    public String getIconProductType() {
        return iconProductType;
    }

    public void setIconProductType(String iconProductType) {
        this.iconProductType = iconProductType;
    }

    public String getTxtProductType() {
        return txtProductType;
    }

    public void setTxtProductType(String txtProductType) {
        this.txtProductType = txtProductType;
    }
}
