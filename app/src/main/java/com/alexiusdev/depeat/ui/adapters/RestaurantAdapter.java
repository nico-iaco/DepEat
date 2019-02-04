package com.alexiusdev.depeat.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.alexiusdev.depeat.R;
import com.alexiusdev.depeat.datamodels.Restaurant;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

public class RestaurantAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private ArrayList<Restaurant> data;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_restaurant_cards, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int index) {
        Context context;
        RestaurantViewHolder restaurantViewHolder = (RestaurantViewHolder) viewHolder;
        restaurantViewHolder.restaurantNameTV.setText(data.get(index).getName());
        restaurantViewHolder.restaurantAddressTV.setText(data.get(index).getAddress());
        restaurantViewHolder.restaurantMinCheckoutValueTV.append(data.get(index).getMinOrder().toString());
        restaurantViewHolder.restaurantRatingTV.setText(String.format(Locale.getDefault(),"%.1f", data.get(index).getRatingFloat()));
        restaurantViewHolder.ratingBar.setProgress(data.get(index).getRatingInt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{
        public TextView restaurantNameTV;
        public TextView restaurantAddressTV;
        public TextView restaurantMinCheckoutValueTV;
        public TextView restaurantRatingTV;
        public RatingBar ratingBar;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTV = itemView.findViewById(R.id.name_tv);
            restaurantAddressTV = itemView.findViewById(R.id.address_tv);
            restaurantMinCheckoutValueTV = itemView.findViewById(R.id.min_checkout_value);
            restaurantRatingTV = itemView.findViewById(R.id.rating_number_tv);
            ratingBar = itemView.findViewById(R.id.rating_stars);
        }
    }
}
