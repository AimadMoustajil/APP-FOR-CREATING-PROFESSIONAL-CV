package com.example.app_creat_profesionell_cv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_interview_question,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nameOfLettreDeMotivation = arrayList.get(position);
        holder.titel.setText(nameOfLettreDeMotivation);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titel = itemView.findViewById(R.id.title);
        }
    }
}
