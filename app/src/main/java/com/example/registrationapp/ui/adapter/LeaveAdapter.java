package com.example.registrationapp.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.registrationapp.R;
import com.example.registrationapp.data.entity.LeaveRequest;
import com.example.registrationapp.utils.DateUtils;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.VH> {
    private List<LeaveRequest> items = new ArrayList<>();

    public void setItems(List<LeaveRequest> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        LeaveRequest item = items.get(pos);
        h.reason.setText(item.reason);
        h.dates.setText(DateUtils.formatForDisplay(item.fromDate) + " → " + DateUtils.formatForDisplay(item.toDate));

        h.status.setText(item.status);
        switch (item.status) {
            case "PENDING": h.status.setChipBackgroundColorResource(android.R.color.holo_orange_light); break;
            case "APPROVED": h.status.setChipBackgroundColorResource(android.R.color.holo_green_light); break;
            case "REJECTED": h.status.setChipBackgroundColorResource(android.R.color.holo_red_light); break;
        }
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView reason, dates;
        Chip status;
        VH(View v) {
            super(v);
            reason = v.findViewById(R.id.textLeaveReason);
            dates = v.findViewById(R.id.textLeaveDates);
            status = v.findViewById(R.id.chipLeaveStatus);
        }
    }
}
