package com.example.registrationapp.ui.marks;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.example.registrationapp.R;
import com.example.registrationapp.data.entity.MstMark;
import com.example.registrationapp.data.entity.Subject;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class MarksDialogHelper {

    public interface MarksSaveListener {
        void onSave(MstMark mark);
    }

    public static void showAddMarksDialog(Context context, List<Subject> subjects, MarksSaveListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_marks, null);

        AutoCompleteTextView spinnerSubject = view.findViewById(R.id.spinnerSubjectMarks);
        AutoCompleteTextView spinnerMst = view.findViewById(R.id.spinnerMstType);
        TextInputEditText editObtained = view.findViewById(R.id.editObtainedMarks);
        TextInputEditText editMax = view.findViewById(R.id.editMaxMarks);

        // Subjects list
        List<String> subNames = new ArrayList<>();
        for (Subject s : subjects) subNames.add(s.name);
        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, subNames);
        spinnerSubject.setAdapter(subAdapter);

        // MST types
        String[] mstTypes = {"MST-1", "MST-2"};
        ArrayAdapter<String> mstAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, mstTypes);
        spinnerMst.setAdapter(mstAdapter);

        builder.setView(view)
                .setTitle("Log MST Marks")
                .setPositiveButton("Save", (dialog, which) -> {
                    String subName = spinnerSubject.getText().toString();
                    String mstName = spinnerMst.getText().toString();
                    String obtainedStr = editObtained.getText().toString();
                    String maxStr = editMax.getText().toString();

                    if (!subName.isEmpty() && !mstName.isEmpty() && !obtainedStr.isEmpty() && !maxStr.isEmpty()) {
                        int subId = -1;
                        for (Subject s : subjects) {
                            if (s.name.equals(subName)) {
                                subId = s.id;
                                break;
                            }
                        }

                        if (subId != -1) {
                            int obtained = Integer.parseInt(obtainedStr);
                            int max = Integer.parseInt(maxStr);
                            listener.onSave(new MstMark(subId, mstName, obtained, max));
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
