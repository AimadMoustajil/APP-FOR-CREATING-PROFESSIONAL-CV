package com.example.app_creat_profesionell_cv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class ReadQuestionsInterviewsActivity extends AppCompatActivity {

    PDFView pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_questions_interviews);

        pdf = findViewById(R.id.pdf);
        //pdf.fromAsset("GeniralQuestion.pdf").load();
        Intent nameOfInterview = getIntent();
        String nt = nameOfInterview.getStringExtra("interview");
        switch (nt){
            case "GeniralQuestion.pdf":
                pdf.fromAsset("GeniralQuestion.pdf").load();
                break;
            case "Python":
                pdf.fromAsset("Python-Interview-Questions.pdf").load();
                break;
            case "Java":
                pdf.fromAsset("Java-Interview-Questions.pdf").load();
                break;
            case "C++":
                pdf.fromAsset("Interview-C++.pdf").load();
                break;
            case "C#":
                pdf.fromAsset("C#_InterviewQuestions.pdf").load();
                break;
            case "Flutter":
                pdf.fromAsset("Flutter-Questions-.pdf").load();
                break;
        }
        //Python-Interview-Questions
        //Java-Interview-Questions
        //Interview-C++.pdf
        //C#_InterviewQuestions
        //Flutter-Questions-
    }
}