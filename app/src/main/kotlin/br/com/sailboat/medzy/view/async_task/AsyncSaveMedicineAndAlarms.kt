package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.SimpleAsyncTask
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medicine
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicineSQLite


class AsyncSaveMedicineAndAlarms private constructor(context: Context, medicine: Medicine, alarms: MutableList<Alarm>, callback: SimpleAsyncTask.Callback)
    : SimpleAsyncTask(callback) {

    private val context = context
    private val medicine = medicine
    private val alarms = alarms

    private val isNewMedicine: Boolean get() = medicine.id == -1L

    companion object {

        fun save(context: Context, medicine: Medicine, alarms: MutableList<Alarm>, callback: SimpleAsyncTask.Callback) {
            AsyncSaveMedicineAndAlarms(context, medicine, alarms, callback).execute()
        }

    }


    override fun onDoInBackground() {
        cancelAlarms()
        saveOrUpdateMedicine()
        saveAlarms()
        setAlarms()
    }

    private fun saveOrUpdateMedicine() {
        if (isNewMedicine) {
            saveNewMedicine()
        } else {
            updateMedicine()
        }
    }

    private fun updateMedicine() {
        MedicineSQLite(context).update(medicine)
    }

    private fun saveNewMedicine() {
        val id = MedicineSQLite(context).saveAndGetId(medicine)
        medicine.id = id
    }

    private fun saveAlarms() {
        val alarmSQLite = AlarmSQLite(context)
        alarmSQLite.deleteAllByMedicine(medicine.id)

        for (alarm in alarms) {
            alarm.medicineId = medicine.id
            alarm.id = alarmSQLite.saveAndGetId(alarm)
        }
    }

    private fun setAlarms() {
        for (alarm in alarms) {
            AlarmManagerHelper.setAlarm(context, alarm.id, alarm.time.timeInMillis)
        }
    }

    private fun cancelAlarms() {
        for (alarm in alarms) {
            AlarmManagerHelper.cancelAlarm(context, alarm.id)
        }
    }

}
