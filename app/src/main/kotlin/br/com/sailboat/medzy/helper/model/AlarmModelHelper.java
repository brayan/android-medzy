package br.com.sailboat.medzy.helper.model;

import android.content.Context;

import java.util.List;

import br.com.sailboat.medzy.model.Alarm;
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite;

public class AlarmModelHelper {

    public static List<Alarm> getAlarmsByMed(Context ctx, long medId) {
        return new AlarmSQLite(ctx).getAlarmsByMed(medId);
    }

}
