
package com.example.resclink;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SafetyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);

        Toast.makeText(
                this,
                "Stay Safe! Follow Road Safety Rules.",
                Toast.LENGTH_SHORT
        ).show();
    }
}