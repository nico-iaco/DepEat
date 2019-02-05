package com.alexiusdev.depeat.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.alexiusdev.depeat.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_menu_restaurant_card);
    }

    @Override
    public void onClick(View view) {

    }
}
