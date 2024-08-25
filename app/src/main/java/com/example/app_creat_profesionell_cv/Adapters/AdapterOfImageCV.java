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

import com.bumptech.glide.Glide;
import com.example.app_creat_profesionell_cv.ContentOfCV.M1;
import com.example.app_creat_profesionell_cv.ContentOfCV.M10;
import com.example.app_creat_profesionell_cv.ContentOfCV.M11;
import com.example.app_creat_profesionell_cv.ContentOfCV.M12;
import com.example.app_creat_profesionell_cv.ContentOfCV.M13;
import com.example.app_creat_profesionell_cv.ContentOfCV.M14;
import com.example.app_creat_profesionell_cv.ContentOfCV.M15;
import com.example.app_creat_profesionell_cv.ContentOfCV.M16;
import com.example.app_creat_profesionell_cv.ContentOfCV.M17;
import com.example.app_creat_profesionell_cv.ContentOfCV.M2;
import com.example.app_creat_profesionell_cv.ContentOfCV.M20;
import com.example.app_creat_profesionell_cv.ContentOfCV.M3;
import com.example.app_creat_profesionell_cv.ContentOfCV.M4;
import com.example.app_creat_profesionell_cv.ContentOfCV.M5;
import com.example.app_creat_profesionell_cv.ContentOfCV.M8;
import com.example.app_creat_profesionell_cv.ContentOfCV.M9;
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

        // Use Glide to load the image
        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the image name as a toast
                Toast.makeText(context, "Clicked on image: " + imageName, Toast.LENGTH_SHORT).show();

                // Handle the click events for each image
                Intent intent = null;
                switch (imageName) {
                    case "m1":
                        intent = new Intent(context, M1.class);
                        break;
                    case "m2":
                        intent = new Intent(context, M2.class);
                        break;
                    case "m3":
                        intent = new Intent(context, M3.class);
                        break;
                    case "m4":
                        intent = new Intent(context, M4.class);
                        break;
                    case "m5":
                        intent = new Intent(context, M5.class);
                        break;
                    case "m11":
                        intent = new Intent(context, M8.class);
                        break;
                    case "m12":
                        intent = new Intent(context, M9.class);
                        break;
                    case "m14":
                        intent = new Intent(context, M10.class);
                        break;
                    case "m15":
                        intent = new Intent(context, M11.class);
                        break;
                    case "m16":
                        intent = new Intent(context, M12.class);
                        break;
                    case "m17":
                        intent = new Intent(context, M13.class);
                        break;
                    case "m21":
                        intent = new Intent(context, M14.class);
                        break;
                    case "m22":
                        intent = new Intent(context, M15.class);
                        break;
                    case "m23":
                        intent = new Intent(context, M16.class);
                        break;
                    case "m26":
                        intent = new Intent(context, M17.class);
                        break;
                    case "m29":
                        intent = new Intent(context, M20.class);
                        break;
                }

                if (intent != null) {
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
