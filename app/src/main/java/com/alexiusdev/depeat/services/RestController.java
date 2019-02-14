package com.alexiusdev.depeat.services;

import android.content.Context;
import android.os.LocaleList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RestController {
    private final static String BASE_URL = "http://5c659d3419df280014b6272a.mockapi.io/api/";
    private final static String VERSION = "v1/";
    private static final String TAG = RestController.class.getSimpleName();

    private RequestQueue queue;

    public RestController(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getRequest(String endPoint, Response.Listener<String> success, Response.ErrorListener error){
        Log.d(TAG,"API link: " + BASE_URL + VERSION + endPoint);
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL.concat(VERSION).concat(endPoint),
                success,
                error);
        queue.add(request);
    }
}
