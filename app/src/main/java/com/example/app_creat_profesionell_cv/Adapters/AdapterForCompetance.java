package com.example.app_creat_profesionell_cv.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.Classes.Competance;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterForCompetance extends RecyclerView.Adapter<AdapterForCompetance.ViewHolder> {
    ArrayList<String> competanceArrayList;

    public AdapterForCompetance(ArrayList<String> competanceArrayList) {
        this.competanceArrayList = competanceArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_add_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String competance = competanceArrayList.get(position);
        holder.competnace.setText(competance); // Assuming Competance has a method getComptenace()
    }

    @Override
    public int getItemCount() {
        return competanceArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText competnace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            competnace = itemView.findViewById(R.id.newInfo);
        }
    }
}
