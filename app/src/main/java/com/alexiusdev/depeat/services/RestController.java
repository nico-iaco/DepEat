package com.alexiusdev.depeat.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class RestController {

    private static final String BASE_URL = "http://5c659d3419df280014b6272a.mockapi.io/api/";
    private static final String VERSION = "v1/";
    private static final String TAG = RestController.class.getSimpleName();
    private static final String KEY = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzY1NmU4ZGZiMmE1YTRiZmE0NGFjZjQiLCJpYXQiOjE1NTAxNTkzOTIsImV4cCI6MTU1Mjc1MTM5Mn0.yNG02NbUVfTBlxjO5Y_-sfzbtvyQnefaFkzRXTSC-v4";
    private RequestQueue queue;

    public RestController(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getRequest(String endPoint, Response.Listener<String> success, Response.ErrorListener error){
        Log.d(TAG,"API link: " + BASE_URL /*+ VERSION*/ + endPoint /*+ FINAL_TAG*/);

        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL.concat(VERSION).concat(endPoint), success, error);
        queue.add(request);
    }


    public void postRequest(String endPoint, final JSONObject body, Response.Listener<String> success, Response.ErrorListener error){
        StringRequest request = new StringRequest(Request.Method.POST, BASE_URL.concat(endPoint), success, error){
            @Override
            public byte[] getBody() throws AuthFailureError {
                if (body != null) {
                    Log.d("body", body.toString());
                    return body.toString().getBytes(StandardCharsets.UTF_8);
                }
                return super.getBody();
            }

            @Override
            public Map<String, String> getHeaders(){
                Map<String,String> map = new HashMap<>();
                map.put("Authorization",KEY);
                return map;
            }
        };
        queue.add(request);
    }

    public void postRequest(String endPoint, final JSONArray body, Response.Listener<String> success, Response.ErrorListener error){
        StringRequest request = new StringRequest(Request.Method.POST, BASE_URL.concat(endPoint), success, error){
            @Override
            public byte[] getBody() throws AuthFailureError {
                if (body != null) {
                    Log.d("body", body.toString());
                    return body.toString().getBytes(StandardCharsets.UTF_8);
                }
                return super.getBody();
            }

            @Override
            public Map<String, String> getHeaders(){
                Map<String,String> map = new HashMap<>();
                map.put("Authorization",KEY);
                return map;
            }
        };
        queue.add(request);
    }
}
