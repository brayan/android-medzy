package br.com.sailboat.elseapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.sailboat.elseapp.common.helper.AlarmManagerHelper
import br.com.sailboat.elseapp.model.RepeatType
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    private val context: Context? = null

    override fun onReceive(context: Context, intent: Intent) {

        val alarmId = intent.getLongExtra(AlarmManagerHelper.ALARM_ID, -1L)


        if (alarmId != -1L) {
            val alarm = AlarmSQLite(context).getAlarmById(alarmId)

            val alarmTime = alarm!!.time
            val currentTime = Calendar.getInstance()
            currentTime.set(Calendar.SECOND, 0)
            currentTime.set(Calendar.MILLISECOND, 0)

            when (alarm?.repeatType) {

                RepeatType.DAY -> {

                    while (alarmTime.before(currentTime) || alarmTime.equals(currentTime)) {
                        alarmTime.add(Calendar.DAY_OF_MONTH, alarmTime.get(Calendar.DAY_OF_MONTH) + 1)
                        break
                    }


                }
                else -> {

                }
            }

            AlarmManagerHelper.setAlarm(context, alarmId, alarmTime.timeInMillis)

            Toast.makeText(context, "Next alarm set to: " + SimpleDateFormat("dd/MM/yyyy HH:mm:SS").format(alarmTime.time), Toast.LENGTH_LONG). show()

        }


    }

}


