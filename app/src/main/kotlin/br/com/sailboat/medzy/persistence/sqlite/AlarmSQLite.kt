package br.com.sailboat.medzy.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.canoe.base.BaseSQLite
import br.com.sailboat.canoe.exception.EntityNotFoundException
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.persistence.DatabaseOpenHelper
import java.util.*

class AlarmSQLite(context: Context) : BaseSQLite(DatabaseOpenHelper.getInstance(context)) {

    fun getAlarmsByMed(medId: Long): MutableList<Alarm> {
        val sb = StringBuilder()
        sb.append(" SELECT Alarm.* FROM Alarm ")
        sb.append(" WHERE Alarm.medicationId = " + medId)

        return getAlarmList(sb)
    }

    fun getAlarmById(alarmId: Long): Alarm {
        val sb = StringBuilder()
        sb.append(" SELECT Alarm.* FROM Alarm ")
        sb.append(" WHERE Alarm.id = " + alarmId)

        val cursor = performQuery(sb.toString())

        if (cursor.moveToNext()) {
            val alarm = getAlarmFromCursor(cursor)
            cursor.close()
            return alarm
        }

        cursor.close()

        throw EntityNotFoundException("Alarm with id "+ alarmId +" not found")
    }

    fun saveAndGetId(alarm: Alarm): Long {
        val sb = StringBuilder()
        sb.append(" INSERT INTO Alarm ")
        sb.append(" (medicationId, time, repeatType, amount) ")
        sb.append(" VALUES (?, ?, ?, ?); ")

        val statement = compileStatement(sb.toString())
        statement.bindLong(1, alarm.medId)
        statement.bindString(2, parseCalendarToString(alarm.time))
        statement.bindLong(3, alarm.repeatType.toLong())
        statement.bindDouble(4, alarm.amount)

        val id = insert(statement)

        return id
    }

    fun update(alarm: Alarm) {
        val sb = StringBuilder()
        sb.append(" UPDATE Alarm SET ")
        sb.append(" time = ?, ")
        sb.append(" repeatType = ?, ")
        sb.append(" amount = ? ")
        sb.append(" WHERE id = ? ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, parseCalendarToString(alarm.time))
        statement.bindLong(2, alarm.repeatType.toLong())
        statement.bindDouble(3, alarm.amount)
        statement.bindLong(4, alarm.id)

        update(statement)
    }

    fun delete(alarmId: Long) {
        val query = "DELETE FROM Alarm WHERE Alarm.id = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, alarmId)

        delete(statement)
    }

    fun deleteAllByMed(medId: Long) {
        val query = "DELETE FROM Alarm WHERE Alarm.medicationId = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, medId)

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
        val id = getLong(cursor, "id")
        val medId = getLong(cursor, "medicationId")
        val time = getCalendar(cursor, "time")
        val repeatType = getInt(cursor, "repeatType")
        val amount = getDouble(cursor, "amount")

        return Alarm(id, medId, time, repeatType, amount)
    }

}