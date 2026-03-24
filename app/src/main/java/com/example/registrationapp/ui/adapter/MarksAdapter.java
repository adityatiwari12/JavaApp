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
import com.example.registrationapp.data.entity.MstMark;
import com.example.registrationapp.data.entity.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.VH> {
    private List<Subject> subjects = new ArrayList<>();
    private Map<Integer, List<MstMark>> marksMap = new HashMap<>();

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        notifyDataSetChanged();
    }

    public void setMarks(List<MstMark> allMarks) {
        this.marksMap.clear();
        if (allMarks != null) {
            for (MstMark m : allMarks) {
                marksMap.computeIfAbsent(m.subjectId, k -> new ArrayList<>()).add(m);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marks, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Subject sub = subjects.get(pos);
        h.name.setText(sub.name);

        List<MstMark> marks = marksMap.getOrDefault(sub.id, new ArrayList<>());
        int totalObt = 0, totalMax = 0;
        String mst1 = "Not happened yet";
        String mst2 = "Not happened yet";
        
        for (MstMark m : marks) {
            totalObt += m.marksObtained;
            totalMax += m.maxMarks;
            if (m.examName.equalsIgnoreCase("MST-1")) {
                mst1 = m.marksObtained + " / " + m.maxMarks;
            } else if (m.examName.equalsIgnoreCase("MST-2")) {
                mst2 = m.marksObtained + " / " + m.maxMarks;
            }
        }

        h.mst1.setText("MST-1: " + mst1);
        h.mst2.setText("MST-2: " + mst2);

        if (totalMax > 0) {
            int avg = (totalObt * 100 / totalMax);
            h.progress.setProgress(avg);
            h.avg.setText("Average: " + avg + "%");

            int color = avg >= 60 ? Color.parseColor("#4CAF50") :
                        avg >= 40 ? Color.parseColor("#FF9800") : Color.parseColor("#F44336");
            h.progress.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

            if (avg < 40) {
                h.name.setTextColor(Color.parseColor("#F44336"));
                h.avg.setText("Average: " + avg + "% ⚠ Weak");
                h.avg.setTextColor(Color.parseColor("#F44336"));
            } else {
                h.name.setTextColor(Color.parseColor("#000000"));
                h.avg.setTextColor(Color.parseColor("#757575"));
            }
        } else {
            h.progress.setProgress(0);
            h.avg.setText("No marks logged yet");
            h.name.setTextColor(Color.parseColor("#000000"));
            h.avg.setTextColor(Color.parseColor("#757575"));
            h.progress.getProgressDrawable().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override public int getItemCount() { return subjects.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, mst1, mst2, avg;
        ProgressBar progress;
        VH(View v) {
            super(v);
            name = v.findViewById(R.id.textMarkSubject);
            mst1 = v.findViewById(R.id.textMst1);
            mst2 = v.findViewById(R.id.textMst2);
            progress = v.findViewById(R.id.progressMarks);
            avg = v.findViewById(R.id.textAvgMark);
        }
    }
}
