package br.com.sailboat.elseapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
//        setContext(context);
//
//        NotificationCompat.Builder builder = buildNotification(intent);
//        throwNotification(builder);

        Toast.makeText(context, "AEEEEEEEE", Toast.LENGTH_LONG).show();
    }

//    private void throwNotification(NotificationCompat.Builder builder) {
//        NotificationManager notifyMgr = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        notifyMgr.notify(NotificationHelper.TASK_NOTIFICATION_ID, builder.build());
//    }
//
//    @NonNull
//    private NotificationCompat.Builder buildNotification(Intent intent) {
//        Intent resultIntent = new Intent(getContext(), MainActivity.class);
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(getContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
//        builder.setSmallIcon(R.drawable.notification_icon) ;
//        builder.setCategory(NotificationCompat.CATEGORY_ALARM);
//        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
//        builder.setContentIntent(resultPendingIntent);
//        builder.setAutoCancel(true);
//        builder.setColor(ContextCompat.getColor(getContext(), R.color.blue_500));
//        initContentTextAndTitle(intent, resultPendingIntent, builder);
//        initVibrate(builder);
//        initSound(builder);
//        return builder;
//    }
//
//    private void initContentTextAndTitle(Intent intent, PendingIntent resultPendingIntent, NotificationCompat.Builder builder) {
//
//        try {
//            List<Task> tasks = getTasksThrowAfterSessionDate();
//
//            if (tasks.size() <= 1) {
//                initContentFromIntent(resultPendingIntent, intent, builder);
//            } else {
//                setTextAndTitleFromList(builder, tasks);
//            }
//
//        } catch (Exception e) {
//            LogHelper.printExceptionLog(e);
//            initContentFromIntent(resultPendingIntent, intent, builder);
//        }
//    }
//
//    private List<Task> getTasksThrowAfterSessionDate() throws Exception {
//        String lastSessionDate = PreferencesHelper.getLastSessionDate(getContext());
//
//        if (!TextUtils.isEmpty(lastSessionDate)) {
//            return TaskRepositorySQLite.getInstance(getContext().getApplicationContext()).getTasksThrowAfterDateTime(lastSessionDate);
//        } else {
//            return new ArrayList<>();
//        }
//
//    }
//
//    private void initContentFromIntent(PendingIntent resultPendingIntent, Intent intent, NotificationCompat.Builder builder) {
//        initNotificationActions(resultPendingIntent, getContext(), builder);
//
//        if (intent.hasExtra("EXTRA_TASK")) {
//            Task task = (Task) intent.getSerializableExtra("EXTRA_TASK");
//            builder.setContentTitle(task.getName());
//
//            if (!TextUtils.isEmpty(task.getNotes())) {
//                builder.setContentText(task.getNotes());
//            }
//
//        } else {
//            builder.setContentTitle(intent.getStringExtra("NOME_TAREFA"));
//        }
//    }
//
//    private void setTextAndTitleFromList(NotificationCompat.Builder builder, List<Task> tasks) {
//        builder.setContentTitle(tasks.size() + " " + getContext().getString(R.string.tasks_to_do));
//        builder.setContentText(getContext().getString(R.string.touch_to_check));
//    }
//
//    private void initNotificationActions(PendingIntent resultPendingIntent, Context context, NotificationCompat.Builder builder) {
////        builder.addAction(R.drawable.ic_thumb_up_white_24dp, context.getString(R.string.done), resultPendingIntent);
////        builder.addAction(R.drawable.ic_thumb_down_white_24dp, context.getString(R.string.not_done), resultPendingIntent);
//    }
//
//    private PendingIntent getTaskNotDoneIntent() {
////        Intent intent = new Intent(getContext(), TaskActionReceiver.class);
////        intent.putExtra();
////
////        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////
////        intentAlarme = new Intent(appContext, AlarmReceiver.class);
////        intentAlarme.putExtra("NOME_TAREFA", task.getName());
////        intentAlarme.putExtra("ID_TAREFA", task.getId());
////        intentAlarme.putExtra("EXTRA_TASK", task);
//
//
//
//        return null; // TODO
//    }
//
//    private PendingIntent getTaskDoneIntent() {
//        return null; // TODO
//    }
//
//    private void initVibrate(NotificationCompat.Builder builder) {
//        if (PreferencesHelper.isVibrationSettingAllowed(getContext())) {
//            builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_LIGHTS);
//        } else {
//            builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
//        }
//    }
//
//    private void initSound(NotificationCompat.Builder builder) {
//        builder.setSound(PreferencesHelper.getCurrentNotificationSound(getContext()));
//    }
//
//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
}
