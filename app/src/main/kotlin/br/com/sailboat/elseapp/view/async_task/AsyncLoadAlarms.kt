package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.BaseAsyncTask
import br.com.sailboat.canoe.async.callback.ResultCallback
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.persistence.sqlite.AlarmSQLite
import java.util.*

class AsyncLoadAlarms private constructor(context: Context, medicineId: Long, callback: ResultCallback<MutableList<Alarm>>) : BaseAsyncTask() {

    private val context = context
    private val callback = callback
    private val medicineId = medicineId
    private var list: MutableList<Alarm> = ArrayList<Alarm>()

    companion object {

        fun load(context: Context, medicineId: Long, callback: ResultCallback<MutableList<Alarm>>) {
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