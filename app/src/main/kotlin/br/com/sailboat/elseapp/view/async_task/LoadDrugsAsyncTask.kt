package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.persistence.sqlite.DrugSQLite
import java.util.*

class LoadDrugsAsyncTask(context: Context, callback: LoadDrugsAsyncTask.Callback) : BaseAsyncTask() {

    private val callback: LoadDrugsAsyncTask.Callback
    private val context: Context
    private var list: MutableList<Drug>

    init {
        this.context = context.applicationContext
        this.callback = callback
        this.list = ArrayList<Drug>()
    }

    override fun onDoInBackground() {
        list = DrugSQLite(context).all
    }

    override fun onSuccess() {
       callback.onSucess(list)
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }


    interface Callback {
        fun onSucess(list: MutableList<Drug>)
        fun onFail(e: Exception)
    }

}