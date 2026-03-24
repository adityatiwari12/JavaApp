package com.example.registrationapp.ui.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.registrationapp.data.prefs.PreferenceManager;
import com.example.registrationapp.data.repository.AttendanceRepository;
import com.example.registrationapp.data.entity.Subject;

import java.util.List;
import java.util.concurrent.Executors;

public class ProfileViewModel extends AndroidViewModel {
    private final PreferenceManager prefs;
    private final AttendanceRepository attendanceRepo;
    private final MutableLiveData<int[]> academicSummary = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        prefs = new PreferenceManager(application);
        attendanceRepo = new AttendanceRepository(application);
    }

    public PreferenceManager getPrefs() { return prefs; }
    public LiveData<int[]> getAcademicSummary() { return academicSummary; }

    public void loadAcademicSummary() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Subject> subs = attendanceRepo.getAllSubjectsSync();
            int totalAttended = 0, totalConducted = 0;
            for (Subject s : subs) {
                totalAttended += attendanceRepo.getAttendedCount(s.id);
                totalConducted += attendanceRepo.getConductedCount(s.id);
            }
            int pct = totalConducted > 0 ? (totalAttended * 100 / totalConducted) : 0;
            academicSummary.postValue(new int[]{subs.size(), totalAttended, totalConducted, pct});
        });
    }

    public void updateProfile(String firstName, String lastName, String phone, String dob, String country) {
        prefs.updateUserInfo(firstName, lastName, phone, dob, country);
    }

    public void toggleDarkMode() {
        prefs.setDarkMode(!prefs.isDarkMode());
    }

    public void logout() {
        prefs.clearSession();
    }
}
