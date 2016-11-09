package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.SimpleAsyncTask
import br.com.sailboat.elseapp.common.helper.AlarmManagerHelper
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite


class SaveMedicineAndAlarmsAsyncTask(context: Context, medicine: Medicine, alarms: MutableList<Alarm>, callback: Callback) : SimpleAsyncTask(context.applicationContext, callback) {

    private val medicine: Medicine
    private val alarms: MutableList<Alarm>

    private val isNewMedicine: Boolean get() = medicine.id == -1L

    init {
        this.medicine = medicine
        this.alarms = alarms
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
