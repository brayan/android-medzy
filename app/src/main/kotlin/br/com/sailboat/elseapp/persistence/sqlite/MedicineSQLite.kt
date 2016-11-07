package br.com.sailboat.elseapp.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.elseapp.base.BaseSQLite
import br.com.sailboat.elseapp.model.Medicine
import java.util.*


class MedicineSQLite(context: Context) : BaseSQLite(context) {

    override fun getQueryCreateTable(): String {
        val sb = StringBuilder()
        sb.append(" CREATE TABLE Medicine ( ")
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ")
        sb.append(" name TEXT NOT NULL ")
        sb.append(" ); ")

        return sb.toString()
    }

    val all: MutableList<Medicine>
        @Throws(Exception::class)
        get() {
            val sb = StringBuilder()
            sb.append(" SELECT Medicine.* FROM Medicine ")

            return getMedicineList(sb)
        }

    fun saveAndGetId(medicine: Medicine): Long {
        val sb = StringBuilder()
        sb.append(" INSERT INTO Medicine ")
        sb.append(" (name) ")
        sb.append(" VALUES (?); ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, medicine.name)

        val id = executeInsert(statement)

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

        executeUpdateOrDelete(statement)
    }

    fun delete(medicineId: Long) {
        val query = "DELETE FROM Medicine WHERE Medicine.id = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, medicineId)

        executeUpdateOrDelete(statement)
    }

    private fun getMedicineList(query: StringBuilder): MutableList<Medicine> {
        val cursor = performQuery(query.toString())
        val medicines = ArrayList<Medicine>()

        while (cursor.moveToNext()) {
            val medicine = getMedicineFromCursor(cursor)
            medicines.add(medicine)
        }

        cursor.close()

        return medicines
    }

    private fun getMedicineFromCursor(cursor: Cursor): Medicine {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))

        return Medicine(id, name)
    }

}
