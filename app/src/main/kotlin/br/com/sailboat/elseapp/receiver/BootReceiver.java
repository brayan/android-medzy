package br.com.sailboat.elseapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (isBootCompleted(intent)) {
//            initAlarms(context);
//        }
    }

//    private boolean isBootCompleted(Intent intent) {
//        return intent.getAction().equals("android.intent.action.BOOT_COMPLETED");
//    }
//
//    private void initAlarms(Context context) {
//        try {
//            initTaskAlarms(context);
//            initUpdateTasksAlarms(context);
//        } catch (Exception e) {
//            LogHelper.printExceptionLog(e);
//        }
//    }
//
//    private void initTaskAlarms(Context context) throws Exception {
//        List<Task> tasks = getTasksWithAlarms(context);
//
//        for (Task task : tasks) {
//            initAlarm(context, task);
//        }
//    }
//
//    private List<Task> getTasksWithAlarms(Context context) throws Exception {
//        return TaskRepositorySQLite.getInstance(context.getApplicationContext()).getTasksWithAlarms();
//    }
//
//    private void initAlarm(Context context, Task task) throws Exception {
//        Alarm alarm = AlarmRepositorySQLite.getInstance(context.getApplicationContext()).getByTask(task.getId());
//        AlarmManagerHelper.setAlarm(context, task, alarm);
//    }
//
//    private void initUpdateTasksAlarms(Context context) {
//        AlarmManagerHelper.setAlarmeAtualizacaoTarefas(context);
//    }

}
