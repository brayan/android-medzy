package br.com.sailboat.elseapp.helper

import java.util.*

object AlarmHelper {

    fun getTimeInitialAlarm(): Calendar {

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

        return currentTime
    }

}

