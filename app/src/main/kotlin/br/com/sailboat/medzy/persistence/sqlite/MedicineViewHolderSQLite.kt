package br.com.sailboat.medzy.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.canoe.base.BaseSQLite
import br.com.sailboat.medzy.persistence.DatabaseOpenHelper
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel
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
        val medicineId = getLong(cursor, "medicineId")
        val name = getString(cursor, "medicineName")
        val alarmId = getLong(cursor, "alarmId")
        val alarm = getCalendar(cursor, "alarmTime")

        return MedicineVHModel(medicineId, alarmId, name, alarm)
    }

}