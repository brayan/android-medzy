package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.SimpleAsyncTask
import br.com.sailboat.elseapp.common.helper.AlarmManagerHelper
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite

class AsyncDeleteMedicine private constructor(context: Context, medicineId: Long, callback: SimpleAsyncTask.Callback)
    : SimpleAsyncTask(context.applicationContext, callback) {

    private val medicineId = medicineId


    companion object {

        fun delete(context: Context, medicineId: Long, callback: SimpleAsyncTask.Callback) {
            AsyncDeleteMedicine(context, medicineId, callback).execute()
        }
    }


    override fun onDoInBackground() {
        cancelAlarms()
        deleteMedicine()
        deleteAlarms()
    }

    private fun cancelAlarms() {
        val alarms = AlarmSQLite(context).getAlarmsByMedicine(medicineId)
        AlarmManagerHelper.cancelAlarms(context, alarms)
    }

    private fun deleteMedicine() {
        MedicineSQLite(context).delete(medicineId)
    }

    private fun deleteAlarms() {
        AlarmSQLite(context).deleteAllByMedicine(medicineId)
    }

}
