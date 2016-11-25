package br.com.sailboat.elseapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.sailboat.elseapp.helper.AlarmManagerHelper
import br.com.sailboat.elseapp.helper.ExtrasHelper
import br.com.sailboat.elseapp.helper.LogHelper
import br.com.sailboat.elseapp.persistence.DatabaseOpenHelper
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.helper.alarm.AlarmHelper
import br.com.sailboat.helper.NotificationHelper
import java.util.*

class AlarmDatabaseUpdateReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        try {
            val alarmId = ExtrasHelper.getAlarmId(intent)
            val alarm = AlarmSQLite(DatabaseOpenHelper.getInstance(context)).getAlarmById(alarmId!!)
            val alarmTime = alarm!!.time

            val currentTime = Calendar.getInstance()
            currentTime.set(Calendar.SECOND, 0)
            currentTime.set(Calendar.MILLISECOND, 0)

            AlarmHelper.incrementToNextValidDate(alarmTime, alarm.repeatType)
            AlarmManagerHelper.setAlarm(context, alarmId, alarmTime.timeInMillis)
            AlarmSQLite(DatabaseOpenHelper.getInstance(context)).update(alarm)
            NotificationHelper.closeNotifications(context, 0)

        } catch (e: Exception) {
            LogHelper.printExceptionLog(e)
            Toast.makeText(context, "Error on set alarm: " + e.message, Toast.LENGTH_LONG).show()
        }

    }


}