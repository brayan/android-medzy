package br.com.sailboat.elseapp.common.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.sailboat.elseapp.base.BaseSQLite
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite
import java.util.*


class CreateTablesHelper(context: Context, database: SQLiteDatabase) {

    private val context = context.applicationContext
    private val database = database
    private val tables = initTables()

    fun createTables() {
        for (table in tables) {
            database.execSQL(table.getQueryCreateTable())
        }
    }

    private fun initTables(): MutableList<BaseSQLite> {
        val list = ArrayList<BaseSQLite>()
        list.add(MedicineSQLite(context))
        list.add(AlarmSQLite(context))

        return list
    }

}
