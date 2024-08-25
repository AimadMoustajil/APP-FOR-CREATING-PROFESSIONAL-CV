package com.example.app_creat_profesionell_cv.ContentOfCV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Classes.InfoEducation;
import com.example.app_creat_profesionell_cv.Classes.InfoExperience;
import com.example.app_creat_profesionell_cv.Classes.InfoPersonnelle;
import com.example.app_creat_profesionell_cv.Classes.InfoProjet;
import com.example.app_creat_profesionell_cv.DB.Education;
import com.example.app_creat_profesionell_cv.DB.ExperinceDeTravaile;
import com.example.app_creat_profesionell_cv.DB.InfoPersonnel;
import com.example.app_creat_profesionell_cv.DB.InformationAdditionnelle;
import com.example.app_creat_profesionell_cv.DB.Projet;
import com.example.app_creat_profesionell_cv.R;
import com.example.app_creat_profesionell_cv.SectionOfCV.ExperinceActivity;
import com.example.app_creat_profesionell_cv.SectionOfCV.InfoEducationActivity;
import com.example.app_creat_profesionell_cv.SectionOfCV.InfoPersonnelleActivity;
import com.example.app_creat_profesionell_cv.SectionOfCV.InfoProjetActivity;
import com.example.app_creat_profesionell_cv.SectionOfCV.InformationAdditionnelleActivity;
import com.example.app_creat_profesionell_cv.ShowDocument.ShowDocumentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class M10 extends AppCompatActivity {

    TextView infoPersonnel,éducation,exDeTravaille,projet,infoAdditionnelle;
    Button generateCV;
    //Image Of Checks
    ImageView checkInfoPersonnel,checkEucation,checkExDeTravaille,checkProjet,checkInfoAdditionnelle;
    //

    //DB
    InfoPersonnel dbInfoPersonnelle;
    Education dbInfoEducation;
    ExperinceDeTravaile dbExperienceDeTravaille;
    Projet dbProjet;
    InformationAdditionnelle dbInformationAitionnelle;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m10);
        //DB
        dbInfoPersonnelle = new InfoPersonnel(this);
        dbInfoEducation = new Education(this);
        dbExperienceDeTravaille = new ExperinceDeTravaile(this);
        dbProjet = new Projet(this);
        dbInformationAitionnelle = new InformationAdditionnelle(this);
        //

        infoPersonnel = findViewById(R.id.infoPersonnel);
        éducation = findViewById(R.id.éducation);
        exDeTravaille = findViewById(R.id.exDeTravaille);
        projet = findViewById(R.id.projet);
        infoAdditionnelle = findViewById(R.id.infoAdditionnelle);
        generateCV = findViewById(R.id.generateCV);


        checkInfoPersonnel = findViewById(R.id.checkInfoPersonnelles);
        checkEucation = findViewById(R.id.checkEducation);
        checkExDeTravaille = findViewById(R.id.checkExDeTravaile);
        checkProjet = findViewById(R.id.checkProjet);
        checkInfoAdditionnelle = findViewById(R.id.checkInfoAdditionnelle);

        //check DB
        if (!dbInfoPersonnelle.isDatabaseEmpty()){
            checkInfoPersonnel.setVisibility(View.VISIBLE);
        }

        if (!dbInfoEducation.isDatabaseEmpty()){
            checkEucation.setVisibility(View.VISIBLE);
        }

        if (!dbExperienceDeTravaille.isDatabaseEmpty()){
            checkExDeTravaille.setVisibility(View.VISIBLE);
        }

        if (!dbProjet.isDatabaseEmpty()){
            checkProjet.setVisibility(View.VISIBLE);
        }

        if (!dbInformationAitionnelle.isDatabaseEmpty()){
            checkInfoAdditionnelle.setVisibility(View.VISIBLE);
        }
        //

        infoPersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M10.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M10.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M10.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M10.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M10.this, InformationAdditionnelleActivity.class));
            }
        });


        try {
            List<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();
            if (languages != null && languages.size() > 1) {
                Toast.makeText(this, languages.get(1), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No languages available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error fetching language data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        generateCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShowDocumentActivity
                // Check for permission to write to external storage
                if (ContextCompat.checkSelfPermission(M10.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M10.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M10.this, ShowDocumentActivity.class);
                        intent.putExtra("pdfByteArray", outputStream.toByteArray());
                        startActivity(intent);

                        // Close the document after starting the activity
                        document.close();
                    }
                }
            }
        });

    }


    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        dbInfoPersonnelle.clearAllData();
        dbInfoEducation.clearAllData();
        dbProjet.clearAllData();
        dbInformationAitionnelle.clearAllData();
        dbExperienceDeTravaille.clearAllData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbInfoPersonnelle.clearAllData();
        dbInfoEducation.clearAllData();
        dbProjet.clearAllData();
        dbInformationAitionnelle.clearAllData();
        dbExperienceDeTravaille.clearAllData();
    }*/












    private static final int START_Y_POSITION = 270;
    private static final int LINE_SPACING = 25;
    private static final int COLUMN_PRICE_X = 50;
    private static final int COLUMN_TVA_X = 150;
    private static final int COLUMN_PRODUCT_X = 250;
    private static final int COLUMN_QUANTITY_X = 400;
    private static final int SIGNATURE_BOX_WIDTH = 200;
    private static final int SIGNATURE_BOX_HEIGHT = 60;
    private static final int IMAGE_SIZE_PERCENT = 20;
    private static final int IMAGE_MARGIN = 50;
    private static final int TABLE_HEADER_COLOR = Color.RED;
    private static final int TOTALS_COLOR = Color.BLUE;
    private static final int BACKGROUND_COLOR = Color.GREEN;


    private PdfDocument createPDF() {
        if (!isExternalStorageWritable()) {
            showToastOnUiThread("External storage not writable");
            return null;
        }

        PdfDocument document = new PdfDocument();
        PdfDocument.Page page = null;

        try {
            PdfDocument.PageInfo pageInfo = createPageInfo();
            page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = createPaint();

            drawBackground(canvas);
            // Draw header image
            drawHeaderImage(canvas);
            drawContactInfo(canvas,220);
            // Draw Competence and get the Y position where the next section should start
            int competenceEndY = drawCompetence(canvas, 245);

            // Draw Experience de Travail section starting just below Competence
            drawExperienceDeTravail(canvas, competenceEndY + 30);

            int experienceEndY = drawExperienceDeTravail(canvas, competenceEndY + 30); // Adding extra margin

            // Draw Projet section starting just below Experience de Travail
            drawProjet(canvas, experienceEndY + 30);
            int projetEndY = drawProjet(canvas, experienceEndY + 30);
            drawEducation(canvas, projetEndY + 30);


            drawLanguages(canvas, 400);


            // Draw user name and job title
            int logoBottomY = IMAGE_MARGIN + (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
            drawUserName(canvas, logoBottomY);

            Paint paintForJobTitle = new Paint();
            paintForJobTitle.setColor(Color.GRAY);
            paintForJobTitle.setTextSize(40);
            paintForJobTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw job title
            drawUserJobTitle(canvas, ((canvas.getWidth() - paint.measureText(dbInfoPersonnelle.getInfo().get(0).getJob())) / 2), logoBottomY - 50);

            document.finishPage(page); // Finish the page before returning

            // Don't close the document here, close it after saving or sending
            return document;

        } catch (Exception e) {
            e.printStackTrace();
            showToastOnUiThread("Error creating PDF: " + e.getMessage());
            if (page != null) {
                document.finishPage(page); // Finish the page in case of an error
            }
            document.close(); // Close the document in case of an error
            return null;
        }
    }


    private void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(240,207,195)); // Set your desired background color

        // Draw a rectangle from y = 0 to y = 40, covering the entire width of the canvas
        canvas.drawRect(0, 0, canvas.getWidth(), 40, paint);
    }


    private int drawProjet(Canvas canvas, int startY) {
        // Create and configure Paint objects
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.rgb(48, 48, 48)); // Set text color to dark gray
        titlePaint.setTextSize(15);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint grayPaint = new Paint();
        grayPaint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        grayPaint.setTextSize(15);
        grayPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Gray color for bullets
        bulletPaint.setTextSize(10);

        // Define margins and spacing
        final int marginLeft = 260;
        final int bulletMarginLeft = 270; // Margin for bullet points
        final int headingMarginBottom = 10; // Space below the heading
        final int elementMarginTop = 25; // Margin between elements
        final int lineHeight = 25; // Space between lines
        final int bulletSize = 10; // Bullet size
        final int bulletTextMargin = 5; // Margin between bullet and text
        final int smallMarginTop = 10; // Small margin before resume text
        final int maxWidth = 250; // Maximum width before wrapping text

        // Draw the "Projet" heading
        String heading = "Projet".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Retrieve project information from the database
        ArrayList<InfoProjet> projectInfo;
        try {
            projectInfo = dbProjet.getAllInfoProjets();
        } catch (Exception e) {
            // Handle exception (e.g., log error)
            return startY; // Return initial Y position if an error occurs
        }

        // Calculate Y offset for project information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of project information
        for (InfoProjet info : projectInfo) {
            boolean hasContent = false;

            // Draw the project dates on the first line if not empty
            String dates = (info.getDateDebut() != null ? info.getDateDebut() : "") +
                    (info.getDateFin() != null ? " - " + info.getDateFin() : "");
            if (!dates.trim().isEmpty()) {
                canvas.drawText(dates, marginLeft, yOffset, titlePaint);
                yOffset += lineHeight;
                hasContent = true;
            }

            // Draw the project name on the next line if not empty
            if (info.getTitreProjet() != null && !info.getTitreProjet().trim().isEmpty()) {
                String formattedTitle = capitalizeFirstLetterOfEachWord(info.getTitreProjet());
                canvas.drawText(formattedTitle, marginLeft, yOffset, grayPaint);
                yOffset += lineHeight;
                hasContent = true;
            }

            // Draw the resume with a bullet point and small margin if not empty
            if (info.getResume() != null && !info.getResume().trim().isEmpty()) {
                String resumeText = info.getResume();
                canvas.drawText("•", bulletMarginLeft, yOffset, bulletPaint);

                // Wrap and draw resume text
                while (!resumeText.isEmpty()) {
                    int charsToDraw = grayPaint.breakText(resumeText, true, maxWidth, null);
                    String line = resumeText.substring(0, charsToDraw);
                    canvas.drawText(line, bulletMarginLeft + bulletSize + bulletTextMargin, yOffset, grayPaint);
                    yOffset += lineHeight + smallMarginTop;
                    resumeText = resumeText.substring(charsToDraw);
                }
                hasContent = true;
            }

            // Add space between different projects only if there is content
            if (hasContent) {
                yOffset += elementMarginTop;
            }
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }




    private void drawContactInfo(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Increased text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 60; // Left margin for text (considering icon width)
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 15; // Space between line and first element
        int lineHeight = 50; // Space between lines
        int iconMarginRight = 10; // Space between icon and text

        // Load icons from resources
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.telephone);
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email);
        Bitmap addressIcon = BitmapFactory.decodeResource(getResources(), R.drawable.social);
        Bitmap placeHolderIcon = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);

        // Contact information text and corresponding icons
        String[] contactInfo = {
                dbInfoPersonnelle.getInfo().get(0).getN_phone(),
                dbInfoPersonnelle.getInfo().get(0).getEmail(),
                dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin(),
                dbInfoPersonnelle.getInfo().get(0).getPays() + "," + dbInfoPersonnelle.getInfo().get(0).getVille()
        };
        Bitmap[] icons = {phoneIcon, emailIcon, addressIcon, placeHolderIcon};

        // Calculate the new left margin to align heading with the phone icon
        int iconWidth = phoneIcon.getWidth();
        int newMarginLeft = marginLeft - iconWidth - iconMarginRight;

        String heading = "Contact".toUpperCase(); // Set the heading text
        float headingY = startY + paint.getTextSize(); // Y position for heading
        canvas.drawText(heading, newMarginLeft, headingY, paint);

        // Measure the width of the heading text
        float headingWidth = paint.measureText(heading);

        // Initialize the Y position for contact information
        float textY = headingY + headingMarginBottom + elementMarginTop;

        // Draw each line of contact information with its icon
        for (int i = 0; i < contactInfo.length; i++) {
            if (contactInfo[i] != null && !contactInfo[i].trim().isEmpty()) {
                // Draw icon
                if (icons[i] != null) {
                    float iconY = textY - icons[i].getHeight() / 2; // Center icon vertically
                    canvas.drawBitmap(icons[i], newMarginLeft, iconY, null);
                }

                // Draw text
                paint.setTextSize(10); // Reset text size for contact information
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for contact information
                canvas.drawText(contactInfo[i], marginLeft, textY, paint);

                // Update the Y position for the next element
                textY += lineHeight;
            }
        }
    }




    private int drawCompetence(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;
        int maxWidth = 250; // Maximum width before wrapping text

        // Draw the "Profil" heading
        String heading = "Profil".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Competence information text (replace with actual data)
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // Set paint for competence details
        paint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        paint.setTextSize(15);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Draw each line of competence information
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (InfoPersonnelle info : aboutUser) {
            String text = info.getAbout();

            // Check if the resume is not empty or null
            if (text != null && !text.trim().isEmpty()) {
                // Wrap text to fit within the specified width
                while (!text.isEmpty()) {
                    int charsToDraw = paint.breakText(text, true, maxWidth, null);
                    String line = text.substring(0, charsToDraw);
                    canvas.drawText(line, marginLeft, currentY, paint);
                    currentY += lineHeight; // Move to the next line
                    text = text.substring(charsToDraw); // Remaining text
                }
                currentY += elementMarginTop; // Add margin between items
            }
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }






    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.rgb(56, 56, 56));
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(96, 96, 96)); // Set text color to gray
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(48, 48, 48)); // Set text color to dark gray
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int bulletMargin = 20;
        int maxWidth = 250; // Maximum width before wrapping text

        // Draw the "Expérience de Travail" heading
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Retrieve experience information from the database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Calculate the Y offset for experience information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : experienceInfo) {
            // Draw the dates and company name
            String dateRange = info.getDateDébut() + " - " + info.getDateDeFin();
            String companyName = info.getNomEntreprise();

            // Draw the date range on the first line
            canvas.drawText(dateRange, marginLeft, yOffset, bulletPaint);
            yOffset += lineHeight;

            // Draw the company name on the next line
            canvas.drawText(companyName, marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the resume only if it is not empty
            String resume = info.getRésumé();
            if (resume != null && !resume.trim().isEmpty()) {
                resume = "• " + resume;
                // Wrap the resume text to the next line if it exceeds maxWidth
                while (!resume.isEmpty()) {
                    int charsToDraw = normalPaint.breakText(resume, true, maxWidth, null);
                    String line = resume.substring(0, charsToDraw);
                    canvas.drawText(line, marginLeft + bulletMargin, yOffset, normalPaint);
                    yOffset += lineHeight; // Move to the next line
                    resume = resume.substring(charsToDraw); // Remaining text
                }
            }

            // Add extra space after the resume and before the next experience if resume was drawn
            if (resume != null && !resume.trim().isEmpty()) {
                yOffset += elementMarginTop;
            }

            // Add space between different experiences
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }

    private int drawEducation(Canvas canvas, int startY) {
        // Create and configure the Paint objects
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.rgb(48, 48, 48)); // Set text color to dark gray
        schoolNamePaint.setTextSize(15);
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.rgb(40, 40, 40));
        professionPaint.setTextSize(15); // Adjust size as needed
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.rgb(40, 40, 40)); // Color for bullet points
        bulletPaint.setTextSize(15); // Bullet point size

        // Define margins and spacing
        int marginLeft = 260;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int maxWidth = 250; // Maximum width before wrapping text
        int bulletMarginLeft = 270; // Margin for bullet points
        int bulletTextMargin = 5; // Margin between bullet and text

        // Draw the "Éducation" heading
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Retrieve education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Calculate the Y offset for education information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (InfoEducation education : educationInfo) {
            boolean hasContent = false;

            // Prepare to draw the dates
            String dates = (education.getStartYier() != null ? education.getStartYier() : "") +
                    (education.getEndYier() != null ? " - " + education.getEndYier() : "");
            if (!dates.isEmpty()) {
                canvas.drawText(dates, marginLeft, currentY, schoolNamePaint);
                currentY += lineHeight;
                hasContent = true;
            }

            // Prepare to draw the school name
            String schoolName = education.getShool();
            if (schoolName != null && !schoolName.trim().isEmpty()) {
                canvas.drawText(schoolName, marginLeft, currentY, professionPaint);
                currentY += lineHeight;
                hasContent = true;
            }

            // Prepare to draw the profession (metier) with a bullet point
            String profession = education.getMetier();
            if (profession != null && !profession.trim().isEmpty()) {
                canvas.drawText("•", bulletMarginLeft, currentY, bulletPaint);
                canvas.drawText(profession, bulletMarginLeft + bulletPaint.measureText("•") + bulletTextMargin, currentY, professionPaint);
                currentY += lineHeight;
                hasContent = true;
            }

            // Only add space between entries if any content was drawn
            if (hasContent) {
                currentY += elementMarginTop;
            }
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }



    private String[] wrapText(String text, int maxWidth, Paint paint, int marginLeft, float startY) {
        // Split text into lines that fit within maxWidth
        ArrayList<String> lines = new ArrayList<>();
        float currentY = startY;
        while (!text.isEmpty()) {
            int charsToDraw = paint.breakText(text, true, maxWidth, null);
            String line = text.substring(0, charsToDraw);
            lines.add(line);
            text = text.substring(charsToDraw);
        }
        return lines.toArray(new String[0]);
    }






    private void drawLanguages(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        int marginLeft = 24; // Left margin for text
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 20; // Space between the heading and the first element
        int lineHeight = 25; // Space between lines
        int sectionSpacing = 30; // Space between sections

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + paint.getTextSize() + 50; // Y position for heading
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Measure the width of the heading text
        paint.setTextSize(15); // Reset text size for languages
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for languages
        float textY = headingY + headingMarginBottom + elementMarginTop;

        // Language information text
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();
        if (languages != null && !languages.isEmpty()) {
            for (String language : languages) {
                if (language != null && !language.trim().isEmpty()) {
                    canvas.drawText(language, marginLeft, textY, paint);
                    textY += lineHeight;
                }
            }
        }

        // Draw the "Skills" heading if there are skills
        ArrayList<String> skills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCompetanceArrayList();
        if (skills != null && !skills.isEmpty()) {
            float skillsHeadingY = textY + sectionSpacing; // Position for "Skills" heading
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Skills".toUpperCase(), marginLeft, skillsHeadingY, paint);

            paint.setTextSize(15); // Text size for the body text
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            textY = skillsHeadingY + headingMarginBottom + elementMarginTop;

            // Draw each skill
            for (String skill : skills) {
                if (skill != null && !skill.trim().isEmpty()) {
                    canvas.drawText(skill, marginLeft, textY, paint);
                    textY += lineHeight;
                }
            }
        }

        // Draw the "Soft Skills" heading if there are soft skills
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();
        if (softSkills != null && !softSkills.isEmpty()) {
            float softSkillsHeadingY = textY + sectionSpacing; // Position for "Soft Skills" heading
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Soft Skills".toUpperCase(), marginLeft, softSkillsHeadingY, paint);

            paint.setTextSize(15); // Text size for the body text
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            textY = softSkillsHeadingY + headingMarginBottom + elementMarginTop;

            // Draw each soft skill
            for (String softSkill : softSkills) {
                if (softSkill != null && !softSkill.trim().isEmpty()) {
                    canvas.drawText(softSkill, marginLeft, textY, paint);
                    textY += lineHeight;
                }
            }
        }

        // Draw the "Loisirs" heading if there are loisirs
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList();
        if (loisirs != null && !loisirs.isEmpty()) {
            float loisirsHeadingY = textY + sectionSpacing; // Position for "Loisirs" heading
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Loisirs".toUpperCase(), marginLeft, loisirsHeadingY, paint);

            paint.setTextSize(15); // Text size for the body text
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            textY = loisirsHeadingY + headingMarginBottom + elementMarginTop;

            // Draw each loisir
            for (String loisir : loisirs) {
                if (loisir != null && !loisir.trim().isEmpty()) {
                    canvas.drawText(loisir, marginLeft, textY, paint);
                    textY += lineHeight;
                }
            }
        }

        // Draw the "Certificate" heading if there are certificates
        ArrayList<String> certificates = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList();
        if (certificates != null && !certificates.isEmpty()) {
            float certificateHeadingY = textY + sectionSpacing; // Position for "Certificate" heading
            paint.setTextSize(20); // Text size for the heading
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Certificate".toUpperCase(), marginLeft, certificateHeadingY, paint);

            paint.setTextSize(15); // Text size for the body text
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            textY = certificateHeadingY + headingMarginBottom + elementMarginTop;

            // Draw each certificate
            for (String certificate : certificates) {
                if (certificate != null && !certificate.trim().isEmpty()) {
                    canvas.drawText(certificate, marginLeft, textY, paint);
                    textY += lineHeight;
                }
            }
        }
    }




    private ByteArrayOutputStream savePDFToByteArray(PdfDocument document) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            document.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            showToastOnUiThread("Error saving PDF to byte array: " + e.getMessage());
        }
        return outputStream;
    }


    private void showToastOnUiThread(final String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void savePDF(PdfDocument document) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "NEWPDFInvoice.pdf");
            document.writeTo(new FileOutputStream(file));
            showToastOnUiThread("PDF created successfully: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            showToastOnUiThread("Error saving PDF: " + e.getMessage());
        }
    }

    private Bitmap getBitmapFromURL(String urlImage) throws IOException {
        URL url = new URL(urlImage);

        if ("http".equalsIgnoreCase(url.getProtocol()) || "https".equalsIgnoreCase(url.getProtocol())) {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            try (InputStream input = connection.getInputStream()) {
                return BitmapFactory.decodeStream(input);
            } finally {
                connection.disconnect();
            }
        } else if ("file".equalsIgnoreCase(url.getProtocol())) {
            try (InputStream input = url.openStream()) {
                return BitmapFactory.decodeStream(input);
            }
        } else {
            throw new IllegalArgumentException("Unsupported URL scheme: " + url.getProtocol());
        }
    }



    private Paint createPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(14);
        return paint;
    }

    private PdfDocument.PageInfo createPageInfo() {
        int contentHeight = 1000;
        int contentWidth = 610; // Or calculate based on content if needed

        // Adjust the height to include some padding or margins
        int pageHeight = contentHeight + 200; // Add some padding or margins
        int pageWidth = contentWidth; // Use the content width or adjust as needed

        return new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
    }


    private Bitmap resizeBitmap(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        float scaleWidth = ((float) 150) / width;
        float scaleHeight = ((float) 150) / height;

        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // Resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // Recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, false);

        // Release the original bitmap
        originalBitmap.recycle();

        return resizedBitmap;
    }


    private void drawHeaderImage(Canvas canvas) {
        try {
            // Retrieve and resize the bitmap from the URL
            Bitmap originalBitmap = getBitmapFromURL(dbInfoPersonnelle.getInfo().get(0).getLogoOfUser());
            Bitmap resizedBitmap = resizeBitmap(originalBitmap);

            // Calculate the position for the image in the top-left corner
            int imageX = IMAGE_MARGIN; // Padding from the left edge
            int imageY = IMAGE_MARGIN; // Padding from the top edge

            // Draw the circular image at the calculated position
            drawCircularImage(canvas, resizedBitmap, imageX, imageY);

            // Draw the user's name centered horizontally between the logo and the end of the page
            drawUserName(canvas, resizedBitmap.getWidth() + 2 * IMAGE_MARGIN); // Position the name after the logo with padding

            // Release the resized bitmap to free memory
            resizedBitmap.recycle();
        } catch (Exception e) {
            // Handle exceptions, e.g., logging or showing a toast message
            e.printStackTrace();
        }
    }







    private void drawUserName(Canvas canvas, int logoBottomY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + dbInfoPersonnelle.getInfo().get(0).getF_name().toUpperCase()  ;
        float textWidth = paint.measureText(capitalizeFirstLetterOfEachWord(userName));


        // Calculate the position for the text (center horizontally)
        float textX = ((canvas.getWidth() - textWidth)+128) / 2;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, 128, paint);
    }

    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(36, 36, 36)); // Set a different color for job title
        paint.setTextSize(30); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle = capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = nameTextY + spaceBelowName + paint.getTextSize();

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = ((canvas.getWidth() - jobTitleWidth) / 2);

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);

        // Create and configure Paint object for the line
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(36, 36, 36)); // Set the color for the line
        linePaint.setStrokeWidth(2); // Set the thickness of the line

        // Calculate the vertical position for the line (just below the job title)
        float lineY = jobTitleTextY + 10; // Adjust the spacing as needed

        // Draw the line below the job title
        canvas.drawLine(jobTitleTextX, lineY, jobTitleTextX + jobTitleWidth+120, lineY, linePaint);
    }









    private void drawCircularImage(Canvas canvas, Bitmap bitmap, int x, int y) {
        // Define fixed width for the circular image
        int imageSize = 150;
        int borderWidth = 3; // Width of the border in pixels

        // Calculate the aspect ratio of the original image
        float aspectRatio = (float) bitmap.getWidth() / bitmap.getHeight();

        // Determine new height based on the aspect ratio and fixed width
        int newHeight = (int) (imageSize / aspectRatio);

        // Create a resized bitmap with fixed width and calculated height
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, imageSize, newHeight, false);

        // Create a circular bitmap with fixed size
        Bitmap circularBitmap = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888);
        Canvas circularCanvas = new Canvas(circularBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Define rectangle and circle for drawing
        Rect rect = new Rect(0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());
        RectF rectF = new RectF(0, 0, imageSize, imageSize);

        // Draw the circular bitmap
        circularCanvas.drawARGB(0, 0, 0, 0);
        circularCanvas.drawCircle(imageSize / 2, imageSize / 2, imageSize / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        circularCanvas.drawBitmap(resizedBitmap, rect, rectF, paint);

        // Draw the border around the circular image
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.WHITE); // Border color
        borderPaint.setStyle(Paint.Style.STROKE); // Set to stroke to draw border
        borderPaint.setStrokeWidth(borderWidth); // Set border width
        borderPaint.setAntiAlias(true);

        circularCanvas.drawCircle(imageSize / 2, imageSize / 2, (imageSize / 2) - borderWidth / 2, borderPaint);

        // Draw the circular bitmap on the main canvas
        canvas.drawBitmap(circularBitmap, x -10, y , null);

        // Recycle bitmaps to free up memory
        resizedBitmap.recycle();
        circularBitmap.recycle();
    }





    // Helper method to capitalize the first letter of each word
    public static String capitalizeFirstLetterOfEachWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] words = str.split("\\s+");
        StringBuilder capitalizedStr = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                capitalizedStr.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    capitalizedStr.append(word.substring(1).toLowerCase());
                }
                capitalizedStr.append(" ");
            }
        }

        // Remove the trailing space
        return capitalizedStr.toString().trim();
    }
}