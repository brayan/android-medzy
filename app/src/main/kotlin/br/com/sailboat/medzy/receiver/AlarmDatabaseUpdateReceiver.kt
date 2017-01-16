package br.com.sailboat.medzy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.sailboat.canoe.alarm.AlarmHelper
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.canoe.helper.NotificationHelper
import br.com.sailboat.canoe.helper.SafeOperation
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import java.util.*

class AlarmDatabaseUpdateReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        try {
            val alarmId = ExtrasHelper.getAlarmId(intent)!!
            val alarm = AlarmSQLite(context).getAlarmById(alarmId)
            val alarmTime = DateHelper.parseStringWithDatabaseFormatToCalendar(alarm.time)

            val currentTime = Calendar.getInstance()
            currentTime.set(Calendar.SECOND, 0)
            currentTime.set(Calendar.MILLISECOND, 0)

            AlarmHelper.incrementToNextValidDate(alarmTime, alarm.repeatType)
            AlarmManagerHelper.setAlarm(context, alarmId, alarmTime.timeInMillis)
            alarm.time = DateHelper.parseCalendarWithDatabaseFormatToString(alarmTime)

            AlarmSQLite(context).update(alarm)
            NotificationHelper.closeNotifications(context, 0)

        } catch (e: Exception) {
            SafeOperation.printLogException(e)
        }

    }


}