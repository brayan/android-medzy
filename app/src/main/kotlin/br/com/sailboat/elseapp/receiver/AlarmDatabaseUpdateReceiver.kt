package br.com.sailboat.elseapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.sailboat.elseapp.common.helper.AlarmManagerHelper
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.common.helper.NotificationHelper
import br.com.sailboat.elseapp.model.RepeatType
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import java.util.*

class AlarmDatabaseUpdateReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        try {

            val alarmId = ExtrasHelper.getAlarmId(intent)
            val alarm = AlarmSQLite(context).getAlarmById(alarmId!!)


            val alarmTime = alarm!!.time
            val currentTime = Calendar.getInstance()
            currentTime.set(Calendar.SECOND, 0)
            currentTime.set(Calendar.MILLISECOND, 0)

            when (alarm?.repeatType) {

                RepeatType.DAY -> {

                    while (alarmTime.before(currentTime) || alarmTime.equals(currentTime)) {
                        alarmTime.add(Calendar.DAY_OF_MONTH, 1)
                        break
                    }

                }
                else -> {

                }
            }

            AlarmManagerHelper.setAlarm(context, alarmId, alarmTime.timeInMillis)


            AlarmSQLite(context).update(alarm)

            NotificationHelper.closeNotifications(context)

        } catch (e: Exception) {
            LogHelper.printExceptionLog(e)
            Toast.makeText(context, "Error on set alarm: " + e.message, Toast.LENGTH_LONG).show()
        }
    }


}