package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite

class DeleteMedicineAsyncTask(context: Context, medicineId: Long, callback: DeleteMedicineAsyncTask.Callback) : BaseAsyncTask() {

    private val medicineId: Long
    private val context: Context
    private val callback: Callback

    init {
        this.context = context.applicationContext
        this.medicineId = medicineId
        this.callback = callback
    }

    override fun onDoInBackground() {
        MedicineSQLite(context).delete(medicineId!!)
        AlarmSQLite(context).deleteAllByMedicine(medicineId!!)
    }

    override fun onSuccess() {
        callback.onSuccess()
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }


    interface Callback {
        fun onSuccess()
        fun onFail(e: Exception)
    }

}
