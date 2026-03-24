package com.example.registrationapp.ui.marks;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.registrationapp.data.entity.MstMark;
import com.example.registrationapp.data.entity.Subject;
import com.example.registrationapp.data.repository.MarksRepository;
import com.example.registrationapp.data.repository.HomeRepository;

import java.util.List;
import java.util.concurrent.Executors;

public class MarksViewModel extends AndroidViewModel {
    private final MarksRepository repo;
    private final LiveData<List<MstMark>> allMarks;
    private final LiveData<List<Subject>> subjects;

    public MarksViewModel(@NonNull Application application) {
        super(application);
        repo = new MarksRepository(application);
        allMarks = repo.getAllMarks();
        subjects = repo.getAllSubjects();
    }

    public LiveData<List<MstMark>> getAllMarks() { return allMarks; }
    public LiveData<List<Subject>> getSubjects() { return subjects; }

    public void saveMark(MstMark mark) {
        Executors.newSingleThreadExecutor().execute(() -> {
            repo.insertMark(mark);
        });
    }

    public void loadMarks() {
        // Marks are already loaded via LiveData allMarks
    }
}
