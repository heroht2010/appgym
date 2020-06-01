package com.myproject.vietnam.gymapp;

import java.io.Serializable;

public class home_PT implements Serializable {
    private int id;
    private String imagePt;
    private String namePt;
    private String followPt;

    public home_PT(int id, String imagePt, String namePt, String followPt) {
        this.id = id;
        this.imagePt = imagePt;
        this.namePt = namePt;
        this.followPt = followPt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePt() {
        return imagePt;
    }

    public void setImagePt(String imagePt) {
        this.imagePt = imagePt;
    }

    public String getNamePt() {
        return namePt;
    }

    public void setNamePt(String namePt) {
        this.namePt = namePt;
    }

    public String getFollowPt() {
        return followPt;
    }

    public void setFollowPt(String followPt) {
        this.followPt = followPt;
    }
}
