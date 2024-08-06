package com.example.app_creat_profesionell_cv.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapteForCertificate extends RecyclerView.Adapter<AdapterForSoftSkills.ViewHolder> {
    ArrayList<String> certeficateArrayList;

    public AdapteForCertificate(ArrayList<String > certeficateArrayList) {
        this.certeficateArrayList = certeficateArrayList;
    }

    @NonNull
    @Override
    public AdapterForSoftSkills.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_add_information, parent, false);
        return new AdapterForSoftSkills.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForSoftSkills.ViewHolder holder, int position) {
        String softSkills = certeficateArrayList.get(position);
        holder.softSkills.setText(softSkills); // Assuming SoftSkills has a method getSkillName()
    }

    @Override
    public int getItemCount() {
        return certeficateArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText softSkills;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            softSkills = itemView.findViewById(R.id.newInfo);
        }
    }
}
