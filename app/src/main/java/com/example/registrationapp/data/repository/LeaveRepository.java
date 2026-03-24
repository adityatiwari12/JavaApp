package com.example.registrationapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.registrationapp.data.db.AppDatabase;
import com.example.registrationapp.data.db.LeaveDao;
import com.example.registrationapp.data.entity.LeaveRequest;

import java.util.List;
import java.util.concurrent.Executors;

public class LeaveRepository {
    private final LeaveDao leaveDao;

    public LeaveRepository(Application app) {
        leaveDao = AppDatabase.getInstance(app).leaveDao();
    }

    public LiveData<List<LeaveRequest>> getAllLeaves() { return leaveDao.getAllLeaves(); }
    public LiveData<List<LeaveRequest>> getLeavesByStatus(String status) { return leaveDao.getLeavesByStatus(status); }
    public LiveData<Integer> getPendingCount() { return leaveDao.getPendingCount(); }

    public void insert(LeaveRequest leave) {
        Executors.newSingleThreadExecutor().execute(() -> leaveDao.insert(leave));
    }

    public void update(LeaveRequest leave) {
        Executors.newSingleThreadExecutor().execute(() -> leaveDao.update(leave));
    }
}
