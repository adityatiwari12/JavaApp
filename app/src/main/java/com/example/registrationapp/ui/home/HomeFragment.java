package com.example.registrationapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.registrationapp.R;
import com.example.registrationapp.ui.adapter.AnnouncementAdapter;
import com.example.registrationapp.ui.adapter.ClassAdapter;
import com.example.registrationapp.ui.leave.LeaveDialogHelper;

public class HomeFragment extends Fragment {
    private HomeViewModel vm;
    private ClassAdapter classAdapter;
    private AnnouncementAdapter announcementAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(HomeViewModel.class);

        TextView welcomeText = view.findViewById(R.id.textWelcomeName);
        ProgressBar progressOverall = view.findViewById(R.id.progressOverall);
        TextView textPct = view.findViewById(R.id.textOverallPct);
        RecyclerView recyclerClasses = view.findViewById(R.id.recyclerTodayClasses);
        RecyclerView recyclerAnnouncements = view.findViewById(R.id.recyclerAnnouncements);
        TextView textNoClasses = view.findViewById(R.id.textNoClasses);
        SwipeRefreshLayout swipe = view.findViewById(R.id.swipeRefresh);

        welcomeText.setText("Welcome, " + vm.getUserName() + "!");

        classAdapter = new ClassAdapter();
        recyclerClasses.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerClasses.setAdapter(classAdapter);

        announcementAdapter = new AnnouncementAdapter();
        recyclerAnnouncements.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAnnouncements.setAdapter(announcementAdapter);

        // Observe
        vm.getOverallAttendance().observe(getViewLifecycleOwner(), pct -> {
            progressOverall.setProgress(pct);
            textPct.setText(pct + "%");
        });

        vm.getTodayClasses().observe(getViewLifecycleOwner(), classes -> {
            if (classes == null || classes.isEmpty()) {
                textNoClasses.setVisibility(View.VISIBLE);
                recyclerClasses.setVisibility(View.GONE);
            } else {
                textNoClasses.setVisibility(View.GONE);
                recyclerClasses.setVisibility(View.VISIBLE);
                // Need subjects for names
                vm.getSubjects().observe(getViewLifecycleOwner(), subs -> classAdapter.setData(classes, subs));
            }
        });

        vm.getAnnouncements().observe(getViewLifecycleOwner(), a -> announcementAdapter.setItems(a));

        vm.calculateOverallAttendance();

        // Quick actions
        view.findViewById(R.id.cardMarkAttendance).setOnClickListener(v -> {
            // Navigate to attendance tab
            if (getActivity() != null) {
                com.google.android.material.bottomnavigation.BottomNavigationView nav =
                        getActivity().findViewById(R.id.bottom_nav);
                nav.setSelectedItemId(R.id.nav_attendance);
            }
        });

        view.findViewById(R.id.cardApplyLeave).setOnClickListener(v -> {
            LeaveDialogHelper.showApplyLeaveDialog(getContext(), null);
        });

        swipe.setOnRefreshListener(() -> {
            vm.calculateOverallAttendance();
            swipe.setRefreshing(false);
        });
    }
}
