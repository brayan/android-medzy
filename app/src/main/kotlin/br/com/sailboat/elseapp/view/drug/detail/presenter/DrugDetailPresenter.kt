package br.com.sailboat.elseapp.view.drug.detail.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.async_task.DeleteDrugAsyncTask
import br.com.sailboat.elseapp.view.drug.detail.view_model.DrugDetailViewModel


class DrugDetailPresenter(view: DrugDetailPresenter.View) : BasePresenter() {

    val view: DrugDetailPresenter.View
    val viewModel: DrugDetailViewModel

    init {
        this.view = view
        this.viewModel = DrugDetailViewModel()
    }

    override fun extractExtrasFromIntent(intent: Intent) {
        val drug = ExtrasHelper.getDrug(intent)
        viewModel.drug = drug
    }

    override fun postResume() {
        updateContentViews()
    }

    fun onClickEdit() {
        view.startInsertDrugActivity(drug!!);
    }

    fun onClickMenuDelete() {
        deleteWorkout()
    }

    fun onActivityResultOkEditDrug(data: Intent?) {
        viewModel.drug = ExtrasHelper.getDrug(data!!)
        updateContentViews()
    }

    private fun deleteWorkout() {

        DeleteDrugAsyncTask(context, drug!!, object : DeleteDrugAsyncTask.Callback {

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
        view.setDrugName(drug?.name ?: "")
    }

    private val context: Context get() = view.activityContext
    private val drug: Drug? get() = viewModel.drug


    interface View {
        val activityContext: Context
        fun showToast(message: String)
        fun setDrugName(name: String)
        fun closeActivityResultOk()
        fun startInsertDrugActivity(drug: Drug)
    }

}
