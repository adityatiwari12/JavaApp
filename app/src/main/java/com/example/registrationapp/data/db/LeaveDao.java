package com.example.registrationapp.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.registrationapp.data.entity.LeaveRequest;

import java.util.List;

@Dao
public interface LeaveDao {
    @Insert
    long insert(LeaveRequest leave);

    @Update
    void update(LeaveRequest leave);

    @Query("SELECT * FROM leave_requests ORDER BY appliedDate DESC")
    LiveData<List<LeaveRequest>> getAllLeaves();

    @Query("SELECT * FROM leave_requests WHERE status = :status ORDER BY appliedDate DESC")
    LiveData<List<LeaveRequest>> getLeavesByStatus(String status);

    @Query("SELECT COUNT(*) FROM leave_requests WHERE status = 'PENDING'")
    LiveData<Integer> getPendingCount();
}
