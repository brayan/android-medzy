package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import java.util.*

class AsyncLoadAlarms private constructor(context: Context, medicineId: Long, callback: BaseAsyncTask.Callback<MutableList<Alarm>>)
    : BaseAsyncTask(context.applicationContext) {

    private val callback = callback
    private val medicineId = medicineId
    private var list: MutableList<Alarm> = ArrayList<Alarm>()

    companion object {

        fun load(context: Context, medicineId: Long, callback: BaseAsyncTask.Callback<MutableList<Alarm>>) {
            AsyncLoadAlarms(context, medicineId, callback).execute()
        }

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