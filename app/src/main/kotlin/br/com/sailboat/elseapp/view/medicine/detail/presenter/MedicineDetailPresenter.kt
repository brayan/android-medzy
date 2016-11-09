package br.com.sailboat.elseapp.view.medicine.detail.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.base.SimpleAsyncTask
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.view.async_task.DeleteMedicineAsyncTask
import br.com.sailboat.elseapp.view.medicine.detail.view_model.MedicineDetailViewModel


class MedicineDetailPresenter(view: MedicineDetailPresenter.View) : BasePresenter() {

    val view: MedicineDetailPresenter.View
    val viewModel: MedicineDetailViewModel

    init {
        this.view = view
        this.viewModel = MedicineDetailViewModel()
    }

    override fun extractExtrasFromIntent(intent: Intent) {
        viewModel.medicineName = ExtrasHelper.getMedicineName(intent)
        viewModel.medicineId = ExtrasHelper.getMedicineId(intent)
    }

    override fun postResume() {
        updateContentViews()
    }

    fun onClickEdit() {
        view.startInsertDrugActivity(viewModel.medicineId!!);
    }

    fun onClickMenuDelete() {
        deleteWorkout()
    }

    fun onActivityResultOkEditDrug(data: Intent?) {
        // TODO: START ANIMATION TO UPDATE THE SCREEN
        // LOAD THE INFO AGAIN;
        updateContentViews()
    }

    private fun deleteWorkout() {

        DeleteMedicineAsyncTask(context, viewModel.medicineId!!, object : SimpleAsyncTask.Callback {

            override fun onSuccess() {
                view.closeActivityResultOk()
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showToast(e?.message ?: "")
            }
        }).execute()

    }

    private fun updateContentViews() {
        view.setDrugName(medicineName?: "")
    }

    private val context: Context get() = view.activityContext
    private val medicineName: String? get() = viewModel.medicineName


    interface View {
        val activityContext: Context
        fun showToast(message: String)
        fun setDrugName(name: String)
        fun closeActivityResultOk()
        fun startInsertDrugActivity(medicineId: Long)
    }

}
