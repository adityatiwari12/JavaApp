package com.example.registrationapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.registrationapp.data.db.AppDatabase;
import com.example.registrationapp.data.db.MstMarkDao;
import com.example.registrationapp.data.db.SubjectDao;
import com.example.registrationapp.data.entity.MstMark;
import com.example.registrationapp.data.entity.Subject;

import java.util.List;

public class MarksRepository {
    private final MstMarkDao markDao;
    private final SubjectDao subjectDao;

    public MarksRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        markDao = db.mstMarkDao();
        subjectDao = db.subjectDao();
    }

    public LiveData<List<MstMark>> getAllMarks() { return markDao.getAllMarks(); }
    public LiveData<List<MstMark>> getMarksBySubject(int subjectId) { return markDao.getMarksBySubject(subjectId); }
    public LiveData<List<Subject>> getAllSubjects() { return subjectDao.getAllSubjects(); }
    public void insertMark(MstMark mark) { markDao.insert(mark); }
}
