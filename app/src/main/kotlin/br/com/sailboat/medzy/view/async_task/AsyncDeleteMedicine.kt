package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.SimpleAsyncTask
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicineSQLite

class AsyncDeleteMedicine private constructor(context: Context, medicineId: Long, callback: SimpleAsyncTask.Callback)
    : SimpleAsyncTask(callback) {

    private val context = context
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
