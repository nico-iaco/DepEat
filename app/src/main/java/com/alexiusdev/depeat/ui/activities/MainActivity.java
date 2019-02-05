package com.alexiusdev.depeat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.alexiusdev.depeat.R;
import com.alexiusdev.depeat.datamodels.Restaurant;
import com.alexiusdev.depeat.ui.adapters.RestaurantAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.alexiusdev.depeat.ui.Utility.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MenuItem logoutMenuItem;
    private MenuItem loginMenuItem;
    private MenuItem gridViewMenuItem;
    private MenuItem cardViewMenuItem;
    private FirebaseAuth mAuth;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;

    FirebaseUser currentUser;
    RecyclerView restaurantRV;
    ArrayList<Restaurant> restaurantList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cardview);

        restaurantRV = findViewById(R.id.restaurant_rv);
        restaurantRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RestaurantAdapter(this,getData());
        restaurantRV.setAdapter(adapter);

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
        gridViewMenuItem = menu.findItem(R.id.grid_menu);
        cardViewMenuItem = menu.findItem(R.id.card_menu);
        if(currentUser == null) {
            loginMenuItem.setVisible(true);
            logoutMenuItem.setVisible(false);
        } else {
            loginMenuItem.setVisible(false);
            logoutMenuItem.setVisible(true);
        }if(adapter.isGridMode()){
            cardViewMenuItem.setVisible(true);
            gridViewMenuItem.setVisible(false);
        }else{
            cardViewMenuItem.setVisible(false);
            gridViewMenuItem.setVisible(true);
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
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case (R.id.grid_menu):
                cardViewMenuItem.setVisible(true);
                gridViewMenuItem.setVisible(false);
                setLayoutManager();
                return true;
            case (R.id.card_menu):
                cardViewMenuItem.setVisible(false);
                gridViewMenuItem.setVisible(true);
                setLayoutManager();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
    }

    private ArrayList<Restaurant> getData(){
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(3.7F,"KFC",29,"via dei Tulipani, 7"));
        restaurantList.add(new Restaurant(8.2F,"McDonald",35,"via dei Girasoli, 147"));
        restaurantList.add(new Restaurant(5.6F,"Burger King",24,"via dei Gerani, 36"));
        restaurantList.add(new Restaurant(4.2F,"Makkitella Food",28,"via dei Lambruschi, 31"));
        restaurantList.add(new Restaurant(1.2F,"567",50,"via delle Camelie, 287"));
        restaurantList.add(new Restaurant(4.5F,"Subway",17,"via dei Narcisi, 75"));
        return restaurantList;
    }

    private void setLayoutManager(){
        layoutManager = adapter.isGridMode() ? new LinearLayoutManager(this) : new GridLayoutManager(this,2);
        adapter.setGridMode(!adapter.isGridMode());
        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);
    }
}
