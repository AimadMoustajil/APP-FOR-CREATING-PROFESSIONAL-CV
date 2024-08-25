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

public class M14 extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m4);

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
                startActivity(new Intent(M14.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M14.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M14.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M14.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M14.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M14.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M14.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M14.this, ShowDocumentActivity.class);
                        intent.putExtra("pdfByteArray", outputStream.toByteArray());
                        startActivity(intent);

                        // Close the document after starting the activity
                        document.close();
                    }
                }
            }
        });

    }







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

            drawBottomRectangle(canvas);
            drawColoredBackground(canvas);
            drawGreenBackground(canvas);
            // Draw header image and content
            drawHeaderImage(canvas);

            //drawLineAtY(canvas, 300);

            // Draw user name and job title
            int logoBottomY = IMAGE_MARGIN + (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
            drawUserName(canvas, logoBottomY);

            Paint paintForJobTitle = new Paint();
            paintForJobTitle.setColor(Color.GRAY);
            paintForJobTitle.setTextSize(40);
            paintForJobTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw job title
            float jobTitleTextY = logoBottomY - 50;
            drawUserJobTitle(canvas, (canvas.getWidth() - paintForJobTitle.measureText(dbInfoPersonnelle.getInfo().get(0).getJob())) / 2, jobTitleTextY);

            // Draw contact info
            float contactInfoY = jobTitleTextY + 70; // Adjust Y position as needed
            drawContactInfo(canvas, contactInfoY);

            // Draw vertical line
            //drawVerticalLine(canvas, contactInfoY + 100, 900); // Adjust lineHeight as needed

            // Draw education section
            int educationBottomY = drawEducation(canvas, 290);

            // Draw skills section below education
            int skillsBottomY = drawSkills(canvas, educationBottomY + 10); // Adding some padding between sections

            // Draw languages section below skills
            int languagesBottomY = drawLanguages(canvas, skillsBottomY + 10); // Adding some padding between sections

            // Draw loisirs section below languages
            int loisirsBottomY = drawLoisirs(canvas, languagesBottomY + 10); // Adding some padding between sections

            int softskillsBottomY = softskillsBottomY(canvas,loisirsBottomY+10);

            // Draw competence section
            int competenceBottomY = drawCompetence(canvas, 380);

            // Draw experience section below competence
            int experienceBottomY = drawExperienceDeTravail(canvas, competenceBottomY + 30); // Adding some padding between sections
            int projects = drawProjects(canvas,experienceBottomY+30);

            // Draw certification section below experience
            drawCertification(canvas, softskillsBottomY + 30); // Adding some padding between sections

            document.finishPage(page); // Finish the page before returning

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

    private void drawColoredBackground(Canvas canvas) {
        // Create a Paint object with the desired background color
        Paint paint = new Paint();
        paint.setColor(Color.rgb(251, 217, 202)); // Replace with your desired color

        // Define the coordinates for the background rectangle
        float startX = 0;
        float startY = 0;
        float endX = canvas.getWidth();
        float endY = 350;

        // Draw the rectangle on the canvas
        canvas.drawRect(startX, startY, endX, endY, paint);
    }



    private int drawProjects(Canvas canvas, int startY) {
        // Create and configure the Paint object for the projects heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for project details (normal)
        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.BLACK);
        normalPaint.setTextSize(15); // Adjust size as needed
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Define margins and spacing
        int marginLeft = 250;
        int bulletMargin = 20; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int maxTextWidth = 300; // Maximum width for wrapping text

        // Draw the "Projects" heading
        String heading = "Projects".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the project information (replace with your method to fetch project data)
        ArrayList<InfoProjet> projects = dbProjet.getAllInfoProjets();

        // Calculate the Y offset for project information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of project information
        for (InfoProjet project : projects) {
            // Skip projects with empty or whitespace-only fields
            if (project.getNomEntreprise().trim().isEmpty() ||
                    project.getDateDebut().trim().isEmpty() ||
                    project.getDateFin().trim().isEmpty() ||
                    project.getTitreProjet().trim().isEmpty() ||
                    project.getResume().trim().isEmpty()) {
                continue; // Skip this project entry
            }

            // Calculate the width of the company name
            float companyWidth = normalPaint.measureText(project.getNomEntreprise());

            // Draw the company name
            canvas.drawText(project.getNomEntreprise(), marginLeft, yOffset, normalPaint);

            // Draw the start and end dates with a 50-pixel margin between them
            float dateX = marginLeft + companyWidth + 50;
            String dateText = project.getDateDebut() + " - " + project.getDateFin();
            canvas.drawText(dateText, dateX, yOffset, normalPaint);
            yOffset += lineHeight;

            // Draw the project title on the next line with a gray background
            String projectTitle = project.getTitreProjet();
            Paint titlePaint = new Paint(normalPaint);
            titlePaint.setColor(Color.WHITE); // Gray background
            Paint.FontMetrics fontMetrics = titlePaint.getFontMetrics();
            canvas.drawRect(marginLeft - 10, yOffset - fontMetrics.bottom, canvas.getWidth() - 10, yOffset + fontMetrics.top, titlePaint);
            titlePaint.setColor(Color.BLACK); // Text color
            canvas.drawText(projectTitle, marginLeft, yOffset, titlePaint);
            yOffset += lineHeight;

            normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            // Draw the project description on the next line with a bullet point, wrapping if necessary
            String projectDescription = "• " + project.getResume();
            yOffset = drawWrappedText(canvas, projectDescription, marginLeft + bulletMargin, yOffset, normalPaint, maxTextWidth);
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }






    private int drawCertification(Canvas canvas, float startY) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(48,48,48));
        paint.setTextSize(16); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Define margins and line height
        float marginLeft = 40;
        float lineHeight = 25;
        float elementMarginTop = 20;

        // Fetch the certifications data
        List<String> certifications = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getCertificateArrayList();

        // Check if certifications are empty; if so, skip drawing this section
        if (certifications == null || certifications.isEmpty()) {
            return (int) startY; // Return the startY value if there are no certifications
        }

        float currentY = startY;
        String sectionTitle = "Certifications";

        // Draw section title
        Paint titlePaint = new Paint(paint);
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(20); // Title text size
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(sectionTitle, marginLeft, currentY, titlePaint);

        // Calculate position for the certifications
        currentY += titlePaint.getTextSize() + elementMarginTop;

        for (String certification : certifications) {
            // Break text into multiple lines if it exceeds a certain width
            List<String> lines = new ArrayList<>();
            int start = 0;
            while (start < certification.length()) {
                int end = paint.breakText(certification, start, certification.length(), true, 250, null);
                lines.add(certification.substring(start, start + end));
                start += end;
            }

            // Draw each line
            for (String line : lines) {
                canvas.drawText(" • " + line, marginLeft, currentY, paint);
                currentY += lineHeight;
            }

            // Add margin for the next block of text
            currentY += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }

    private void drawContactInfo(Canvas canvas, float startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Load icons
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_phone); // Phone icon
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email); // Email icon
        Bitmap addressIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_linkdin); // Address icon
        Bitmap linkedinIcon = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder); // Placeholder for LinkedIn

        // Fetch contact information
        String phone = dbInfoPersonnelle.getInfo().get(0).getN_phone(); // Phone number
        String email = dbInfoPersonnelle.getInfo().get(0).getEmail(); // Email address
        String address = dbInfoPersonnelle.getInfo().get(0).getPays(); // Physical address
        String linkedinUrl = dbInfoPersonnelle.getInfo().get(0).getPays() + "," + dbInfoPersonnelle.getInfo().get(0).getVille(); // LinkedIn URL

        // Define margins and spacing
        float iconMargin = 10; // Margin between icon and text
        float lineHeight = 40; // Line height for spacing between lines
        float startX = 250; // Starting X position

        // Draw phone
        float phoneX = startX + phoneIcon.getWidth() + iconMargin;
        canvas.drawBitmap(phoneIcon, startX, startY - phoneIcon.getHeight() / 2, null);
        canvas.drawText(phone, phoneX, startY, paint);

        // Draw email below phone
        float emailY = startY + lineHeight;
        float emailX = startX + emailIcon.getWidth() + iconMargin;
        canvas.drawBitmap(emailIcon, startX, emailY - emailIcon.getHeight() / 2, null);
        canvas.drawText(email, emailX, emailY, paint);

        // Draw address below email
        float addressY = emailY + lineHeight;
        float addressX = startX + addressIcon.getWidth() + iconMargin;
        canvas.drawBitmap(addressIcon, startX, addressY - addressIcon.getHeight() / 2, null);
        canvas.drawText(address, addressX, addressY, paint);

        // Draw LinkedIn URL below address
        float linkedinY = addressY + lineHeight;
        float linkedinX = startX + linkedinIcon.getWidth() + iconMargin;
        canvas.drawBitmap(linkedinIcon, startX, linkedinY - linkedinIcon.getHeight() / 2, null);
        canvas.drawText(linkedinUrl, linkedinX, linkedinY, paint);

        // Recycle bitmaps
        phoneIcon.recycle();
        emailIcon.recycle();
        addressIcon.recycle();
        linkedinIcon.recycle();
    }





    private int drawEducation(Canvas canvas, float startY) {
        // Create and configure the Paint object for headings
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for school names (big and black)
        Paint schoolNamePaint = new Paint();
        schoolNamePaint.setColor(Color.rgb(48,48,48));
        schoolNamePaint.setTextSize(18); // Adjust size as needed
        schoolNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for date ranges (small and gray)
        Paint dateRangePaint = new Paint();
        dateRangePaint.setColor(Color.rgb(48,48,48)); // Color for date ranges
        dateRangePaint.setTextSize(14); // Adjust size as needed
        dateRangePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Create and configure the Paint object for professions (normal)
        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.rgb(40,40,40));
        professionPaint.setTextSize(15); // Adjust size as needed
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Draw the "Éducation" heading
        String heading = "Éducation".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the education information
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Draw each piece of education information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (InfoEducation education : educationInfo) {
            boolean isNotEmpty =
                    !education.getShool().trim().isEmpty() &&
                            !education.getMetier().trim().isEmpty() &&
                            !education.getStartYier().trim().isEmpty() &&
                            !education.getEndYier().trim().isEmpty();

            if (isNotEmpty) {
                // Draw the school name on the first line
                canvas.drawText(education.getShool(), marginLeft, currentY, schoolNamePaint);

                // Move to the next line for the job title
                currentY += lineHeight;
                canvas.drawText(education.getMetier(), marginLeft, currentY, professionPaint);

                // Move to the next line for the date range
                currentY += lineHeight;
                String dateRange = education.getStartYier() + " - " + education.getEndYier();
                canvas.drawText(dateRange, marginLeft, currentY, dateRangePaint);

                // Move to the next section with padding
                currentY += lineHeight + elementMarginTop;
            }
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }






    private int drawSkills(Canvas canvas, int startY) {
        // Create and configure the Paint object for the skills heading (bold and big)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for skill items (normal)
        Paint skillPaint = new Paint();
        skillPaint.setColor(Color.rgb(40, 40, 40));
        skillPaint.setTextSize(15); // Adjust size as needed
        skillPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;

        // Get the skills information
        ArrayList<String> skillsInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList(); // Assuming a method to fetch skills info

        // Check if there are skills to display
        if (skillsInfo == null || skillsInfo.isEmpty()) {
            // No skills, return the current Y position without drawing anything
            return startY;
        }

        // Draw the "Skills" heading
        String heading = "Skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw each skill
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        for (String skill : skillsInfo) {
            canvas.drawText("• " + skill, marginLeft, currentY, skillPaint);
            currentY += lineHeight - 5; // Adjust vertical spacing
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }


    private int drawLoisirs(Canvas canvas, int startY) {
        // Create and configure the Paint object for the loisirs heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for loisirs (normal)
        Paint loisirsPaint = new Paint();
        loisirsPaint.setColor(Color.rgb(40, 40, 40));
        loisirsPaint.setTextSize(15); // Adjust size as needed
        loisirsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int bulletMarginLeft = 40; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 0;

        /*
        * int marginLeft = 40;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;*/

        // Get the loisirs information
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList(); // Replace with your method to fetch loisirs

        // Check if the loisirs list is empty
        if (loisirs == null || loisirs.isEmpty()) {
            // If empty, return the current Y position without drawing the section
            return startY;
        }

        // Draw the "Loisirs" heading
        String heading = "Loisirs".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw each piece of loisirs information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (String loisir : loisirs) {
            // Draw the loisirs on the line with additional margin for bullet point
            canvas.drawText("• " + loisir, bulletMarginLeft, currentY, loisirsPaint);

            // Move to the next line with padding
            currentY += lineHeight + elementMarginTop - 5;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }



    private int softskillsBottomY(Canvas canvas, int startY) {
        // Create and configure the Paint object for the soft skills heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for skills (normal)
        Paint skillsPaint = new Paint();
        skillsPaint.setColor(Color.rgb(40, 40, 40));
        skillsPaint.setTextSize(15); // Adjust size as needed
        skillsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int bulletMarginLeft = 40; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 0;

        // Get the soft skills information
        ArrayList<String> skills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList(); // Fetch skills info

        // Check if the skills list is empty
        if (skills == null || skills.isEmpty()) {
            // If empty, return the current Y position without drawing the section
            return startY;
        }

        // Draw the "Soft Skills" heading
        String heading = "Soft Skills".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Draw each skill
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (String skill : skills) {
            // Draw the skill with a bullet point
            canvas.drawText("• " + skill, bulletMarginLeft, currentY, skillsPaint);
            // Move to the next line with padding
            currentY += lineHeight + elementMarginTop - 5;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }




    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.BLACK); // Set text color to black
        normalPaint.setTextSize(15);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.BLACK); // Set text color to black
        bulletPaint.setTextSize(15);
        bulletPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 250;
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 25;
        int bulletMargin = 20;
        int maxTextWidth = 300; // Maximum width for text before wrapping

        // Retrieve experience information from database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Filter out empty or invalid experiences
        ArrayList<InfoExperience> validExperienceInfo = new ArrayList<>();
        for (InfoExperience info : experienceInfo) {
            if (info.getNomEntreprise() != null && !info.getNomEntreprise().isEmpty() &&
                    info.getDateDébut() != null && !info.getDateDébut().isEmpty() &&
                    info.getDateDeFin() != null && !info.getDateDeFin().isEmpty() &&
                    info.getTitreDePoste() != null && !info.getTitreDePoste().isEmpty() &&
                    info.getRésumé() != null && !info.getRésumé().isEmpty()) {
                validExperienceInfo.add(info);
            }
        }

        // If no valid experiences, skip this section
        if (validExperienceInfo.isEmpty()) {
            return startY;
        }

        // Draw the "Expérience de Travail" heading
        String heading = "Expérience de Travail".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Calculate the Y offset for experience information
        float yOffset = headingY + headingMarginBottom + elementMarginTop;

        // Draw each piece of experience information
        for (InfoExperience info : validExperienceInfo) {
            // Calculate the width of the company name
            float companyWidth = bulletPaint.measureText(info.getNomEntreprise());

            // Draw the company name
            canvas.drawText(info.getNomEntreprise(), marginLeft, yOffset, bulletPaint);

            // Draw the start and end dates with a 50-pixel margin between them
            float dateX = marginLeft + companyWidth + 50;
            String dateText = info.getDateDébut() + " - " + info.getDateDeFin();
            canvas.drawText(dateText, dateX, yOffset, bulletPaint);
            yOffset += lineHeight;

            // Draw the job title on the next line
            canvas.drawText(info.getTitreDePoste(), marginLeft, yOffset, normalPaint);
            yOffset += lineHeight;

            normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            // Draw the resume on the next line with a bullet point, wrapping if necessary
            String resume = "• " + info.getRésumé();
            yOffset = drawWrappedText(canvas, resume, marginLeft + bulletMargin, yOffset, normalPaint, maxTextWidth);
            yOffset += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }


    private float drawWrappedText(Canvas canvas, String text, float x, float startY, Paint paint, int maxWidth) {
        float y = startY;
        float textWidth = paint.measureText(text);

        // If text is too wide, wrap it
        if (textWidth > maxWidth) {
            // Split text into words
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder();

            for (String word : words) {
                String testLine = line.toString() + word + " ";
                float testWidth = paint.measureText(testLine);

                // If line width exceeds maxWidth, draw the line and start a new one
                if (testWidth > maxWidth) {
                    canvas.drawText(line.toString(), x, y, paint);
                    y += paint.getTextSize();
                    line = new StringBuilder(word + " ");
                } else {
                    line.append(word).append(" ");
                }
            }
            // Draw the last line
            canvas.drawText(line.toString(), x, y, paint);
            y += paint.getTextSize();
        } else {
            // If text does not exceed maxWidth, draw it on one line
            canvas.drawText(text, x, y, paint);
            y += paint.getTextSize();
        }

        return y;
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


    private int drawLanguages(Canvas canvas, int startY) {
        // Create and configure the Paint object for the languages heading (big and black)
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Adjust size as needed
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Create and configure the Paint object for languages (normal)
        Paint languagePaint = new Paint();
        languagePaint.setColor(Color.rgb(40, 40, 40));
        languagePaint.setTextSize(15); // Adjust size as needed
        languagePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Define margins and spacing
        int marginLeft = 40;
        int bulletMarginLeft = 40; // Additional margin for bullet points
        int headingMarginBottom = 10;
        int elementMarginTop = 25;
        int lineHeight = 0;

        // Draw the "Languages" heading
        String heading = "Languages".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        // Get the languages information
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList(); // Replace with your method to fetch languages

        // Draw each piece of language information
        float currentY = headingY + headingMarginBottom + elementMarginTop;

        for (String language : languages) {
            // Draw the language on the line with additional margin for bullet point
            canvas.drawText("• " + language, bulletMarginLeft, currentY, languagePaint);

            // Move to the next line with padding
            currentY += lineHeight + elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }



    private int drawCompetence(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int marginLeft = 250;
        int headingMarginBottom = 10;
        int elementMarginTop = 20;
        int lineHeight = 25;

        // Draw the "Compétence" heading
        String heading = "Proféle".toUpperCase();
        float headingY = startY + paint.getTextSize();
        canvas.drawText(heading, marginLeft, headingY, paint);

        // Competence information text (dummy data here; replace with actual data)
        ArrayList<InfoPersonnelle> aboutUser = dbInfoPersonnelle.getInfo();

        // Prepare for drawing competence information
        float currentY = headingY + headingMarginBottom + elementMarginTop;
        paint.setTextSize(15);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        for (int i = 0; i < aboutUser.size(); i++) {
            String text = aboutUser.get(i).getAbout()+aboutUser.get(i).getAbout()+aboutUser.get(i).getAbout();
            List<String> lines = new ArrayList<>();

            // Break text into multiple lines if it exceeds 300 pixels
            int start = 0;
            while (start < text.length()) {
                int end = paint.breakText(text, start, text.length(), true, 300, null);
                lines.add(text.substring(start, start + end));
                start += end;
            }

            // Draw each line
            for (String line : lines) {
                canvas.drawText(line, marginLeft, currentY, paint);
                currentY += lineHeight;
            }

            // Add margin for the next block of text
            currentY += elementMarginTop;
        }

        // Return the bottom Y position of this section
        return (int) currentY;
    }


    private Paint createPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(14);
        return paint;
    }

    private PdfDocument.PageInfo createPageInfo() {
        int contentHeight = 1080;
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



    private void drawBottomRectangle(Canvas canvas) {
        // Create and configure the Paint object for the rectangle
        Paint rectanglePaint = new Paint();
        rectanglePaint.setColor(Color.rgb(251, 217, 202)); // Set the desired color for the rectangle

        // Define the coordinates for the rectangle
        float startX = 0;
        float startY = canvas.getHeight()-30;
        float endX = canvas.getWidth();
        float endY = canvas.getHeight();

        // Draw the rectangle on the canvas
        canvas.drawRect(startX, startY, endX, endY, rectanglePaint);
    }





    private void drawUserName(Canvas canvas, int logoBottomY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getF_name())  ;
        float textWidth = paint.measureText(capitalizeFirstLetterOfEachWord(userName));

        // Define a fixed vertical space from the bottom of the logo

        // Calculate the vertical position for the text (fixed space from the bottom of the logo)

        // Calculate the position for the text (center horizontally)
        float textX = 250;

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, 110, paint);
    }

    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Set a different color for job title
        paint.setTextSize(25); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle =capitalizeFirstLetterOfEachWord( dbInfoPersonnelle.getInfo().get(0).getJob()); // Adjust method to get job title
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = nameTextY + spaceBelowName + paint.getTextSize();

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = 250;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);
    }



    private void drawGreenBackground(Canvas canvas) {
        // Create a Paint object with the desired green color
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255,236,227)); // Set the color to green

        // Define the coordinates for the background rectangle
        float startX = 10;
        float startY = 20;
        float endX = 230;
        float endY = canvas.getHeight()-80;

        // Draw the rectangle on the canvas
        canvas.drawRect(startX, startY, endX, endY, paint);
    }






    private Bitmap drawCircularImage(Canvas canvas, Bitmap bitmap, int x, int y) {
        int imageSize = (int) (canvas.getWidth() * IMAGE_SIZE_PERCENT / 100);
        int borderWidth = 2;
        int padding = 0;
        int totalSize = imageSize + 2 * (borderWidth + padding);

        Bitmap circularBitmap = Bitmap.createBitmap(totalSize, totalSize, Bitmap.Config.ARGB_8888);
        Canvas circularCanvas = new Canvas(circularBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Draw the circular mask
        float radius = totalSize / 2f;
        circularCanvas.drawCircle(radius, radius, radius - borderWidth - padding, paint);

        // Set the Xfermode to SRC_IN to draw the bitmap only within the circular mask
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect destRect = new Rect(padding + borderWidth, padding + borderWidth, totalSize - (padding + borderWidth), totalSize - (padding + borderWidth));
        circularCanvas.drawBitmap(bitmap, srcRect, destRect, paint);

        // Draw the blue border around the circular image
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setAntiAlias(true);
        circularCanvas.drawCircle(radius, radius, radius - borderWidth / 2f, borderPaint);

        // Draw the resulting circular bitmap with border onto the original canvas
        canvas.drawBitmap(circularBitmap, x, y, null);

        return circularBitmap;
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
