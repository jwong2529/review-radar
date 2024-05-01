package com.example.reviewradar;

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

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<String> restaurants;

    private Context context;

    public FavoritesAdapter(List<String> restaurants, Context context) {
        this.restaurants = restaurants;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_restaurant_post, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        String restaurantName = restaurants.get(position);
        Restaurant restaurant = AccessData.restaurantMap.get(restaurantName);

        String imagePath = restaurant.getImageUrls().get(0);
        int resourceId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
        holder.restaurantImageIV.setImageResource(resourceId);

        holder.bind(restaurantName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewRestaurantPage.class);
                intent.putExtra("restaurantName", restaurantName);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurantNameTV;
        private RatingBar ratingRB;

        private ImageView restaurantImageIV;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTV = itemView.findViewById(R.id.favRestaurantName);
            ratingRB = itemView.findViewById(R.id.favRestaurantRating);
            restaurantImageIV = itemView.findViewById(R.id.favRestaurantImage);
        }

        public void bind(String restaurantName) {
            Restaurant restaurant = AccessData.restaurantMap.get(restaurantName);
            restaurantNameTV.setText(restaurant.getName());
            ratingRB.setRating(restaurant.getAverageRating());
        }
    }
}
