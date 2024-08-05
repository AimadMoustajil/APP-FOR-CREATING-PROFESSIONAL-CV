package com.example.app_creat_profesionell_cv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.ContentOfCV.ContentOfInformationCv;
import com.example.app_creat_profesionell_cv.ContentOfCV.M1;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterOfImageCV extends RecyclerView.Adapter<AdapterOfImageCV.ImageViewHolder> {
    private final ArrayList<Integer> images;
    private final ArrayList<String> imageNames;
    private final Context context;

    public AdapterOfImageCV(ArrayList<Integer> images, ArrayList<String> imageNames, Context context) {
        this.images = images;
        this.imageNames = imageNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_cv, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Integer imageUrl = images.get(position);
        String imageName = imageNames.get(position);
        holder.imageView.setImageResource(imageUrl);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the image name as a toast
                Toast.makeText(context, "Clicked on image: " + imageName, Toast.LENGTH_SHORT).show();
                if (imageName.equals("m31")){
                    Intent intent = new Intent(context, ContentOfInformationCv.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m1")){
                    Intent intent = new Intent(context, M1.class);
                    context.startActivity(intent);
                }
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
