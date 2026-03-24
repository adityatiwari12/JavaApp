package com.example.registrationapp.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.registrationapp.R;
import com.example.registrationapp.data.entity.Announcement;
import com.example.registrationapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.VH> {
    private List<Announcement> items = new ArrayList<>();

    public void setItems(List<Announcement> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announcement, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Announcement a = items.get(pos);
        h.title.setText(a.title);
        h.body.setText(a.body);
        h.date.setText(DateUtils.formatForDisplay(a.date));

        int[] colors = {Color.parseColor("#4CAF50"), Color.parseColor("#FF9800"), Color.parseColor("#F44336")};
        h.priority.setBackgroundColor(colors[Math.min(a.priority, 2)]);
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, body, date;
        View priority;
        VH(View v) {
            super(v);
            title = v.findViewById(R.id.textAnnouncementTitle);
            body = v.findViewById(R.id.textAnnouncementBody);
            date = v.findViewById(R.id.textAnnouncementDate);
            priority = v.findViewById(R.id.viewPriority);
        }
    }
}
