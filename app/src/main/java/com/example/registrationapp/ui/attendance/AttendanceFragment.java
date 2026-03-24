package com.example.registrationapp.ui.attendance;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.registrationapp.R;
import com.example.registrationapp.data.entity.AttendanceRecord;
import com.example.registrationapp.data.entity.Subject;
import com.example.registrationapp.ui.adapter.AttendanceAdapter;
import com.example.registrationapp.utils.DateUtils;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceFragment extends Fragment {
    private AttendanceViewModel vm;
    private AttendanceAdapter adapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(AttendanceViewModel.class);

        RecyclerView recycler = view.findViewById(R.id.recyclerAttendance);
        SwipeRefreshLayout swipe = view.findViewById(R.id.swipeRefreshAttendance);
        ExtendedFloatingActionButton fab = view.findViewById(R.id.fabMarkAttendance);

        adapter = new AttendanceAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        vm.getAttendanceItems().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
            swipe.setRefreshing(false);
        });

        swipe.setOnRefreshListener(() -> vm.loadAttendanceStats());
        
        fab.setOnClickListener(v -> {
            List<Subject> subjects = vm.getSubjects().getValue();
            if (subjects != null && !subjects.isEmpty()) {
                showMarkAttendanceDialog(subjects);
            } else {
                Toast.makeText(getContext(), "Loading subjects...", Toast.LENGTH_SHORT).show();
            }
        });

        vm.loadAttendanceStats();
    }

    private void showMarkAttendanceDialog(List<Subject> subjects) {
        String today = DateUtils.formatForDisplay(DateUtils.getTodayDb());
        
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_attendance, null);
        com.google.android.material.textfield.MaterialAutoCompleteTextView spinner = dialogView.findViewById(R.id.spinnerSubjectAttendance);
        RadioGroup rg = dialogView.findViewById(R.id.rgStatus);

        List<String> subNames = new ArrayList<>();
        for (Subject s : subjects) subNames.add(s.name);
        spinner.setAdapter(new android.widget.ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, subNames));

        new AlertDialog.Builder(getContext())
                .setTitle("Mark Attendance — " + today)
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String subName = spinner.getText().toString();
                    int checkedId = rg.getCheckedRadioButtonId();
                    
                    if (!subName.isEmpty() && checkedId != -1) {
                        int subId = -1;
                        for (Subject s : subjects) {
                            if (s.name.equals(subName)) {
                                subId = s.id;
                                break;
                            }
                        }
                        
                        if (subId != -1) {
                            String status = "PRESENT";
                            if (checkedId == R.id.rbAbsent) status = "ABSENT";
                            else if (checkedId == R.id.rbCancelled) status = "CANCELLED";
                            
                            vm.markAttendance(subId, status);
                            Toast.makeText(getContext(), "Attendance saved!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.loadAttendanceStats();
    }
}
