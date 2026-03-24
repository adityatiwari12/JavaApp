package com.example.registrationapp.ui.leave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.registrationapp.R;
import com.example.registrationapp.data.entity.LeaveRequest;
import com.example.registrationapp.ui.adapter.LeaveAdapter;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class LeaveFragment extends Fragment {
    private LeaveViewModel vm;
    private LeaveAdapter adapter;
    private List<LeaveRequest> allLeaves = new ArrayList<>();
    private String currentFilter = "ALL";

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        return inflater.inflate(R.layout.fragment_leave, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(LeaveViewModel.class);

        RecyclerView recycler = view.findViewById(R.id.recyclerLeaves);
        TextView noLeaves = view.findViewById(R.id.textNoLeaves);
        ChipGroup chipGroup = view.findViewById(R.id.chipGroupFilter);

        adapter = new LeaveAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        vm.getAllLeaves().observe(getViewLifecycleOwner(), leaves -> {
            allLeaves = leaves;
            applyFilter(recycler, noLeaves);
        });

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.contains(R.id.chipPending)) currentFilter = "PENDING";
            else if (checkedIds.contains(R.id.chipApproved)) currentFilter = "APPROVED";
            else if (checkedIds.contains(R.id.chipRejected)) currentFilter = "REJECTED";
            else currentFilter = "ALL";
            applyFilter(recycler, noLeaves);
        });

        view.findViewById(R.id.btnApplyLeave).setOnClickListener(v -> {
            LeaveDialogHelper.showApplyLeaveDialog(getContext(), null);
        });
    }

    private void applyFilter(RecyclerView recycler, TextView noLeaves) {
        List<LeaveRequest> filtered;
        if (currentFilter.equals("ALL")) {
            filtered = allLeaves;
        } else {
            filtered = new ArrayList<>();
            for (LeaveRequest l : allLeaves) {
                if (l.status.equals(currentFilter)) filtered.add(l);
            }
        }
        adapter.setItems(filtered);
        noLeaves.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
        recycler.setVisibility(filtered.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
