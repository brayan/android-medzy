package br.com.sailboat.elseapp.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.sailboat.elseapp.helper.CreateTablesHelper


class DatabaseOpenHelper private constructor(context: Context) :
        SQLiteOpenHelper(context, DatabaseOpenHelper.NAME, null, DatabaseOpenHelper.VERSION) {

    var context: Context

    init {
        this.context = context
    }

    override fun onCreate(database: SQLiteDatabase) {
        CreateTablesHelper.createTables(context, database)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO
    }

    companion object {

        private val VERSION = 1
        private val NAME = "database.db"
        private var instance: DatabaseOpenHelper? = null

        fun getInstance(context: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(context.applicationContext)
            }
            return instance!!
        }
    }
}
