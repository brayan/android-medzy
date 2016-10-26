package br.com.sailboat.elseapp.view.async_tasks

import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Drug
import java.util.*

class LoadDrugsAsyncTask(callback: LoadDrugsAsyncTask.Callback) : BaseAsyncTask() {

    val list = ArrayList<Drug>()
    val callback: LoadDrugsAsyncTask.Callback

    init {
        this.callback = callback
    }

    override fun onDoInBackground() {
        list.add(Drug(1, "Drug 1"))
        list.add(Drug(2, "Drug 2"))
        list.add(Drug(3, "Drug 3"))
        list.add(Drug(4, "Drug 4"))
        list.add(Drug(5, "Drug 5"))
        list.add(Drug(6, "Drug 6"))
    }

    override fun onSuccess() {
       callback.onSucess(list)
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }


    interface Callback {
        fun onSucess(list: List<Drug>)
        fun onFail(e: Exception)
    }

}