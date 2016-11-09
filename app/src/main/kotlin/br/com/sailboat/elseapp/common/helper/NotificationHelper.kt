package br.com.sailboat.elseapp.common.helper

import android.app.NotificationManager
import android.content.Context

class NotificationHelper {

    companion object {
        val NOTIFICATION_ID = 0

        fun closeNotifications(context: Context) {
            val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.cancel(NOTIFICATION_ID)
        }
    }

}
