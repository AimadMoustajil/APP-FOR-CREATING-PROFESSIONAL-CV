package com.example.app_creat_profesionell_cv.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;
import java.util.Random;

public class AdaptersOfLetterDePromotion extends RecyclerView.Adapter<AdaptersOfLetterDePromotion.ViewHolder> {
    ArrayList<String> nameOfModels;

    // Constructor
    public AdaptersOfLetterDePromotion(ArrayList<String> nameOfModels) {
        this.nameOfModels = nameOfModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create a new ViewHolder
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_interview_question, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the item at the current position
        String typeDeletter = nameOfModels.get(position);
        holder.title.setText(typeDeletter);

        // Generate a dark color for each item
        int color = generateDarkColor();
        holder.title.setBackgroundColor(color);
        holder.title.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        // Return the size of the data set
        return nameOfModels.size();
    }

    // Method to generate a dark color
    private int generateDarkColor() {
        Random random = new Random();
        int r = random.nextInt(180); // Red value (0-100), making it dark
        int g = random.nextInt(180); // Green value (0-100), making it dark
        int b = random.nextInt(180); // Blue value (0-100), making it dark
        return Color.rgb(r, g, b); // Return the dark color
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
