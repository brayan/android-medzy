package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.SimpleAsyncTask
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite

class DeleteMedicineAsyncTask(context: Context, medicineId: Long, callback: Callback) : SimpleAsyncTask(context.applicationContext, callback) {

    private val medicineId: Long

    init {
        this.medicineId = medicineId
    }

    override fun onDoInBackground() {
        MedicineSQLite(context).delete(medicineId!!)
        AlarmSQLite(context).deleteAllByMedicine(medicineId!!)
    }

}
