package com.example.registrationapp.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "attendance_records",
        foreignKeys = @ForeignKey(
                entity = Subject.class,
                parentColumns = "id",
                childColumns = "subjectId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("subjectId"), @Index(value = {"subjectId", "date"}, unique = true)})
public class AttendanceRecord {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int subjectId;
    public String date; // yyyy-MM-dd
    public String status; // PRESENT, ABSENT, CANCELLED

    public AttendanceRecord(int subjectId, String date, String status) {
        this.subjectId = subjectId;
        this.date = date;
        this.status = status;
    }
}
