package com.example.registrationapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "leave_requests")
public class LeaveRequest {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String fromDate;
    public String toDate;
    public String reason;
    public String status; // PENDING, APPROVED, REJECTED
    public String appliedDate;

    public LeaveRequest(String fromDate, String toDate, String reason, String status, String appliedDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.appliedDate = appliedDate;
    }
}
