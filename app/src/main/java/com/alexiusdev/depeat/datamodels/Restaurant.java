package com.alexiusdev.depeat.datamodels;

public class Restaurant {
    private float minOrder;
    private String name;
    private int rating;
    private String address;

    public Restaurant(float minOrder, String name, int rating, String address) {
        this.minOrder = minOrder;
        this.name = name;
        this.rating = rating;
        this.address = address;
    }

    public void setMinOrder(float minOrder) {
        this.minOrder = minOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRatingFloat() {
        return rating/10F;
    }

    public int getRatingInt() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getMinOrder() {
        return minOrder;
    }
}
