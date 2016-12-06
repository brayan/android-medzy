package br.com.sailboat.medzy.view.async_task

import android.content.Context
import br.com.sailboat.canoe.async.BaseAsync
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import java.util.*

class AsyncLoadAlarms private constructor(context: Context, medId: Long, callback: OnSuccessWithResult<MutableList<Alarm>>) : BaseAsync() {

    private val context = context
    private val callback = callback
    private val medId = medId
    private var list: MutableList<Alarm> = ArrayList<Alarm>()

    companion object {

        fun load(context: Context, medId: Long, callback: OnSuccessWithResult<MutableList<Alarm>>) {
            AsyncLoadAlarms(context, medId, callback).execute()
        }

    }

    override fun onDoInBackground() {
        list = AlarmSQLite(context).getAlarmsByMed(medId)
    }

    override fun onSuccess() {
        callback.onSuccess(list)
    }

    override fun onFail(e: Exception?) {
        callback.onFail(e)
    }

}