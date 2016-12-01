package br.com.sailboat.medzy.persistence

import android.database.sqlite.SQLiteDatabase
import br.com.sailboat.canoe.sqlite.TablesCreator
import java.util.*

class TablesCreatorHelper(database: SQLiteDatabase) : TablesCreator(database) {

    override fun getSQLCreateTables(): MutableList<String> {
        val createTables = ArrayList<String>()
        createTables.add(getSqlCreateTableMedicine())
        createTables.add(getSqlCreateTableAlarm())

        return createTables
    }

    private fun getSqlCreateTableMedicine(): String {
        val sb = StringBuilder()
        sb.append(" CREATE TABLE Medication ( ")
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ")
        sb.append(" name TEXT NOT NULL ")
        sb.append(" ); ")

        return sb.toString()
    }

    private fun getSqlCreateTableAlarm(): String {
        val sb = StringBuilder()
        sb.append(" CREATE TABLE Alarm ( ")
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ")
        sb.append(" medicationId INTEGER, ")
        sb.append(" time TEXT NOT NULL, ")
        sb.append(" repeatType INTEGER, ")
        sb.append(" FOREIGN KEY(medicationId) REFERENCES Medication(id) ")
        sb.append(" ); ")

        return sb.toString()
    }

}
