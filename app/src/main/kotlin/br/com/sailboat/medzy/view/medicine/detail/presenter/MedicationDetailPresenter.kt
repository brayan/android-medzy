package br.com.sailboat.medzy.view.medication.detail.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.canoe.async.callback.OnSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.view.async_task.AsyncDeleteMedication
import br.com.sailboat.medzy.view.async_task.AsyncLoadAlarms
import br.com.sailboat.medzy.view.async_task.AsyncLoadMedication
import br.com.sailboat.medzy.view.medicine.detail.view_model.MedicationDetailViewModel


class MedicationDetailPresenter(view: MedicationDetailPresenter.View) : BasePresenter() {

    private val view = view
    private val viewModel = MedicationDetailViewModel()

    override fun extractExtrasFromIntent(intent: Intent) {
        viewModel.medId = ExtrasHelper.getMedicationId(intent)
    }

    override fun onResumeFirstSession() {
        loadInfo()
    }

    override fun onResumeAfterRestart() {
        updateContentViews()
    }

    fun onClickEdit() {
        view.startInsertMedicationActivity(viewModel.medId!!)
    }

    fun onClickMenuDelete() {
        deleteWorkout()
    }

    fun postActivityResult() {
        loadInfo()
    }

    private fun loadInfo() {
        loadMedication()
        loadAlarms()
    }

    private fun loadMedication() {
        AsyncLoadMedication.load(view.getContext(), viewModel.medId!!, object : OnSuccessWithResult<Medication> {

            override fun onSuccess(med: Medication) {
                viewModel.medication = med
                view.setMedicationName(med.name)
            }

        })
    }

    private fun loadAlarms() {
        AsyncLoadAlarms.load(view.getContext(), viewModel.medId!!, object : OnSuccessWithResult<MutableList<Alarm>> {

            override fun onSuccess(list: MutableList<Alarm>) {
                viewModel.alarms.clear()
                viewModel.alarms.addAll(list)
//
                updateMedicationAlarmView()
            }

        })
    }

    private fun deleteWorkout() {
        AsyncDeleteMedication.delete(view.getContext(), viewModel.medId!!, OnSuccess {
            view.closeActivityResultOk()
        })
    }

    private fun updateContentViews() {
        updateMedNameView()
        // TODO: UPDATE ALARMS
    }

    private fun updateMedNameView() {
        view.setMedicationName(viewModel.medication!!.name)
    }

    private fun updateMedicationAlarmView() {
//        view.setAlarm(AlarmHelper.formatTimeWithAndroidFormat(medication!!.alarm.time, context))
        // TODO: JUST FOR TESTS
        view.setAlarmDate(DateHelper.getSimpleDayName(view.getContext(), viewModel.alarms[0].time))
        view.setAlarmTime(DateHelper.formatTimeWithAndroidFormat(view.getContext(), viewModel.alarms[0].time))
    }


    interface View {
        fun getContext() : Context
        fun showToast(message: String)
        fun setMedicationName(name: String)
        fun setAlarmDate(date: String)
        fun setAlarmTime(time: String)
        fun closeActivityResultOk()
        fun startInsertMedicationActivity(medicationId: Long)
    }

}
