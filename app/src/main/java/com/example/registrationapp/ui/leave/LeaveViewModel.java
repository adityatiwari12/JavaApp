package com.example.registrationapp.ui.leave;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.registrationapp.data.entity.LeaveRequest;
import com.example.registrationapp.data.repository.LeaveRepository;
import com.example.registrationapp.utils.DateUtils;

import java.util.List;

public class LeaveViewModel extends AndroidViewModel {
    private final LeaveRepository repo;
    private final LiveData<List<LeaveRequest>> allLeaves;

    public LeaveViewModel(@NonNull Application application) {
        super(application);
        repo = new LeaveRepository(application);
        allLeaves = repo.getAllLeaves();
    }

    public LiveData<List<LeaveRequest>> getAllLeaves() { return allLeaves; }

    public void applyLeave(String from, String to, String reason) {
        LeaveRequest leave = new LeaveRequest(from, to, reason, "PENDING", DateUtils.getTodayDb());
        repo.insert(leave);
    }
}
