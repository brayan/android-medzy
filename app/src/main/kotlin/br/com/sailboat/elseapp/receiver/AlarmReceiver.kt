package br.com.sailboat.elseapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.sailboat.elseapp.helper.AlarmNotificationHelper
import br.com.sailboat.elseapp.helper.ExtrasHelper
import br.com.sailboat.elseapp.helper.LogHelper
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        try {
            val alarmId = getAlarmId(intent)
            val medicine = getMedicineFromAlarmId(context, alarmId)

            AlarmNotificationHelper(context).throwNotification(alarmId, medicine)

        } catch (e: Exception) {
            LogHelper.printExceptionLog(e)
            Toast.makeText(context, "Error on throw notification: " + e.message, Toast.LENGTH_LONG).show()
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


