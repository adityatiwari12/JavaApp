package com.example.registrationapp.ui.attendance;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.registrationapp.data.entity.AttendanceRecord;
import com.example.registrationapp.data.entity.Subject;
import com.example.registrationapp.data.repository.AttendanceRepository;
import com.example.registrationapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AttendanceViewModel extends AndroidViewModel {
    private final AttendanceRepository repo;
    private final LiveData<List<Subject>> subjects;
    private final MutableLiveData<List<SubjectAttendanceItem>> attendanceItems = new MutableLiveData<>();
    private final MutableLiveData<List<AttendanceRecord>> todayRecords = new MutableLiveData<>();

    public AttendanceViewModel(@NonNull Application application) {
        super(application);
        repo = new AttendanceRepository(application);
        subjects = repo.getAllSubjects();
    }

    public LiveData<List<Subject>> getSubjects() { return subjects; }
    public LiveData<List<SubjectAttendanceItem>> getAttendanceItems() { return attendanceItems; }
    public LiveData<List<AttendanceRecord>> getTodayRecords() { return todayRecords; }

    public void loadAttendanceStats() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Subject> subs = repo.getAllSubjectsSync();
            List<SubjectAttendanceItem> items = new ArrayList<>();
            for (Subject s : subs) {
                int attended = repo.getAttendedCount(s.id);
                int conducted = repo.getConductedCount(s.id);
                int pct = conducted > 0 ? (attended * 100 / conducted) : 0;
                int classesNeeded = calcClassesNeeded(attended, conducted, 75);
                int canMiss = calcCanMiss(attended, conducted, 75);
                items.add(new SubjectAttendanceItem(s, attended, conducted, pct, classesNeeded, canMiss));
            }
            attendanceItems.postValue(items);
        });
    }

    public void loadTodayRecords() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<AttendanceRecord> records = repo.getRecordsByDateSync(DateUtils.getTodayDb());
            todayRecords.postValue(records);
        });
    }

    public void markAttendance(int subjectId, String status) {
        Executors.newSingleThreadExecutor().execute(() -> {
            String today = DateUtils.getTodayDb();
            AttendanceRecord existing = repo.getRecordBySubjectAndDate(subjectId, today);
            if (existing != null) {
                existing.status = status;
                repo.updateRecord(existing);
            } else {
                repo.insertRecord(new AttendanceRecord(subjectId, today, status));
            }
            loadTodayRecords();
            loadAttendanceStats();
        });
    }

    public void addSubject(String name, String code, String color) {
        repo.insertSubject(new Subject(name, code, color));
    }

    public void deleteSubject(Subject subject) {
        repo.deleteSubject(subject);
    }

    private int calcClassesNeeded(int attended, int conducted, int target) {
        // How many more classes to attend to reach target%
        // (attended + x) / (conducted + x) >= target/100
        // 100*(attended+x) >= target*(conducted+x)
        // x*(100-target) >= target*conducted - 100*attended
        if (conducted == 0) return 0;
        int pct = conducted > 0 ? (attended * 100 / conducted) : 0;
        if (pct >= target) return 0;
        int diff = target * conducted - 100 * attended;
        int divisor = 100 - target;
        return divisor > 0 ? (int) Math.ceil((double) diff / divisor) : 0;
    }

    private int calcCanMiss(int attended, int conducted, int target) {
        // How many classes can miss while staying >= target%
        // attended / (conducted + x) >= target/100
        // 100*attended >= target*(conducted+x)
        // x <= (100*attended - target*conducted) / target
        if (conducted == 0) return 0;
        int pct = conducted > 0 ? (attended * 100 / conducted) : 0;
        if (pct < target) return 0;
        int diff = 100 * attended - target * conducted;
        return diff / target;
    }

    // UI model
    public static class SubjectAttendanceItem {
        public final Subject subject;
        public final int attended;
        public final int conducted;
        public final int percentage;
        public final int classesNeeded;
        public final int canMiss;

        public SubjectAttendanceItem(Subject subject, int attended, int conducted, int percentage, int classesNeeded, int canMiss) {
            this.subject = subject;
            this.attended = attended;
            this.conducted = conducted;
            this.percentage = percentage;
            this.classesNeeded = classesNeeded;
            this.canMiss = canMiss;
        }
    }
}
