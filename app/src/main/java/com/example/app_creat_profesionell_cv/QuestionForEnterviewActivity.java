package com.example.app_creat_profesionell_cv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.app_creat_profesionell_cv.Adapters.AdapterForQuestionInterview;
import com.example.app_creat_profesionell_cv.Classes.TitleQuestion;

import java.util.ArrayList;
import java.util.List;

public class QuestionForEnterviewActivity extends AppCompatActivity {

    RecyclerView recForQuestion;
    AdapterForQuestionInterview adapter;
    ArrayList<TitleQuestion> questionArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_for_enterview);

        // Initialize the ArrayList
        questionArrayList = new ArrayList<>();

        // Add items to the questionArrayList
        String[] titleOfPost = {"Python", "Java", "C++", "C#", "Kotlin"};
        for (String title : titleOfPost) {
            questionArrayList.add(new TitleQuestion(title));
        }

        // Initialize the RecyclerView and set its layout manager
        recForQuestion = findViewById(R.id.recForQuestion);
        recForQuestion.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with the questionArrayList and set it to the RecyclerView
        adapter = new AdapterForQuestionInterview(questionArrayList);
        recForQuestion.setAdapter(adapter);
    }
}
