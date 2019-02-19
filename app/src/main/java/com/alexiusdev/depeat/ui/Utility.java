package com.alexiusdev.depeat.ui;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

public class Utility {
    public static final String EMAIL_KEY = "email";
    public static final String RESTAURANT_NAME = "restaurantName";
    public static final String RESTAURANT_ADDRESS = "restaurantAddress";
    public static final String RESTAURANT_IMAGE_URL = "restaurantImageUrl";
    public static final String RESTAURANT_MIN_ORDER = "restaurantMinOrder";
    public static final String RESTAURANT_PRODUCTS = "restaurantProducts";
    public static final String PRICE = "price";

    public static boolean isValidEmail(String emailStr) {
        return Patterns.EMAIL_ADDRESS.matcher(emailStr).matches();
    }

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
