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
import com.example.app_creat_profesionell_cv.ContentOfCV.M10;
import com.example.app_creat_profesionell_cv.ContentOfCV.M11;
import com.example.app_creat_profesionell_cv.ContentOfCV.M12;
import com.example.app_creat_profesionell_cv.ContentOfCV.M13;
import com.example.app_creat_profesionell_cv.ContentOfCV.M14;
import com.example.app_creat_profesionell_cv.ContentOfCV.M2;
import com.example.app_creat_profesionell_cv.ContentOfCV.M3;
import com.example.app_creat_profesionell_cv.ContentOfCV.M4;
import com.example.app_creat_profesionell_cv.ContentOfCV.M5;
import com.example.app_creat_profesionell_cv.ContentOfCV.M6;
import com.example.app_creat_profesionell_cv.ContentOfCV.M7;
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
                if (imageName.equals("m2")){
                    Intent intent = new Intent(context, M2.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m3")){
                    Intent intent = new Intent(context, M3.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m4")){
                    Intent intent = new Intent(context, M4.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m5")){
                    Intent intent = new Intent(context, M5.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m7")){
                    Intent intent = new Intent(context, M6.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m10")){
                    Intent intent = new Intent(context, M7.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m11")){
                    Intent intent = new Intent(context, M8.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m12")){
                    Intent intent = new Intent(context, M9.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m14")){
                    Intent intent = new Intent(context, M10.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m15")){
                    Intent intent = new Intent(context, M11.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m16")){
                    Intent intent = new Intent(context, M12.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m17")){
                    Intent intent = new Intent(context, M13.class);
                    context.startActivity(intent);
                }
                if (imageName.equals("m19")){
                    Intent intent = new Intent(context, M14.class);
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
