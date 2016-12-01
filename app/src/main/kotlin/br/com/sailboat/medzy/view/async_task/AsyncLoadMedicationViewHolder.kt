package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.AsyncSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.medzy.persistence.sqlite.MedicationViewHolderSQLite
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem

class AsyncLoadMedicationViewHolder private constructor(context: Context, callback: OnSuccessWithResult<MutableList<MedicationRecyclerItem>>) : AsyncSuccess(context) {

    private val context = context
    private val callback = callback
    private lateinit var meds: MutableList<MedicationRecyclerItem>

    companion object {

        fun load(context: Context, callback: OnSuccessWithResult<MutableList<MedicationRecyclerItem>>) {
            AsyncLoadMedicationViewHolder(context, callback).execute()
        }

    }

    override fun onDoInBackground() {
        meds = MedicationViewHolderSQLite(context).getAll()
    }

    override fun onSuccess() {
       callback.onSuccess(meds)
    }


}