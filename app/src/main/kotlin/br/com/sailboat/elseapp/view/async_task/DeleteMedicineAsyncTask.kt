package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite

class DeleteMedicineAsyncTask(context: Context, medicine: Medicine, callback: DeleteMedicineAsyncTask.Callback) : BaseAsyncTask() {

    private val medicine: Medicine
    private val context: Context
    private val callback: Callback

    init {
        this.context = context.applicationContext
        this.medicine = medicine
        this.callback = callback
    }

    override fun onDoInBackground() {
        MedicineSQLite(context).delete(medicine.id!!)
        AlarmSQLite(context).deleteAllByMedicine(medicine.id!!)
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
