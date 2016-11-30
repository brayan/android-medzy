package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.AsyncSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import java.util.*

class AsyncLoadAlarms private constructor(context: Context, medId: Long, callback: OnSuccessWithResult<MutableList<Alarm>>) : AsyncSuccess(context) {

    private val context = context
    private val callback = callback
    private val medId = medId
    private var list: MutableList<Alarm> = ArrayList<Alarm>()

    companion object {

        fun load(context: Context, medicineId: Long, callback: OnSuccessWithResult<MutableList<Alarm>>) {
            AsyncLoadAlarms(context, medicineId, callback).execute()
        }

    }

    override fun onDoInBackground() {
        list = AlarmSQLite(context).getAlarmsByMedicine(medId)
    }

    override fun onSuccess() {
        callback.onSuccess(list)
    }

}