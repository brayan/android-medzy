package br.com.sailboat.medzy.view.medicine.detail.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.canoe.async.callback.OnSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessResult
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medicine
import br.com.sailboat.medzy.view.async_task.AsyncDeleteMedicine
import br.com.sailboat.medzy.view.async_task.AsyncLoadAlarms
import br.com.sailboat.medzy.view.async_task.AsyncLoadMedication
import br.com.sailboat.medzy.view.medicine.detail.view_model.MedicineDetailViewModel


class MedicineDetailPresenter(view: MedicineDetailPresenter.View) : BasePresenter() {

    private val view = view
    private val viewModel = MedicineDetailViewModel()

    override fun extractExtrasFromIntent(intent: Intent) {
        viewModel.medicineId = ExtrasHelper.getMedicineId(intent)
    }

    override fun onResumeFirstSession() {
        loadInfo()
    }

    override fun onResumeAfterRestart() {
        updateContentViews()
    }

    fun onClickEdit() {
        view.startInsertMedicineActivity(viewModel.medicineId!!)
    }

    fun onClickMenuDelete() {
        deleteWorkout()
    }

    fun postActivityResult() {
        loadInfo()
    }

    private fun loadInfo() {
        loadMedicine()
        loadAlarms()
    }

    private fun loadMedicine() {
        AsyncLoadMedication.load(view.getContext(), viewModel.medicineId!!, object : OnSuccessResult<Medicine> {

            override fun onSuccess(med: Medicine) {
                viewModel.medicine = med
                view.setMedicineName(med.name)
            }

        })
    }

    private fun loadAlarms() {
        AsyncLoadAlarms.load(view.getContext(), viewModel.medicineId!!, object : OnSuccessResult<MutableList<Alarm>> {

            override fun onSuccess(list: MutableList<Alarm>) {
                viewModel.alarms.clear()
                viewModel.alarms.addAll(list)

//                view.setAlarmsView(viewModel.alarms)
//
//                updateMedicineAlarmView()
            }

        })
    }

    private fun deleteWorkout() {
        AsyncDeleteMedicine.delete(view.getContext(), viewModel.medicineId!!, OnSuccess {
            view.closeActivityResultOk()
        })
    }

    private fun updateContentViews() {
        updateMedNameView()
        // TODO: UPDATE ALARMS
    }

    private fun updateMedNameView() {
        view.setMedicineName(viewModel.medicine!!.name)
    }


    interface View {
        fun getContext() : Context
        fun showToast(message: String)
        fun setMedicineName(name: String)
        fun closeActivityResultOk()
        fun startInsertMedicineActivity(medicineId: Long)
    }

}
