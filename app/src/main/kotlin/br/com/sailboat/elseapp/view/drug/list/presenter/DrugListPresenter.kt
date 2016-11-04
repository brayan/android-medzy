package br.com.sailboat.elseapp.view.drug.list.presenter

import android.content.Context
import android.content.Intent
import android.os.Build
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.common.helper.ApiLevelHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.async_task.LoadDrugsAsyncTask
import br.com.sailboat.elseapp.view.drug.list.view_model.DrugListViewModel


class DrugListPresenter(view: DrugListPresenter.View) : BasePresenter() {

    val view: DrugListPresenter.View
    val viewModel: DrugListViewModel

    init {
        this.view = view
        this.viewModel = DrugListViewModel()
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
        val drug = drugs[position]

        if (ApiLevelHelper.isLowerThan(Build.VERSION_CODES.LOLLIPOP)) {
            view.startDrugDetailActivity(drug)
        } else {
            view.startDrugDetailActivityWithAnimation(drug)
        }
    }

    fun onActivityResultOk(data: Intent?) {
        loadDrugs()
    }

    fun onActivityResultCanceled(data: Intent?) {
        loadDrugs()
    }

    private fun loadDrugs() {

        LoadDrugsAsyncTask(context, object : LoadDrugsAsyncTask.Callback {

            override fun onSucess(list: MutableList<Drug>) {
                drugs.clear()
                drugs.addAll(list)

                view.updateContentViews()
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
            }

        }).execute()

    }

    private val context: Context get() = view.activityContext

    val drugs: MutableList<Drug> get() = viewModel.drugList


    interface View {
        val activityContext: Context
        fun updateContentViews()
        fun showToast(message: String)
        fun startInsertOrEditDrugActivity()
        fun startDrugDetailActivity(drug: Drug)
        fun updateWorkoutRemoved(position: Int)
        fun startDrugDetailActivityWithAnimation(drug: Drug)
    }

}
