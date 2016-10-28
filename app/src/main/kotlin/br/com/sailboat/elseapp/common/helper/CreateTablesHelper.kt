package br.com.sailboat.elseapp.common.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.sailboat.elseapp.base.BaseSQLite
import br.com.sailboat.elseapp.persistence.sqlite.DrugSQLite
import java.util.*


class CreateTablesHelper private constructor(context: Context, database: SQLiteDatabase) {

    private var context: Context
    private var database: SQLiteDatabase
    private lateinit var tableList: MutableList<BaseSQLite>

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
