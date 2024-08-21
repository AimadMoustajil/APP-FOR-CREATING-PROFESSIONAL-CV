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

import com.example.app_creat_profesionell_cv.M_LettersDesDémission.Career_Change_Resignation;
import com.example.app_creat_profesionell_cv.M_LettersDesDémission.Web_Designer;
import com.example.app_creat_profesionell_cv.M_LettersDesMotivation.*;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterOfLettreDeMotivation extends RecyclerView.Adapter<AdapterOfLettreDeMotivation.ViewHolder> {
    ArrayList<String> arrayList;
    Context context;

    public AdapterOfLettreDeMotivation(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_interview_question, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String jobTitle = arrayList.get(position);
        holder.titel.setText(jobTitle);
        holder.titel.setTextColor(Color.WHITE);
        holder.titel.setBackgroundColor(getColorForPosition(position));

        // Handle item click event
        holder.itemView.setOnClickListener(v -> {
            switch (jobTitle) {
                case "Registered Nurse":
                    startActivity(Registered_Nurse.class);
                    break;
                case "Research Assistant":
                    startActivity(Research_Assistant.class);
                    break;
                case "Resident Assistant":
                    startActivity(Resident_Assistant.class);
                    break;
                case "Residential Assistant":
                    startActivity(Residential_Assistant.class);
                    break;
                case "Restaurant Manager":
                    startActivity(Restaurant_Manager.class);
                    break;
                case "Risk Manager":
                    startActivity(Risk_Manager.class);
                    break;
                case "Sales":
                    startActivity(Sales.class);
                    break;
                case "Sales Manager":
                    startActivity(Sales_Manager.class);
                    break;
                case "Sales Representative":
                    startActivity(Sales_Representative.class);
                    break;
                case "School Counselor":
                    startActivity(School_Counselor.class);
                    break;
                case "School Social Worker":
                    startActivity(School_Social_Worker.class);
                    break;
                case "Scientist":
                    startActivity(Scientist.class);
                    break;
                case "Secretary":
                    startActivity(Secretary.class);
                    break;
                case "Security":
                    startActivity(Security.class);
                    break;
                case "Security Guard":
                    startActivity(Security_Guard.class);
                    break;
                case "Security Officer":
                    startActivity(Security_Officer.class);
                    break;
                case "Senior Social Worker":
                    startActivity(Senior_Social_Worker.class);
                    break;
                case "Server":
                    startActivity(Server.class);
                    break;
                case "Server Coordinator":
                    startActivity(Server_Coordinator.class);
                    break;
                case "Shift Supervisor":
                    startActivity(Shift_Supervisor.class);
                    break;
                case "Shift Technician":
                    startActivity(Shift_Technician.class);
                    break;
                case "Showroom Manager":
                    startActivity(Showroom_Manager.class);
                    break;
                case "Social Media Marketing Manager":
                    startActivity(Social_Media_Marketing_Manager.class);
                    break;
                case "Social Worker":
                    startActivity(Social_Worker.class);
                    break;
                case "Software Developer":
                    startActivity(Software_Developer.class);
                    break;
                case "Software Engineer":
                    startActivity(Software_Engineer.class);
                    break;
                case "Special Education Teacher":
                    startActivity(Special_Education_Teacher.class);
                    break;
                case "Speech Language Pathologist":
                    startActivity(Speech_Language_Pathologist.class);
                    break;
                case "Substitute Teacher":
                    startActivity(Substitute_Teacher.class);
                    break;
                case "Supervisor":
                    startActivity(Supervisor.class);
                    break;
                case "Teacher Aide":
                    startActivity(Teacher_Aide.class);
                    break;
                case "Teaching Assistant":
                    startActivity(Teaching_Assistant.class);
                    break;
                case "Technical Support":
                    startActivity(Technical_Support.class);
                    break;
                case "Training Developer":
                    startActivity(Training_Developer.class);
                    break;
                case "Veterinarian":
                    startActivity(Veterinarian.class);
                    break;
                case "Veterinary Assistant":
                    startActivity(Veterinary_Assistant.class);
                    break;
                case "Veterinary Technician":
                    startActivity(Veterinary_Technician.class);
                    break;
                case "Warehouse":
                    startActivity(Warehouse_Worker.class);
                    break;
                case "Web Developer":
                    startActivity(Web_Designer.class);
                    break;
                case "Web Manager":
                    startActivity(Web_Manager.class);
                    break;
                case "Welder":
                    startActivity(Welder.class);
                    break;
                case "Writer":
                    startActivity(Writer.class);
                    break;
                default:
                    // Handle default case or unknown job titles
                    // Add your default action here
                    break;
            }
        });
    }

    private void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // Helper method to return a color based on the position
    private int getColorForPosition(int position) {
        // Array of colors
        int[] colors = {
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
        // Return a color from the array, cycling through if the position exceeds the array length
        return colors[position % colors.length];
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titel = itemView.findViewById(R.id.title);
        }
    }
}

