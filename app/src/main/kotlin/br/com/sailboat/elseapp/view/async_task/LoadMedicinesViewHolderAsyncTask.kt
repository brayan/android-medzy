package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.persistence.sqlite.MedicineViewHolderSQLite
import java.util.*

class LoadMedicinesViewHolderAsyncTask(context: Context, callback: LoadMedicinesViewHolderAsyncTask.Callback) : BaseAsyncTask() {

    private val callback: LoadMedicinesViewHolderAsyncTask.Callback
    private val context: Context
    private var list: MutableList<MedicineVHModel>

    init {
        this.context = context.applicationContext
        this.callback = callback
        this.list = ArrayList<MedicineVHModel>()
    }

    override fun onDoInBackground() {
        list = MedicineViewHolderSQLite(context).getAll()
    }

    override fun onSuccess() {
       callback.onSucess(list)
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }


    interface Callback {
        fun onSucess(list: MutableList<MedicineVHModel>)
        fun onFail(e: Exception)
    }

}