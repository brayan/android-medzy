package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.AsyncSuccess
import br.com.sailboat.canoe.async.callback.OnSuccess
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite

class AsyncDeleteMedication private constructor(context: Context, medId: Long, callback: OnSuccess) : AsyncSuccess(context) {

    private val callback = callback
    private val context = context
    private val medId = medId

    companion object {

        fun delete(context: Context, medicineId: Long, callback: OnSuccess) {
            AsyncDeleteMedication(context, medicineId, callback).execute()
        }
    }

    override fun onDoInBackground() {
        cancelAlarms()
        deleteMedication()
        deleteAlarms()
    }

    override fun onSuccess() {
        callback.onSuccess()
    }

    private fun cancelAlarms() {
        val alarms = AlarmSQLite(context).getAlarmsByMedicine(medId)
        AlarmManagerHelper.cancelAlarms(context, alarms)
    }

    private fun deleteMedication() {
        MedicationSQLite(context).delete(medId)
    }

    private fun deleteAlarms() {
        AlarmSQLite(context).deleteAllByMedicine(medId)
    }

}
