package br.com.sailboat.medzy.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.sailboat.medzy.R;

public class TextInputDialog extends DialogFragment {

    private String text;
    private EditText etDialogTextInput;
    private TextInputDialog.Callback callback;

    public static void show(FragmentManager fragmentManager, TextInputDialog.Callback callback) {
        show(fragmentManager, null, callback);
    }

    public static void show(FragmentManager fragmentManager, String text, TextInputDialog.Callback callback) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setText(text);

        dialog.show(fragmentManager, TextInputDialog.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_text_input, container, false);
        etDialogTextInput = (EditText) view.findViewById(R.id.tvDialogTextInput);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setPositiveButton(android.R.string.ok, null);
        dialog.setNegativeButton(R.string.cancel, null);

        LayoutInflater i = getActivity().getLayoutInflater();

        View view = i.inflate(R.layout.dialog_text_input, null, false);
        etDialogTextInput = (EditText) view.findViewById(R.id.tvDialogTextInput);

        dialog.setView(view);


        return dialog.create();
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onClickOk(int hourOfDay, int minute);
    }

}
