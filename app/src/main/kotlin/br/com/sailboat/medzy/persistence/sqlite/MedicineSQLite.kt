package br.com.sailboat.medzy.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.canoe.base.BaseSQLite
import br.com.sailboat.medzy.model.Medicine
import br.com.sailboat.medzy.persistence.DatabaseOpenHelper


class MedicineSQLite(context: Context) : BaseSQLite(DatabaseOpenHelper.getInstance(context)) {

    fun getMedicineById(medicineId: Long): Medicine? {
        val sb = StringBuilder()
        sb.append(" SELECT * FROM Medicine ")
        sb.append(" WHERE Medicine.id = " + medicineId)

        val cursor = performQuery(sb.toString())
        var medicine: Medicine? = null

        if (cursor.moveToNext()) {
            medicine = getMedicineFromCursor(cursor)
        }

        cursor.close()

        return medicine
    }

    fun saveAndGetId(medicine: Medicine): Long {
        val sb = StringBuilder()
        sb.append(" INSERT INTO Medicine ")
        sb.append(" (name) ")
        sb.append(" VALUES (?); ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, medicine.name)

        val id = insert(statement)

        return id
    }

    fun update(medicine: Medicine) {
        val sb = StringBuilder()
        sb.append(" UPDATE Medicine SET ")
        sb.append(" name = ? ")
        sb.append(" WHERE id = ? ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, medicine.name)
        statement.bindLong(2, medicine.id)

        update(statement)
    }

    fun delete(medicineId: Long) {
        val query = "DELETE FROM Medicine WHERE Medicine.id = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, medicineId)

        delete(statement)
    }

    private fun getMedicineFromCursor(cursor: Cursor): Medicine {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))

        return Medicine(id, name)
    }

}
