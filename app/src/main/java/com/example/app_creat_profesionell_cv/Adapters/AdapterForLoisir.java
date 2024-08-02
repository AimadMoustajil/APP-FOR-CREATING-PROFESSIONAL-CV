package com.example.app_creat_profesionell_cv.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.Classes.Loisir;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterForLoisir extends RecyclerView.Adapter<AdapterForLoisir.ViewHolder> {

    ArrayList<Loisir> loisirArrayList;

    public AdapterForLoisir(ArrayList<Loisir> loisirArrayList) {
        this.loisirArrayList = loisirArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_add_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Loisir loisir = loisirArrayList.get(position);
        holder.loisir.setText(loisir.getLoisir()); // Assuming Loisir has a method getName()
    }

    @Override
    public int getItemCount() {
        return loisirArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText loisir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loisir = itemView.findViewById(R.id.newInfo);
        }
    }
}
