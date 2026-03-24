package com.example.registrationapp.ui.auth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.registrationapp.R;
import com.example.registrationapp.data.prefs.PreferenceManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editFirstName, editLastName, editEmail, editPassword, editPhone, editDob;
    private AutoCompleteTextView spinnerCountry;
    private RadioGroup radioGenderGroup;
    private CheckBox checkTerms;
    private PreferenceManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = new PreferenceManager(this);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        editDob = findViewById(R.id.editDob);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        radioGenderGroup = findViewById(R.id.radioGenderGroup);
        checkTerms = findViewById(R.id.checkTerms);
        Button btnRegister = findViewById(R.id.btnRegister);

        editDob.setOnClickListener(v -> showDatePicker());

        btnRegister.setOnClickListener(v -> {
            if (validateForm()) {
                saveUserData();
                showSuccessDialog();
            }
        });
    }

    private void saveUserData() {
        String gender = "";
        int genderId = radioGenderGroup.getCheckedRadioButtonId();
        if (genderId != -1) gender = ((RadioButton) findViewById(genderId)).getText().toString();

        prefs.saveUserInfo(
                editFirstName.getText().toString().trim(),
                editLastName.getText().toString().trim(),
                editEmail.getText().toString().trim(),
                editPassword.getText().toString().trim(),
                editPhone.getText().toString().trim(),
                editDob.getText().toString().trim(),
                spinnerCountry.getText().toString().trim(),
                gender
        );
    }

    private void showSuccessDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage("Registration completed successfully!")
                .setPositiveButton(R.string.login_now, (d, w) -> {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton(R.string.register_again, (d, w) -> clearForm())
                .setCancelable(false)
                .show();
    }

    private void clearForm() {
        editFirstName.setText("");
        editLastName.setText("");
        editEmail.setText("");
        editPassword.setText("");
        editPhone.setText("");
        editDob.setText("");
        spinnerCountry.setText("", false);
        radioGenderGroup.clearCheck();
        checkTerms.setChecked(false);
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (v, y, m, d) ->
                editDob.setText(d + "/" + (m + 1) + "/" + y),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private boolean validateForm() {
        if (editFirstName.getText().toString().trim().isEmpty() ||
                editLastName.getText().toString().trim().isEmpty() ||
                editEmail.getText().toString().trim().isEmpty() ||
                editPassword.getText().toString().trim().isEmpty() ||
                editDob.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        String country = spinnerCountry.getText().toString().trim();
        if (country.isEmpty() || country.equals("Select Country")) {
            Toast.makeText(this, "Please select your country", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (radioGenderGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkTerms.isChecked()) {
            Toast.makeText(this, "Please agree to terms", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
