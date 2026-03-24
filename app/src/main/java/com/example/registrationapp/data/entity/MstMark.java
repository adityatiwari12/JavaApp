package com.example.registrationapp.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "mst_marks",
        foreignKeys = @ForeignKey(
                entity = Subject.class,
                parentColumns = "id",
                childColumns = "subjectId",
                onDelete = ForeignKey.CASCADE),
        indices = @Index("subjectId"))
public class MstMark {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int subjectId;
    public String examName; // MST-1, MST-2
    public int marksObtained;
    public int maxMarks;

    public MstMark(int subjectId, String examName, int marksObtained, int maxMarks) {
        this.subjectId = subjectId;
        this.examName = examName;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }
}
