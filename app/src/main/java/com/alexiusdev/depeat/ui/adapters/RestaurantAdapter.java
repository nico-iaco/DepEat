package com.alexiusdev.depeat.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexiusdev.depeat.R;
import com.alexiusdev.depeat.datamodels.Restaurant;
import com.alexiusdev.depeat.ui.activities.ShopActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.alexiusdev.depeat.ui.Utility.RESTAURANT_ADDRESS;
import static com.alexiusdev.depeat.ui.Utility.RESTAURANT_ID;
import static com.alexiusdev.depeat.ui.Utility.RESTAURANT_IMAGE_URL;
import static com.alexiusdev.depeat.ui.Utility.RESTAURANT_MIN_ORDER;
import static com.alexiusdev.depeat.ui.Utility.RESTAURANT_NAME;

public class RestaurantAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private List<Restaurant> restaurants;
    private boolean isGridMode;
    private Context context;

    public RestaurantAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.restaurants = new ArrayList<>();
        this.context = context;
    }

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        inflater = LayoutInflater.from(context);
        this.restaurants = restaurants;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layout = isGridMode ? R.layout.item_main_grid : R.layout.item_main_cards;
        View view = inflater.inflate(layout, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int index) {
        RestaurantViewHolder restaurantViewHolder = (RestaurantViewHolder) viewHolder;
        restaurantViewHolder.restaurantNameTV.setText(restaurants.get(index).getName());
        restaurantViewHolder.restaurantAddressTV.setText(restaurants.get(index).getAddress());
        restaurantViewHolder.restaurantMinOrderTV.setText(context.getString(R.string.currency).concat(String.format(Locale.getDefault(),"%.2f",restaurants.get(index).getMinOrder())));
        restaurantViewHolder.restaurantRatingTV.setText(String.format(Locale.getDefault(),"%.1f", restaurants.get(index).getRatingFloat()));
        restaurantViewHolder.ratingBar.setProgress(restaurants.get(index).getRatingInt());
        Glide.with(context).load(restaurants.get(index).getImageUrl()).into(restaurantViewHolder.restaurantIV);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView restaurantNameTV, restaurantAddressTV, restaurantMinOrderTV, restaurantRatingTV;
        private RatingBar ratingBar;
        private MaterialCardView restaurantCard;
        private ImageView restaurantIV;

        private RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTV = itemView.findViewById(R.id.name_tv);
            restaurantAddressTV = itemView.findViewById(R.id.restaurant_address_tv);
            restaurantMinOrderTV = itemView.findViewById(R.id.min_order_value);
            restaurantRatingTV = itemView.findViewById(R.id.rating_number_tv);
            ratingBar = itemView.findViewById(R.id.rating_stars);
            restaurantIV = itemView.findViewById(R.id.logo_iv);
            restaurantCard = itemView.findViewById(R.id.restaurant_card);
            restaurantCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == restaurantCard.getId()){
                context.startActivity(new Intent(context, ShopActivity.class)
                        .putExtra(RESTAURANT_NAME, restaurants.get(getAdapterPosition()).getName())
                        .putExtra(RESTAURANT_ADDRESS, restaurants.get(getAdapterPosition()).getAddress())
                        .putExtra(RESTAURANT_IMAGE_URL, restaurants.get(getAdapterPosition()).getImageUrl())
                        .putExtra(RESTAURANT_MIN_ORDER, restaurants.get(getAdapterPosition()).getMinOrder())
                        .putExtra(RESTAURANT_ID, restaurants.get(getAdapterPosition()).getId()));
            }
        }
    }

    public boolean isGridMode() {
        return isGridMode;
    }

    public void setGridMode(boolean gridMode) {
        isGridMode = gridMode;
    }

}