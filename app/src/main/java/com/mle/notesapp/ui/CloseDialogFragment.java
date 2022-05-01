package com.mle.notesapp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.mle.notesapp.R;

public class CloseDialogFragment extends DialogFragment {

    public static final String MESSAGE = "MESSAGE";
    public static final String RESULT_KEY = "CloseDialogFragment_RESULT_KEY";

    public static CloseDialogFragment newInstance(String message) {

        Bundle args = new Bundle();
        args.putString(MESSAGE, message);

        CloseDialogFragment fragment = new CloseDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View customTitle = getLayoutInflater().inflate(R.layout.dialog_custom_title, null);
        View customView = getLayoutInflater().inflate(R.layout.dialog_custom_view_hint,null);

        EditText editText = customView.findViewById(R.id.edit_text);

        String message = getArguments().getString(MESSAGE);
        editText.setText(message);


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setCustomTitle(customTitle)
                .setView(customView)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Bundle result = new Bundle();
                        result.putString(MESSAGE, editText.getText().toString());
                        getParentFragmentManager()
                                .setFragmentResult(RESULT_KEY, result);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(requireContext(), "No", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();

    }
}
