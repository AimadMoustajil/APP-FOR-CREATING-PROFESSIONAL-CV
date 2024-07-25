package com.example.app_creat_profesionell_cv.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.Classes.InfoEducation;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterForAddEducation extends RecyclerView.Adapter<AdapterForAddEducation.ViewHolder> {

    ArrayList<InfoEducation> educations;

    public AdapterForAddEducation(ArrayList<InfoEducation> educations) {
        this.educations = educations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_info_education, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfoEducation infoEducation = educations.get(position);
        holder.nameOfSchool.setText(infoEducation.getShool());
        holder.yourMetier.setText(infoEducation.getMetier());
        holder.start.setText(infoEducation.getStartYier());
        holder.Finich.setText(infoEducation.getEndYier());
    }

    @Override
    public int getItemCount() {
        return educations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText nameOfSchool,yourMetier,start,Finich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfSchool = itemView.findViewById(R.id.nameOfSchool);
            yourMetier = itemView.findViewById(R.id.yourMetier);
            start = itemView.findViewById(R.id.start);
            Finich = itemView.findViewById(R.id.Finich);
        }

    }
}
