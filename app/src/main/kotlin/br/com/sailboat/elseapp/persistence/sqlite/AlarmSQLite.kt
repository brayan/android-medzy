package br.com.sailboat.elseapp.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.elseapp.base.BaseSQLite
import br.com.sailboat.elseapp.common.helper.AlarmHelper
import br.com.sailboat.elseapp.model.Alarm
import java.util.*

class AlarmSQLite(context: Context) : BaseSQLite(context) {

    override fun getQueryCreateTable(): String {
        val sb = StringBuilder()
        sb.append(" CREATE TABLE Alarm ( ")
        sb.append(" id INTEGER, ")
        sb.append(" drugId INTEGER, ")
        sb.append(" time TEXT NOT NULL, ")
        sb.append(" repeatType INTEGER, ")
        sb.append(" PRIMARY KEY(id, drugId), ")
        sb.append(" FOREIGN KEY(drugId) REFERENCES Drug(id) ")
        sb.append(" ); ")

        return sb.toString()
    }

    fun getAlarmsByDrug(drugId: Long): MutableList<Alarm> {
        val sb = StringBuilder()
        sb.append(" SELECT Alarm.* FROM Alarm ")
        sb.append(" WHERE Alarm.drugId = " + drugId)

        return getAlarmList(sb)
    }

    fun saveAndGetId(alarm: Alarm): Long {
        val sb = StringBuilder()
        sb.append(" INSERT INTO Alarm ")
        sb.append(" (drugId, time, repeatType) ")
        sb.append(" VALUES (?, ?, ?); ")

        val statement = compileStatement(sb.toString())
        statement.bindLong(1, alarm.drugId)
        statement.bindString(2, formatTimeFromDate(alarm.time))
        statement.bindLong(3, alarm.repeatType as Long)

        val id = executeInsert(statement)

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
        statement.bindLong(2, alarm.repeatType as Long)
        statement.bindLong(3, alarm.id)

        executeUpdateOrDelete(statement)
    }

    fun delete(alarmId: Long) {
        val query = "DELETE FROM Alarm WHERE Alarm.id = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, alarmId)

        executeUpdateOrDelete(statement)
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
        val drugId = cursor.getLong(cursor.getColumnIndexOrThrow("drugId"))
        val time = cursor.getString(cursor.getColumnIndexOrThrow("time"))
        val repeatType = cursor.getInt(cursor.getColumnIndexOrThrow("repeatType"))

        return Alarm(id, drugId, formatTimeFromString(time), repeatType)
    }

    private fun formatTimeFromDate(date: Calendar) : String {
        return AlarmHelper.formatTimeWithDatabaseFormat(date.time)
    }

    private fun formatTimeFromString(date: String) : Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = AlarmHelper.formatTimeFromDatabaseFormat(date)

        return calendar
    }
}