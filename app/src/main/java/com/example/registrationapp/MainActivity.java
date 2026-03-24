package com.example.registrationapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.registrationapp.data.prefs.PreferenceManager;
import com.example.registrationapp.ui.DashboardActivity;
import com.example.registrationapp.ui.auth.LoginActivity;

/**
 * Launcher activity — redirector only.
 * If logged in → Dashboard, else → Login.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager prefs = new PreferenceManager(this);

        if (prefs.isLoggedIn()) {
            startActivity(new Intent(this, DashboardActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
