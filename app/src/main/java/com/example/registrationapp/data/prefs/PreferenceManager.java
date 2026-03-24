package com.example.registrationapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "UserPrefs";
    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Login state
    public boolean isLoggedIn() { return prefs.getBoolean("isLoggedIn", false); }
    public void setLoggedIn(boolean val) { prefs.edit().putBoolean("isLoggedIn", val).apply(); }

    // Dark mode
    public boolean isDarkMode() { return prefs.getBoolean("darkMode", false); }
    public void setDarkMode(boolean val) { prefs.edit().putBoolean("darkMode", val).apply(); }

    // User info
    public String getFirstName() { return prefs.getString("firstName", ""); }
    public String getLastName() { return prefs.getString("lastName", ""); }
    public String getEmail() { return prefs.getString("email", ""); }
    public String getPassword() { return prefs.getString("password", ""); }
    public String getPhone() { return prefs.getString("phone", ""); }
    public String getDob() { return prefs.getString("dob", ""); }
    public String getCountry() { return prefs.getString("country", ""); }
    public String getGender() { return prefs.getString("gender", ""); }

    public void saveUserInfo(String firstName, String lastName, String email, String password,
                             String phone, String dob, String country, String gender) {
        prefs.edit()
                .putString("firstName", firstName)
                .putString("lastName", lastName)
                .putString("email", email)
                .putString("password", password)
                .putString("phone", phone)
                .putString("dob", dob)
                .putString("country", country)
                .putString("gender", gender)
                .apply();
    }

    public void updateUserInfo(String firstName, String lastName, String phone, String dob, String country) {
        prefs.edit()
                .putString("firstName", firstName)
                .putString("lastName", lastName)
                .putString("phone", phone)
                .putString("dob", dob)
                .putString("country", country)
                .apply();
    }

    public void clearSession() {
        prefs.edit().putBoolean("isLoggedIn", false).apply();
    }
}
