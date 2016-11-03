package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import android.util.Log

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

    override fun onDoInBackground() {

        val startTime = System.currentTimeMillis()

        Log.e("ELSE", "startTime " + startTime)

        if (isNewDrug) {
            saveNewDrug()
        } else {
            updateDrug()
        }

        val stopTime = System.currentTimeMillis()

        Log.e("ELSE", "stopTime " + stopTime)

        val elapsedTime = stopTime - startTime

        Log.e("ELSE", "elapsedTime " + elapsedTime)

        if (elapsedTime < 1000) {
            Thread.sleep(1000 - elapsedTime)
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
