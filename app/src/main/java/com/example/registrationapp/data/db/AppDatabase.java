package com.example.registrationapp.data.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.registrationapp.data.entity.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;

@Database(entities = {Subject.class, AttendanceRecord.class, LeaveRequest.class,
        MstMark.class, Announcement.class, TimetableEntry.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract SubjectDao subjectDao();
    public abstract AttendanceDao attendanceDao();
    public abstract LeaveDao leaveDao();
    public abstract MstMarkDao mstMarkDao();
    public abstract AnnouncementDao announcementDao();
    public abstract TimetableDao timetableDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "csit_aitr_db")
                            .addCallback(seedCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback seedCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    // Small delay to ensure INSTANCE is assigned after build()
                    Thread.sleep(200);
                    AppDatabase database = INSTANCE;
                    if (database != null) {
                        if (database.subjectDao().getSubjectCount() == 0) {
                            seedSubjects(database);
                            // seedAttendance(database); // User requested zero attendance
                            // seedMarks(database);
                            seedAnnouncements(database);
                            seedTimetable(database);
                            seedLeaves(database);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    };

    private static void seedSubjects(AppDatabase db) {
        db.subjectDao().insert(new Subject("Android Programming", "CS301", "#4CAF50"));
        db.subjectDao().insert(new Subject("Python", "CS302", "#2196F3"));
        db.subjectDao().insert(new Subject("Cryptography and Network Security", "CS303", "#FF9800"));
        db.subjectDao().insert(new Subject("Machine Learning", "CS304", "#9C27B0"));
        db.subjectDao().insert(new Subject("Computer Graphics and Multimedia", "CS305", "#F44336"));
        db.subjectDao().insert(new Subject("Minor Project Lab", "CS306", "#00BCD4"));
    }

    private static void seedAttendance(AppDatabase db) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        String[] statuses = {"PRESENT", "ABSENT", "PRESENT", "PRESENT", "CANCELLED", "PRESENT"};

        // Generate 14 days of attendance for each subject
        for (int day = 0; day < 14; day++) {
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -day);
            // Skip weekends
            int dow = cal.get(Calendar.DAY_OF_WEEK);
            if (dow == Calendar.SATURDAY || dow == Calendar.SUNDAY) continue;

            String date = sdf.format(cal.getTime());
            for (int subId = 1; subId <= 6; subId++) {
                // Vary status deterministically
                String status = statuses[(subId + day) % statuses.length];
                db.attendanceDao().insert(new AttendanceRecord(subId, date, status));
            }
        }
    }

    private static void seedMarks(AppDatabase db) {
        int[][] marks = {
                {28, 40}, {35, 40}, {22, 40}, {30, 40}, {18, 40}, {33, 40},  // MST-1
                {32, 40}, {38, 40}, {25, 40}, {34, 40}, {20, 40}, {36, 40}   // MST-2
        };
        for (int subId = 1; subId <= 6; subId++) {
            db.mstMarkDao().insert(new MstMark(subId, "MST-1", marks[subId - 1][0], marks[subId - 1][1]));
            db.mstMarkDao().insert(new MstMark(subId, "MST-2", marks[subId + 5][0], marks[subId + 5][1]));
        }
    }

    private static void seedAnnouncements(AppDatabase db) {
        db.announcementDao().insert(new Announcement(
                "Mid-Semester Exam Schedule Released",
                "MST-2 exams will be held from April 10-15. Check the notice board for the detailed timetable.",
                "2026-03-22", 2));
        db.announcementDao().insert(new Announcement(
                "Workshop on Android Development",
                "A two-day workshop on modern Android development will be held in Lab 204 on March 28-29.",
                "2026-03-20", 1));
        db.announcementDao().insert(new Announcement(
                "Library Hours Extended",
                "The central library will now remain open until 9 PM on weekdays during the exam season.",
                "2026-03-18", 0));
    }

    private static void seedTimetable(AppDatabase db) {
        // Monday
        db.timetableDao().insert(new TimetableEntry(1, 1, "09:00", "10:00", "Room 101"));
        db.timetableDao().insert(new TimetableEntry(5, 1, "10:00", "11:00", "Lab 204"));
        db.timetableDao().insert(new TimetableEntry(3, 1, "11:30", "12:30", "Room 103"));
        // Tuesday
        db.timetableDao().insert(new TimetableEntry(2, 2, "09:00", "10:00", "Room 102"));
        db.timetableDao().insert(new TimetableEntry(6, 2, "10:00", "11:00", "Lab 205"));
        db.timetableDao().insert(new TimetableEntry(4, 2, "11:30", "12:30", "Room 104"));
        // Wednesday
        db.timetableDao().insert(new TimetableEntry(1, 3, "09:00", "10:00", "Room 101"));
        db.timetableDao().insert(new TimetableEntry(5, 3, "10:00", "11:00", "Lab 204"));
        db.timetableDao().insert(new TimetableEntry(2, 3, "11:30", "12:30", "Room 102"));
        // Thursday
        db.timetableDao().insert(new TimetableEntry(3, 4, "09:00", "10:00", "Room 103"));
        db.timetableDao().insert(new TimetableEntry(6, 4, "10:00", "11:00", "Lab 205"));
        db.timetableDao().insert(new TimetableEntry(4, 4, "11:30", "12:30", "Room 104"));
        // Friday
        db.timetableDao().insert(new TimetableEntry(1, 5, "09:00", "10:00", "Room 101"));
        db.timetableDao().insert(new TimetableEntry(2, 5, "10:00", "11:00", "Room 102"));
        db.timetableDao().insert(new TimetableEntry(5, 5, "11:30", "12:30", "Lab 204"));
    }

    private static void seedLeaves(AppDatabase db) {
        db.leaveDao().insert(new LeaveRequest("2026-03-10", "2026-03-12", "Family function", "APPROVED", "2026-03-08"));
        db.leaveDao().insert(new LeaveRequest("2026-03-25", "2026-03-26", "Medical appointment", "PENDING", "2026-03-23"));
    }
}
