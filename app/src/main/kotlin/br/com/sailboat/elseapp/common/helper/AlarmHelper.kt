package br.com.sailboat.elseapp.common.helper

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object AlarmHelper {

    val DATABASE_TIME_FORMAT = "HH:mm"

    fun formatTimeFromDatabaseFormat(date: String): Date {
        return SimpleDateFormat(DATABASE_TIME_FORMAT).parse(date)
    }

    fun formatTimeWithDatabaseFormat(date: Date): String {
        return SimpleDateFormat(DATABASE_TIME_FORMAT).format(date)
    }

    fun formatTimeWithAndroidFormat(date: Date, ctx: Context): String {
        val format = getAndroidHourFormat(ctx)
        return SimpleDateFormat(format).format(date)
    }

    fun getAndroidHourFormat(ctx: Context): String {
        if (DateFormat.is24HourFormat(ctx)) {
            return "HH:mm"
        } else {
            return "hh:mm a"
        }
    }

    fun getTimeInitialAlarm(): Date {

        val currentTime = Calendar.getInstance()

        currentTime.set(Calendar.SECOND, 0)
        currentTime.set(Calendar.MILLISECOND, 0)

        var hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        if (minute >= 0 && minute < 30) {
            currentTime.set(Calendar.MINUTE, 30)

        } else if (minute >= 30 && minute <= 59) {

            currentTime.set(Calendar.MINUTE, 0)

            if (hour == 23) {
                currentTime.set(Calendar.HOUR_OF_DAY, 0)
            } else {
                hour++
                currentTime.set(Calendar.HOUR_OF_DAY, hour)
            }

        }

        return currentTime.time
    }

}

