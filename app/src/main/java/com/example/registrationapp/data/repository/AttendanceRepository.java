package com.example.registrationapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.registrationapp.data.db.AppDatabase;
import com.example.registrationapp.data.db.SubjectDao;
import com.example.registrationapp.data.db.AttendanceDao;
import com.example.registrationapp.data.entity.AttendanceRecord;
import com.example.registrationapp.data.entity.Subject;

import java.util.List;
import java.util.concurrent.Executors;

public class AttendanceRepository {
    private final AttendanceDao attendanceDao;
    private final SubjectDao subjectDao;

    public AttendanceRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        attendanceDao = db.attendanceDao();
        subjectDao = db.subjectDao();
    }

    public LiveData<List<Subject>> getAllSubjects() { return subjectDao.getAllSubjects(); }
    public LiveData<List<AttendanceRecord>> getAllRecords() { return attendanceDao.getAllRecords(); }
    public LiveData<List<AttendanceRecord>> getRecordsByDate(String date) { return attendanceDao.getRecordsByDate(date); }
    public LiveData<List<AttendanceRecord>> getRecordsBySubject(int subjectId) { return attendanceDao.getRecordsBySubject(subjectId); }
    public LiveData<List<String>> getAllDates() { return attendanceDao.getAllDates(); }

    public void insertRecord(AttendanceRecord record) {
        Executors.newSingleThreadExecutor().execute(() -> attendanceDao.insert(record));
    }

    public void updateRecord(AttendanceRecord record) {
        Executors.newSingleThreadExecutor().execute(() -> attendanceDao.update(record));
    }

    public void insertSubject(Subject subject) {
        Executors.newSingleThreadExecutor().execute(() -> subjectDao.insert(subject));
    }

    public void updateSubject(Subject subject) {
        Executors.newSingleThreadExecutor().execute(() -> subjectDao.update(subject));
    }

    public void deleteSubject(Subject subject) {
        Executors.newSingleThreadExecutor().execute(() -> subjectDao.delete(subject));
    }

    // Sync methods for background calculations
    public int getAttendedCount(int subjectId) { return attendanceDao.getAttendedCount(subjectId); }
    public int getConductedCount(int subjectId) { return attendanceDao.getConductedCount(subjectId); }
    public List<AttendanceRecord> getRecordsByDateSync(String date) { return attendanceDao.getRecordsByDateSync(date); }
    public AttendanceRecord getRecordBySubjectAndDate(int subId, String date) { return attendanceDao.getRecordBySubjectAndDate(subId, date); }
    public List<Subject> getAllSubjectsSync() { return subjectDao.getAllSubjectsSync(); }
}
