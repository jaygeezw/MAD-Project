package com.example.resclink;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.*;

public class AlertsActivity extends Activity {

    DatabaseHelper dbHelper;

    LinearLayout reportsContainer;
    TextView tvNoReports;
    Button refresh;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_alerts);

        dbHelper = new DatabaseHelper(this);

        reportsContainer = findViewById(R.id.reportsContainer);
        tvNoReports = findViewById(R.id.tvNoReports);
        refresh = findViewById(R.id.btnRefreshAlerts);

        refresh.setOnClickListener(v -> loadAccidentReports());

        loadAccidentReports();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (dbHelper != null && reportsContainer != null) {
            loadAccidentReports();
        }
    }

    private void loadAccidentReports() {

        reportsContainer.removeAllViews();

        Cursor cursor = dbHelper.getAccidents();

        if (cursor == null) {
            tvNoReports.setText("Unable to load reports.");
            tvNoReports.setVisibility(View.VISIBLE);
            return;
        }

        if (cursor.getCount() == 0) {
            tvNoReports.setText("No accident reports available.");
            tvNoReports.setVisibility(View.VISIBLE);
            cursor.close();
            return;
        }

        tvNoReports.setVisibility(View.GONE);

        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id"));

                String location = cursor.getString(
                        cursor.getColumnIndexOrThrow("location"));

                String date = cursor.getString(
                        cursor.getColumnIndexOrThrow("date"));

                String time = cursor.getString(
                        cursor.getColumnIndexOrThrow("time"));

                String description = cursor.getString(
                        cursor.getColumnIndexOrThrow("description"));

                addReportCard(
                        id, location, date, time, description);
            }

        } finally {
            cursor.close();
        }
    }

    private void addReportCard(
            int id,
            String location,
            String date,
            String time,
            String description) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(20, 20, 20, 20);
        card.setBackgroundColor(0xFFF1F3F5);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 12, 0, 12);
        card.setLayoutParams(params);

        TextView title = new TextView(this);
        title.setText("Accident Report #" + id);
        title.setTextSize(19);
        title.setTextColor(0xFFE51E35);
        title.setTypeface(null, 1);

        TextView details = new TextView(this);

        details.setText(
                "Location: " + safe(location) +
                        "\nDate: " + safe(date) +
                        "\nTime: " + safe(time) +
                        "\nDescription: " + safe(description)
        );

        details.setTextSize(15);
        details.setTextColor(0xFF252525);
        details.setPadding(0, 12, 0, 12);

        Button viewButton = new Button(this);
        viewButton.setText("View Full Report");

        viewButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        0xFFE51E35));

        viewButton.setTextColor(0xFFFFFFFF);

        viewButton.setOnClickListener(v -> {

            String fullReport =
                    "Report ID: " + id +
                            "\n\nLocation: " + safe(location) +
                            "\n\nDate: " + safe(date) +
                            "\n\nTime: " + safe(time) +
                            "\n\nDescription: " + safe(description);

            new AlertDialog.Builder(this)
                    .setTitle("Accident Report Details")
                    .setMessage(fullReport)
                    .setPositiveButton("Close", null)
                    .show();
        });

        card.addView(title);
        card.addView(details);
        card.addView(viewButton);

        reportsContainer.addView(card);
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty()
                ? "Not available" : value;
    }
}