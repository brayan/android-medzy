package br.com.sailboat.elseapp.view.async_task

import android.content.Context

import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.persistence.sqlite.DrugSQLite


class SaveDrugAsyncTask(context: Context, drug: Drug, callback: SaveDrugAsyncTask.Callback) : BaseAsyncTask() {

    private val drug: Drug
    private val context: Context
    private val callback: Callback

    private val isNewDrug: Boolean get() = drug.id == -1L

    init {
        this.context = context.applicationContext
        this.drug = drug
        this.callback = callback
    }

    @Throws(Exception::class)
    override fun onDoInBackground() {

        if (isNewDrug) {
            saveNewDrug()
        } else {
            updateDrug()
        }

    }

    override fun onSuccess() {
        callback.onSuccess()
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }

    private fun updateDrug() {
        DrugSQLite(context).update(drug)
    }

    private fun saveNewDrug() {
        val id = DrugSQLite(context).saveAndGetId(drug)
        drug.id = id
    }


    interface Callback {
        fun onSuccess()
        fun onFail(e: Exception)
    }

}
