package br.com.sailboat.medzy.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.canoe.base.BaseSQLite
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel
import br.com.sailboat.medzy.persistence.DatabaseOpenHelper
import java.util.*

class MedicineViewHolderSQLite(context: Context) : BaseSQLite(DatabaseOpenHelper.getInstance(context)) {

    fun getAll(): MutableList<MedicineVHModel> {
        val sb = StringBuilder()
        sb.append(" SELECT Medicine.id AS medicineId, ")
        sb.append(" Medicine.name AS medicineName, ")
        sb.append(" Alarm.id AS alarmId, ")
        sb.append(" Alarm.time AS alarmTime ")
        sb.append(" FROM Medicine ")
        sb.append(" LEFT JOIN Alarm ON Medicine.id = Alarm.medicineId ")
        sb.append(" ORDER BY alarmTime ")

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
        val medicineId = cursor.getLong(cursor.getColumnIndexOrThrow("medicineId"))
        val name = cursor.getString(cursor.getColumnIndexOrThrow("medicineName"))
        val alarmId = cursor.getLong(cursor.getColumnIndexOrThrow("alarmId"))
        val alarm = cursor.getString(cursor.getColumnIndexOrThrow("alarmTime"))


        return MedicineVHModel(medicineId, alarmId, name, formatTimeFromString(alarm))
    }

    private fun formatTimeFromString(date: String) : Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = DateHelper.formatDateTimeFromDatabaseFormat(date)

        return calendar
    }

}