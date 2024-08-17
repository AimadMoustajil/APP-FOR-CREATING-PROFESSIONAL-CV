package com.example.app_creat_profesionell_cv.Letters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_creat_profesionell_cv.Adapters.AdapterOfLettreDeMotivation;
import com.example.app_creat_profesionell_cv.R;
import com.example.app_creat_profesionell_cv.ShowDocument.ShowDocumentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class letterDeMotivation extends AppCompatActivity {

    /*ImageView showLetter;
    EditText entete, corps, basDePage;*/

    RecyclerView recyclerView;
    ArrayList<String> arrayList;
    AdapterOfLettreDeMotivation adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_de_motivation);
        recyclerView = findViewById(R.id.recyclerViewOfLettreDeMotivation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        arrayList.add("Acadimic Advisor");
        arrayList.add("Acadimic Coach");
        arrayList.add("Acadimic Coordinator");
        arrayList.add("Acadimic Tutor");
        arrayList.add("Account Executive");
        arrayList.add("Account Manager");
        arrayList.add("Accountant ");
        arrayList.add("Accounting Intern");
        arrayList.add("Accounting Manager");
        arrayList.add("Accounts Paybale");
        arrayList.add("Actor");
        arrayList.add("Admissions Officers");
        arrayList.add("Administrator");
        arrayList.add("Admissions Counselor");
        arrayList.add("Air Traffic Controller");
        arrayList.add("Analyst");
        arrayList.add("Architect");
        arrayList.add("Art Teacher");
        arrayList.add("Artist");
        arrayList.add("Assistant Buyer");
        arrayList.add("Assistant Principal");
        arrayList.add("Attorney");
        arrayList.add("Author Assistant");
        arrayList.add("Autocentre Manager");
        arrayList.add("Automotive Sales Manager");
        arrayList.add("Babysitter");
        arrayList.add("Banker");
        arrayList.add("Bank Manager");
        arrayList.add("Barista");
        arrayList.add("Bartender");
        arrayList.add("Bid Writer");
        arrayList.add("Bookkeeper");
        arrayList.add("Business Analyst");
        arrayList.add("Business Support Manager");
        arrayList.add("Careers Advisor");
        arrayList.add("Caregiver");
        arrayList.add("Case Manager");
        arrayList.add("Cashier");
        arrayList.add("Chef");
        arrayList.add("Chiropractor");
        arrayList.add("Civil Engineer");
        arrayList.add("Coach");
        arrayList.add("Consultant");
        arrayList.add("Controller");
        arrayList.add("Copywriter");
        arrayList.add("Cosmetologist");
        arrayList.add("Counselor");
        arrayList.add("Credit Analyst");
        arrayList.add("Curriculum Developer");
        arrayList.add("Custodian");
        arrayList.add("Customer Experience Manager");
        arrayList.add("Customer Service Respresentative");
        arrayList.add("Data Analyst");
        arrayList.add("Data Science");
        arrayList.add("Database Analyst");
        arrayList.add("Dental Assistant");
        arrayList.add("Dental Hygienist");
        arrayList.add("Dentist");
        arrayList.add("Designer");
        arrayList.add("Design Manager");
        arrayList.add("Distribution Manager");
        arrayList.add("Diversity Manager");
        arrayList.add("Ecologist");
        arrayList.add("Electrical Engineer");
        arrayList.add("Engineer");
        arrayList.add("Engineering Intern");
        arrayList.add("Esthetician");
        arrayList.add("Event Coordinator");
        arrayList.add("Event Manager");
        arrayList.add("Event Planner");
        arrayList.add("Executive Assistant");
        arrayList.add("Finance Intern");
        arrayList.add("Finance Analyst");
        arrayList.add("Fleet Manager");
        arrayList.add("Firefighter");
        arrayList.add("Flight Attendant");
        arrayList.add("Freelance Writer");
        arrayList.add("Furniture Design");
        arrayList.add("Graduate Assistant");
        arrayList.add("Graphic Designer");
        arrayList.add("Guest Relations Manager");
        arrayList.add("Hair Stylist");
        arrayList.add("Head of Marketing");
        arrayList.add("Head of Operation");
        arrayList.add("Housekeeper");
        arrayList.add("Human Resources Assistant");
        arrayList.add("Human Resources Generalist");
        arrayList.add("Human Resources Manager");
        arrayList.add("Intern ");
        arrayList.add("IT Project Manager");
        arrayList.add("Laboratory Technician");
        arrayList.add("Lead Case Manager");
        arrayList.add("Legal Assistant");
        arrayList.add("Legal Intern ");
        arrayList.add("Libratian");
        arrayList.add("Library Assistant ");
        arrayList.add("Management Consultant");
        arrayList.add("Manager");
        arrayList.add("Marketer");
        arrayList.add("Marketing");
        arrayList.add("Marketing Coordinator");
        arrayList.add("Marketing Intern");
        arrayList.add("Marketing Manager");
        arrayList.add("Message Therapist");
        arrayList.add("Mechanical Engineer");
        arrayList.add("Medical Assistant ");
        arrayList.add("Medical Receptionist");
        arrayList.add("Microbiologist");
        arrayList.add("Nanny");
        arrayList.add("Nurse Practitioner");
        arrayList.add("Nursing Assistant");
        arrayList.add("Nursing Student ");
        arrayList.add("Occupational Therapist");
        arrayList.add("Office Administrator");
        arrayList.add("Office Assistant");
        arrayList.add("Office Manager");
        arrayList.add("Operation Manager");
        arrayList.add("Outreach Worker");
        arrayList.add("Paralegal");
        arrayList.add("Paraprofessional");
        arrayList.add("Partnership Manager");
        arrayList.add("Parts Advisor");
        arrayList.add("Personal Assistant");
        arrayList.add("Personal Banker");
        arrayList.add("Pest Control");
        arrayList.add("Pharmacist");
        arrayList.add("Pharmacy Technician");
        arrayList.add("Photographer");
        arrayList.add("Physician");
        arrayList.add("Physician Assistant");
        arrayList.add("Pilot");
        arrayList.add("Platform Engineer");
        arrayList.add("Police Officer");
        arrayList.add("Preshool Teacher");
        arrayList.add("Product Manager");
        arrayList.add("Production Assistant");
        arrayList.add("Program Coordinator");
        arrayList.add("Program Manager");
        arrayList.add("Project Coordinator");
        arrayList.add("Project Manager");
        arrayList.add("Property Manager");
        arrayList.add("Psychologist");
        arrayList.add("Quality Manager");
        arrayList.add("Receptionist");
        arrayList.add("Recruiter");
        arrayList.add("Recruitment Coordinator");
        arrayList.add("Recruitment Manager");
        arrayList.add("Regional Operational Manager");
        arrayList.add("Registered Nurse");
        arrayList.add("Research Assistant");
        arrayList.add("Resident Assistant ");
        arrayList.add("Residentail Assistant");
        arrayList.add("Restaurant Manager");
        arrayList.add("Risk Manager");
        arrayList.add("Sales");
        arrayList.add("Sales Manager");
        arrayList.add("Sales Representative");
        arrayList.add("School Counselor");
        arrayList.add("School Social Worker");
        arrayList.add("Scientist");
        arrayList.add("Secretary");
        arrayList.add("Secutity");
        arrayList.add("Security Guard");
        arrayList.add("Security Office");
        arrayList.add("Senior Social Worker");
        arrayList.add("Server");
        arrayList.add("Server Coordinator");
        arrayList.add("Shift Supervisor");
        arrayList.add("Shift Technician");
        arrayList.add("Showroom Manager");
        arrayList.add("Social Media Marketing Manager");
        arrayList.add("Social Worker");
        arrayList.add("Software Developer");
        arrayList.add("Software Engineer");
        arrayList.add("Special Education Teacher");
        arrayList.add("Speech Language Pathologist");
        arrayList.add("Substitute Teacher");
        arrayList.add("Supervisor");
        arrayList.add("Teacher Aide");
        arrayList.add("Teaching Assistant");
        arrayList.add("Teachnical Support");
        arrayList.add("Training Developer");
        arrayList.add("Veterinarian");
        arrayList.add("Veterinary Assistant");
        arrayList.add("Veterinary Technician");
        arrayList.add("Warehouse");
        arrayList.add("Web Developer");
        arrayList.add("Web Manager");
        arrayList.add("Welder");
        arrayList.add("Writer");


        adapter = new AdapterOfLettreDeMotivation(arrayList,this);
        recyclerView.setAdapter(adapter);

        // Initialize UI elements
        /*showLetter = findViewById(R.id.showLetter);
        entete = findViewById(R.id.entete);
        corps = findViewById(R.id.corps);
        basDePage = findViewById(R.id.basDePage);

        showLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for permission to write to external storage
                if (ContextCompat.checkSelfPermission(letterDeMotivation.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(letterDeMotivation.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    // Permission is granted, proceed with PDF creation
                    PdfDocument document = createPDF();
                    if (document != null) {
                        ByteArrayOutputStream outputStream = savePDFToByteArray(document);

                        // Start ShowDocumentActivity to display the PDF
                        Intent intent = new Intent(letterDeMotivation.this, ShowDocumentActivity.class);
                        intent.putExtra("pdfByteArray", outputStream.toByteArray());
                        startActivity(intent);

                        // Close the document after starting the activity
                        document.close();
                    }
                }
            }
        });*/
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, create PDF
                createPDF();
            } else {
                // Permission denied, show a toast message
                showToastOnUiThread("Permission denied to write to external storage");
            }
        }
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

    private PdfDocument createPDF() {
        if (!isExternalStorageWritable()) {
            showToastOnUiThread("External storage not writable");
            return null;
        }

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = createPageInfo();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = createPaint();

        int margin = 40;
        int lineHeight = 20;
        int paragraphSpacing = lineHeight * 2; // Extra space between paragraphs
        int pageWidth = pageInfo.getPageWidth();
        int currentY = margin;
        int textWidth = pageWidth - 2 * margin;

        // Draw the header
        String headerText = entete.getText().toString();
        String[] headerLines = headerText.split("\n");
        for (String line : headerLines) {
            canvas.drawText(line, margin, currentY, paint);
            currentY += lineHeight;
        }

        // Draw a gap between header and body
        currentY += lineHeight * 2;

        // Draw the body with text wrapping and paragraph spacing
        String bodyText = corps.getText().toString();
        String[] paragraphs = bodyText.split("\n\n"); // Split paragraphs
        for (String paragraph : paragraphs) {
            String[] lines = paragraph.split("\n"); // Split lines within a paragraph
            for (String line : lines) {
                if (paint.measureText(line) > textWidth) {
                    // Text wrapping
                    String[] wrappedLines = wrapText(line, paint, textWidth);
                    for (String wrappedLine : wrappedLines) {
                        canvas.drawText(wrappedLine, margin, currentY, paint);
                        currentY += lineHeight;
                    }
                } else {
                    canvas.drawText(line, margin, currentY, paint);
                    currentY += lineHeight;
                }
            }
            // Add space between paragraphs
            currentY += paragraphSpacing;
        }
        // Draw the footer

        String footerText = basDePage.getText().toString();
        canvas.drawText(footerText, margin, pageInfo.getPageHeight()-(margin*5), paint);

        document.finishPage(page);

        return document;
    }

    private String[] wrapText(String text, Paint paint, int textWidth) {
        ArrayList<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (paint.measureText(line.toString() + " " + word) > textWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
            }
        }
        lines.add(line.toString());

        return lines.toArray(new String[0]);
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
        int contentHeight = 1200;
        int contentWidth = 610; // Or calculate based on content if needed

        // Adjust the height to include some padding or margins
        int pageHeight = contentHeight + 200; // Add some padding or margins
        int pageWidth = contentWidth; // Use the content width or adjust as needed

        return new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
    }

    private Bitmap resizeBitmap(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        float scaleWidth = ((float) 128) / width;
        float scaleHeight = ((float) 128) / height;

        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // Resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // Recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, false);

        // Release the original bitmap
        originalBitmap.recycle();

        return resizedBitmap;
    }*/
}
