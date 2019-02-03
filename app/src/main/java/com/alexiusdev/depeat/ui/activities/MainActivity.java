package com.alexiusdev.depeat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.alexiusdev.depeat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.alexiusdev.depeat.Utility.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MenuItem logoutMenuItem;
    private MenuItem loginMenuItem;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        if(getIntent().getExtras() != null)
            showToast(this, getString(R.string.welcome) + " " + getIntent().getExtras().getString(EMAIL_KEY));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        loginMenuItem = menu.findItem(R.id.login_menu);
        logoutMenuItem = menu.findItem(R.id.logout_menu);
        if(currentUser == null) {
            loginMenuItem.setVisible(true);
            logoutMenuItem.setVisible(false);
        } else {
            loginMenuItem.setVisible(false);
            logoutMenuItem.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case (R.id.login_menu):
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case (R.id.cart_menu):
                startActivity(new Intent(this, CartActivity.class));
                return true;
            case (R.id.logout_menu):
                mAuth.signOut();
                showToast(this,getString(R.string.user_logged_out));
                startActivity(new Intent(this,MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
