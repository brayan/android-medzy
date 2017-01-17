package br.com.sailboat.medzy.helper

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import br.com.sailboat.canoe.helper.NotificationHelper
import br.com.sailboat.medzy.R

import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.receiver.AlarmDatabaseUpdateReceiver
import br.com.sailboat.medzy.receiver.PostponeAlarmReceiver
import br.com.sailboat.medzy.view.medication.list.MedicationListActivity

class AlarmNotificationHelper(context: Context) {

    private val NOTIFICATION_ID = 0

    private val context = context

    fun throwNotification(alarmId: Long, medication: Medication) {
        val resultIntent = Intent(context, MedicationListActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context)
        builder.setSmallIcon(R.drawable.notification_template_icon_bg)
        builder.setCategory(NotificationCompat.CATEGORY_ALARM)
        builder.priority = NotificationCompat.PRIORITY_MAX
        builder.setContentIntent(resultPendingIntent)
        builder.setAutoCancel(true)
        builder.color = ContextCompat.getColor(context, R.color.md_cyan_500)
        setTextAndTitleFromList(medication, builder)
        initVibrate(builder)
        initSound(builder)

        addActions(builder, alarmId)

        NotificationHelper.throwNotification(context, NOTIFICATION_ID, builder)
    }

    private fun setTextAndTitleFromList(medication: Medication, builder: NotificationCompat.Builder) {
        builder.setContentTitle(medication.name)
    }

    private fun addActions(builder: NotificationCompat.Builder, alarmId: Long) {
        addTakenAction(alarmId, builder)
        addPostponeAction(alarmId, builder)
    }

    private fun addTakenAction(alarmId: Long, builder: NotificationCompat.Builder) {
        val intent = Intent(context, AlarmDatabaseUpdateReceiver::class.java)

        ExtrasHelper.putAlarmId(alarmId, intent)

        val dismissIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.addAction(android.R.drawable.ic_delete, context.getString(R.string.taken), dismissIntent)
    }

    private fun addPostponeAction(alarmId: Long, builder: NotificationCompat.Builder) {
        val intent = Intent(context, PostponeAlarmReceiver::class.java)

        ExtrasHelper.putAlarmId(alarmId, intent)

        val dismissIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        builder.addAction(android.R.drawable.ic_delete, context.getString(R.string.postpone), dismissIntent)
    }

    private fun initVibrate(builder: NotificationCompat.Builder) {
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE or NotificationCompat.DEFAULT_LIGHTS)
    }

    private fun initSound(builder: NotificationCompat.Builder) {
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        builder.setSound(alarmSound)
    }

}