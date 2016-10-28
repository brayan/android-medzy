package br.com.sailboat.elseapp.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.sailboat.elseapp.common.helper.CreateTablesHelper


class DatabaseOpenHelper private constructor(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var context: Context

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

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "database.db"
        private var instance: DatabaseOpenHelper? = null

        fun getInstance(context: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(context.applicationContext)
            }
            return instance!!
        }
    }

}
