package br.com.sailboat.elseapp.helper

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import br.com.sailboat.canoe.helper.NotificationHelper
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.receiver.AlarmDatabaseUpdateReceiver
import br.com.sailboat.elseapp.receiver.PostponeAlarmReceiver
import br.com.sailboat.elseapp.view.medicine.list.MedicineListActivity

class AlarmNotificationHelper(context: Context) {

    private val NOTIFICATION_ID = 0

    private val context = context

    fun throwNotification(alarmId: Long, medicine: Medicine) {
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

        addActions(builder, alarmId)

        NotificationHelper.throwNotification(context, NOTIFICATION_ID, builder)
    }

    private fun setTextAndTitleFromList(medicine: Medicine, builder: NotificationCompat.Builder) {
        builder.setContentTitle(medicine.name)
        builder.setContentText("1 capsula")
    }

    private fun addActions(builder: NotificationCompat.Builder, alarmId: Long) {
        addTakenAction(alarmId, builder)
        addPostponeAction(alarmId, builder)
    }

    private fun addTakenAction(alarmId: Long, builder: NotificationCompat.Builder) {
        val intent = Intent(context, AlarmDatabaseUpdateReceiver::class.java)

        ExtrasHelper.putAlarmId(alarmId, intent)

        val dismissIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.addAction(android.R.drawable.ic_delete, "Taken", dismissIntent)
    }

    private fun addPostponeAction(alarmId: Long, builder: NotificationCompat.Builder) {
        val intent = Intent(context, PostponeAlarmReceiver::class.java)

        ExtrasHelper.putAlarmId(alarmId, intent)

        val dismissIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        builder.addAction(android.R.drawable.ic_delete, "Postpone for 10 minutes", dismissIntent)
    }

    private fun initVibrate(builder: NotificationCompat.Builder) {
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE or NotificationCompat.DEFAULT_LIGHTS)
    }

    private fun initSound(builder: NotificationCompat.Builder) {
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        builder.setSound(alarmSound)
    }

}