package com.alexiusdev.depeat.ui.activities;

import android.content.Intent;
import com.bumptech.glide.Glide;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alexiusdev.depeat.R;
import com.alexiusdev.depeat.datamodels.Product;
import com.alexiusdev.depeat.datamodels.Restaurant;
import com.alexiusdev.depeat.ui.adapters.ProductAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Locale;

import static com.alexiusdev.depeat.ui.Utility.*;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.OnQuanityChangedListener{

    private Button checkout_btn;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private RecyclerView productRv;
    private LinearLayoutManager layoutManager;
    private ProductAdapter adapter;

    private TextView restaurantNameTv, restaurantAddressTv, totalPriceTv, minCheckoutTv;
    private ProgressBar progressBar;

    private Restaurant restaurant;
    private ImageView restaurantIv, mapsIv;

    private float total;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();
        checkout_btn = findViewById(R.id.checkout_btn);
        progressBar = findViewById(R.id.progressbar);
        restaurantNameTv = findViewById(R.id.restaurant_name_tv);
        restaurantAddressTv = findViewById(R.id.restaurant_address_tv);
        totalPriceTv = findViewById(R.id.total_price_tv);
        minCheckoutTv = findViewById(R.id.min_checkout_tv);
        mapsIv = findViewById(R.id.maps_iv);
        restaurantIv = findViewById(R.id.restaurant_iv);
        productRv = findViewById(R.id.product_rv);

        checkout_btn.setOnClickListener(this);

        restaurant = getRestaurant();

        restaurant.setProducts(getProducts());
        restaurantNameTv.setText(restaurant.getName());
        restaurantAddressTv.setText(restaurant.getAddress());
        progressBar.setMax((int)restaurant.getMinOrder() * 100);

        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this, restaurant.getProducts());
        adapter.setOnQuanityChangedListener(this);

        productRv.setAdapter(adapter);
        productRv.setLayoutManager(layoutManager);

        minCheckoutTv.setText(getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f",restaurant.getMinOrder())));
        onChange(0F);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case (R.id.checkout_btn):
                if(currentUser == null) {
                    showToast(this, getString(R.string.login_required));
                    startActivity(new Intent(this, LoginActivity.class));
                }
        }
    }


    private Restaurant getRestaurant(){
        return new Restaurant("UNRISTORANTEDAJAVA","via Casalvecchio, 7","description","imgUrl",42,8.4F);
    }

    private ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("hamburger", 1,2.6F));
        products.add(new Product("hamburger", 1,2.6F));
        products.add(new Product("hamburger", 1,2.6F));
        products.add(new Product("hamburger", 1,2.6F));
        products.add(new Product("hamburger", 1,2.6F));
        products.add(new Product("hamburger", 1,2.6F));
        products.add(new Product("hamburger", 1,2.6F));
        return products;
    }

    private void updateTotal(float item){
        total += item;
        totalPriceTv.setText(getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f",total)));
    }

    private void updateProgress(int progress){
        progressBar.setProgress(progress);
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgress((int)total*100);
    }
}
