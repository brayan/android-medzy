package br.com.sailboat.elseapp.view.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;

import java.util.Calendar;

public class AlarmPickerDialog extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    private Calendar currentTime;

    private Callback callback;

    private boolean shouldSetTime = false;

    public static void show(FragmentManager fragmentManager, AlarmPickerDialog.Callback callback) {
        show(fragmentManager, null, callback);
    }

    public static void show(FragmentManager fragmentManager, Calendar currentTime, AlarmPickerDialog.Callback callback) {
        AlarmPickerDialog alarmPickerDialog = new AlarmPickerDialog();
        alarmPickerDialog.setCurrentTime(currentTime);
        alarmPickerDialog.setCallback(callback);

        alarmPickerDialog.show(fragmentManager, AlarmPickerDialog.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inicializarHorarioInicial();

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, getHour(), getMinute(),
                DateFormat.is24HourFormat(getActivity()));

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        return dialog;
    }

    private void inicializarHorarioInicial() {
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

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        callback.onClickCancelTimePicker();
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        callback.onClickOkAlarmTimePicker(hourOfDay, minute);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }


    public interface Callback {
        void onClickOkAlarmTimePicker(int horaDoDia, int minutos);
        void onClickCancelTimePicker();
    }

}
