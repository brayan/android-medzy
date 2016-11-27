package br.com.sailboat.medzy.view.medicine.list.presenter

import android.content.Context
import br.com.sailboat.canoe.alarm.AlarmHelper
import br.com.sailboat.canoe.async.callback.ResultCallback
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.helper.SafeOperation
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.view.adapter.MedicineListAdapter
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel
import br.com.sailboat.medzy.view.async_task.AsyncLoadMedicinesViewHolder
import br.com.sailboat.medzy.view.medicine.list.view_model.MedicineListViewModel


class MedicineListPresenter(view: MedicineListPresenter.View) : BasePresenter(), MedicineListAdapter.Callback {

    private val view = view
    private val viewModel = MedicineListViewModel()

    override val meds: MutableList<MedicineVHModel> get() = viewModel.medicines

    override fun onResumeFirstSession() {
        loadMedicines()
    }

    override fun onResumeAfterRestart() {
        updateContentViews()
    }

    override fun onClickMedicine(position: Int) {
        val medicine = meds[position]
        view.startMedicineDetailActivity(medicine)
    }

    fun onClickNewMedicine() {
        view.startInsertMedicineActivity()
    }

    fun onActivityResult() {
        loadMedicines()
    }

    fun onSwipedMedication(position: Int) {
        val med = meds[position]
        meds.removeAt(position)
        view.updateMedicationRemoved(position)

        Thread.sleep(300)

        val alarmSqlite = AlarmSQLite(view.getContext())
        val alarm = alarmSqlite.getAlarmById(med.alarmId)!!

        AlarmHelper.incrementToNextValidDate(alarm.time, alarm.repeatType)

        alarmSqlite.update(alarm)
        AlarmManagerHelper.setAlarm(view.getContext(), alarm.id, alarm.time.timeInMillis)

        loadMedicines()
    }

    private fun loadMedicines() {

        AsyncLoadMedicinesViewHolder.load(view.getContext(), object : ResultCallback<MutableList<MedicineVHModel>> {

            override fun onSuccess(result: MutableList<MedicineVHModel>) {
                onSuccessLoadMedication(result)
            }

            override fun onFail(e: Exception) {
                SafeOperation.printLogAndShowDialog(view.getContext(), "An error occurred while loading the meds", e)
            }

        })

    }

    private fun onSuccessLoadMedication(result: MutableList<MedicineVHModel>) {
        meds.clear()
        meds.addAll(result!!)

        // TODO: GENERATE MEDS LIST

        updateContentViews()
    }

    private fun updateContentViews() {
        updateMedicinesViews()
        updateVisibilityOfMedicines()
    }

    private fun updateVisibilityOfMedicines() {
        if (meds.isEmpty()) {
            view.showEmptyList()
            view.hideMedicines()
        } else {
            view.showMedicines()
            view.hideEmptyList()
        }
    }

    private fun updateMedicinesViews() {
        view.updateMedicines()
    }


    interface View {
        fun getContext(): Context
        fun updateMedicines()
        fun showDialog(message: String)
        fun startInsertMedicineActivity()
        fun startMedicineDetailActivity(medicine: MedicineVHModel)
        fun updateMedicationRemoved(position: Int)
        fun showMedicines()
        fun hideEmptyList()
        fun showEmptyList()
        fun hideMedicines()
    }

}
