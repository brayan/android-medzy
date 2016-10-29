package br.com.sailboat.elseapp.view.async_tasks

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.persistence.sqlite.DrugSQLite

class DeleteDrugAsyncTask (context: Context, drug: Drug, callback: DeleteDrugAsyncTask.Callback) : BaseAsyncTask() {

    private val drug: Drug
    private val context: Context
    private val callback: Callback

    init {
        this.context = context.applicationContext
        this.drug = drug
        this.callback = callback
    }

    override fun onDoInBackground() {
        DrugSQLite(context).delete(drug.id)
    }

    override fun onSuccess() {
        callback.onSuccess()
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }


    interface Callback {
        fun onSuccess()
        fun onFail(e: Exception)
    }

}
