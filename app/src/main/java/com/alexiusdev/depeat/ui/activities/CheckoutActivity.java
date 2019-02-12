package com.alexiusdev.depeat.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alexiusdev.depeat.R;
import com.alexiusdev.depeat.ui.adapters.CheckoutAdapter;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nameTv, addressTv, priceTv;
    private Button payBtn;
    private CheckoutAdapter adapter;
    private LinearLayoutManager layoutManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        nameTv = findViewById(R.id.name_tv);
        addressTv = findViewById(R.id.address_tv);
        priceTv = findViewById(R.id.price_tv);
        payBtn = findViewById(R.id.pay_btn);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CheckoutAdapter(this);
    }

    @Override
    public void onClick(View view) {

    }
}
