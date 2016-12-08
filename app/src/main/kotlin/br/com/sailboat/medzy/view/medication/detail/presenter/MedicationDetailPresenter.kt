package br.com.sailboat.medzy.view.medication.detail.presenter

import android.content.Context
import android.os.Bundle
import br.com.sailboat.canoe.async.callback.OnSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.canoe.helper.SafeOperation
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.view.async_task.AsyncDeleteMedication
import br.com.sailboat.medzy.view.async_task.AsyncLoadAlarms
import br.com.sailboat.medzy.view.async_task.AsyncLoadMedication
import br.com.sailboat.medzy.view.medication.detail.view_model.MedicationDetailViewModel


class MedicationDetailPresenter(view: MedicationDetailPresenter.View) : BasePresenter() {

    private val view = view
    private val viewModel = MedicationDetailViewModel()

    override fun extractExtrasFromArguments(arguments: Bundle?) {
        arguments.let {
            viewModel.medId = ExtrasHelper.getMedicationId(arguments!!)
        }
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
                view.setTotalAmount(med.totalAmount)
            }

            override fun onFail(e: Exception) {
                SafeOperation.printLogAndShowDialog(view.getContext(), e)
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

            override fun onFail(e: Exception) {
                SafeOperation.printLogAndShowDialog(view.getContext(), e)
            }

        })
    }

    private fun deleteWorkout() {
        AsyncDeleteMedication.delete(view.getContext(), viewModel.medId!!, object : OnSuccess {

            override fun onSuccess() {
                view.closeActivityResultOk()
            }

            override fun onFail(e: Exception) {
                SafeOperation.printLogAndShowDialog(view.getContext(), e)
            }

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
        view.setAlarmTime(DateHelper.formatTimeWithAndroidFormat(view.getContext(), viewModel.alarms[0].time))
        view.setAlarmAmount(viewModel.alarms[0].amount)
    }


    interface View {
        fun getContext() : Context
        fun showToast(message: String)
        fun setMedicationName(name: String)
        fun setTotalAmount(total: Double)
        fun setAlarmTime(time: String)
        fun setAlarmAmount(amount: Double)
        fun closeActivityResultOk()
        fun startInsertMedicationActivity(medicationId: Long)
    }

}
