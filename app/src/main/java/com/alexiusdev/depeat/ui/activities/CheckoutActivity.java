package com.alexiusdev.depeat.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alexiusdev.depeat.R;
import static com.alexiusdev.depeat.ui.Utility.*;

import com.alexiusdev.depeat.datamodels.Product;
import com.alexiusdev.depeat.ui.adapters.CheckoutAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameTv, priceTv;
    private Button payBtn;
    private CheckoutAdapter adapter;
    private ArrayList<Product> products;
    private RecyclerView checkoutRv;


    @SuppressWarnings({"unchecked","ConstantConditions"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        nameTv = findViewById(R.id.name_tv);
        payBtn = findViewById(R.id.pay_btn);
        checkoutRv = findViewById(R.id.product_rv);
        priceTv = findViewById(R.id.price_tv);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().get(RESTAURANT_PRODUCTS) instanceof ArrayList){ //get products from intent
            nameTv.setText(getIntent().getExtras().getString(RESTAURANT_NAME));
            products = (ArrayList<Product>) getIntent().getExtras().get(RESTAURANT_PRODUCTS);
            priceTv.append(" ".concat(getString(R.string.currency)).concat(String.format(Locale.getDefault(), "%.2f", getIntent().getExtras().getDouble(PRICE))));
            for(Iterator<Product> i = products.iterator(); i.hasNext();){ //remove empty products from the ArrayList
                while(i.hasNext()) {
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

    @Override
    public void onClick(View view) {
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}
