package com.example.resclink;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Button ambulance = findViewById(R.id.btnCallAmbulance);
        Button wreckage = findViewById(R.id.btnCallWreckage);
        Button forensic = findViewById(R.id.btnCallForensic);

        ambulance.setOnClickListener(v -> dial("8448299942"));
        wreckage.setOnClickListener(v -> dial("0711490341"));
        forensic.setOnClickListener(v -> dial("0785051756"));
    }

    private void dial(String number) {
        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + number)));
    }
}