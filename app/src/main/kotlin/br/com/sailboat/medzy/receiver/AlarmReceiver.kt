package br.com.sailboat.medzy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.sailboat.canoe.helper.SafeOperation
import br.com.sailboat.medzy.helper.AlarmNotificationHelper
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.model.Medicine
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicineSQLite

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        SafeOperation.withLog {
            val alarmId = getAlarmId(intent)
            val medicine = getMedicineFromAlarmId(context, alarmId)

            AlarmNotificationHelper(context).throwNotification(alarmId, medicine)
        }

    }

    private fun getAlarmId(intent: Intent): Long {
        return ExtrasHelper.getAlarmId(intent)!!
    }

    private fun getMedicineFromAlarmId(context: Context, alarmId: Long): Medicine {
        val alarm = AlarmSQLite(context).getAlarmById(alarmId)!!
        return MedicineSQLite(context).getMedicineById(alarm.medicineId)!!
    }

}


