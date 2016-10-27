package br.com.sailboat.elseapp.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.sailboat.elseapp.base.BaseSQLite
import br.com.sailboat.elseapp.persistence.sqlite.DrugSQLite
import java.util.*


class CreateTablesHelper private constructor(context: Context, database: SQLiteDatabase) {

    var context: Context
    var database: SQLiteDatabase
    lateinit var tableList: MutableList<BaseSQLite>

    companion object {

        fun createTables(context: Context, database: SQLiteDatabase) {
            CreateTablesHelper(context, database).createTables()
        }

    }

    init {
        this.context = context.applicationContext
        this.database = database
        initTableList()
    }

    private fun createTables() {
        for (table in tableList) {
            database.execSQL(table.queryCreateTable)
        }
    }

    private fun initTableList() {
        tableList = ArrayList<BaseSQLite>()
        tableList.add(DrugSQLite(context))
    }


}
