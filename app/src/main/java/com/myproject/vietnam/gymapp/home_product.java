package com.myproject.vietnam.gymapp;

import java.io.Serializable;

public class home_product implements Serializable {
    private int id;
    private String imageProduct;
    private String nameProduct;
    private String priceProduct;
    private String priceSale;
    private String weightProduct;
    private String flavor1;
    private String flavor2;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private int id_product_type;
    private String descri;
    private String distributor;
    private String detailProduct;
    private float ratingProduct;

    public home_product(int id, String imageProduct, String nameProduct,
                        String priceProduct, String priceSale, String weightProduct,
                        String flavor1, String flavor2, String image1, String image2,
                        String image3, String image4, int id_product_type, String descri,
                        String distributor, String detailProduct, float ratingProduct) {
        this.id = id;
        this.imageProduct = imageProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.priceSale = priceSale;
        this.weightProduct = weightProduct;
        this.flavor1 = flavor1;
        this.flavor2 = flavor2;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.id_product_type = id_product_type;
        this.descri = descri;
        this.distributor = distributor;
        this.detailProduct = detailProduct;
        this.ratingProduct = ratingProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(String priceSale) {
        this.priceSale = priceSale;
    }

    public String getWeightProduct() {
        return weightProduct;
    }

    public void setWeightProduct(String weightProduct) {
        this.weightProduct = weightProduct;
    }

    public String getFlavor1() {
        return flavor1;
    }

    public void setFlavor1(String flavor1) {
        this.flavor1 = flavor1;
    }

    public String getFlavor2() {
        return flavor2;
    }

    public void setFlavor2(String flavor2) {
        this.flavor2 = flavor2;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public int getId_product_type() {
        return id_product_type;
    }

    public void setId_product_type(int id_product_type) {
        this.id_product_type = id_product_type;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getDetailProduct() {
        return detailProduct;
    }

    public void setDetailProduct(String detailProduct) {
        this.detailProduct = detailProduct;
    }

    public float getRatingProduct() {
        return ratingProduct;
    }

    public void setRatingProduct(float ratingProduct) {
        this.ratingProduct = ratingProduct;
    }
}
