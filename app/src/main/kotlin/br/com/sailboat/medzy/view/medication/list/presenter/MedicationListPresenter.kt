package br.com.sailboat.medzy.view.medication.list.presenter

import android.content.Context
import br.com.sailboat.canoe.alarm.AlarmHelper
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.helper.SafeOperation
import br.com.sailboat.canoe.recycler.RecyclerItem
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite
import br.com.sailboat.medzy.view.adapter.MedicationListAdapter
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import br.com.sailboat.medzy.view.async_task.AsyncLoadMedicationViewHolder
import br.com.sailboat.medzy.view.medication.list.view_model.MedicineListViewModel


class MedicationListPresenter(view: MedicationListPresenter.View) : BasePresenter(), MedicationListAdapter.Callback {

    private val view = view
    private val viewModel = MedicineListViewModel()

    override val meds: MutableList<RecyclerItem> get() = viewModel.medications

    override fun onResumeFirstSession() {
        loadMedicines()
    }

    override fun onResumeAfterRestart() {
        updateContentViews()
    }

    override fun onClickMedicine(position: Int) {
        val medicine = meds[position]
        view.startMedicineDetailActivity(medicine as MedicationRecyclerItem)
    }

    fun onClickNewMedicine() {
        view.startInsertMedicineActivity()
    }

    fun onActivityResult() {
        loadMedicines()
    }

    fun onSwipedMedication(position: Int) {
        val med = meds[position] as MedicationRecyclerItem
        val alarm = AlarmSQLite(view.getContext()).getAlarmById(med.alarmId)

        incrementAlarm(alarm)
        updateAlarm(alarm)
        setAlarmManager(alarm)
        updateTotalAmount(alarm, med)
        updateMedication(med)

        loadMedicines()
    }

    private fun setAlarmManager(alarm: Alarm) {
        AlarmManagerHelper.setAlarm(view.getContext(), alarm.id, alarm.time.timeInMillis)
    }

    private fun updateAlarm(alarm: Alarm) {
        AlarmSQLite(view.getContext()).update(alarm)
    }

    private fun updateTotalAmount(alarm: Alarm, med: MedicationRecyclerItem) {
        if (med.totalAmount > 0) {
            med.totalAmount = med.totalAmount - alarm.amount
        }
    }

    private fun incrementAlarm(alarm: Alarm) {
        AlarmHelper.incrementToNextValidDate(alarm.time, alarm.repeatType)
    }

    private fun updateMedication(med: MedicationRecyclerItem) {
        val medicationDao = MedicationSQLite(view.getContext())
        val medication = medicationDao.getMedicationById(med.medId)

        medication.totalAmount = med.totalAmount

        medicationDao.update(medication)
    }

    private fun loadMedicines() {

        AsyncLoadMedicationViewHolder.load(view.getContext(), object : OnSuccessWithResult<MutableList<MedicationRecyclerItem>> {

            override fun onSuccess(result: MutableList<MedicationRecyclerItem>) {
                onSuccessLoadMedication(result)
            }

            override fun onFail(e: Exception?) {
                SafeOperation.printLogAndShowDialog(view.getContext(), e)
            }

        })

    }

    private fun onSuccessLoadMedication(result: MutableList<MedicationRecyclerItem>) {
        meds.clear()
        meds.addAll(MedsListBuilder.buildFrom(result))

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
        fun startMedicineDetailActivity(medication: MedicationRecyclerItem)
        fun updateMedicationRemoved(position: Int)
        fun showMedicines()
        fun hideEmptyList()
        fun showEmptyList()
        fun hideMedicines()
    }

}
