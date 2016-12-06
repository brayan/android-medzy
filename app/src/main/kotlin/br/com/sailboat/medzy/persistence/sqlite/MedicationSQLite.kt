package br.com.sailboat.medzy.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.canoe.base.BaseSQLite
import br.com.sailboat.canoe.exception.EntityNotFoundException
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.persistence.DatabaseOpenHelper


class MedicationSQLite(context: Context) : BaseSQLite(DatabaseOpenHelper.getInstance(context)) {

    fun getMedicationById(medId: Long): Medication {
        val sb = StringBuilder()
        sb.append(" SELECT * FROM Medication ")
        sb.append(" WHERE Medication.id = " + medId)

        val cursor = performQuery(sb.toString())

        if (cursor.moveToNext()) {
            val med = getMedicationFromCursor(cursor)
            cursor.close()
            return med
        }

        cursor.close()

        throw EntityNotFoundException("Medication with id "+ medId +" not found")
    }

    fun saveAndGetId(medication: Medication): Long {
        val sb = StringBuilder()
        sb.append(" INSERT INTO Medication ")
        sb.append(" (name, totalAmount) ")
        sb.append(" VALUES (?, ?); ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, medication.name)
        statement.bindDouble(2, medication.totalAmount)

        val id = insert(statement)

        return id
    }

    fun update(medication: Medication) {
        val sb = StringBuilder()
        sb.append(" UPDATE Medication SET ")
        sb.append(" name = ?, totalAmount = ? ")
        sb.append(" WHERE id = ? ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, medication.name)
        statement.bindDouble(2, medication.totalAmount)
        statement.bindLong(3, medication.id)

        update(statement)
    }

    fun delete(medId: Long) {
        val query = "DELETE FROM Medication WHERE Medication.id = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, medId)

        delete(statement)
    }

    private fun getMedicationFromCursor(cursor: Cursor): Medication {
        val id = getLong(cursor, "id")
        val name = getString(cursor, "name")
        val totalAmount = getDouble(cursor, "totalAmount")

        return Medication(id, name, totalAmount)
    }

}
