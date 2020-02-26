package com.wedidsystem.goapp.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.wedidsystem.goapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CustomDialog extends AppCompatDialogFragment {

    private EditText editKeyword;
    private Spinner editValidityPeriod;
    private CustomDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        editKeyword = view.findViewById(R.id.delete_keyword);
        editValidityPeriod = view.findViewById(R.id.validity_period);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.validityValues));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editValidityPeriod.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Add Keyword")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String keyword = editKeyword.getText().toString();
                        String spinner = "";
                        if (!editValidityPeriod.getSelectedItem().toString().equalsIgnoreCase("Select validity period")){
                            spinner = editValidityPeriod.getSelectedItem().toString();
                        }

                        listener.applyData(keyword, spinner);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CustomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CustomDialogListener");
        }
    }

    public interface CustomDialogListener {
        String applyData(String Keyword, String Validity);
    }
}
