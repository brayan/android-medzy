package br.com.sailboat.elseapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.helper.AlarmManagerHelper
import br.com.sailboat.helper.NotificationHelper
import java.util.*

class PostponeAlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val alarmId = intent!!.getLongExtra(AlarmManagerHelper.ALARM_ID, -1L)

        val time = Calendar.getInstance()
        time.add(Calendar.MINUTE, 10)

        AlarmManagerHelper.setAlarm(context, alarmId, time.timeInMillis)

        NotificationHelper.closeNotifications(context, 0)
    }

}