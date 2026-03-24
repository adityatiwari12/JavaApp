package com.example.registrationapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.registrationapp.R;
import com.example.registrationapp.data.prefs.PreferenceManager;
import com.example.registrationapp.ui.auth.LoginActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ProfileFragment extends Fragment {
    private ProfileViewModel vm;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        return inflater.inflate(R.layout.fragment_profile, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ProfileViewModel.class);
        PreferenceManager prefs = vm.getPrefs();

        // User info
        TextView name = view.findViewById(R.id.textProfileName);
        TextView email = view.findViewById(R.id.textProfileEmail);
        TextView phone = view.findViewById(R.id.textProfilePhone);
        TextView dob = view.findViewById(R.id.textProfileDob);
        TextView gender = view.findViewById(R.id.textProfileGender);
        TextView country = view.findViewById(R.id.textProfileCountry);

        name.setText(prefs.getFirstName() + " " + prefs.getLastName());
        email.setText(prefs.getEmail());
        phone.setText(prefs.getPhone().isEmpty() ? "N/A" : prefs.getPhone());
        dob.setText(prefs.getDob().isEmpty() ? "N/A" : prefs.getDob());
        gender.setText(prefs.getGender().isEmpty() ? "N/A" : prefs.getGender());
        country.setText(prefs.getCountry().isEmpty() ? "N/A" : prefs.getCountry());

        // Academic summary
        TextView subCount = view.findViewById(R.id.textSubjectCount);
        TextView attended = view.findViewById(R.id.textClassesAttended);
        TextView pctProfile = view.findViewById(R.id.textOverallPctProfile);

        vm.getAcademicSummary().observe(getViewLifecycleOwner(), data -> {
            subCount.setText(String.valueOf(data[0]));
            attended.setText(String.valueOf(data[1]));
            pctProfile.setText(data[3] + "%");
        });
        vm.loadAcademicSummary();

        // Dark mode toggle
        SwitchMaterial darkSwitch = view.findViewById(R.id.switchDarkMode);
        darkSwitch.setChecked(prefs.isDarkMode());
        darkSwitch.setOnCheckedChangeListener((btn, isChecked) -> {
            vm.toggleDarkMode();
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        // Logout
        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            vm.logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
