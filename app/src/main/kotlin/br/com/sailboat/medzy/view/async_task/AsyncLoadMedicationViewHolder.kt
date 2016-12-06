package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.BaseAsync
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.medzy.persistence.sqlite.MedicationRecyclerItemSQLite
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem

class AsyncLoadMedicationViewHolder private constructor(context: Context, callback: OnSuccessWithResult<MutableList<MedicationRecyclerItem>>) : BaseAsync() {

    private val context = context
    private val callback = callback
    private lateinit var meds: MutableList<MedicationRecyclerItem>

    companion object {

        fun load(context: Context, callback: OnSuccessWithResult<MutableList<MedicationRecyclerItem>>) {
            AsyncLoadMedicationViewHolder(context, callback).execute()
        }

    }

    override fun onDoInBackground() {
        meds = MedicationRecyclerItemSQLite(context).getAll()
    }

    override fun onSuccess() {
       callback.onSuccess(meds)
    }

    override fun onFail(e: Exception?) {
        callback.onFail(e)
    }


}