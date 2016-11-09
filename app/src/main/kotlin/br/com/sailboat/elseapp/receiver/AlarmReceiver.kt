package br.com.sailboat.elseapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


        // TODO: IMPLEMENT NOTIFICATION LIKE ANDROID ALARM

//        try {
//            val alarmId = intent.getLongExtra(AlarmManagerHelper.ALARM_ID, -1L)
//            val alarm = AlarmSQLite(context).getAlarmById(alarmId)
//
//            val alarmTime = alarm!!.time
//            val currentTime = Calendar.getInstance()
//            currentTime.set(Calendar.SECOND, 0)
//            currentTime.set(Calendar.MILLISECOND, 0)
//
//            when (alarm?.repeatType) {
//
//                RepeatType.DAY -> {
//
//                    while (alarmTime.before(currentTime) || alarmTime.equals(currentTime)) {
//                        alarmTime.add(Calendar.DAY_OF_MONTH, 1)
//                        break
//                    }
//
//
//                }
//                else -> {
//
//                }
//            }
//
//            AlarmManagerHelper.setAlarm(context, alarmId, alarmTime.timeInMillis)
//
//            Toast.makeText(context, "Next alarm set to: " + SimpleDateFormat("dd/MM/yyyy HH:mm:SS").format(alarmTime.time), Toast.LENGTH_LONG).show()
//
//
//        } catch (e: Exception) {
//            LogHelper.printExceptionLog(e)
//            Toast.makeText(context, "Error on set alarm: " + e.message, Toast.LENGTH_LONG).show()
//        }

    }

}


