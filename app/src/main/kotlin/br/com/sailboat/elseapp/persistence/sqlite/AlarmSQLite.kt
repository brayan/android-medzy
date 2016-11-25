package br.com.sailboat.elseapp.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.canoe.base.BaseSQLite
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.persistence.DatabaseOpenHelper
import java.util.*

class AlarmSQLite(context: Context) : BaseSQLite(DatabaseOpenHelper.getInstance(context)) {

    fun getAlarmsByMedicine(medicineId: Long): MutableList<Alarm> {
        val sb = StringBuilder()
        sb.append(" SELECT Alarm.* FROM Alarm ")
        sb.append(" WHERE Alarm.medicineId = " + medicineId)

        return getAlarmList(sb)
    }

    fun getAlarmById(alarmId: Long): Alarm? {
        val sb = StringBuilder()
        sb.append(" SELECT Alarm.* FROM Alarm ")
        sb.append(" WHERE Alarm.id = " + alarmId)

        val cursor = performQuery(sb.toString())
        var alarm: Alarm? = null

        if (cursor.moveToNext()) {
            alarm = getAlarmFromCursor(cursor)
        }

        cursor.close()

        return alarm
    }

    fun saveAndGetId(alarm: Alarm): Long {
        val sb = StringBuilder()
        sb.append(" INSERT INTO Alarm ")
        sb.append(" (medicineId, time, repeatType) ")
        sb.append(" VALUES (?, ?, ?); ")

        val statement = compileStatement(sb.toString())
        statement.bindLong(1, alarm.medicineId)
        statement.bindString(2, formatTimeFromDate(alarm.time))
        statement.bindLong(3, alarm.repeatType.toLong())

        val id = insert(statement)

        return id
    }

    fun update(alarm: Alarm) {
        val sb = StringBuilder()
        sb.append(" UPDATE Alarm SET ")
        sb.append(" time = ?, ")
        sb.append(" repeatType = ? ")
        sb.append(" WHERE id = ? ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, formatTimeFromDate(alarm.time))
        statement.bindLong(2, alarm.repeatType.toLong())
        statement.bindLong(3, alarm.id)

        update(statement)
    }

    fun delete(alarmId: Long) {
        val query = "DELETE FROM Alarm WHERE Alarm.id = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, alarmId)

        delete(statement)
    }

    fun deleteAllByMedicine(medicineId: Long) {
        val query = "DELETE FROM Alarm WHERE Alarm.medicineId = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, medicineId)

        delete(statement)
    }

    private fun getAlarmList(query: StringBuilder): MutableList<Alarm> {
        val cursor = performQuery(query.toString())
        val alarms = ArrayList<Alarm>()

        while (cursor.moveToNext()) {
            val alarm = getAlarmFromCursor(cursor)
            alarms.add(alarm)
        }

        cursor.close()

        return alarms
    }

    private fun getAlarmFromCursor(cursor: Cursor): Alarm {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        val medicineId = cursor.getLong(cursor.getColumnIndexOrThrow("medicineId"))
        val time = cursor.getString(cursor.getColumnIndexOrThrow("time"))
        val repeatType = cursor.getInt(cursor.getColumnIndexOrThrow("repeatType"))

        return Alarm(id, medicineId, formatTimeFromString(time), repeatType)
    }

    private fun formatTimeFromDate(date: Calendar) : String {
        return DateHelper.formatDateTimeWithDatabaseFormat(date.time)
    }

    private fun formatTimeFromString(date: String) : Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = DateHelper.formatDateTimeFromDatabaseFormat(date)

        return calendar
    }
}