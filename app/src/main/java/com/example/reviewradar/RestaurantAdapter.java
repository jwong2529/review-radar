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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;

    private Context context;

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    public void filterRestaurants(List<Restaurant> filteredList) {
        this.restaurantList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_post, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {

        Restaurant restaurant = restaurantList.get(position);

        //Displaying restaurant images
        String imagePath = restaurant.getImageUrls().get(0);
        int resourceId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
        holder.restaurantImageIV.setImageResource(resourceId);

        holder.bind(restaurant);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewRestaurantPage.class);
                intent.putExtra("restaurantName", restaurant.getName()); //i added this, mb del
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView restaurantNameTextView;
        private TextView cuisineTypeTextView;
        private RatingBar restaurantPostRB;
        private ImageView restaurantImageIV;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurant_name);
            cuisineTypeTextView = itemView.findViewById(R.id.restaurant_cuisine_type);
            restaurantPostRB = itemView.findViewById(R.id.restaurantPostRating);
            restaurantImageIV = itemView.findViewById(R.id.restaurant_image);
        }

        public void bind(Restaurant restaurant) {
            restaurantNameTextView.setText(restaurant.getName());
            cuisineTypeTextView.setText(restaurant.getCuisineType());
            restaurantPostRB.setRating(restaurant.getAverageRating());
        }
    }
}

