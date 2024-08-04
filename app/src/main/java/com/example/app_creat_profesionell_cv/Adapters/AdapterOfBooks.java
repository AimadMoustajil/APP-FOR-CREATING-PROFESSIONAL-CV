package com.example.app_creat_profesionell_cv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.R;
import com.example.app_creat_profesionell_cv.ReadBooksActivity;
import com.example.app_creat_profesionell_cv.ReadQuestionsInterviewsActivity;

import java.util.ArrayList;

public class AdapterOfBooks extends RecyclerView.Adapter<AdapterOfBooks.ViewHolder> {

    private final ArrayList<String> urlOfImages;

    public AdapterOfBooks(ArrayList<String> urlOfImages) {
        this.urlOfImages = urlOfImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_for_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageName = urlOfImages.get(position);
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        holder.imageView.setImageResource(imageResId);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageName == "a"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","60_Seconds_and_You're_hired!");
                    context.startActivity(intent);
                }
                if (imageName == "b"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","Cracking the coding interview");
                    context.startActivity(intent);
                }
                if (imageName == "c"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","Get That Job");
                    context.startActivity(intent);
                }
                if (imageName == "d"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","Ignite Your LinkedIn Profile");
                    context.startActivity(intent);
                }
                if (imageName == "e"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","Interview Questions");
                    context.startActivity(intent);
                }
                if (imageName == "f"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","Mark as downloadedTéléchargé");
                    context.startActivity(intent);
                }
                if (imageName == "g"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","The Reverse Job Search Method");
                    context.startActivity(intent);
                }
                if (imageName == "h"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","The STAR Interview");
                    context.startActivity(intent);
                }
                if (imageName == "k"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","The Ultimate LinkedIn Messaging Guide");
                    context.startActivity(intent);
                }
                if (imageName == "l"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","Over-40 Job Search Guide 10 Strategies for Making Your Age an Advantage in Your Career (Gail Geary) (Z-Library)");
                    context.startActivity(intent);
                }
                if (imageName == "m"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","The complete hiring and firing handbook every managers guide to working with employees legally (Charles H. Fleischer) (Z-Library).pdf");
                    context.startActivity(intent);
                }
                if (imageName == "n"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","HR for Small Business, 2E An Essential Guide for Managers, Human Resources Professionals, and Small Business Owners (Quick... (Charles Fleischer) (Z-Library).pdf");
                    context.startActivity(intent);
                }
                if (imageName == "o"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","Powerful Phrases for Successful Interviews (Tony Beshara, Phil McGraw) (Z-Library).pdf");
                    context.startActivity(intent);
                }
                if (imageName == "p"){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReadBooksActivity.class);
                    intent.putExtra("book","Over-40 Job Search Guide 10 Strategies for Making Your Age an Advantage in Your Career (Gail Geary) (Z-Library).pdf");
                    context.startActivity(intent);
                }
            }
            /*
            *  * a = 60_Seconds_and_You're_hired!
             * b = Cracking the coding interview
             * c = Get That Job
             * d = Ignite Your LinkedIn Profile
             * e = Interview Questions
             * f = Mark as downloadedTéléchargé
             * g = The Reverse Job Search Method
             * h = The STAR Interview
             * k = The Ultimate LinkedIn Messaging Guide*/
        });
    }

    @Override
    public int getItemCount() {
        return urlOfImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView5);
        }
    }
}
