package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.persistence.sqlite.MedicineViewHolderSQLite

class AsyncLoadMedicinesViewHolder private constructor(context: Context, callback: BaseAsyncTask.Callback<MutableList<MedicineVHModel>>)
    : BaseAsyncTask(context) {

    private val callback = callback
    private lateinit var meds: MutableList<MedicineVHModel>

    companion object {

        fun load(context: Context, callback: BaseAsyncTask.Callback<MutableList<MedicineVHModel>>) {
            AsyncLoadMedicinesViewHolder(context, callback).execute()
        }

    }


    override fun onDoInBackground() {
        meds = MedicineViewHolderSQLite(context).getAll()
    }

    override fun onSuccess() {
       callback.onSuccess(meds)
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }

}