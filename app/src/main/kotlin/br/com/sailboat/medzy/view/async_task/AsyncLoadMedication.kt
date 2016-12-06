package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.BaseAsync
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite

class AsyncLoadMedication private constructor(context: Context, medId: Long, callback: OnSuccessWithResult<Medication>) : BaseAsync() {

    private val context = context
    private val callback = callback
    private val medId = medId
    private lateinit var medication: Medication

    companion object {

        fun load(context: Context, medId: Long, callback: OnSuccessWithResult<Medication>) {
            AsyncLoadMedication(context, medId, callback).execute()
        }

    }

    override fun onDoInBackground() {
        medication = MedicationSQLite(context).getMedicationById(medId)
    }

    override fun onSuccess() {
        callback.onSuccess(medication)
    }

    override fun onFail(e: Exception?) {
        callback.onFail(e)
    }

}