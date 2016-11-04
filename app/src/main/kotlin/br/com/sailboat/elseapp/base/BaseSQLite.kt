package br.com.sailboat.elseapp.base

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement

import br.com.sailboat.elseapp.persistence.DatabaseOpenHelper


abstract class BaseSQLite(context: Context) {

    private val databaseOpenHelper: DatabaseOpenHelper

    val context: Context

    init {
        this.context = context.applicationContext
        this.databaseOpenHelper = DatabaseOpenHelper.getInstance(context)
    }

    abstract fun getQueryCreateTable(): String

    protected fun executeInsert(statement: SQLiteStatement): Long {
        try {
            writableDatabase.beginTransactionNonExclusive()
            val id = statement.executeInsert()
            statement.clearBindings()
            writableDatabase.setTransactionSuccessful()

            return id
        } finally {
            writableDatabase.endTransaction()
        }
    }

    protected fun executeUpdateOrDelete(statement: SQLiteStatement) {
        try {
            writableDatabase.beginTransactionNonExclusive()
            statement.executeUpdateDelete()
            statement.clearBindings()
            writableDatabase.setTransactionSuccessful()
        } finally {
            writableDatabase.endTransaction()
        }
    }

    protected fun performQuery(query: String): Cursor {
        return readableDatabase.rawQuery(query, null)
    }

    protected fun compileStatement(statement: String): SQLiteStatement {
        return writableDatabase.compileStatement(statement)
    }

    private val readableDatabase: SQLiteDatabase get() = databaseOpenHelper.getReadableDatabase()
    private val writableDatabase: SQLiteDatabase get() = databaseOpenHelper.getWritableDatabase()

}
