package com.example.app_creat_profesionell_cv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Assistant_Department_Head_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Editor_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Enveronmental_Solutions_Surpervisor;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Managers_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Marketing_Head_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Project_Head_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Research_Manager_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Salary_Hike_Sample;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Salary_Hike_Sample_2;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Salary_Review_Account_Executive;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Sales_representative_promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Sample_1_Job_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Sample_2_Job_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Sample_3_Job_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Senior_Manager_Promotion;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Six_Month_Review;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Software_Develepement;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Supervisor_Promotion_and_Salary_Hike;
import com.example.app_creat_profesionell_cv.M_LetterDesPromorion.Years_Experience_Job_Promotion;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptersOfLetterDePromotion extends RecyclerView.Adapter<AdaptersOfLetterDePromotion.ViewHolder> {
    private ArrayList<String> nameOfModels;
    private List<Integer> predefinedColors;
    private Context context; // Add this field

    // Constructor
    public AdaptersOfLetterDePromotion(Context context, ArrayList<String> nameOfModels) {
        this.context = context; // Initialize the context
        this.nameOfModels = nameOfModels;
        // Define a list of colors
        predefinedColors = List.of(
                Color.parseColor("#FF5722"), // Deep Orange
                Color.parseColor("#4CAF50"), // Green
                Color.parseColor("#2196F3"), // Blue
                Color.parseColor("#9C27B0"), // Purple
                Color.parseColor("#FFC107")  // Amber
        );
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

        // Set a predefined color for each item
        int color = predefinedColors.get(position % predefinedColors.size());
        holder.title.setBackgroundColor(color);
        holder.title.setTextColor(Color.WHITE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedItemText = holder.title.getText().toString();
                if (clickedItemText.equals("Research Manager Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Research_Manager_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Six Month Review")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Six_Month_Review.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Sample 1: Job Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Sample_1_Job_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Sample 2: Job Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Sample_2_Job_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Sample 3: Job Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Sample_3_Job_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Assistant Department Head Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Assistant_Department_Head_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Supervisor: Promotion and Salary Hike")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Supervisor_Promotion_and_Salary_Hike.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Software Develepement")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Software_Develepement.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("5 Years Experience : Job Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Years_Experience_Job_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Marketing Head Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Marketing_Head_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Senior Manager Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Senior_Manager_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Salary Hike: Sample 1")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Salary_Hike_Sample.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Salary Hike: Sample 2")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Salary_Hike_Sample_2.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Project Head Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Project_Head_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Enveronmental Solutions Surpervisor")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Enveronmental_Solutions_Surpervisor.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Editor Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Editor_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Sales representative promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Sales_representative_promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Managers Promotion")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Managers_Promotion.class);
                    context.startActivity(intent);
                }
                if (clickedItemText.equals("Salary Review : Account Executive")) {
                    // Use the context to start the new activity
                    Intent intent = new Intent(context, Salary_Review_Account_Executive.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the size of the data set
        return nameOfModels.size();
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
