package com.example.app_creat_profesionell_cv.LoginAndCreatAccount;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_creat_profesionell_cv.DashBoardActivity;
import com.example.app_creat_profesionell_cv.DataBase.LoginAndRegister;
import com.example.app_creat_profesionell_cv.OtherClasses.ClassForFunction;
import com.example.app_creat_profesionell_cv.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameOfUser, email, password, confirmPassword;
    private Button create;
    private TextView login;
    private ImageView imageUser;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private Uri selectedImage;
    LoginAndRegister db;
    static  SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageUser = findViewById(R.id.imageUser);
        nameOfUser = findViewById(R.id.nameOfUser);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        create = findViewById(R.id.creat); // Corrected ID reference
        login = findViewById(R.id.loginFromHere);
        db  = new LoginAndRegister(this);
        sharedPreferences = getSharedPreferences("myInfo",MODE_PRIVATE);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameOfUser.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                if (selectedImage != null && !name.isEmpty() && !emailText.isEmpty() && !passwordText.isEmpty()) {
                    db.add(name, emailText, passwordText, selectedImage.toString());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name",nameOfUser.getText().toString());
                    editor.putString("password",passwordText.toString());
                    editor.commit();
                    startActivity(new Intent(RegisterActivity.this, DashBoardActivity.class));
                } else {
                    db.add(name, emailText, passwordText, null);
                }
                //db.getAll();
            }
        });

        setupImagePicker();

        login.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void setupImagePicker() {
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            selectedImage = data.getData();
                            setProfileImage(selectedImage);
                        }
                    }
                });

        imageUser.setOnClickListener(v -> pickImage());
    }

    private void pickImage() {
        ImagePicker.with(this)
                .cropSquare()
                .compress(512)
                .maxResultSize(512, 512)
                .createIntent(intent -> {
                    imagePickerLauncher.launch(intent);
                    return null;
                });
    }

    private void setProfileImage(Uri imageUri) {
        ClassForFunction.setProfileTic(getApplicationContext(), imageUri, imageUser);
    }

    public String getName(){
        SharedPreferences sharedPreferences = getSharedPreferences("myInfo", MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        return name;
    }

    public String getPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("myInfo", MODE_PRIVATE);
        String password = sharedPreferences.getString("password","");
        return password;
    }
}
