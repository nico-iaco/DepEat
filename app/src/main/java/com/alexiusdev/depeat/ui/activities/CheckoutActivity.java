package com.alexiusdev.depeat.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexiusdev.depeat.R;
import com.alexiusdev.depeat.datamodels.Product;
import com.alexiusdev.depeat.services.RestController;
import com.alexiusdev.depeat.ui.adapters.CheckoutAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import static com.alexiusdev.depeat.ui.Utility.PRICE;
import static com.alexiusdev.depeat.ui.Utility.RESTAURANT_ID;
import static com.alexiusdev.depeat.ui.Utility.RESTAURANT_NAME;
import static com.alexiusdev.depeat.ui.Utility.RESTAURANT_PRODUCTS;

public class CheckoutActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    private TextView nameTv, priceTv;
    private Button payBtn;
    private CheckoutAdapter adapter;
    private ArrayList<Product> products;
    private RecyclerView checkoutRv;
    private String restaurantId;


    @SuppressWarnings({"unchecked","ConstantConditions"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        nameTv = findViewById(R.id.name_tv);
        payBtn = findViewById(R.id.pay_btn);
        checkoutRv = findViewById(R.id.product_rv);
        priceTv = findViewById(R.id.price_tv);

        payBtn.setOnClickListener(v -> paymentRequest(String.valueOf(getIntent().getExtras().get(RESTAURANT_ID)), "123123", priceTv.getText().toString()));

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().get(RESTAURANT_PRODUCTS) instanceof ArrayList){ //get products from intent
            nameTv.setText(getIntent().getExtras().getString(RESTAURANT_NAME));
            products = (ArrayList<Product>) getIntent().getExtras().get(RESTAURANT_PRODUCTS);
            priceTv.append(" ".concat(getString(R.string.currency)).concat(String.format(Locale.getDefault(), "%.2f", getIntent().getExtras().getDouble(PRICE))));
            for(Iterator<Product> i = products.iterator(); i.hasNext();){ //remove empty products from the ArrayList
                while(i.hasNext()) {    //TODO improve method removing empty products from ShopActivity in order to lighten the weight of the Intent
                    Product p = i.next();
                    if(p.getQuantity() == 0)
                        i.remove();
                }
            }
        }

        adapter = new CheckoutAdapter(this, getProducts());
        checkoutRv.setAdapter(adapter);
        checkoutRv.setLayoutManager(new LinearLayoutManager(this));

    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    private void paymentRequest(String restaurantId, String userId, String total){
        RestController restController = new RestController(this);
        JSONArray body = new JSONArray();
        body.put(restaurantId).put(userId).put(total).put(jsonArrayFromJsonProductFromArrayList(products));
        restController.postRequest("orders",body, this,this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("order error response", error.toString());
    }

    @Override
    public void onResponse(String response) {
        Log.d("order response",response);
    }

    private JSONArray jsonArrayFromJsonProductFromArrayList(ArrayList<Product> products){
        ArrayList<JSONObject> jsonProduct = new ArrayList<>();
        for(Product p : products)
            jsonProduct.add(p.toJSONObject());
        return new JSONArray(jsonProduct);
    }
}
