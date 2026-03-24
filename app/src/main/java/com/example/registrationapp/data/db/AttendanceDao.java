package com.example.registrationapp.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.registrationapp.data.entity.AttendanceRecord;

import java.util.List;

@Dao
public interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AttendanceRecord record);

    @Update
    void update(AttendanceRecord record);

    @Delete
    void delete(AttendanceRecord record);

    @Query("SELECT * FROM attendance_records WHERE subjectId = :subjectId ORDER BY date DESC")
    LiveData<List<AttendanceRecord>> getRecordsBySubject(int subjectId);

    @Query("SELECT * FROM attendance_records ORDER BY date DESC")
    LiveData<List<AttendanceRecord>> getAllRecords();

    @Query("SELECT * FROM attendance_records WHERE date = :date")
    LiveData<List<AttendanceRecord>> getRecordsByDate(String date);

    @Query("SELECT * FROM attendance_records WHERE date = :date")
    List<AttendanceRecord> getRecordsByDateSync(String date);

    @Query("SELECT COUNT(*) FROM attendance_records WHERE subjectId = :subjectId AND status = 'PRESENT'")
    int getAttendedCount(int subjectId);

    @Query("SELECT COUNT(*) FROM attendance_records WHERE subjectId = :subjectId AND (status = 'PRESENT' OR status = 'ABSENT')")
    int getConductedCount(int subjectId);

    @Query("SELECT COUNT(*) FROM attendance_records WHERE subjectId = :subjectId")
    int getTotalCount(int subjectId);

    @Query("SELECT * FROM attendance_records WHERE subjectId = :subjectId AND date = :date LIMIT 1")
    AttendanceRecord getRecordBySubjectAndDate(int subjectId, String date);

    @Query("SELECT DISTINCT date FROM attendance_records ORDER BY date DESC")
    LiveData<List<String>> getAllDates();
}
