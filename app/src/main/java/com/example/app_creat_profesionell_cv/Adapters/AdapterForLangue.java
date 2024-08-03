package com.example.app_creat_profesionell_cv.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.Classes.Langue;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterForLangue extends RecyclerView.Adapter<AdapterForLangue.ViewHolder> {

    ArrayList<String> langueArrayList;

    public AdapterForLangue(ArrayList<String> langueArrayList) {
        this.langueArrayList = langueArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_add_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String langue = langueArrayList.get(position);
        holder.newLanguage.setText(langue); // Assuming Langue has a method getName()
    }

    @Override
    public int getItemCount() {
        return langueArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText newLanguage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newLanguage = itemView.findViewById(R.id.newInfo);
        }
    }
}
