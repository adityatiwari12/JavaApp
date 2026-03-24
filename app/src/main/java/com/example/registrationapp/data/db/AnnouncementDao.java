package com.example.registrationapp.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.registrationapp.data.entity.Announcement;

import java.util.List;

@Dao
public interface AnnouncementDao {
    @Insert
    long insert(Announcement announcement);

    @Query("SELECT * FROM announcements ORDER BY date DESC, priority DESC")
    LiveData<List<Announcement>> getAllAnnouncements();

    @Query("SELECT COUNT(*) FROM announcements")
    LiveData<Integer> getAnnouncementCount();
}
