package br.com.sailboat.medzy.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseOpenHelper private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase) {
        TablesCreatorHelper(database).createTables()
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) { }

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
