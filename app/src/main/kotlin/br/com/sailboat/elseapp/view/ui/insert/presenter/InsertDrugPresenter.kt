package br.com.sailboat.elseapp.view.ui.insert.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.common.exception.RequiredFieldNotFilledException
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.async_tasks.SaveDrugAsyncTask
import br.com.sailboat.elseapp.view.ui.insert.presenter.checker.InsertDrugChecker
import br.com.sailboat.elseapp.view.ui.insert.view_model.InsertDrugViewModel


class InsertDrugPresenter(view: InsertDrugPresenter.View) : BasePresenter() {

    val view: InsertDrugPresenter.View
    val viewModel: InsertDrugViewModel

    init {
        this.view = view
        this.viewModel = InsertDrugViewModel()
    }

    override fun extractExtrasFromIntent(intent: Intent) {
        val drugToEdit = ExtrasHelper.getDrug(intent)
        viewModel.drug = drugToEdit
    }

    override fun onResumeFirstSession() {
        checkIfIsEditingAndSetViews()
    }

    override fun postResume() {
        updateToolbarTitle()
    }

    fun onClickTime() {

    }

    fun onClickFrequency() {

    }

    fun onClickMenuSave() {

        try {
            collectDataFromFieldsAndBindToDrug()
            checkRequiredFields()
            save()

        } catch (e: RequiredFieldNotFilledException) {
            view.showDialog(e?.message ?: "")

        } catch (e: Exception) {
            LogHelper.printExceptionLog(e)
        }

    }

    private fun checkIfIsEditingAndSetViews() {

        if (isEditingDrug()) {
            view.setDrugName(drug?.name ?: "-")
        }

    }

    private fun updateToolbarTitle() {

        if (isInsertingDrug()) {
            view.setToolbarTitle("New Drug")
        } else {
            view.setToolbarTitle("Edit Toolbar")
        }

    }

    private fun isInsertingDrug() = viewModel.drug == null
    private fun isEditingDrug() = viewModel.drug != null

    private fun collectDataFromFieldsAndBindToDrug() {

        if (isInsertingDrug()) {
            viewModel.drug = Drug(-1, null)
        }

        viewModel.drug!!.name = view.getNameFromView()
    }

    private fun checkRequiredFields() {
        InsertDrugChecker().check(drug!!)
    }

    private fun save() {

        SaveDrugAsyncTask(context, drug!!, object : SaveDrugAsyncTask.Callback {

            override fun onSuccess() {
                view.closeActivityResultOk(drug!!);
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showDialog("An error occurred while saving the drug :/")
            }

        }).execute()

    }

    private val context: Context get() = view.activityContext
    private val drug: Drug? get() = viewModel.drug


    interface View {
        val activityContext: Context
        fun closeActivityResultOk(drug: Drug)
        fun getNameFromView(): String
        fun setToolbarTitle(title: String)
        fun setDrugName(name: String)
        fun showToast(message: String)
        fun showDialog(message: String)
    }

}
