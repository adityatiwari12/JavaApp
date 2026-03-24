package com.example.registrationapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String code;
    public String color; // hex color for UI

    public Subject(String name, String code, String color) {
        this.name = name;
        this.code = code;
        this.color = color;
    }
}
