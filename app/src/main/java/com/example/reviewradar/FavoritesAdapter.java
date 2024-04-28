package com.example.reviewradar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<Restaurant> restaurants;

    @NonNull
    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_restaurant_post, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.bind(restaurant);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurantNameTV;
        private RatingBar ratingRB;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTV = itemView.findViewById(R.id.favRestaurantName);
            ratingRB = itemView.findViewById(R.id.favRestaurantRating);
        }

        public void bind(Restaurant restaurant) {
            restaurantNameTV.setText(restaurant.getName());
            ratingRB.setRating(restaurant.getAverageRating());
        }
    }
}
