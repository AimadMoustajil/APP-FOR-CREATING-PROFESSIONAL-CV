package com.example.app_creat_profesionell_cv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class ReadBooksActivity extends AppCompatActivity {

    PDFView pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_books);
        pdf = findViewById(R.id.pdf);

        Intent intent = getIntent();
        String nameOfBook = intent.getStringExtra("book");

        if (nameOfBook.equals("60_Seconds_and_You're_hired!")){
            pdf.fromAsset("60 Seconds and Youre Hired  Revised Edition (Ryan, Robin) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("Cracking the coding interview")){
            pdf.fromAsset("Cracking the Coding Interview (Gayle Laakmann McDowell) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("Get That Job")){
            pdf.fromAsset("Get That Job The Quick and Complete Guide to a Winning Interview (Thea Kelley [Kelley, Thea]) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("Ignite Your LinkedIn Profile")){
            pdf.fromAsset("Ignite Your LinkedIn Profile (Donald J Wittman) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("Interview Questions")){
            pdf.fromAsset("101 Interview Questions The Ultimate and Most Complete Guide to Interview Questions (perfect answers, tough questions,... (James Austin) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("Mark as downloadedTéléchargé")){
            pdf.fromAsset("How to Answer Interview Questions 100 Smart Answers to the most Asked Questions. Conquer the Recruiter, show up your Best... (Donald Thomson) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("The Reverse Job Search Method")){
            pdf.fromAsset("The Reverse Job Search Method How Busy Professionals, Executives and Leaders Are Securing Career-Defining Roles Fast by... (James H Whittaker [Whittaker, James H]) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("The STAR Interview")){
            pdf.fromAsset("The STAR Interview How to Tell a Great Story, Nail the Interview and Land Your Dream Job (Misha Yurchenko [Yurchenko, Misha]) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("The Ultimate LinkedIn Messaging Guide")){
            pdf.fromAsset("The Ultimate LinkedIn Messaging Guide (Disney, DanielMurray, Chris) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("if (nameOfBook.equals(\"The Ultimate LinkedIn Messaging Guide\")){\n" +
                "            pdf.fromAsset(\"The Ultimate LinkedIn Messaging Guide (Disney, DanielMurray, Chris) (Z-Library).pdf\").load();\n" +
                "        }")){
            pdf.fromAsset("if (nameOfBook.equals(\"The Ultimate LinkedIn Messaging Guide\")){\n" +
                    "            pdf.fromAsset(\"The Ultimate LinkedIn Messaging Guide (Disney, DanielMurray, Chris) (Z-Library).pdf\").load();\n" +
                    "        }.pdf").load();
        }

        //Over-40 Job Search Guide 10 Strategies for Making Your Age an Advantage in Your Career (Gail Geary) (Z-Library)

        if (nameOfBook.equals("The complete hiring and firing handbook every managers guide to working with employees legally (Charles H. Fleischer) (Z-Library).pdf")){
            pdf.fromAsset("The complete hiring and firing handbook every managers guide to working with employees legally (Charles H. Fleischer) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("HR for Small Business, 2E An Essential Guide for Managers, Human Resources Professionals, and Small Business Owners (Quick... (Charles Fleischer) (Z-Library).pdf")){
            pdf.fromAsset("HR for Small Business, 2E An Essential Guide for Managers, Human Resources Professionals, and Small Business Owners (Quick... (Charles Fleischer) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("Powerful Phrases for Successful Interviews (Tony Beshara, Phil McGraw) (Z-Library).pdf")){
            pdf.fromAsset("Powerful Phrases for Successful Interviews (Tony Beshara, Phil McGraw) (Z-Library).pdf").load();
        }
        if (nameOfBook.equals("Over-40 Job Search Guide 10 Strategies for Making Your Age an Advantage in Your Career (Gail Geary) (Z-Library).pdf")){
            pdf.fromAsset("Over-40 Job Search Guide 10 Strategies for Making Your Age an Advantage in Your Career (Gail Geary) (Z-Library).pdf").load();
        }
    }
}