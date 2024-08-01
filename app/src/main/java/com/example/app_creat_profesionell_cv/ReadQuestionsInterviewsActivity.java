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

        Intent nameOfInterview = getIntent();
        String nt = nameOfInterview.getStringExtra("interview");

        if (nt != null) {
            switch (nt) {
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
                case "C":
                    pdf.fromAsset("C Language 100 Questions Answers.pdf").load();
                    break;
                case "Perl":
                    pdf.fromAsset("Perl-FAQs.pdf").load();
                    break;
                case "Data Analyst":
                    pdf.fromAsset("data_analyst_interview.pdf").load();
                    break;
                case "Data Science":
                    pdf.fromAsset("data_science_interview_questions.pdf").load();
                    break;
                case "DBMS":
                    pdf.fromAsset("DBMS-Questions-and-Answers.pdf").load();
                    break;
                case "Machine Learning":
                    pdf.fromAsset("10-41-Essential-Machine-Learning-Interview-Questions-.pdf").load();
                    break;
                case "Computer Engineer":
                    pdf.fromAsset("computer_engineer_interview_questions_answers_star_method_guide.pdf").load();
                    break;
                case "Compatibility":
                    pdf.fromAsset("Couples.pdf").load();
                    break;
                case "Full Stack":
                    pdf.fromAsset("full_stack_developer_interview_questions_answers_star_method_guide.pdf").load();
                    break;
                case "Android":
                    pdf.fromAsset("android_interview_questions.pdf").load();
                    break;
                default:
                    // Handle case where the interview name does not match any known files
                    break;
            }
        } else {
            // Handle case where the intent extra is null
        }
    }
}
