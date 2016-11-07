package br.com.sailboat.elseapp.view.async_task

import android.content.Context
import android.util.Log

import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.persistence.sqlite.MedicineSQLite


class SaveMedicineAsyncTask(context: Context, medicine: Medicine, callback: SaveMedicineAsyncTask.Callback) : BaseAsyncTask() {

    private val medicine: Medicine
    private val context: Context
    private val callback: Callback

    private val isNewDrug: Boolean get() = medicine.id == -1L

    init {
        this.context = context.applicationContext
        this.medicine = medicine
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
        MedicineSQLite(context).update(medicine)
    }

    private fun saveNewDrug() {
        val id = MedicineSQLite(context).saveAndGetId(medicine)
        medicine.id = id
    }


    interface Callback {
        fun onSuccess()
        fun onFail(e: Exception)
    }

}
