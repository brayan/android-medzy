package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.BaseAsyncTask
import br.com.sailboat.canoe.async.callback.ResultCallback
import br.com.sailboat.medzy.persistence.sqlite.MedicineViewHolderSQLite
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel

class AsyncLoadMedicinesViewHolder private constructor(context: Context, callback: ResultCallback<MutableList<MedicineVHModel>>) : BaseAsyncTask() {

    private val context = context
    private val callback = callback
    private lateinit var meds: MutableList<MedicineVHModel>

    companion object {

        fun load(context: Context, callback: ResultCallback<MutableList<MedicineVHModel>>) {
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