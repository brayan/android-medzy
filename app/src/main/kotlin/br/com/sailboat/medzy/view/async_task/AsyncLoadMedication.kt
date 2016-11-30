package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.AsyncSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite

class AsyncLoadMedication private constructor(context: Context, medId: Long, callback: OnSuccessWithResult<Medication>) : AsyncSuccess(context) {

    private val context = context
    private val callback = callback
    private val medId = medId
    private lateinit var medication: Medication

    companion object {

        fun load(context: Context, medicineId: Long, callback: OnSuccessWithResult<Medication>) {
            AsyncLoadMedication(context, medicineId, callback).execute()
        }

    }

    override fun onDoInBackground() {
        medication = MedicationSQLite(context).getMedicationById(medId)
    }

    override fun onSuccess() {
        callback.onSuccess(medication)
    }

}