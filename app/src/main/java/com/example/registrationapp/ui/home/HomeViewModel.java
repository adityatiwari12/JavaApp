package com.example.registrationapp.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.registrationapp.data.entity.Announcement;
import com.example.registrationapp.data.entity.Subject;
import com.example.registrationapp.data.entity.TimetableEntry;
import com.example.registrationapp.data.prefs.PreferenceManager;
import com.example.registrationapp.data.repository.AttendanceRepository;
import com.example.registrationapp.data.repository.HomeRepository;
import com.example.registrationapp.utils.DateUtils;

import java.util.List;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {
    private final HomeRepository homeRepo;
    private final AttendanceRepository attendanceRepo;
    private final PreferenceManager prefs;
    private final LiveData<List<Announcement>> announcements;
    private final LiveData<List<TimetableEntry>> todayClasses;
    private final LiveData<List<Subject>> subjects;
    private final MutableLiveData<Integer> overallAttendance = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepo = new HomeRepository(application);
        attendanceRepo = new AttendanceRepository(application);
        prefs = new PreferenceManager(application);
        announcements = homeRepo.getAnnouncements();
        todayClasses = homeRepo.getTimetableForDay(DateUtils.getCurrentDayOfWeek());
        subjects = attendanceRepo.getAllSubjects();
    }

    public LiveData<List<Announcement>> getAnnouncements() { return announcements; }
    public LiveData<List<TimetableEntry>> getTodayClasses() { return todayClasses; }
    public LiveData<List<Subject>> getSubjects() { return subjects; }
    public LiveData<Integer> getOverallAttendance() { return overallAttendance; }
    public String getUserName() { return prefs.getFirstName() + " " + prefs.getLastName(); }

    public void calculateOverallAttendance() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Subject> subs = attendanceRepo.getAllSubjectsSync();
            int totalAttended = 0, totalConducted = 0;
            for (Subject s : subs) {
                totalAttended += attendanceRepo.getAttendedCount(s.id);
                totalConducted += attendanceRepo.getConductedCount(s.id);
            }
            int pct = totalConducted > 0 ? (totalAttended * 100 / totalConducted) : 0;
            overallAttendance.postValue(pct);
        });
    }
}
