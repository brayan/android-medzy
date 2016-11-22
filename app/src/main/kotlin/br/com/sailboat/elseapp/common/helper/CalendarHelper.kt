package br.com.sailboat.elseapp.common.helper

import java.util.*

object CalendarHelper {

    fun isToday(calendar: Calendar) : Boolean {
        return calendar.after(getFinalCalendarForYesterday())
                && calendar.before(getInitialCalendarForTomorrow())
    }

    fun isTomorrow(calendar: Calendar) : Boolean {
        return calendar.after(getFinalCalendarForToday())
                && calendar.before(getInitialCalendarForAfterTomorrow())
    }

    fun isBeforeToday(calendar: Calendar) : Boolean {
        return calendar.before(getInitialCalendarForToday())
    }

    private fun getInitialCalendarForToday(): Calendar {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        return today
    }

    private fun getFinalCalendarForToday(): Calendar {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 23)
        today.set(Calendar.MINUTE, 59)
        today.set(Calendar.SECOND, 59)
        today.set(Calendar.MILLISECOND, 0)

        return today
    }

    private fun getFinalCalendarForYesterday(): Calendar {
        val yesterday = getFinalCalendarForToday()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)

        return yesterday
    }

    private fun getInitialCalendarForTomorrow(): Calendar {
        val tomorrow = getInitialCalendarForToday()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)

        return tomorrow
    }

    private fun getInitialCalendarForAfterTomorrow(): Calendar {
        val afterTomorrow = getInitialCalendarForToday()
        afterTomorrow.add(Calendar.DAY_OF_MONTH, 2)

        return afterTomorrow
    }

    private fun getInitialCalendarForYesterday(): Calendar {
        val yesterday = getInitialCalendarForToday()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)

        return yesterday
    }

}