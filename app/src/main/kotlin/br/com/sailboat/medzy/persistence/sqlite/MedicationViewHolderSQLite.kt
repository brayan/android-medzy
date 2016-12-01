package br.com.sailboat.medzy.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.canoe.base.BaseSQLite
import br.com.sailboat.medzy.persistence.DatabaseOpenHelper
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import java.util.*

class MedicationViewHolderSQLite(context: Context) : BaseSQLite(DatabaseOpenHelper.getInstance(context)) {

    fun getAll(): MutableList<MedicationRecyclerItem> {
        val sb = StringBuilder()
        sb.append(" SELECT Medication.id AS medId, ")
        sb.append(" Medication.name AS medName, ")
        sb.append(" Alarm.id AS alarmId, ")
        sb.append(" Alarm.time AS alarmTime ")
        sb.append(" FROM Medication ")
        sb.append(" LEFT JOIN Alarm ON Medication.id = Alarm.medicationId ")
        sb.append(" ORDER BY alarmTime ")

        return getMedicationViewHolderModelList(sb)
    }

    private fun getMedicationViewHolderModelList(query: StringBuilder): MutableList<MedicationRecyclerItem> {
        val cursor = performQuery(query.toString())
        val medications = ArrayList<MedicationRecyclerItem>()

        while (cursor.moveToNext()) {
            val medication = getMedicationFromCursor(cursor)
            medications.add(medication)
        }

        cursor.close()

        return medications
    }

    private fun getMedicationFromCursor(cursor: Cursor): MedicationRecyclerItem {
        val medicationId = getLong(cursor, "medId")
        val name = getString(cursor, "medName")
        val alarmId = getLong(cursor, "alarmId")
        val alarm = getCalendar(cursor, "alarmTime")

        return MedicationRecyclerItem(medicationId, alarmId, name, alarm)
    }

}