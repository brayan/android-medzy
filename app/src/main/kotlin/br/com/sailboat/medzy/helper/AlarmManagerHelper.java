package br.com.sailboat.medzy.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.util.List;

import br.com.sailboat.canoe.helper.SafeOperation;
import br.com.sailboat.medzy.model.Alarm;
import br.com.sailboat.medzy.receiver.AlarmReceiver;

public class AlarmManagerHelper {

    public static final int REQUEST_POSTPONE = 999999998;

    private Context context;
    private Intent intent;
    private PendingIntent pendingIntent;

    private AlarmManagerHelper(Context context) {
        this.context = context.getApplicationContext();

        SafeOperation.withLog(new SafeOperation.Callback() {
            @Override
            public void perform() throws Exception {
                // TODO
            }
        });
    }

    public static void setAlarm(Context ctx, long alarmId, long timeInMillis) throws ParseException {
        new AlarmManagerHelper(ctx).setAlarm(alarmId, (int) alarmId, timeInMillis);
    }

    public static void setPostponeAlarm(Context ctx, long alarmId, long timeInMillis) throws ParseException {
        new AlarmManagerHelper(ctx).setAlarm(alarmId, REQUEST_POSTPONE, timeInMillis);
    }

    private void setAlarm(long alarmId, int requestId, long timeInMillis) throws ParseException {
        intent = new Intent(context, AlarmReceiver.class);
        ExtrasHelper.INSTANCE.putAlarmId(alarmId, intent);
        pendingIntent = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        getAlarmManager().setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    public static void cancelAlarms(Context context, List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            AlarmManagerHelper.cancelAlarm(context, alarm.getId());
        }
    }

    public static void cancelAlarm(Context context, long alarmId) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        ExtrasHelper.INSTANCE.putAlarmId(alarmId, intent);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, (int) alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmIntent.cancel();
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }


}
