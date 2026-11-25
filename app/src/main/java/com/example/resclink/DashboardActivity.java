package com.example.resclink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class DashboardActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.btnReportAccident).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, AccidentReportingActivity.class)));

        findViewById(R.id.btnViewAlerts).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, AlertsActivity.class)));

        findViewById(R.id.btnEmergencyContacts).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, ContactActivity.class)));

        findViewById(R.id.btnSettings).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, SafetyActivity.class)));
    }
}