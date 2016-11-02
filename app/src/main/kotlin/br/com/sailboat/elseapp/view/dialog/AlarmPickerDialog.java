package br.com.sailboat.elseapp.view.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmPickerDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private Calendar currentTime;
    private Callback callback;

    public static void show(FragmentManager fragmentManager, AlarmPickerDialog.Callback callback) {
        show(fragmentManager, null, callback);
    }

    public static void show(FragmentManager fragmentManager, Calendar currentTime, AlarmPickerDialog.Callback callback) {
        AlarmPickerDialog dialog = new AlarmPickerDialog();
        dialog.setCurrentTime(currentTime);
        dialog.setCallback(callback);

        dialog.show(fragmentManager, AlarmPickerDialog.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        checkAndInitTime();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, getHour(), getMinute(),
                DateFormat.is24HourFormat(getActivity()));

        return dialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {
            callback.onClickOk(hourOfDay, minute);
        }
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    private void checkAndInitTime() {
        if (currentTime == null) {
            this.currentTime = Calendar.getInstance();
        }
    }

    private int getMinute() {
        return currentTime.get(Calendar.MINUTE);
    }

    private int getHour() {
        return currentTime.get(Calendar.HOUR_OF_DAY);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setCurrentTime(Calendar currentTime) {
        this.currentTime = currentTime;
    }


    public interface Callback {
        void onClickOk(int hourOfDay, int minute);
    }

}
