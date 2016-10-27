package br.com.sailboat.elseapp.persistence.sqlite

import android.content.Context
import android.database.Cursor
import br.com.sailboat.elseapp.base.BaseSQLite
import br.com.sailboat.elseapp.model.Drug
import java.util.*


class DrugSQLite(context: Context) : BaseSQLite(context) {

    override val queryCreateTable: String
        get() {
            val sb = StringBuilder()
            sb.append(" CREATE TABLE Drug ( ")
            sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ")
            sb.append(" name TEXT NOT NULL ")
            sb.append(" ); ")

            return sb.toString()
        }

    val all: MutableList<Drug>
        @Throws(Exception::class)
        get() {
            val sb = StringBuilder()
            sb.append(" SELECT Drug.* FROM Drug ")

            return getDrugList(sb)
        }

    @Throws(Exception::class)
    fun saveAndGetId(drug: Drug): Long {
        val sb = StringBuilder()
        sb.append(" INSERT INTO Drug ")
        sb.append(" (name) ")
        sb.append(" VALUES (?); ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, drug.name)

        val id = executeInsert(statement)

        return id
    }

    @Throws(Exception::class)
    fun update(drug: Drug) {
        val sb = StringBuilder()
        sb.append(" UPDATE Drug SET ")
        sb.append(" name = ? ")
        sb.append(" WHERE id = ? ")

        val statement = compileStatement(sb.toString())
        statement.bindString(1, drug.name)
        statement.bindLong(2, drug.id)

        executeUpdateOrDelete(statement)
    }

    @Throws(Exception::class)
    fun delete(drugId: Long) {
        val query = "DELETE FROM Drug WHERE Drug.id = ?"
        val statement = compileStatement(query)
        statement.bindLong(1, drugId)

        executeUpdateOrDelete(statement)
    }

    private fun getDrugList(query: StringBuilder): MutableList<Drug> {
        val cursor = performQuery(query.toString())
        val drugs = ArrayList<Drug>()

        while (cursor.moveToNext()) {
            val drug = getDrugFromCursor(cursor)
            drugs.add(drug)
        }

        cursor.close()

        return drugs
    }

    private fun getDrugFromCursor(cursor: Cursor): Drug {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
        val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))

        val drug = Drug(id, name)

        return drug
    }
}
