package com.alexiusdev.depeat;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

public class Utility {
    public static final String EMAIL_KEY = "email";

    public static boolean isValidEmail(String emailStr) {
        return Patterns.EMAIL_ADDRESS.matcher(emailStr).matches();
    }

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
