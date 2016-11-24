package br.com.sailboat.elseapp.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.helper.AlarmManagerHelper
import br.com.sailboat.elseapp.helper.ExtrasHelper
import br.com.sailboat.elseapp.helper.LogHelper
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite
import br.com.sailboat.elseapp.view.medicine.list.MedicineListActivity

class AlarmReceiver : BroadcastReceiver() {

    var context: Context? = null

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context

        // TODO: IMPLEMENT NOTIFICATION LIKE ANDROID ALARM

        try {
            val alarmId = intent.getLongExtra(AlarmManagerHelper.ALARM_ID, -1L)
            val alarm = AlarmSQLite(context).getAlarmById(alarmId)
            val medicine = MedicineSQLite(context).getMedicineById(alarm!!.medicineId)

            val builder = buildNotification(intent, medicine, alarmId)
            throwNotification(builder)

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


        } catch (e: Exception) {
            LogHelper.printExceptionLog(e)
            Toast.makeText(context, "Error on set alarm: " + e.message, Toast.LENGTH_LONG).show()
        }


    }

    private fun throwNotification(builder: NotificationCompat.Builder) {
        val notifyMgr = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifyMgr.notify(0, builder.build())
    }

    private fun buildNotification(intent: Intent, medicine: Medicine?, alarmId: Long): NotificationCompat.Builder {
        val resultIntent = Intent(context, MedicineListActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context)
        builder.setSmallIcon(R.drawable.notification_template_icon_bg)
        builder.setCategory(NotificationCompat.CATEGORY_ALARM)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setContentIntent(resultPendingIntent)
        builder.setAutoCancel(true)
        builder.color = ContextCompat.getColor(context, R.color.cyan_500)
        builder.setOngoing(true)
        setTextAndTitleFromList(medicine, builder)
        initVibrate(builder)
        initSound(builder)

        initNotificationActions(builder, alarmId)

        return builder
    }

    private fun setTextAndTitleFromList(medicine: Medicine?, builder: NotificationCompat.Builder) {
        builder.setContentTitle(medicine!!.name)
        builder.setContentText("1 capsula")
    }

    private fun initNotificationActions(builder: NotificationCompat.Builder, alarmId: Long) {
        val intent = Intent(context, AlarmDatabaseUpdateReceiver::class.java)

        ExtrasHelper.putAlarmId(alarmId, intent)

        val dismissIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.addAction(android.R.drawable.ic_delete, "Taken", dismissIntent)
        // TODO:


//        builder.addAction(android.R.drawable.ic_delete, "Snooze", dismissIntent)
    }

    private fun initVibrate(builder: NotificationCompat.Builder) {
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE or NotificationCompat.DEFAULT_LIGHTS)
    }

    private fun initSound(builder: NotificationCompat.Builder) {
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        builder.setSound(alarmSound)
    }

}


