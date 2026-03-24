package com.example.registrationapp.ui.leave;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.registrationapp.data.db.AppDatabase;
import com.example.registrationapp.data.entity.LeaveRequest;
import com.example.registrationapp.utils.DateUtils;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class LeaveDialogHelper {

    public static void showApplyLeaveDialog(Context ctx, Runnable onDone) {
        LinearLayout layout = new LinearLayout(ctx);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 32, 48, 16);

        EditText fromDate = new EditText(ctx);
        fromDate.setHint("From Date (tap to pick)");
        fromDate.setFocusable(false);
        fromDate.setClickable(true);
        fromDate.setOnClickListener(v -> pickDate(ctx, fromDate));
        layout.addView(fromDate);

        EditText toDate = new EditText(ctx);
        toDate.setHint("To Date (tap to pick)");
        toDate.setFocusable(false);
        toDate.setClickable(true);
        toDate.setOnClickListener(v -> pickDate(ctx, toDate));
        layout.addView(toDate);

        EditText reason = new EditText(ctx);
        reason.setHint("Reason for leave");
        reason.setMinLines(2);
        layout.addView(reason);

        new androidx.appcompat.app.AlertDialog.Builder(ctx)
                .setTitle("Apply for Leave")
                .setView(layout)
                .setPositiveButton("Submit", (d, w) -> {
                    String from = fromDate.getText().toString().trim();
                    String to = toDate.getText().toString().trim();
                    String r = reason.getText().toString().trim();

                    if (from.isEmpty() || to.isEmpty() || r.isEmpty()) {
                        Toast.makeText(ctx, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Executors.newSingleThreadExecutor().execute(() -> {
                        AppDatabase.getInstance(ctx).leaveDao().insert(
                                new LeaveRequest(from, to, r, "PENDING", DateUtils.getTodayDb()));
                    });
                    Toast.makeText(ctx, "Leave request submitted!", Toast.LENGTH_SHORT).show();
                    if (onDone != null) onDone.run();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private static void pickDate(Context ctx, EditText target) {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(ctx, (v, y, m, d) -> {
            target.setText(String.format("%04d-%02d-%02d", y, m + 1, d));
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }
}
