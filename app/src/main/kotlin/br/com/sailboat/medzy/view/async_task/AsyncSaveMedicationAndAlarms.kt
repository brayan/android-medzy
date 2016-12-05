package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.AsyncSuccess
import br.com.sailboat.canoe.async.callback.OnSuccess
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite


class AsyncSaveMedicationAndAlarms private constructor(context: Context, medication: Medication, alarms: MutableList<Alarm>, callback: OnSuccess)
    : AsyncSuccess(context) {

    private val context = context
    private val medication = medication
    private val alarms = alarms
    private val callback = callback

    companion object {

        fun save(context: Context, medication: Medication, alarms: MutableList<Alarm>, callback: OnSuccess) {
            AsyncSaveMedicationAndAlarms(context, medication, alarms, callback).execute()
        }

    }

    override fun onDoInBackground() {
        cancelAndDeleteAlarms()
        saveOrUpdateMedicine()
        saveAlarms()
    }

    override fun onSuccess() {
        callback.onSuccess()
    }

    private fun saveOrUpdateMedicine() {
        if (isMedicineNew()) {
            saveNewMedicine()
        } else {
            updateMedicine()
        }
    }

    private fun updateMedicine() {
        MedicationSQLite(context).update(medication)
    }

    private fun saveNewMedicine() {
        val id = MedicationSQLite(context).saveAndGetId(medication)
        medication.id = id
    }

    private fun saveAlarms() {
        for (alarm in alarms) {
            alarm.medId = medication.id
            alarm.id = AlarmSQLite(context).saveAndGetId(alarm)
            AlarmManagerHelper.setAlarm(context, alarm.id, alarm.time.timeInMillis)
        }
    }

    private fun cancelAndDeleteAlarms() {

        if (isMedicineNotNew()) {
            val alarmSQLite = AlarmSQLite(context)
            var alarmss = alarmSQLite.getAlarmsByMedicine(medication.id)

            for (alarm in alarmss) {
                AlarmManagerHelper.cancelAlarm(context, alarm.id)
                alarmSQLite.delete(alarm.id)
            }

        }

    }

    fun isMedicineNew(): Boolean {
        return medication.id == -1L
    }

    fun isMedicineNotNew(): Boolean {
        return !isMedicineNew()
    }

}