package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.common.helper.AlarmManagerHelper
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite


class SaveMedicineAndAlarmsAsyncTask(context: Context, medicine: Medicine, alarms: MutableList<Alarm>, callback: SaveMedicineAndAlarmsAsyncTask.Callback) : BaseAsyncTask() {

    private val medicine: Medicine
    private val alarms: MutableList<Alarm>
    private val context: Context
    private val callback: Callback

    private val isNewMedicine: Boolean get() = medicine.id == -1L

    init {
        this.context = context.applicationContext
        this.medicine = medicine
        this.alarms = alarms
        this.callback = callback
    }

    override fun onPreExecute() {

        for (alarm in alarms) {
            AlarmManagerHelper.cancelAlarm(context, alarm)
        }

    }

    override fun onDoInBackground() {

        if (isNewMedicine) {
            saveNewMedicine()
        } else {
            updateMedicine()
        }

        saveAlarms()

    }

    override fun onSuccess() {
        for (alarm in alarms) {
            AlarmManagerHelper.setAlarm(context, alarm.time.timeInMillis, alarm.id)
        }

        callback.onSuccess()
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
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


    interface Callback {
        fun onSuccess()
        fun onFail(e: Exception)
    }

}
