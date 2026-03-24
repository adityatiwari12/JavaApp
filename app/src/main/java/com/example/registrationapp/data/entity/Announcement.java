package com.example.registrationapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "announcements")
public class Announcement {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String body;
    public String date;
    public int priority; // 0=low, 1=medium, 2=high

    public Announcement(String title, String body, String date, int priority) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.priority = priority;
    }
}
