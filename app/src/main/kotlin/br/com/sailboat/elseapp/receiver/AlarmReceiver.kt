package br.com.sailboat.elseapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.common.helper.AlarmManagerHelper
import br.com.sailboat.elseapp.model.RepeatType
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite

class AlarmReceiver : BroadcastReceiver() {

    private val context: Context? = null

    override fun onReceive(context: Context, intent: Intent) {

        val alarmId = intent.getLongExtra(AlarmManagerHelper.ALARM_ID, -1L)

        if (alarmId != -1L) {
            val alarm = AlarmSQLite(context).getAlarmById(alarmId)

            when (alarm?.repeatType) {

                RepeatType.DAY -> {

                }
                else -> {

                }
            }

        }


    }

}


