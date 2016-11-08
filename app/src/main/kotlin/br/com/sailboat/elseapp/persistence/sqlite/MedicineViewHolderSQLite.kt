package br.com.sailboat.elseapp.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.elseapp.base.BaseSQLite
import br.com.sailboat.elseapp.common.helper.AlarmHelper
import br.com.sailboat.elseapp.model.MedicineVHModel
import java.util.*

class MedicineViewHolderSQLite(context: Context) : BaseSQLite(context) {

    fun getAll(): MutableList<MedicineVHModel> {
        val sb = StringBuilder()
        sb.append(" SELECT Medicine.id AS medicineId, ")
        sb.append(" Medicine.name AS medicineName, ")
        sb.append(" Alarm.time AS alarmTime ")
        sb.append(" FROM Medicine ")
        sb.append(" LEFT JOIN Alarm ON Medicine.id = Alarm.medicineId ")
        sb.append(" ORDER BY Alarm.time DESC")

        return getMedicineViewHolderModelList(sb)
    }

    private fun getMedicineViewHolderModelList(query: StringBuilder): MutableList<MedicineVHModel> {
        val cursor = performQuery(query.toString())
        val medicines = ArrayList<MedicineVHModel>()

        while (cursor.moveToNext()) {
            val medicine = getMedicineFromCursor(cursor)
            medicines.add(medicine)
        }

        cursor.close()

        return medicines
    }

    private fun getMedicineFromCursor(cursor: Cursor): MedicineVHModel {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("medicineId"))
        val name = cursor.getString(cursor.getColumnIndexOrThrow("medicineName"))
        val alarm = cursor.getString(cursor.getColumnIndexOrThrow("alarmTime"))


        return MedicineVHModel(id, name, formatTimeFromString(alarm))
    }

    private fun formatTimeFromString(date: String) : Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = AlarmHelper.formatDateTimeFromDatabaseFormat(date)

        return calendar
    }

}