package br.com.sailboat.medzy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.sailboat.canoe.helper.SafeOperation
import br.com.sailboat.medzy.helper.AlarmNotificationHelper
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        SafeOperation.withLog {
            val alarmId = getAlarmId(intent)
            val medication = getMedicationFromAlarmId(context, alarmId)

            AlarmNotificationHelper(context).throwNotification(alarmId, medication)
        }

    }

    private fun getAlarmId(intent: Intent): Long {
        return ExtrasHelper.getAlarmId(intent)!!
    }

    private fun getMedicationFromAlarmId(context: Context, alarmId: Long): Medication {
        val alarm = AlarmSQLite(context).getAlarmById(alarmId)
        return MedicationSQLite(context).getMedicationById(alarm.medId)
    }

}


