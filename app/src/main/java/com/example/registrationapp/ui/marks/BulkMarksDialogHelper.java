package com.example.registrationapp.ui.marks;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.registrationapp.data.entity.MstMark;
import com.example.registrationapp.data.entity.Subject;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class BulkMarksDialogHelper {

    public interface BulkSaveListener {
        void onSave(List<MstMark> marks);
    }

    public static void showBulkMarksDialog(Context context, String mstName, List<Subject> subjects, BulkSaveListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        
        ScrollView scrollView = new ScrollView(context);
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(48, 32, 48, 32);

        List<EditText> editTexts = new ArrayList<>();
        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(context);
        
        for (Subject sub : subjects) {
            android.view.View row = inflater.inflate(com.example.registrationapp.R.layout.item_bulk_mark_entry, container, false);
            TextView label = row.findViewById(com.example.registrationapp.R.id.txtSubjectNameBulk);
            EditText edit = row.findViewById(com.example.registrationapp.R.id.etMarksBulk);
            
            label.setText(sub.name);
            edit.setTag(sub.id);
            container.addView(row);
            editTexts.add(edit);
        }

        scrollView.addView(container);

        builder.setTitle("Log " + mstName + " Marks (Max: 20)")
                .setView(scrollView)
                .setPositiveButton("Save All", (dialog, which) -> {
                    List<MstMark> marksToSave = new ArrayList<>();
                    for (EditText et : editTexts) {
                        String val = et.getText().toString();
                        if (!val.isEmpty()) {
                            try {
                                int subId = (int) et.getTag();
                                int obt = Integer.parseInt(val);
                                marksToSave.add(new MstMark(subId, mstName, Math.min(obt, 20), 20));
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                    if (!marksToSave.isEmpty()) {
                        listener.onSave(marksToSave);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
