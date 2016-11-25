package br.com.sailboat.medzy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.sailboat.canoe.helper.NotificationHelper
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.helper.ExtrasHelper
import java.util.*

class PostponeAlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val alarmId = ExtrasHelper.getAlarmId(intent!!)

        val currentTime = Calendar.getInstance()
        currentTime.add(Calendar.MINUTE, 10)

        AlarmManagerHelper.setPostponeAlarm(context, alarmId!!, currentTime.timeInMillis)

        NotificationHelper.closeNotifications(context, 0)
    }

}