package com.example.registrationapp.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "timetable_entries",
        foreignKeys = @ForeignKey(
                entity = Subject.class,
                parentColumns = "id",
                childColumns = "subjectId",
                onDelete = ForeignKey.CASCADE),
        indices = @Index("subjectId"))
public class TimetableEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int subjectId;
    public int dayOfWeek; // 1=Monday...7=Sunday
    public String startTime; // HH:mm
    public String endTime;   // HH:mm
    public String room;

    public TimetableEntry(int subjectId, int dayOfWeek, String startTime, String endTime, String room) {
        this.subjectId = subjectId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }
}
