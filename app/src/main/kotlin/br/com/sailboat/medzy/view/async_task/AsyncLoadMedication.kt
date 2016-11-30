package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.AsyncSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.medzy.model.Medicine
import br.com.sailboat.medzy.persistence.sqlite.MedicineSQLite

class AsyncLoadMedication private constructor(context: Context, medId: Long, callback: OnSuccessWithResult<Medicine>) : AsyncSuccess(context) {

    private val context = context
    private val callback = callback
    private val medicineId = medId
    private lateinit var medication: Medicine

    companion object {

        fun load(context: Context, medicineId: Long, callback: OnSuccessWithResult<Medicine>) {
            AsyncLoadMedication(context, medicineId, callback).execute()
        }

    }

    override fun onDoInBackground() {
        medication = MedicineSQLite(context).getMedicineById(medicineId)
    }

    override fun onSuccess() {
        callback.onSuccess(medication)
    }

}