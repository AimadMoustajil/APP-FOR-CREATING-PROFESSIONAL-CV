package com.example.app_creat_profesionell_cv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Languages extends AppCompatActivity {

    TextView English, French, German, Italian, Russian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);

        // Initialize TextView elements
        English = findViewById(R.id.English);
        French = findViewById(R.id.French);
        German = findViewById(R.id.German);
        Italian = findViewById(R.id.Italian);
        Russian = findViewById(R.id.Russian);

        // Set onClickListeners for each language option
        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("en");
                saveLanguagePreference("en");
                restartMainActivity();
            }
        });

        French.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("fr");
                saveLanguagePreference("fr");
                restartMainActivity();
            }
        });

        German.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("de");
                saveLanguagePreference("de");
                restartMainActivity();
            }
        });

        Italian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("it");
                saveLanguagePreference("it");
                restartMainActivity();
            }
        });

        Russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("ru");
                saveLanguagePreference("ru");
                restartMainActivity();
            }
        });

        // Load and set the previously selected language
        loadLanguagePreference();
    }

    public void setLanguage(String languageCode) {
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private void saveLanguagePreference(String languageCode) {
        SharedPreferences sharedPreferences = getSharedPreferences("LanguagePreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SelectedLanguage", languageCode);
        editor.apply();
    }

    private void loadLanguagePreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("LanguagePreferences", MODE_PRIVATE);
        String languageCode = sharedPreferences.getString("SelectedLanguage", "en"); // Default to English if not set
        setLanguage(languageCode);
    }

    private void restartMainActivity() {
        Intent intent = new Intent(Languages.this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional: close the Languages activity so it doesn't remain in the back stack
    }
}
