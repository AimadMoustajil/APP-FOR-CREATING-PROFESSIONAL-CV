package com.example.app_creat_profesionell_cv.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_creat_profesionell_cv.ContentOfCV.ContentOfInformationCv;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterOfImageCV extends RecyclerView.Adapter<AdapterOfImageCV.ImageViewHolder> {
    private final ArrayList<Integer> images;
    private final Context context;

    public AdapterOfImageCV(ArrayList<Integer> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_cv, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Integer imageUrl = images.get(position);
        holder.imageView.setImageResource(imageUrl);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, ContentOfInformationCv.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
