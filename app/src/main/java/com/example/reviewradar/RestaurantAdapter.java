package com.example.reviewradar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewradar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private Map<String, String> restaurantMap;
    private List<String> restaurantNames;

    public RestaurantAdapter(Map<String, String> restaurantMap) {
        this.restaurantMap = restaurantMap;
        this.restaurantNames = new ArrayList<>(restaurantMap.keySet());
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_post, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        String restaurantName = restaurantNames.get(position);
        String cuisineType = restaurantMap.get(restaurantName);
        holder.bind(restaurantName, cuisineType);
    }

    @Override
    public int getItemCount() {
        return restaurantMap.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView restaurantNameTextView;
        private TextView cuisineTypeTextView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurant_title);
            cuisineTypeTextView = itemView.findViewById(R.id.restaurant_cuisine_type);
        }

        public void bind(String restaurantName, String cuisineType) {
            restaurantNameTextView.setText(restaurantName);
            cuisineTypeTextView.setText(cuisineType);
        }
    }
}

