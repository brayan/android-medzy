package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.AsyncSuccess
import br.com.sailboat.canoe.async.callback.OnSuccess
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicineSQLite

class AsyncDeleteMedicine private constructor(context: Context, medId: Long, callback: OnSuccess) : AsyncSuccess(context) {

    private val callback = callback
    private val context = context
    private val medicineId = medId

    companion object {

        fun delete(context: Context, medicineId: Long, callback: OnSuccess) {
            AsyncDeleteMedicine(context, medicineId, callback).execute()
        }
    }

    override fun onDoInBackground() {
        cancelAlarms()
        deleteMedicine()
        deleteAlarms()
    }

    override fun onSuccess() {
        callback.onSuccess()
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
