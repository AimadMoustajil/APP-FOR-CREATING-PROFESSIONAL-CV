package com.example.app_creat_profesionell_cv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the saved language preference and apply it
        loadLanguagePreference();

        // Set the content view after setting the language
        setContentView(R.layout.activity_main);

        // Delay before transitioning to the DashBoardActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void loadLanguagePreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("LanguagePreferences", MODE_PRIVATE);
        String languageCode = sharedPreferences.getString("SelectedLanguage", "en"); // Default to English if not set
        setLanguage(languageCode);
    }

    private void setLanguage(String languageCode) {
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
