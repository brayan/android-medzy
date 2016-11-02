package br.com.sailboat.elseapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class AlarmUpdateReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        inicializarAlarmes(context);
    }

    private void inicializarAlarmes(Context context) {

//        try {
//            List<Task> listaTasks = TaskRepositorySQLite.getInstance(context.getApplicationContext()).getTasksWithAlarms();
//
//            for (Task task : listaTasks) {
//                Alarm alarm = AlarmRepositorySQLite.getInstance(context.getApplicationContext()).getByTask(task.getId());
//                AlarmManagerHelper.setAlarm(context, task, alarm);
//            }
//
//        } catch (Exception e) {
//            LogHelper.printExceptionLog(e);
//        }
    }
}
