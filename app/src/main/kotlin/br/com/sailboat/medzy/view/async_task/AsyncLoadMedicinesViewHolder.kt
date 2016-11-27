package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.AsyncSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessResult
import br.com.sailboat.medzy.persistence.sqlite.MedicineViewHolderSQLite
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel

class AsyncLoadMedicinesViewHolder private constructor(context: Context, callback: OnSuccessResult<MutableList<MedicineVHModel>>) : AsyncSuccess(context) {

    private val context = context
    private val callback = callback
    private lateinit var meds: MutableList<MedicineVHModel>

    companion object {

        fun load(context: Context, callback: OnSuccessResult<MutableList<MedicineVHModel>>) {
            AsyncLoadMedicinesViewHolder(context, callback).execute()
        }

    }

    override fun onDoInBackground() {
        meds = MedicineViewHolderSQLite(context).getAll()
    }

    override fun onSuccess() {
       callback.onSuccess(meds)
    }


}