package com.example.registrationapp.ui.marks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.registrationapp.R;
import com.example.registrationapp.data.entity.MstMark;
import com.example.registrationapp.data.entity.Subject;
import com.example.registrationapp.ui.adapter.MarksAdapter;

import java.util.ArrayList;
import java.util.List;

public class MarksFragment extends Fragment {
    private MarksViewModel vm;
    private MarksAdapter adapter;
    private List<Subject> cachedSubjects = new ArrayList<>();
    private List<MstMark> cachedMarks = new ArrayList<>();

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        return inflater.inflate(R.layout.fragment_marks, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerMarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MarksAdapter();
        recyclerView.setAdapter(adapter);

        vm = new ViewModelProvider(this).get(MarksViewModel.class);
        
        vm.getSubjects().observe(getViewLifecycleOwner(), subjects -> {
            adapter.setSubjects(subjects);
            
            // Set up FAB click listener only when subjects are available
            view.findViewById(R.id.fabAddMarks).setOnClickListener(v -> {
                MarksDialogHelper.showAddMarksDialog(getContext(), subjects, mark -> {
                    vm.saveMark(mark);
                });
            });
        });

        vm.getAllMarks().observe(getViewLifecycleOwner(), marks -> {
            adapter.setMarks(marks);
        });
    }
}
