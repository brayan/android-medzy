package br.com.sailboat.medzy.persistence

import android.database.sqlite.SQLiteDatabase
import br.com.sailboat.canoe.sqlite.TablesCreator
import java.util.*

class TablesCreatorHelper(database: SQLiteDatabase) : TablesCreator(database) {

    override fun getSQLCreateTables(): MutableList<String> {
        val createTables = ArrayList<String>()
        createTables.add(getQueryCreateTableMedicine())
        createTables.add(getQueryCreateTableAlarm())

        return createTables
    }

    fun getQueryCreateTableMedicine(): String {
        val sb = StringBuilder()
        sb.append(" CREATE TABLE Medicine ( ")
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ")
        sb.append(" name TEXT NOT NULL ")
        sb.append(" ); ")

        return sb.toString()
    }

    fun getQueryCreateTableAlarm(): String {
        val sb = StringBuilder()
        sb.append(" CREATE TABLE Alarm ( ")
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ")
        sb.append(" medicineId INTEGER, ")
        sb.append(" time TEXT NOT NULL, ")
        sb.append(" repeatType INTEGER, ")
        sb.append(" FOREIGN KEY(medicineId) REFERENCES Medicine(id) ")
        sb.append(" ); ")

        return sb.toString()
    }

}
