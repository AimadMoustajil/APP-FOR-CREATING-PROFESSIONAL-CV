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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Classes.InfoEducation;
import com.example.app_creat_profesionell_cv.Classes.InfoExperience;
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

public class M1 extends AppCompatActivity {

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
        setContentView(R.layout.activity_m1);

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
                startActivity(new Intent(M1.this, InfoPersonnelleActivity.class));
            }
        });

        éducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, InfoEducationActivity.class));
            }
        });
        exDeTravaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, ExperinceActivity.class));
            }
        });
        projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, InfoProjetActivity.class));
            }
        });

        infoAdditionnelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(M1.this, InformationAdditionnelleActivity.class));
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
                if (ContextCompat.checkSelfPermission(M1.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(M1.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(M1.this, ShowDocumentActivity.class);
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

            // Draw the background color
            Paint backgroundPaint = new Paint();
            backgroundPaint.setColor(Color.parseColor("#DBEFFF"));
            canvas.drawRect(0, 0, pageInfo.getPageWidth(), 200, backgroundPaint); // Draw rectangle with full width and height of 200

            drawVerticalLine(canvas, 200, canvas.getHeight());
            // Draw header image
            drawContactInfo(canvas,220);

            // Draw Competence and get the Y position where the next section should start
            int competenceEndY = drawCompetence(canvas, 220);

            // Draw Experience de Travail section starting just below Competence
            drawExperienceDeTravail(canvas, competenceEndY + 30);

            int experienceEndY = drawExperienceDeTravail(canvas, competenceEndY + 30); // Adding extra margin

            // Draw Projet section starting just below Experience de Travail
            drawProjet(canvas, experienceEndY + 30);
            int projetEndY = drawProjet(canvas, experienceEndY + 30);
            drawEducation(canvas, projetEndY + 30);


            drawLanguages(canvas, 430);

            // Draw user name and job title
            int logoBottomY =100;
            drawUserName(canvas, logoBottomY);

            Paint paintForJobTitle = new Paint();
            paintForJobTitle.setColor(Color.GRAY);
            paintForJobTitle.setTextSize(40);
            paintForJobTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            // Draw job title
            drawUserJobTitle(canvas, (canvas.getWidth() - paint.measureText(dbInfoPersonnelle.getInfo().get(0).getJob())) / 2, logoBottomY - 50);

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






    private int drawProjet(Canvas canvas, int startY) {
        // Create and configure Paint objects
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Paint blueCirclePaint = new Paint();
        blueCirclePaint.setColor(Color.rgb(219,239,255));
        blueCirclePaint.setStyle(Paint.Style.FILL);

        final int marginLeft = 260;
        final int headingMarginBottom = 10;
        final int elementMarginTop = 25;
        final int lineHeight = 25;
        final int verticalLineX = 240;
        final int lineLength = 240;

        // Define margins for resume text
        final int bulletPointMarginLeft = marginLeft + 20; // Margin before the bullet point
        final int resumeTextMarginLeft = bulletPointMarginLeft + 20; // Additional margin for the text

        ArrayList<InfoProjet> projectInfo;
        try {
            projectInfo = dbProjet.getAllInfoProjets();
        } catch (Exception e) {
            return startY;
        }

        boolean hasContent = false;
        for (InfoProjet info : projectInfo) {
            if ((info.getTitreProjet() != null && !info.getTitreProjet().isEmpty()) ||
                    (info.getDateDebut() != null && !info.getDateDebut().isEmpty()) ||
                    (info.getDateFin() != null && !info.getDateFin().isEmpty()) ||
                    (info.getResume() != null && !info.getResume().isEmpty())) {
                hasContent = true;
                break;
            }
        }

        if (!hasContent) {
            return startY;
        }

        // Draw the heading with a blue circle behind the first letter
        String heading = "Projet".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();

        // Draw blue circle behind the first letter
        float firstLetterWidth = headingPaint.measureText(heading.substring(0, 1));
        canvas.drawCircle(marginLeft + firstLetterWidth / 2, headingY - headingPaint.getTextSize() / 2, firstLetterWidth, blueCirclePaint);

        // Draw the heading text
        canvas.drawText(heading, marginLeft, headingY, headingPaint);

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(100,100,100));
        normalPaint.setTextSize(17);
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        Paint bigPaint = new Paint();
        bigPaint.setColor(Color.rgb(84,84,84));
        bigPaint.setTextSize(17);
        bigPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        int yOffset = (int) (headingY + headingMarginBottom + elementMarginTop);

        for (InfoProjet info : projectInfo) {
            if ((info.getTitreProjet() != null && !info.getTitreProjet().isEmpty()) ||
                    (info.getDateDebut() != null && !info.getDateDebut().isEmpty()) ||
                    (info.getDateFin() != null && !info.getDateFin().isEmpty()) ||
                    (info.getResume() != null && !info.getResume().isEmpty())) {

                // Draw the enterprise name, start date, and end date on the same line
                if (info.getNomEntreprise() != null && !info.getNomEntreprise().isEmpty()) {
                    String enterpriseText = info.getNomEntreprise().toUpperCase();
                    canvas.drawText(enterpriseText, marginLeft, yOffset, bigPaint);

                    // Draw start and end dates with margin
                    String duration = (info.getDateDebut() != null ? info.getDateDebut() : "") +
                            " - " +
                            (info.getDateFin() != null ? info.getDateFin() : "");
                    float dateX = marginLeft + 150; // Assuming margin
                    canvas.drawText(duration, dateX, yOffset, bigPaint);

                    yOffset += lineHeight;
                }

                // Draw the project title with gray background
                if (info.getTitreProjet() != null && !info.getTitreProjet().isEmpty()) {
                    String titleText = info.getTitreProjet();
                    float titleWidth = bigPaint.measureText(titleText);

                    // Draw the title text
                    canvas.drawText(titleText, marginLeft, yOffset, normalPaint);
                    yOffset += lineHeight;
                }

                // Draw the resume with bullet point, wrapping text longer than 300 units
                if (info.getResume() != null && !info.getResume().isEmpty()) {
                    // Define bullet point text
                    String bulletPoint = "•";
                    String resumeText = info.getResume();

                    // Draw the bullet point with margin
                    canvas.drawText(bulletPoint, bulletPointMarginLeft, yOffset, normalPaint);

                    // Define maximum width for wrapping text
                    int maxWidth = 300;
                    List<String> lines = wrapText(resumeText, normalPaint, maxWidth - 30); // Adjust for bullet point margin

                    // Draw the resume text lines
                    for (String line : lines) {
                        canvas.drawText(line, resumeTextMarginLeft, yOffset, normalPaint);
                        yOffset += lineHeight;
                    }

                    yOffset += elementMarginTop;
                }
            }
        }

        drawHorizontalLineWithCircle(canvas, verticalLineX, yOffset, 0, lineLength);
        return yOffset;
    }




    // Helper method to wrap text to the next line if it exceeds the specified width
    private List<String> wrapText(String text, Paint paint, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            float width = paint.measureText(line.toString() + " " + word);
            if (width > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
            }
        }
        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines;
    }



    private void drawHorizontalLineWithCircle(Canvas canvas, float verticalLineX, float startY, float headingWidth, float lineLength) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Line width

        // Draw the horizontal line
        canvas.drawLine(verticalLineX, startY, verticalLineX + lineLength, startY, linePaint);

        // Draw a circle with green fill and black border at the intersection with the vertical line
        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.rgb(219,239,255)); // Fill color

        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.BLACK); // Border color
        borderPaint.setStrokeWidth(2); // Border width

        float circleRadius = 5; // Radius of the circle
        canvas.drawCircle(verticalLineX, startY, circleRadius, circlePaint); // Draw circle fill
        canvas.drawCircle(verticalLineX, startY, circleRadius, borderPaint); // Draw circle border
    }























    // Method to draw contact information
    // Method to draw contact information
    // Method to draw contact information
    // Method to draw contact information
    // Method to draw contact information
    // Method to draw contact information
    private int drawContactInfo(Canvas canvas, int startY) {
        // Create a Paint object for the heading with a bold typeface and black color
        Paint headingPaint = createPaint(20, Typeface.defaultFromStyle(Typeface.BOLD), Color.BLACK);
        Paint letterBackgroundPaint = createPaint(0, Typeface.defaultFromStyle(Typeface.NORMAL), Color.rgb(219, 239, 255));
        letterBackgroundPaint.setStyle(Paint.Style.FILL);

        // Set margins and spacing values
        int marginLeft = 60;
        int headingMarginBottom = 10;
        int elementMarginTop = 15;
        int lineHeight = 50;
        int sectionBottomY;

        // Load icons from resources
        Bitmap phoneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_phone);
        Bitmap emailIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email);
        Bitmap linkdinIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_linkdin);
        Bitmap placeholderIcon = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder); // Placeholder icon

        // Array to store icons
        Bitmap[] icons = {phoneIcon, emailIcon, linkdinIcon, placeholderIcon};

        // Calculate the new left margin to align heading with the phone icon
        int iconWidth = phoneIcon.getWidth();
        int newMarginLeft = marginLeft - iconWidth - 10;

        // Draw the "Contact" heading with first letter having a blue background
        String heading = "C ontact".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();

        // Define size and position for the blue background circle
        float circleRadius = headingPaint.getTextSize() * 0.7f; // Radius of the circle
        float circleCenterX = newMarginLeft + circleRadius;
        float circleCenterY = headingY - headingPaint.getTextSize() / 2;

        // Draw blue circle background
        canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, letterBackgroundPaint);

        // Draw the first letter on top of the blue circle
        Paint letterPaint = new Paint(headingPaint);
        letterPaint.setColor(Color.BLACK); // Color for the letter
        canvas.drawText(heading.substring(0, 1), circleCenterX - circleRadius / 2, headingY, letterPaint);

        // Draw the rest of the heading text
        canvas.drawText(heading.substring(1), newMarginLeft + circleRadius * 1.2f, headingY, headingPaint);

        // Text information for contact details
        String[] contactInfo = {
                dbInfoPersonnelle.getInfo().get(0).getN_phone(),
                dbInfoPersonnelle.getInfo().get(0).getEmail(),
                dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLinkdin(), // LinkedIn information
                dbInfoPersonnelle.getInfo().get(0).getPays() + ", " + dbInfoPersonnelle.getInfo().get(0).getVille()
        };

        // Configure Paint object for contact information
        headingPaint.setTextSize(10);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // Draw each line of contact information with its icon
        for (int i = 0; i < contactInfo.length; i++) {
            // Draw icon
            if (icons[i] != null) {
                float iconY = headingY + headingMarginBottom + elementMarginTop - 10 + headingPaint.getTextSize() + lineHeight * i - icons[i].getHeight() / 2;
                canvas.drawBitmap(icons[i], newMarginLeft, iconY, null);
            }

            // Draw text
            float textY = headingY + headingMarginBottom + elementMarginTop + headingPaint.getTextSize() + lineHeight * i;
            canvas.drawText(contactInfo[i], marginLeft, textY, headingPaint);
        }

        // Calculate section bottom Y position
        sectionBottomY = (int) (headingY + headingMarginBottom + elementMarginTop + headingPaint.getTextSize() + lineHeight * contactInfo.length);

        // Draw horizontal line below section from icon position to 240x
        float iconLineX = newMarginLeft + iconWidth + 10;
        canvas.drawLine(iconLineX - 40, sectionBottomY, 240, sectionBottomY, headingPaint);

        // Draw vertical line at 240x
        canvas.drawLine(240, startY, 240, sectionBottomY, headingPaint);

        // Draw circle at intersection points with blue fill and black border
        Paint circlePaint = createPaint(0, Typeface.defaultFromStyle(Typeface.NORMAL), Color.rgb(219, 239, 255));
        circlePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(240, sectionBottomY, 5, circlePaint);

        // Draw the black border around the circle
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE); // Set style to STROKE for border
        borderPaint.setStrokeWidth(2); // Set the stroke width for the border
        canvas.drawCircle(240, sectionBottomY, 5, borderPaint);

        // Return the bottom Y position of this section
        return sectionBottomY;
    }




    // Method to create and configure a Paint object
    private Paint createPaint(int textSize, Typeface typeface, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setTypeface(typeface);
        return paint;
    }







    private int drawCompetence(Canvas canvas, int startY) {
        // Retrieve the competence information
        ArrayList<String> competenceInfo = dbInformationAitionnelle.getInfoAdditionnelle().get(dbInformationAitionnelle.getInfoAdditionnelle().size() - 1).getCompetanceArrayList();

        // Check if competenceInfo is empty; if so, skip drawing and return startY
        if (competenceInfo == null || competenceInfo.isEmpty()) {
            return startY;
        }

        // Create and configure Paint objects
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Heading text size
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        Paint blueCirclePaint = new Paint();
        blueCirclePaint.setColor(Color.rgb(219,239,255));
        blueCirclePaint.setStyle(Paint.Style.FILL);

        Paint competencePaint = new Paint();
        competencePaint.setColor(Color.BLACK);
        competencePaint.setTextSize(15); // Text size for competence info
        competencePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface

        // Define margins and spacing
        final int marginLeft = 260;
        final int headingMarginBottom = 10; // Space below the heading
        final int elementMarginTop = 25; // Space between line and first element
        final int lineHeight = 25; // Space between lines
        final int verticalLineX = 240; // X position for vertical line
        final int lineLength = 240; // Length of the horizontal line

        // Draw the "Compétence" heading with a blue circle
        String heading = "Compétence".toUpperCase();
        float headingY = startY + headingPaint.getTextSize();

        // Draw blue circle behind the first letter
        float firstLetterWidth = headingPaint.measureText(heading.substring(0, 1));
        canvas.drawCircle(marginLeft + firstLetterWidth / 2, headingY - headingPaint.getTextSize() / 2, firstLetterWidth, blueCirclePaint);

        // Draw the heading text
        canvas.drawText(heading, marginLeft, headingY, headingPaint);


        // Define bullet point and spacing
        String bulletPoint = "• "; // Bullet point symbol
        final int bulletPointMarginLeft = 10; // Margin before the bullet point

        // Draw each line of competence information with a bullet point
        float yOffset = headingY + headingMarginBottom + elementMarginTop;
        for (String competence : competenceInfo) {
            // Wrap text if it exceeds 300 units in width
            float textWidth = competencePaint.measureText(competence);
            if (textWidth > 300) {
                // Implement text wrapping logic here
            }

            canvas.drawText(bulletPoint + competence, marginLeft + bulletPointMarginLeft, yOffset, competencePaint);
            yOffset += lineHeight;
        }

        // Calculate the Y position for the horizontal line and circle
        int sectionBottomY = (int) (yOffset + elementMarginTop);

        // Draw the horizontal line and circle below the entire section
        drawHorizontalLineWithCircle(canvas, verticalLineX, sectionBottomY, 0, lineLength);

        // Return the bottom Y position of this section
        return sectionBottomY;
    }













    private int drawExperienceDeTravail(Canvas canvas, int startY) {
        // Retrieve experience information from the database
        ArrayList<InfoExperience> experienceInfo = dbExperienceDeTravaille.getAllInfoExperience();

        // Check if experienceInfo is empty; if so, skip drawing and return startY
        if (experienceInfo == null || experienceInfo.isEmpty()) {
            return startY;
        }

        // Create and configure Paint objects
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Text size for the heading
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        Paint blueCirclePaint = new Paint();
        blueCirclePaint.setColor(Color.rgb(219, 239, 255)); // Light blue color
        blueCirclePaint.setStyle(Paint.Style.FILL);

        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(84, 84, 84)); // Dark gray color
        normalPaint.setTextSize(17); // Text size for experience details
        normalPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface

        Paint bigPaint = new Paint();
        bigPaint.setColor(Color.rgb(100, 100, 100));
        bigPaint.setTextSize(15); // Text size for title and subject
        bigPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.BLACK);
        bulletPaint.setTextSize(17); // Text size for bullet points

        Paint grayBackgroundPaint = new Paint();
        grayBackgroundPaint.setColor(Color.GRAY);
        grayBackgroundPaint.setStyle(Paint.Style.FILL);

        final int marginLeft = 260;
        final int headingMarginBottom = 10;
        final int elementMarginTop = 10;
        final int lineHeight = 25;
        final int verticalLineX = 240;
        final int lineLength = 240;
        final int bulletPointMargin = 10; // Margin before the bullet point

        boolean hasContent = false;
        float yOffset = startY;

        // Check if any experience item has content
        for (InfoExperience info : experienceInfo) {
            if ((info.getNomEntreprise() != null && !info.getNomEntreprise().isEmpty()) ||
                    (info.getTitreDePoste() != null && !info.getTitreDePoste().isEmpty()) ||
                    (info.getDateDébut() != null && !info.getDateDébut().isEmpty()) ||
                    (info.getDateDeFin() != null && !info.getDateDeFin().isEmpty()) ||
                    (info.getRésumé() != null && !info.getRésumé().isEmpty())) {
                hasContent = true;
                break;
            }
        }

        if (hasContent) {
            // Draw the "Expérience de Travail" heading with a blue circle
            String heading = "Expérience de Travail".toUpperCase();
            float headingY = yOffset + headingPaint.getTextSize();

            // Draw blue circle behind the first letter
            float firstLetterWidth = headingPaint.measureText(heading.substring(0, 1));
            canvas.drawCircle(marginLeft + firstLetterWidth / 2, headingY - headingPaint.getTextSize() / 2, firstLetterWidth, blueCirclePaint);

            // Draw the heading text
            canvas.drawText(heading, marginLeft, headingY, headingPaint);

            yOffset = headingY + headingMarginBottom + elementMarginTop;

            // Draw each piece of experience information
            for (InfoExperience info : experienceInfo) {
                boolean infoHasContent = false;

                // Draw the company name, start date, and end date on the same line
                if (info.getNomEntreprise() != null && !info.getNomEntreprise().isEmpty()) {
                    canvas.drawText(info.getNomEntreprise().toUpperCase(), marginLeft, yOffset, normalPaint);

                    if (info.getDateDébut() != null && info.getDateDeFin() != null) {
                        String duration = info.getDateDébut() + " - " + info.getDateDeFin();
                        float dateX = marginLeft + 150; // Adjusted margin for date
                        canvas.drawText(duration, dateX, yOffset, normalPaint);
                    }

                    yOffset += lineHeight;
                    infoHasContent = true;
                }

                // Draw the job title with gray background
                if (info.getTitreDePoste() != null && !info.getTitreDePoste().isEmpty()) {
                    String titleText = info.getTitreDePoste();
                    canvas.drawText(titleText, marginLeft, yOffset, bigPaint);
                    yOffset += lineHeight;
                }

                // Draw the resume with bullet point and wrapping
                if (info.getRésumé() != null && !info.getRésumé().isEmpty()) {
                    String resumeText = "• " + info.getRésumé();
                    int maxWidth = 300;
                    List<String> lines = wrapText(resumeText, normalPaint, maxWidth);

                    for (String line : lines) {
                        // Adjust X position for bullet point with margin
                        String bulletPoint = "• "; // Bullet point character
                        float bulletPointX = marginLeft + bulletPointMargin;
                        canvas.drawText(bulletPoint, bulletPointX, yOffset, bulletPaint);

                        // Draw the resume text, adjusting X position to be after the bullet point
                        float bulletPointWidth = bulletPaint.measureText(bulletPoint);
                        canvas.drawText(line, bulletPointX + bulletPointWidth, yOffset, normalPaint);
                        yOffset += lineHeight;
                    }

                    yOffset += elementMarginTop;
                }

                // Add space between different experiences if any content was drawn
                if (infoHasContent) {
                    yOffset += elementMarginTop;
                }
            }

            // Draw the horizontal line and circle below the entire section
            drawHorizontalLineWithCircle(canvas, verticalLineX, yOffset, 0, lineLength);
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }

    // Helper method to wrap text to the next line if it exceeds the specified width












    private int drawEducation(Canvas canvas, int startY) {
        // Retrieve education information from the database
        ArrayList<InfoEducation> educationInfo = dbInfoEducation.getAllInfoEducation();

        // Check if educationInfo is empty; if so, skip drawing and return startY
        if (educationInfo == null || educationInfo.isEmpty()) {
            return startY;
        }

        // Create and configure Paint objects
        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLACK);
        headingPaint.setTextSize(20); // Text size for the heading
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        Paint blueCirclePaint = new Paint();
        blueCirclePaint.setColor(Color.rgb(219,239,255));
        blueCirclePaint.setStyle(Paint.Style.FILL);

        Paint schoolAndDatePaint = new Paint();
        schoolAndDatePaint.setColor(Color.rgb(84,84,84));
        schoolAndDatePaint.setTextSize(17); // Text size for school names and date ranges
        schoolAndDatePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface

        Paint professionPaint = new Paint();
        professionPaint.setColor(Color.BLACK);
        professionPaint.setTextSize(15); // Text size for professions
        professionPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Normal typeface for professions

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.BLACK);
        bulletPaint.setTextSize(15); // Text size for bullet points

        int marginLeft = 260;
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 25; // Space between elements
        int lineHeight = 25; // Space between lines
        int verticalLineX = 240; // X position for vertical line
        int lineLength = 240; // Length of the horizontal line
        int bulletPointMargin = 10; // Margin before the bullet point

        boolean hasContent = false;
        float yOffset = startY;

        // Check if any education item has content
        for (InfoEducation info : educationInfo) {
            if ((info.getShool() != null && !info.getShool().isEmpty()) ||
                    (info.getStartYier() != null && !info.getStartYier().isEmpty()) ||
                    (info.getEndYier() != null && !info.getEndYier().isEmpty()) ||
                    (info.getMetier() != null && !info.getMetier().isEmpty())) {
                hasContent = true;
                break;
            }
        }

        if (hasContent) {
            // Draw the "Éducation" heading with a blue circle
            String heading = "ÉDUCATION".toUpperCase();
            float headingY = yOffset + headingPaint.getTextSize();

            // Draw blue circle behind the first letter
            float firstLetterWidth = headingPaint.measureText(heading.substring(0, 1));
            canvas.drawCircle(marginLeft + firstLetterWidth / 2, headingY - headingPaint.getTextSize() / 2, firstLetterWidth, blueCirclePaint);

            // Draw the heading text
            canvas.drawText(heading, marginLeft, headingY, headingPaint);

            yOffset = headingY + headingMarginBottom + elementMarginTop;

            // Draw each piece of education information
            for (InfoEducation info : educationInfo) {
                boolean infoHasContent = false;

                // Combine school name and date range
                String schoolName = info.getShool() != null ? info.getShool().toUpperCase() : "";
                String startYear = info.getStartYier() != null ? info.getStartYier() : "";
                String endYear = info.getEndYier() != null ? info.getEndYier() : "";
                String dateRange = startYear.isEmpty() && endYear.isEmpty() ? "" : (startYear + " - " + endYear).trim();
                String schoolAndDate = !dateRange.isEmpty() ? schoolName + " " + dateRange : schoolName;

                // Draw the school name and date range
                if (!schoolAndDate.isEmpty()) {
                    canvas.drawText(schoolAndDate, marginLeft, yOffset, schoolAndDatePaint);
                    yOffset += lineHeight;
                    infoHasContent = true;
                }

                // Draw the profession if it has content
                if (info.getMetier() != null && !info.getMetier().isEmpty()) {
                    // Draw bullet point with margin
                    String bulletPoint = "• "; // Bullet point character
                    float bulletPointX = marginLeft + bulletPointMargin;
                    canvas.drawText(bulletPoint, bulletPointX, yOffset, bulletPaint);

                    // Draw the profession text, adjusting X position to be after the bullet point
                    float bulletPointWidth = bulletPaint.measureText(bulletPoint);
                    canvas.drawText(info.getMetier(), bulletPointX + bulletPointWidth, yOffset, professionPaint);

                    yOffset += lineHeight;
                    infoHasContent = true;
                }

                // Add space between different education items if any content was drawn
                if (infoHasContent) {
                    yOffset += elementMarginTop;
                }
            }

            // Draw the horizontal line and circle below the entire section
            drawHorizontalLineWithCircle(canvas, verticalLineX, yOffset, 0, lineLength);
        }

        // Return the bottom Y position of this section
        return (int) yOffset;
    }






    private void drawLanguages(Canvas canvas, int startY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20); // Text size for the heading
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Bold typeface for the heading

        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Line width

        Paint bulletPaint = new Paint();
        bulletPaint.setColor(Color.BLACK);
        bulletPaint.setTextSize(15); // Text size for bullets

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.rgb(219,239,255)); // Background color

        int marginLeft = 24; // Left margin for text
        int headingMarginBottom = 10; // Space below the heading
        int elementMarginTop = 15; // Space between line and first element
        int lineHeight = 25; // Space between lines
        int bulletMarginLeft = 10; // Margin between bullet and text
        int circleRadius = 15; // Radius of the background circle

        float currentY = startY + 50; // Starting Y position with some padding

        // Language information text
        ArrayList<String> languages = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLangueArrayList();

        if (languages != null && !languages.isEmpty()) {
            // Draw the "Languages" heading
            String heading = "Languages".toUpperCase();
            float headingY = currentY + paint.getTextSize(); // Y position for heading

            // Draw the background circle for the first letter
            float headingFirstLetterX = marginLeft; // X position for the first letter
            canvas.drawCircle(headingFirstLetterX + circleRadius / 2, headingY - paint.getTextSize() / 2, circleRadius, backgroundPaint);

            // Draw the first letter of the heading with white color
            paint.setColor(Color.BLACK); // Text color for the first letter
            canvas.drawText(String.valueOf(heading.charAt(0)), headingFirstLetterX - circleRadius / 2, headingY, paint);

            // Reset text color and draw the remaining part of the heading
            paint.setColor(Color.BLACK);
            canvas.drawText(heading.substring(1), headingFirstLetterX + circleRadius / 2, headingY, paint);

            // Measure the width of the heading text
            float headingWidth = paint.measureText(heading);

            // Draw a line directly below the heading
            //canvas.drawLine(marginLeft, headingY + headingMarginBottom, marginLeft + headingWidth, headingY + headingMarginBottom, linePaint);

            // Draw each language with a bullet point
            for (int i = 0; i < languages.size(); i++) {
                //paint.setTextSize(20); // Reset text size for languages
                //paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for languages

                // Draw the bullet point
                String bullet = "•";
                float bulletWidth = bulletPaint.measureText(bullet);
                float textY = headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
                canvas.drawText(bullet, marginLeft, textY, bulletPaint);

                // Draw the language text
                float textX = marginLeft + bulletWidth + bulletMarginLeft; // X position for text
                canvas.drawText(languages.get(i), textX, textY, bulletPaint);
            }

            // Update currentY for the next section
            currentY = headingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * languages.size() + 40; // Y position for the line
            drawHorizontalLine(canvas, marginLeft, currentY, 240);
        }

        // Soft skills information text
        ArrayList<String> softSkills = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getSoftSkillsArrayList();

        if (softSkills != null && !softSkills.isEmpty()) {
            // Draw the "Soft Skills" heading below the languages
            float softSkillsHeadingY = currentY + headingMarginBottom + elementMarginTop; // Adjust Y position

            // Draw the background circle for the first letter
            float softSkillsHeadingFirstLetterX = marginLeft; // X position for the first letter
            canvas.drawCircle(softSkillsHeadingFirstLetterX + circleRadius / 2, softSkillsHeadingY - paint.getTextSize() / 2, circleRadius, backgroundPaint);

            // Draw the first letter of the heading with white color
            paint.setColor(Color.BLACK); // Text color for the first letter
            canvas.drawText(String.valueOf("Soft Skills".charAt(0)).toUpperCase(), softSkillsHeadingFirstLetterX - circleRadius / 2, softSkillsHeadingY, paint);

            // Reset text color and draw the remaining part of the heading
            //paint.setColor(Color.BLACK);
            canvas.drawText("Soft Skills".substring(1).toUpperCase(), softSkillsHeadingFirstLetterX + circleRadius / 2, softSkillsHeadingY, paint);

            // Measure the width of the Soft Skills heading text
            float softSkillsHeadingWidth = paint.measureText("Soft Skills");
            //canvas.drawLine(marginLeft, softSkillsHeadingY + headingMarginBottom, marginLeft + softSkillsHeadingWidth, softSkillsHeadingY + headingMarginBottom, linePaint);

            // Draw each soft skill with a bullet point
            for (int i = 0; i < softSkills.size(); i++) {
                //paint.setTextSize(15); // Reset text size for soft skills
                //paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for soft skills

                // Draw the bullet point
                String bullet = "•";
                float bulletWidth = bulletPaint.measureText(bullet);
                float textY = softSkillsHeadingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
                canvas.drawText(bullet, marginLeft, textY, bulletPaint);

                // Draw the soft skill text
                float textX = marginLeft + bulletWidth + bulletMarginLeft; // X position for text
                canvas.drawText(softSkills.get(i), textX, textY, bulletPaint);
            }

            // Update currentY for the next section
            currentY = softSkillsHeadingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * softSkills.size() + 40; // Y position for the line
            drawHorizontalLine(canvas, marginLeft, currentY, 240);
        }

        // Loisirs information text
        ArrayList<String> loisirs = dbInformationAitionnelle.getInfoAdditionnelle().get(0).getLoisirArrayList();

        if (loisirs != null && !loisirs.isEmpty()) {
            // Draw the "Loisirs" heading below the soft skills
            float loisirsHeadingY = currentY + headingMarginBottom + elementMarginTop; // Adjust Y position

            // Draw the background circle for the first letter
            float loisirsHeadingFirstLetterX = marginLeft; // X position for the first letter
            canvas.drawCircle(loisirsHeadingFirstLetterX + circleRadius / 2, loisirsHeadingY - paint.getTextSize() / 2, circleRadius, backgroundPaint);

            // Draw the first letter of the heading with white color
            //paint.setColor(Color.BLACK); // Text color for the first letter
            canvas.drawText(String.valueOf("Loisirs".charAt(0)).toUpperCase(), loisirsHeadingFirstLetterX - circleRadius / 2, loisirsHeadingY, paint);

            // Reset text color and draw the remaining part of the heading
            //paint.setColor(Color.BLACK);
            canvas.drawText("Loisirs".substring(1).toUpperCase(), loisirsHeadingFirstLetterX + circleRadius / 2, loisirsHeadingY, paint);

            // Measure the width of the Loisirs heading text
            float loisirsHeadingWidth = paint.measureText("Loisirs");
            //canvas.drawLine(marginLeft, loisirsHeadingY + headingMarginBottom, marginLeft + loisirsHeadingWidth, loisirsHeadingY + headingMarginBottom, linePaint);

            // Draw each loisir with a bullet point
            for (int i = 0; i < loisirs.size(); i++) {
                paint.setTextSize(15); // Reset text size for loisirs
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Reset typeface for loisirs

                // Draw the bullet point
                String bullet = "•";
                float bulletWidth = bulletPaint.measureText(bullet);
                float textY = loisirsHeadingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * i;
                canvas.drawText(bullet, marginLeft, textY, bulletPaint);

                // Draw the loisir text
                float textX = marginLeft + bulletWidth + bulletMarginLeft; // X position for text
                canvas.drawText(loisirs.get(i), textX, textY, bulletPaint);
            }

            // Update currentY for the final section
            currentY = loisirsHeadingY + headingMarginBottom + elementMarginTop + paint.getTextSize() + lineHeight * loisirs.size() + 40; // Y position for the line
            drawHorizontalLine(canvas, marginLeft, currentY, 240);
        }
    }





    private void drawHorizontalLine(Canvas canvas, float startX, float y, float length) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Line width
        canvas.drawLine(startX, y, length, y, linePaint);


        // Draw a circle at the end of the line
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.rgb(219,239,255));
        float circleRadius = 5; // Radius of the circle

        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.BLACK); // Border color
        borderPaint.setStrokeWidth(2); // Border width

        canvas.drawCircle(length, y, circleRadius, circlePaint);
        canvas.drawCircle(length, y, circleRadius, borderPaint);
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



    private void drawUserName(Canvas canvas, int logoBottomY) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40); // Adjust text size as needed
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set text style

        // Fetch the user's name
        String userName = dbInfoPersonnelle.getInfo().get(0).getL_name().toUpperCase() + " " + capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getF_name());
        float textWidth = paint.measureText(userName);

        // Calculate the position for the text (center horizontally)
        float textX = (canvas.getWidth() - textWidth) / 2;
        float textY = logoBottomY; // Vertical position for the text

        // Draw the text at the calculated position
        canvas.drawText(userName, textX, textY, paint);

        // Draw the lines
        drawLines(canvas, textX, textY, textWidth);
    }

    private void drawLines(Canvas canvas, float textX, float textY, float textWidth) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2); // Line width

        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.FILL); // Solid fill for circles

        // Define positions for lines
        float startX = 0; // Start of the first line (left edge of the canvas)
        float endX = canvas.getWidth(); // End of the second line (right edge of the canvas)

        float circleRadius = 6; // Radius of the small circles
        float circleDiameter = circleRadius * 2; // Diameter of the circles

        // Draw the line from the start of the canvas to the left edge of the text
        canvas.drawLine(startX, textY, textX - 50, textY, linePaint); // Draw line with 50px distance from text
        // Draw a small circle at the end of the first line
        canvas.drawCircle(textX - 50, textY, circleRadius, circlePaint);

        // Draw the line from the right edge of the text to the end of the canvas
        canvas.drawLine(textX + textWidth + 50, textY, endX, textY, linePaint); // Draw line with 50px distance from text
        // Draw a small circle at the beginning of the second line
        canvas.drawCircle(textX + textWidth + 50, textY, circleRadius, circlePaint);
    }





    private void drawUserJobTitle(Canvas canvas, float nameTextX, float nameTextY) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY); // Set a different color for job title
        paint.setTextSize(30); // Adjust text size for job title
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)); // Set text style

        // Fetch the user's job title
        String userJobTitle = capitalizeFirstLetterOfEachWord(dbInfoPersonnelle.getInfo().get(0).getJob());
        float jobTitleWidth = paint.measureText(userJobTitle);

        // Define a fixed vertical space below the user's name
        float spaceBelowName = 10; // Adjust this value as needed

        // Calculate the vertical position for the job title
        float jobTitleTextY = 145;

        // Calculate the horizontal position for the job title (center horizontally)
        float jobTitleTextX = (canvas.getWidth() - jobTitleWidth) / 2;

        // Draw the job title at the calculated position
        canvas.drawText(userJobTitle, jobTitleTextX, jobTitleTextY, paint);
    }





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



    private void drawVerticalLine(Canvas canvas, int startY, int endY) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK); // Set the color for the line
        linePaint.setStrokeWidth(2); // Set the width of the line

        // Define the X position for the vertical line (from the left margin or edge)
        int lineX = 240; // X position for the vertical line

        // Draw the vertical line from startY to endY
        canvas.drawLine(lineX, startY+20, lineX, endY, linePaint);
    }




}