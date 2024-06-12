package com.example.app_creat_profesionell_cv.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.Classes.TitleQuestion;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;

public class AdapterForQuestionInterview extends RecyclerView.Adapter<AdapterForQuestionInterview.MyTitleViewHolder> {

    ArrayList<TitleQuestion> titleQuestions;

    public AdapterForQuestionInterview(ArrayList<TitleQuestion> titleQuestions) {
        this.titleQuestions = titleQuestions;
    }

    @NonNull
    @Override
    public MyTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_of_interview_question, parent, false);
        return new MyTitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTitleViewHolder holder, int position) {
        // Bind the data to the ViewHolder
        TitleQuestion titleQuestion = titleQuestions.get(position);
        holder.title.setText(titleQuestion.getTitle());
    }

    @Override
    public int getItemCount() {
        return titleQuestions.size();
    }

    class MyTitleViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextView from the item layout
            title = itemView.findViewById(R.id.title);
        }
    }
}
