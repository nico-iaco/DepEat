package com.alexiusdev.depeat.services;

import android.content.Context;
import android.os.LocaleList;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RestController {
    //private final static String BASE_URL = "http://5c659d3419df280014b6272a.mockapi.io/api/";
    private final static String BASE_URL = "http://138.68.86.70/";
    //private final static String VERSION = "v1/";
    private final static String VERSION = "";
    private static final String TAG = RestController.class.getSimpleName();

    private RequestQueue queue;

    public RestController(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getRequest(String endPoint, Response.Listener<String> success, Response.ErrorListener error){
        Log.d(TAG,"API link: " + BASE_URL + VERSION + endPoint);

        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL.concat(VERSION).concat(endPoint), success, error);
        queue.add(request);
    }

    public void postRequest(final String endPoint, final Map<String,String> body, Response.Listener<String> success, Response.ErrorListener error){
        Log.d(TAG,"API link: " + BASE_URL + VERSION + endPoint);
        StringRequest request = new StringRequest(Request.Method.POST, BASE_URL.concat(VERSION).concat(endPoint), success, error){
           @Override
            protected Map<String, String> getParams(){
                return body;
            }
        };
        queue.add(request);
    }




}
