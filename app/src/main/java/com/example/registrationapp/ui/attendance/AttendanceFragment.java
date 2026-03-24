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
        fab.setOnClickListener(v -> showMarkAttendanceDialog());

        vm.loadAttendanceStats();
    }

    private void showMarkAttendanceDialog() {
        vm.getSubjects().observe(getViewLifecycleOwner(), subjects -> {
            if (subjects == null || subjects.isEmpty()) {
                Toast.makeText(getContext(), "No subjects found", Toast.LENGTH_SHORT).show();
                return;
            }
            // Remove observer after first call
            vm.getSubjects().removeObservers(getViewLifecycleOwner());

            // Get already marked subjects for today
            vm.loadTodayRecords();
            vm.getTodayRecords().observe(getViewLifecycleOwner(), todayRecs -> {
                vm.getTodayRecords().removeObservers(getViewLifecycleOwner());

                Set<Integer> markedIds = new HashSet<>();
                if (todayRecs != null) {
                    for (AttendanceRecord r : todayRecs) markedIds.add(r.subjectId);
                }

                showSubjectListDialog(subjects, markedIds);
            });
        });
    }

    private void showSubjectListDialog(List<Subject> subjects, Set<Integer> alreadyMarked) {
        String today = DateUtils.formatForDisplay(DateUtils.getTodayDb());

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 32, 48, 16);

        for (Subject sub : subjects) {
            TextView label = new TextView(getContext());
            label.setText(sub.name + (alreadyMarked.contains(sub.id) ? " ✓" : ""));
            label.setTextSize(16);
            label.setPadding(0, 16, 0, 8);
            layout.addView(label);

            RadioGroup rg = new RadioGroup(getContext());
            rg.setOrientation(RadioGroup.HORIZONTAL);
            rg.setTag(sub.id);

            String[] labels = {"Present", "Absent", "Cancelled"};
            String[] values = {"PRESENT", "ABSENT", "CANCELLED"};
            for (int i = 0; i < 3; i++) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(labels[i]);
                rb.setTag(values[i]);
                rg.addView(rb);
            }
            layout.addView(rg);
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Mark Attendance — " + today)
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View child = layout.getChildAt(i);
                        if (child instanceof RadioGroup) {
                            RadioGroup rg = (RadioGroup) child;
                            int subId = (int) rg.getTag();
                            int checkedId = rg.getCheckedRadioButtonId();
                            if (checkedId != -1) {
                                RadioButton rb = rg.findViewById(checkedId);
                                vm.markAttendance(subId, rb.getTag().toString());
                            }
                        }
                    }
                    Toast.makeText(getContext(), "Attendance saved!", Toast.LENGTH_SHORT).show();
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
