package com.example.registrationapp.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.registrationapp.data.entity.MstMark;

import androidx.room.OnConflictStrategy;
import java.util.List;

@Dao
public interface MstMarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MstMark mark);

    @Query("SELECT * FROM mst_marks WHERE subjectId = :subjectId AND examName = :examName LIMIT 1")
    MstMark getMarkSync(int subjectId, String examName);

    @Query("DELETE FROM mst_marks WHERE subjectId = :subjectId AND examName = :examName")
    void deleteMark(int subjectId, String examName);

    @Query("SELECT * FROM mst_marks WHERE subjectId = :subjectId ORDER BY examName ASC")
    LiveData<List<MstMark>> getMarksBySubject(int subjectId);

    @Query("SELECT * FROM mst_marks ORDER BY subjectId ASC, examName ASC")
    LiveData<List<MstMark>> getAllMarks();

    @Query("SELECT AVG(marksObtained * 100.0 / maxMarks) FROM mst_marks WHERE subjectId = :subjectId")
    LiveData<Float> getAveragePercentBySubject(int subjectId);
}
