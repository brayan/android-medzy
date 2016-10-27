package br.com.sailboat.elseapp.view.async_tasks

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
//        list.add(Drug(1, "Drug 1"))
//        list.add(Drug(2, "Drug 2"))
//        list.add(Drug(3, "Drug 3"))
//        list.add(Drug(4, "Drug 4"))
//        list.add(Drug(5, "Drug 5"))
//        list.add(Drug(6, "Drug 6"))

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