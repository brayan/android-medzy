package br.com.sailboat.elseapp.view.medicine.detail.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.Medicine
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
        val drug = ExtrasHelper.getMedicine(intent)
        viewModel.medicine = drug
    }

    override fun postResume() {
        updateContentViews()
    }

    fun onClickEdit() {
        view.startInsertDrugActivity(medicine!!);
    }

    fun onClickMenuDelete() {
        deleteWorkout()
    }

    fun onActivityResultOkEditDrug(data: Intent?) {
        viewModel.medicine = ExtrasHelper.getMedicine(data!!)
        updateContentViews()
    }

    private fun deleteWorkout() {

        DeleteMedicineAsyncTask(context, medicine!!, object : DeleteMedicineAsyncTask.Callback {

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
        view.setDrugName(medicine?.name ?: "")
    }

    private val context: Context get() = view.activityContext
    private val medicine: Medicine? get() = viewModel.medicine


    interface View {
        val activityContext: Context
        fun showToast(message: String)
        fun setDrugName(name: String)
        fun closeActivityResultOk()
        fun startInsertDrugActivity(medicine: Medicine)
    }

}
