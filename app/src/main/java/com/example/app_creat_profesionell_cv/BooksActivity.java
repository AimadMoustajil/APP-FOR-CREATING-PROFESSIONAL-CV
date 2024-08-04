package com.example.app_creat_profesionell_cv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.app_creat_profesionell_cv.Adapters.AdapterOfBooks;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {

    private RecyclerView recForBooks;
    private AdapterOfBooks adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        recForBooks = findViewById(R.id.recForBooks);
        recForBooks.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        arrayList = new ArrayList<>();
        // Add drawable resource names here
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        arrayList.add("d");
        arrayList.add("e");
        arrayList.add("f");
        arrayList.add("g");
        arrayList.add("h");
        arrayList.add("k");
        arrayList.add("l");
        arrayList.add("m");
        arrayList.add("n");
        arrayList.add("o");
        arrayList.add("p");

        adapter = new AdapterOfBooks(arrayList);
        recForBooks.setAdapter(adapter);

        /*
         * a = 60_Seconds_and_You're_hired!
         * b = Cracking the coding interview
         * c = Get That Job
         * d = Ignite Your LinkedIn Profile
         * e = Interview Questions
         * f = Mark as downloadedTéléchargé
         * g = The Reverse Job Search Method
         * h = The STAR Interview
         * k = The Ultimate LinkedIn Messaging Guide
         * l = Over-40 Job Search Guide 10 Strategies for Making Your Age an Advantage in Your Career (Gail Geary) (Z-Library)
         * m = The complete hiring and firing handbook every managers guide to working with employees legally (Charles H. Fleischer) (Z-Library).pdf
         * n = HR for Small Business, 2E An Essential Guide for Managers, Human Resources Professionals, and Small Business Owners (Quick... (Charles Fleischer) (Z-Library).pdf
         * o = Powerful Phrases for Successful Interviews (Tony Beshara, Phil McGraw) (Z-Library).pdf
         * p = Over-40 Job Search Guide 10 Strategies for Making Your Age an Advantage in Your Career (Gail Geary) (Z-Library).pdf
         */
    }
}
