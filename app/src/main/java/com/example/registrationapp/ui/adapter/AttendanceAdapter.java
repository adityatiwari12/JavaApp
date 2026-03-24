package com.example.registrationapp.ui.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.registrationapp.R;
import com.example.registrationapp.ui.attendance.AttendanceViewModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.VH> {
    private List<AttendanceViewModel.SubjectAttendanceItem> items = new ArrayList<>();

    public void setItems(List<AttendanceViewModel.SubjectAttendanceItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        AttendanceViewModel.SubjectAttendanceItem item = items.get(pos);
        h.name.setText(item.subject.name);
        h.pct.setText(item.percentage + "%");
        h.progress.setProgress(item.percentage);
        h.count.setText(item.attended + " / " + item.conducted + " classes");

        // Color coding
        int color;
        if (item.percentage >= 75) color = Color.parseColor("#4CAF50");
        else if (item.percentage >= 65) color = Color.parseColor("#FF9800");
        else color = Color.parseColor("#F44336");

        h.pct.setTextColor(color);
        h.progress.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        // Insight text
        if (item.percentage < 75) {
            h.insight.setText("Need " + item.classesNeeded + " more classes");
            h.insight.setTextColor(Color.parseColor("#F44336"));
        } else {
            h.insight.setText("Can miss " + item.canMiss + " classes");
            h.insight.setTextColor(Color.parseColor("#4CAF50"));
        }
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, pct, count, insight;
        ProgressBar progress;
        VH(View v) {
            super(v);
            name = v.findViewById(R.id.textSubjectName);
            pct = v.findViewById(R.id.textPercentage);
            progress = v.findViewById(R.id.progressAttendance);
            count = v.findViewById(R.id.textAttendedCount);
            insight = v.findViewById(R.id.textInsight);
        }
    }
}
