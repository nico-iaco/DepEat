package com.alexiusdev.depeat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alexiusdev.depeat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.alexiusdev.depeat.ui.Utility.*;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button checkout_btn;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();
        checkout_btn = findViewById(R.id.checkout_btn);

        checkout_btn.setOnClickListener(this);


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
}
