package com.company.todolistproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {
    private final DialogInterface.OnClickListener _dialogListener;

    public AlertDialogFragment(DialogInterface.OnClickListener dialogListener) {
        this._dialogListener = dialogListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Delete")
                .setMessage("Do you want to delete this item from list?")
                .setPositiveButton("Yes", _dialogListener)
                .create();
    }

    public static String TAG = "AlertDialogFragment";
}
