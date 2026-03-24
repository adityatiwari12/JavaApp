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
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class MarksFragment extends Fragment {
    private MarksViewModel vm;
    private MarksAdapter adapter;

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
        
        MaterialButton btn1 = view.findViewById(R.id.btnLogMst1);
        MaterialButton btn2 = view.findViewById(R.id.btnLogMst2);
        btn1.setEnabled(false);
        btn2.setEnabled(false);

        vm.getSubjects().observe(getViewLifecycleOwner(), subjects -> {
            adapter.setSubjects(subjects);
            if (subjects != null && !subjects.isEmpty()) {
                btn1.setEnabled(true);
                btn2.setEnabled(true);
            }
        });

        vm.getAllMarks().observe(getViewLifecycleOwner(), marks -> {
            adapter.setMarks(marks);
        });

        btn1.setOnClickListener(v -> {
            List<Subject> subjects = vm.getSubjects().getValue();
            if (subjects != null && !subjects.isEmpty()) {
                BulkMarksDialogHelper.showBulkMarksDialog(getContext(), "MST-1", subjects, marks -> {
                    vm.saveMarks(marks);
                });
            }
        });

        btn2.setOnClickListener(v -> {
            List<Subject> subjects = vm.getSubjects().getValue();
            if (subjects != null && !subjects.isEmpty()) {
                BulkMarksDialogHelper.showBulkMarksDialog(getContext(), "MST-2", subjects, marks -> {
                    vm.saveMarks(marks);
                });
            }
        });
    }
}
