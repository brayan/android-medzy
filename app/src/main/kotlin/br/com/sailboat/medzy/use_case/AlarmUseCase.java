package br.com.sailboat.medzy.use_case;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.List;

import br.com.sailboat.medzy.helper.AlarmManagerHelper;
import br.com.sailboat.medzy.model.Alarm;
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite;

public class AlarmUseCase {

    public static List<Alarm> getAlarms(Context ctx, long medId) {
        return new AlarmSQLite(ctx).getAlarmsByMed(medId);
    }

    public static void cancelAlarms(Context ctx, long medId) {
        List<Alarm> alarms = getAlarms(ctx, medId);

        for (Alarm alarm : alarms) {
            AlarmManagerHelper.cancelAlarm(ctx, alarm.getId());
        }
    }

    public static void deleteAlarms(Context ctx, long medId) {
        List<Alarm> alarms = getAlarms(ctx, medId);

        for (Alarm alarm : alarms) {
            new AlarmSQLite(ctx).delete(alarm.getId());
        }
    }

    public static void saveAndSetAlarms(Context ctx, long medId, @NotNull List<Alarm> alarms) throws ParseException {
        for (Alarm alarm : alarms) {
            alarm.setMedId(medId);
            long alarmId = new AlarmSQLite(ctx).saveAndGetId(alarm);
            alarm.setId(alarmId);
            AlarmManagerHelper.setAlarm(ctx, alarm.getId(), alarm.getTime().getTimeInMillis());
        }
    }
}
