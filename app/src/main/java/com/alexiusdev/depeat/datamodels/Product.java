package com.alexiusdev.depeat.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Product implements Serializable {
    private String name, ingredients, imageUrl;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(JSONObject jsonProduct) throws JSONException {
        this.name = jsonProduct.getString("name");
        this.ingredients = jsonProduct.getString("ingredients");
        this.imageUrl = jsonProduct.getString("image_url");
        this.quantity = 0;
        this.price = jsonProduct.getDouble("price");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void increaseQuantity(){
        this.quantity++;
    }

    public void decreaseQuantity(){
        if(quantity == 0) return;
        this.quantity--;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
