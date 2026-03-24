package com.example.registrationapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.registrationapp.data.db.AppDatabase;
import com.example.registrationapp.data.db.AnnouncementDao;
import com.example.registrationapp.data.db.TimetableDao;
import com.example.registrationapp.data.entity.Announcement;
import com.example.registrationapp.data.entity.TimetableEntry;

import java.util.List;

public class HomeRepository {
    private final AnnouncementDao announcementDao;
    private final TimetableDao timetableDao;

    public HomeRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        announcementDao = db.announcementDao();
        timetableDao = db.timetableDao();
    }

    public LiveData<List<Announcement>> getAnnouncements() { return announcementDao.getAllAnnouncements(); }
    public LiveData<List<TimetableEntry>> getTimetableForDay(int day) { return timetableDao.getEntriesByDay(day); }
}
