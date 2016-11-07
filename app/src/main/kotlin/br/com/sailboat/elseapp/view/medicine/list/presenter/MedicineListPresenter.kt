package br.com.sailboat.elseapp.view.medicine.list.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.view.async_task.LoadMedicinesViewHolderAsyncTask
import br.com.sailboat.elseapp.view.medicine.list.view_model.MedicineListViewModel


class MedicineListPresenter(view: MedicineListPresenter.View) : BasePresenter() {

    val view: MedicineListPresenter.View
    val viewModel: MedicineListViewModel

    init {
        this.view = view
        this.viewModel = MedicineListViewModel()
    }

    override fun onResumeFirstSession() {
        loadDrugs()
    }

    override fun onResumeAfterRestart() {
        view.updateContentViews()
    }

    fun onClickNewDrug() {
        view.startInsertOrEditDrugActivity()
    }

    fun onClickDrug(position: Int) {
        val medicine = medicines[position]
        view.startDrugDetailActivity(medicine)
    }

    fun onActivityResultOk(data: Intent?) {
        loadDrugs()
    }

    fun onActivityResultCanceled(data: Intent?) {
        loadDrugs()
    }

    private fun loadDrugs() {

        LoadMedicinesViewHolderAsyncTask(context, object : LoadMedicinesViewHolderAsyncTask.Callback {

            override fun onSucess(list: MutableList<MedicineVHModel>) {
                medicines.clear()
                medicines.addAll(list)

                view.updateContentViews()
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
            }

        }).execute()

    }

    private val context: Context get() = view.activityContext

    val medicines: MutableList<MedicineVHModel> get() = viewModel.medicines


    interface View {
        val activityContext: Context
        fun updateContentViews()
        fun showToast(message: String)
        fun startInsertOrEditDrugActivity()
        fun startDrugDetailActivity(medicine: MedicineVHModel)
        fun updateWorkoutRemoved(position: Int)
    }

}
