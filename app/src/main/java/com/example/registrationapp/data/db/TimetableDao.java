package com.example.registrationapp.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.registrationapp.data.entity.TimetableEntry;

import java.util.List;

@Dao
public interface TimetableDao {
    @Insert
    long insert(TimetableEntry entry);

    @Query("SELECT * FROM timetable_entries WHERE dayOfWeek = :day ORDER BY startTime ASC")
    LiveData<List<TimetableEntry>> getEntriesByDay(int day);

    @Query("SELECT * FROM timetable_entries ORDER BY dayOfWeek ASC, startTime ASC")
    LiveData<List<TimetableEntry>> getAllEntries();
}
