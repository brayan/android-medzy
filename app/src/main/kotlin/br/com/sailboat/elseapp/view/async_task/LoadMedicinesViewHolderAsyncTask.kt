package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.persistence.sqlite.MedicineViewHolderSQLite
import java.util.*

class LoadMedicinesViewHolderAsyncTask(context: Context, callback: Callback<MutableList<MedicineVHModel>>) : BaseAsyncTask(context) {

    private val callback: Callback<MutableList<MedicineVHModel>>
    private var list: MutableList<MedicineVHModel>

    init {
        this.callback = callback
        this.list = ArrayList<MedicineVHModel>()
    }

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