package com.example.app_creat_profesionell_cv.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.Classes.SoftSkills;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterForSoftSkills extends RecyclerView.Adapter<AdapterForSoftSkills.ViewHolder> {

    ArrayList<SoftSkills> softSkillsArrayList;

    public AdapterForSoftSkills(ArrayList<SoftSkills> softSkillsArrayList) {
        this.softSkillsArrayList = softSkillsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_add_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SoftSkills softSkills = softSkillsArrayList.get(position);
        holder.softSkills.setText(softSkills.getSoftSkills()); // Assuming SoftSkills has a method getSkillName()
    }

    @Override
    public int getItemCount() {
        return softSkillsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText softSkills;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            softSkills = itemView.findViewById(R.id.newInfo);
        }
    }
}
