package com.alexiusdev.depeat.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.alexiusdev.depeat.ui.Utility.showToast;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.OnQuantityChangedListener {

    private Button checkoutBtn;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private RecyclerView productRv;
    private LinearLayoutManager layoutManager;
    private ProductAdapter adapter;
    private TextView restaurantNameTv, restaurantAddressTv, totalPriceTv, minCheckoutTv;
    private ProgressBar progressBar;
    private Restaurant restaurant;
    private ImageView restaurantIv, mapsIv;
    private static float total;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mAuth = FirebaseAuth.getInstance();
        checkoutBtn = findViewById(R.id.checkout_btn);
        progressBar = findViewById(R.id.progressbar);
        restaurantNameTv = findViewById(R.id.restaurant_name_tv);
        restaurantAddressTv = findViewById(R.id.restaurant_address_tv);
        totalPriceTv = findViewById(R.id.total_price_tv);
        minCheckoutTv = findViewById(R.id.min_checkout_tv);
        mapsIv = findViewById(R.id.maps_iv);
        restaurantIv = findViewById(R.id.restaurant_iv);
        productRv = findViewById(R.id.product_rv);

        checkoutBtn.setOnClickListener(this);
        mapsIv.setOnClickListener(this);

        restaurant = getRestaurant();

        restaurant.setProducts(getProducts());
        restaurantNameTv.setText(restaurant.getName());
        restaurantAddressTv.setText(restaurant.getAddress());
        progressBar.setMax((int)restaurant.getMinOrder() * 100);

        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this, restaurant.getProducts());
        adapter.setOnQuantityChangedListener(this);

        productRv.setAdapter(adapter);
        productRv.setLayoutManager(layoutManager);

        minCheckoutTv.setText(getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f",restaurant.getMinOrder())));

        //initialise stuff at 0
        progressBar.setProgress(0);

        totalPriceTv.setText(getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f",0F)));
        //TODO set all the appropriate icons
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
                } else
                    startActivity(new Intent(this, CheckoutActivity.class));
                break;
            case (R.id.maps_iv):
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=".concat(Uri.encode(restaurantAddressTv.getText().toString())))));
        }
    }


    private Restaurant getRestaurant(){
        return new Restaurant("UNRISTORANTEDAJAVA","via Casalvecchio, 7","description","imgUrl",42,8.4F);
    }

    private ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("hamburger", 0,1.0F));
        products.add(new Product("hamburger", 0,2.0F));
        products.add(new Product("hamburger", 0,1.0F));
        products.add(new Product("hamburger", 0,5.0F));
        products.add(new Product("hamburger", 0,3.0F));
        products.add(new Product("hamburger", 0,4.0F));
        products.add(new Product("hamburger", 0,1.0F));
        return products;
    }

    @Override
    public void onChange(float price) {
        Log.i("PREZZO",String.valueOf(price));
        total += price;
        updateUi(total);
        Log.i("PREZZO_TOTAL",String.valueOf(total));
    }

    private void updateUi(float total){
        totalPriceTv.setText(getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f",total)));
        enableCheckout(total);
        updateProgress((int)total*100);
    }

    private void updateProgress(int progress){
        progressBar.setProgress(progress);
    }

    public void enableCheckout(float total){
        checkoutBtn.setEnabled(total >= restaurant.getMinOrder());
        checkoutBtn.setTextColor(total >= restaurant.getMinOrder() ? getResources().getColor(R.color.primary_text) : getResources().getColor(R.color.secondary_text));
    }
}
