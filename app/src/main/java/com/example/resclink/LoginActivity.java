package com.example.resclink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        EditText user = findViewById(R.id.etUsername);
        EditText pass = findViewById(R.id.etPassword);
        Button login = findViewById(R.id.btnLogin);
        TextView register = findViewById(R.id.tvRegister);

        login.setOnClickListener(v -> {
            if (user.getText().toString().trim().equals("justin")
                    && pass.getText().toString().equals("1234")) {
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid login",
                        Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(v ->
                Toast.makeText(this, "Registration not available",
                        Toast.LENGTH_SHORT).show());
    }
}