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

import com.example.app_creat_profesionell_cv.M_LettersDesDémission.*;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterOfLettreDeDémission extends RecyclerView.Adapter<AdapterOfLettreDeDémission.ViewHolder> {
    private final ArrayList<String> stringArrayList;
    private final Context context;

    // Map to store letter type and corresponding activity class
    private final Map<String, Class<?>> letterTypeToActivityMap = new HashMap<>();

    private final int[] colors = {
            Color.parseColor("#FF5722"), // Deep Orange
            Color.parseColor("#4CAF50"), // Green
            Color.parseColor("#2196F3"), // Blue
            Color.parseColor("#9C27B0"), // Purple
            Color.parseColor("#FFC107"), // Amber
            Color.parseColor("#E91E63"), // Pink
            Color.parseColor("#00BCD4"), // Cyan
            Color.parseColor("#FF9800"), // Orange
            Color.parseColor("#009688"), // Teal
            Color.parseColor("#3F51B5"), // Indigo
            Color.parseColor("#FFEB3B"), // Yellow
            Color.parseColor("#8BC34A"), // Light Green
            Color.parseColor("#FF5722"), // Deep Orange
            Color.parseColor("#9E9E9E")  // Grey
    };

    public AdapterOfLettreDeDémission(ArrayList<String> stringArrayList, Context context) {
        this.stringArrayList = stringArrayList;
        this.context = context;
        populateLetterTypeToActivityMap();
    }

    private void populateLetterTypeToActivityMap() {
        // Populate the map with letter types and their corresponding activity classes
        letterTypeToActivityMap.put("One Week Notice : Sales Executive", One_Week_Notice_Sales_Executive.class);
        letterTypeToActivityMap.put("Sample 1: Two Week Notice", Sample_1_Two_Week_Notice.class);
        letterTypeToActivityMap.put("Sample 2: Two Week Notice", Sample_2_Two_Week_Notice.class);
        letterTypeToActivityMap.put("Sample 3: Two Week Notice", Sample_3_Two_Week_Notice.class);
        letterTypeToActivityMap.put("Sample 1: One Month Notice", Sample_1_One_Month_Notice.class);
        letterTypeToActivityMap.put("Sample 1: Short Resignation", Sample_1_Short_Resignation.class);
        letterTypeToActivityMap.put("Sample 2: Short Resignation", Sample_2_Short_Resignation.class);
        letterTypeToActivityMap.put("Sample 1: Simple Resignation", Sample_1_Simple_Resignation.class);
        letterTypeToActivityMap.put("Sample 2: Simple Resignation", Sample_2_Simple_Resignation.class);
        letterTypeToActivityMap.put("Formal Resignation", Formal_Resignation.class);
        letterTypeToActivityMap.put("Sample 1: Professional Resignation", Sample_1_Professional_Resignation.class);
        letterTypeToActivityMap.put("Sample 2: Professional Resignation", Sample_2_Professional_Resignation.class);
        letterTypeToActivityMap.put("Sample 3: Professional Resignation", Sample_3_Professional_Resignation.class);
        letterTypeToActivityMap.put("Teacher Resignation", Teacher_Resignation.class);
        letterTypeToActivityMap.put("Nurse Resignation", Nurse_Resignation.class);
        letterTypeToActivityMap.put("Board Resignation", Board_Resignation.class);
        letterTypeToActivityMap.put("Police Resignation", Police_Resignation.class);
        letterTypeToActivityMap.put("Church Resignation", Church_Resignation.class);
        letterTypeToActivityMap.put("Career Change Resignation", Career_Change_Resignation.class);
        letterTypeToActivityMap.put("Promotion Letter of Resignation", Promotion_Letter_of_Resignation.class);
        letterTypeToActivityMap.put("Accountant Resignation", Accountant_Resignation.class);
        letterTypeToActivityMap.put("Analyst Resignation", Analyst_Resignation.class);
        letterTypeToActivityMap.put("Customer Service Manager Resignation", Customer_Service_Manager_Resignation.class);
        letterTypeToActivityMap.put("Data Analyst Resignation", Data_Analyst_Resignation.class);
        letterTypeToActivityMap.put("Content Creator Resignation", Content_Creator_Resignation.class);
        letterTypeToActivityMap.put("Bank Employee Resignation", Bank_Employee_Resignation.class);
        letterTypeToActivityMap.put("Sample 1: Immediate Resignation", Sample_1_Immediate_Resignation.class);
        letterTypeToActivityMap.put("Sample 2: Immediate Resignation", Sample_2_Immediate_Resignation.class);
        letterTypeToActivityMap.put("Medical Assistant Resignation", Medical_Assistant_Resignation.class);
        letterTypeToActivityMap.put("New Job Resignation", New_Job_Resignation.class);
        letterTypeToActivityMap.put("Email Resignation", Email_Resignation.class);
        letterTypeToActivityMap.put("Employee Resignation", Employee_Resignation.class);
        letterTypeToActivityMap.put("Personal Reason Resignation", Personal_Reason_Resignation.class);
        letterTypeToActivityMap.put("Assistant Sales Manager Resignation", Assistant_Sales_Manager_Resignation.class);
        letterTypeToActivityMap.put("Doctor Resignation", Doctor_Resignation.class);
        letterTypeToActivityMap.put("Writer Resignation Letter", Writer_Resignation_Letter.class);
        letterTypeToActivityMap.put("Technical Executive Resignation", Technical_Executive_Resignation.class);
        letterTypeToActivityMap.put("Senior Sales Executive Resignation", Senior_Sales_Executive_Resignation.class);
        letterTypeToActivityMap.put("Retirement Resignation", Retirement_Resignation.class);
        letterTypeToActivityMap.put("Marketing Manager With Reasons", Marketing_Manager_With_Reasons.class);
        letterTypeToActivityMap.put("Marketing Manager", Marketing_Manager.class);
        letterTypeToActivityMap.put("Director Resignation", Director_Resignation.class);
        letterTypeToActivityMap.put("Assistant Manager Of Operation", Assistant_Manager_Of_Operation.class);
        letterTypeToActivityMap.put("Administrative Assistant", Administrative_Assistant.class);
        letterTypeToActivityMap.put("Board Of Industry Resignation", Board_Of_Industry_Resignation.class);
        letterTypeToActivityMap.put("Lecture Resignation", Lecture_Resignation.class);
        letterTypeToActivityMap.put("Sales Manager and Marketing", Sales_Manager_and_Marketing.class);
        letterTypeToActivityMap.put("Deputy Manager (HR)", Deputy_Manager_HR.class);
        letterTypeToActivityMap.put("Sales Support", Sales_Support.class);
        letterTypeToActivityMap.put("Senior Assistant Accountant", Senior_Assistant_Accountant.class);
        letterTypeToActivityMap.put("Web Designer", Web_Designer.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_interview_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nameOfTypeLettreDeDémission = stringArrayList.get(position);
        holder.title.setText(nameOfTypeLettreDeDémission);

        // Set the background color based on position
        int colorIndex = position % colors.length; // To loop through colors
        holder.title.setBackgroundColor(colors[colorIndex]);
        holder.title.setTextColor(Color.WHITE);

        // Handle specific cases based on text content
        Class<?> activityClass = letterTypeToActivityMap.get(nameOfTypeLettreDeDémission);
        if (activityClass != null) {
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, activityClass);
                context.startActivity(intent);
            });
        } else {
            holder.title.setTextColor(Color.WHITE); // Ensure default text color is white
        }
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
