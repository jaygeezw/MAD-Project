package com.example.resclink;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AccidentReportingActivity extends AppCompatActivity {

    Button btnCapturePhoto, btnGetLocation, btnSendAlert;
    TextView tvLocation;
    EditText etDescription;
    ImageView ivCapturedPhoto;

    DatabaseHelper dbHelper;

    private ActivityResultLauncher<Void> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_reporting);

        dbHelper = new DatabaseHelper(this);

        btnCapturePhoto = findViewById(R.id.btnCapturePhoto);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnSendAlert = findViewById(R.id.btnSendAlert);

        tvLocation = findViewById(R.id.tvLocation);
        etDescription = findViewById(R.id.etDescription);
        ivCapturedPhoto = findViewById(R.id.ivCapturedPhoto);

        Toast.makeText(
                this,
                "Stay Safe! Follow Road Safety Rules.",
                Toast.LENGTH_SHORT
        ).show();


        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicturePreview(),
                photo -> {
                    if (photo != null) {
                        ivCapturedPhoto.setImageBitmap(photo);
                        ivCapturedPhoto.setVisibility(ImageView.VISIBLE);

                        Toast.makeText(
                                this,
                                "Photo captured successfully!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );

        btnCapturePhoto.setOnClickListener(v ->
                cameraLauncher.launch(null));

        btnGetLocation.setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "GPS location functionality is not yet implemented.",
                    Toast.LENGTH_SHORT
            ).show();
        });

        btnSendAlert.setOnClickListener(v -> saveAccidentReport());
    }

    private void saveAccidentReport() {

        String description = etDescription.getText()
                .toString().trim();

        String location = tvLocation.getText()
                .toString().trim();

        if (description.isEmpty()) {
            etDescription.setError(
                    "Please enter accident details");
            etDescription.requestFocus();
            return;
        }

        if (location.isEmpty()) {
            location = "Location not available";
        }

        String date = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        ).format(new Date());

        // Get current time
        String time = new SimpleDateFormat(
                "hh:mm a",
                Locale.getDefault()
        ).format(new Date());

        boolean inserted = dbHelper.addAccident(
                location,
                date,
                time,
                description
        );

        if (inserted) {

            Toast.makeText(
                    this,
                    "Accident report saved successfully!",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent = new Intent(
                    AccidentReportingActivity.this,
                    AlertsActivity.class
            );

            startActivity(intent);

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to save accident report.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}