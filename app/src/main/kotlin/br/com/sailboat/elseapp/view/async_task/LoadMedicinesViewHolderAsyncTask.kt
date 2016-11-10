package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.persistence.sqlite.MedicineViewHolderSQLite

class LoadMedicinesViewHolderAsyncTask(context: Context, callback: BaseAsyncTask.Callback<MutableList<MedicineVHModel>>)
    : BaseAsyncTask(context) {

    private val callback = callback
    private lateinit var list: MutableList<MedicineVHModel>

    override fun onDoInBackground() {
        list = MedicineViewHolderSQLite(context).getAll()
    }

    override fun onSuccess() {
       callback.onSuccess(list)
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }

}