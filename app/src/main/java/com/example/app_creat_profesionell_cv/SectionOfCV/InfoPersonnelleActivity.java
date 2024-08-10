package com.example.app_creat_profesionell_cv.SectionOfCV;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Classes.InfoPersonnelle;
import com.example.app_creat_profesionell_cv.ContentOfCV.ContentOfInformationCv;
import com.example.app_creat_profesionell_cv.DB.InfoPersonnel;
import com.example.app_creat_profesionell_cv.OtherClasses.ClassForFunction;
import com.example.app_creat_profesionell_cv.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class InfoPersonnelleActivity extends AppCompatActivity {

    ImageView imageOfUser, check;
    EditText firstName, lastName, numberPhone, emailOfUser, paysOfUser, villeOfUser, jobOfUser, AboutUSer;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    Uri selectedImage;
    // DB
    InfoPersonnel infoPersonnel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_personnelle);

        imageOfUser = findViewById(R.id.imageView3);
        check = findViewById(R.id.check);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        numberPhone = findViewById(R.id.numberPhone);
        emailOfUser = findViewById(R.id.emailOfUSer);
        paysOfUser = findViewById(R.id.paysOfUser);
        villeOfUser = findViewById(R.id.villeOfUSer);
        jobOfUser = findViewById(R.id.jobOfUSer);
        AboutUSer = findViewById(R.id.AboutUSer);
        setupImagePicker();

        infoPersonnel = new InfoPersonnel(this);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f_name = firstName.getText().toString();
                String l_name = lastName.getText().toString();
                String n_phone = numberPhone.getText().toString();
                String e_user = emailOfUser.getText().toString();
                String p_user = paysOfUser.getText().toString();
                String v_user = villeOfUser.getText().toString();
                String j_user = jobOfUser.getText().toString();
                String ab_user = AboutUSer.getText().toString();

                // Check if the required fields are empty
                if (f_name.isEmpty() || l_name.isEmpty() || n_phone.isEmpty() || e_user.isEmpty() || p_user.isEmpty() || v_user.isEmpty() || j_user.isEmpty()) {
                    Toast.makeText(InfoPersonnelleActivity.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If image is not selected, use default image
                if (selectedImage == null) {
                    selectedImage = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.baseline_account_circle_24);
                }

                // Set ab_user to "empty" if it is blank
                if (ab_user.isEmpty()) {
                    ab_user = "";
                }

                // Create InfoPersonnelle object with the information provided
                InfoPersonnelle infoPersonnelle = new InfoPersonnelle(selectedImage.toString(), f_name, l_name, n_phone, e_user, p_user, v_user, j_user, ab_user);
                infoPersonnel.addInfoPersonnelle(infoPersonnelle);

                // Show success message and navigate to the next screen
                Toast.makeText(InfoPersonnelleActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InfoPersonnelleActivity.this, ContentOfInformationCv.class));
            }
        });
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

        imageOfUser.setOnClickListener(v -> pickImage());
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
        ClassForFunction.setProfileTic(getApplicationContext(), imageUri, imageOfUser);
    }
}
