package com.alexiusdev.depeat.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class RestController {

    private final String BASE_URL;
    private static final String TAG = RestController.class.getSimpleName();
    private RequestQueue queue;
    private Context context;
    private Properties properties;

    public RestController(Context context){
        this.context = context;
        properties = getApplicationProperties("application.properties");
        this.BASE_URL = properties.getProperty("url.rest.basePath");


        queue = Volley.newRequestQueue(context);
    }


    public void getRestaurants(Response.Listener<String> success, Response.ErrorListener error) {
        String endpoint = properties.getProperty("url.rest.getRestaurants");
        getRequest(this.BASE_URL, endpoint, success, error);
    }

    public void getRestaurantProducts(String restaurantId, Response.Listener<String> success, Response.ErrorListener error) {
        String endpoint = properties.getProperty("url.rest.getProducts");
        String[] paramNames = {"restaurantId"};
        endpoint = resolveUrl(endpoint, paramNames, restaurantId);
        getRequest(this.BASE_URL, endpoint, success, error);
    }

    public void postRequest(String endPoint, final JSONArray body, Response.Listener<String> success, Response.ErrorListener error) {
        StringRequest request = new StringRequest(Request.Method.POST, BASE_URL.concat(endPoint), success, error){
            @Override
            public byte[] getBody() throws AuthFailureError {
                if (body != null) {
                    Log.d("body", body.toString());
                    return body.toString().getBytes(StandardCharsets.UTF_8);
                }
                return super.getBody();
            }
        };
        queue.add(request);
    }

    private Properties getApplicationProperties(String file) {
        try {
            properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(file);
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return properties;
    }

    private String resolveUrl(String url, final String[] paramNames, final String... paramValues) {
        url = StringUtils.replace(url, "{", "");
        url = StringUtils.replace(url, "}", "");
        return StringUtils.replaceEach(url, paramNames, paramValues);
    }

    private void getRequest(final String BASE_URL, final String endPoint, Response.Listener<String> success, Response.ErrorListener error) {
        Log.d(TAG, "API link: " + BASE_URL + endPoint);

        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL.concat(endPoint), success, error);
        queue.add(request);
    }

    private void postRequest(final String BASE_URL, final String endPoint, final JSONObject body, Response.Listener<String> success, Response.ErrorListener error) {
        StringRequest request = new StringRequest(Request.Method.POST, BASE_URL.concat(endPoint), success, error){
            @Override
            public byte[] getBody() throws AuthFailureError {
                if (body != null) {
                    Log.d("body", body.toString());
                    return body.toString().getBytes(StandardCharsets.UTF_8);
                }
                return super.getBody();
            }
        };
        queue.add(request);
    }

}
