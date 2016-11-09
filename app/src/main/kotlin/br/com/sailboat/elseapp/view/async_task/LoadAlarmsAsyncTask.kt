package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import java.util.*

class LoadAlarmsAsyncTask(context: Context, medicineId: Long, callback: Callback<MutableList<Alarm>>) : BaseAsyncTask(context.applicationContext) {

    private val callback: Callback<MutableList<Alarm>>
    private val medicineId: Long
    private var list: MutableList<Alarm>

    init {
        this.callback = callback
        this.medicineId = medicineId
        this.list = ArrayList<Alarm>()
    }

    override fun onDoInBackground() {
        list = AlarmSQLite(context).getAlarmsByMedicine(medicineId)
    }

    override fun onSuccess() {
        callback.onSuccess(list)
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }

}