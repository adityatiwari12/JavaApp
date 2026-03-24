package com.example.registrationapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.registrationapp.R;
import com.example.registrationapp.data.entity.TimetableEntry;
import com.example.registrationapp.data.entity.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.VH> {
    private List<TimetableEntry> entries = new ArrayList<>();
    private Map<Integer, String> subjectNames = new HashMap<>();

    public void setData(List<TimetableEntry> entries, List<Subject> subjects) {
        this.entries = entries;
        subjectNames.clear();
        if (subjects != null) {
            for (Subject s : subjects) subjectNames.put(s.id, s.name);
        }
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        TimetableEntry e = entries.get(pos);
        h.time.setText(e.startTime + "\n" + e.endTime);
        h.name.setText(subjectNames.getOrDefault(e.subjectId, "Subject"));
        h.room.setText(e.room);
    }

    @Override public int getItemCount() { return entries.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView time, name, room;
        VH(View v) {
            super(v);
            time = v.findViewById(R.id.textClassTime);
            name = v.findViewById(R.id.textClassName);
            room = v.findViewById(R.id.textClassRoom);
        }
    }
}
