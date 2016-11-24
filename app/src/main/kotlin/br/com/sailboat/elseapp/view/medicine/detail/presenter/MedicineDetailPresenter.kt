package br.com.sailboat.elseapp.view.medicine.detail.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.helper.ExtrasHelper
import br.com.sailboat.elseapp.helper.LogHelper
import br.com.sailboat.elseapp.view.async_task.AsyncDeleteMedicine
import br.com.sailboat.elseapp.view.medicine.detail.view_model.MedicineDetailViewModel
import br.com.sailboat.helper.async.SimpleAsyncTask
import br.com.sailboat.helper.base.BasePresenter


class MedicineDetailPresenter(view: MedicineDetailPresenter.View) : BasePresenter() {

    private val view = view
    private val viewModel = MedicineDetailViewModel()

    override fun extractExtrasFromIntent(intent: Intent) {
        viewModel.medicineName = ExtrasHelper.getMedicineName(intent)
        viewModel.medicineId = ExtrasHelper.getMedicineId(intent)
    }

    override fun postResume() {
        updateContentViews()
    }

    fun onClickEdit() {
        view.startInsertMedicineActivity(viewModel.medicineId!!);
    }

    fun onClickMenuDelete() {
        deleteWorkout()
    }

    fun onActivityResultOkEditMedicine() {
        // TODO: START ANIMATION TO UPDATE THE SCREEN
        // LOAD THE INFO AGAIN;
        updateContentViews()
    }

    private fun deleteWorkout() {

        AsyncDeleteMedicine.delete(view.getContext(), viewModel.medicineId!!, object : SimpleAsyncTask.Callback {

            override fun onSuccess() {
                view.closeActivityResultOk()
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showToast(e?.message ?: "")
            }
        })

    }

    private fun updateContentViews() {
        view.setMedicineName(medicineName?: "")
    }

    private val medicineName: String? get() = viewModel.medicineName


    interface View {
        fun getContext() : Context
        fun showToast(message: String)
        fun setMedicineName(name: String)
        fun closeActivityResultOk()
        fun startInsertMedicineActivity(medicineId: Long)
    }

}
