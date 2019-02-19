package com.alexiusdev.depeat.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Restaurant {
    private String name, address, imageUrl;
    private int rating;
    private double minOrder;
    private ArrayList<Product> products = new ArrayList<>();
    public static final String END_POINT = "restaurants";

    public Restaurant(String name, String address, String imageUrl, int rating, double minOrder, ArrayList<Product> products) {
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.minOrder = minOrder;
        this.products = products;
    }

    public Restaurant(JSONObject jsonRestaurant) throws JSONException {
        name = jsonRestaurant.getString("name");
        address = jsonRestaurant.getString("address");
        imageUrl = jsonRestaurant.getString("image_url");
        rating = Integer.parseInt(jsonRestaurant.getString("rating"));
        minOrder = Double.parseDouble(jsonRestaurant.getString("min_order"));
        for(int i = 0; i < jsonRestaurant.getJSONArray("products").length(); i++)
            products.add(new Product(jsonRestaurant.getJSONArray("products").getJSONObject(i)));
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

    public double getMinOrder() {
        return minOrder;
    }

    public int getRating() {
        return rating;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static String getEndPoint() {
        return END_POINT;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
