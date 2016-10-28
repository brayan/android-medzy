package br.com.sailboat.elseapp.view.ui.insert.presenter

import android.content.Context
import android.content.Intent
import android.os.Build
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.helper.ApiLevelHelper
import br.com.sailboat.elseapp.helper.LogHelper
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.async_tasks.LoadDrugsAsyncTask
import br.com.sailboat.elseapp.view.ui.insert.view_model.InsertDrugViewModel


class InsertDrugPresenter(view: InsertDrugPresenter.View) : BasePresenter() {

    val view: InsertDrugPresenter.View
    val viewModel: InsertDrugViewModel

    init {
        this.view = view
        this.viewModel = InsertDrugViewModel()
    }

    override fun onResumeFirstSession() {
        loadDrugs()
    }

    override fun onResumeAfterRestart() {
        view.updateContentViews()
    }

    fun onResultCanceledWorkoutDetails() {
        loadDrugs()
    }

    fun onClickTime() {

    }

    fun onClickFrequency() {

    }

    fun onClickMenuSave() {
        view.showToast("SAVING!!!!!!")
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

    private fun deleteWorkout(drugToDelete: Drug) {
//        DeleteWorkoutAsyncTask(context, workoutToDelete, object : DeleteWorkoutAsyncTask.Callback {
//            override fun onSuccess() {
//            }
//
//            override fun onFail(e: Exception) {
//                LogHelper.printExceptionLog(e)
//                view.showToast(e.message)
//            }
//        }).execute()
    }

    private fun saveWorkout(drug: Drug) {
//        SaveWorkoutAsyncTask(context, workout, exercises, object : SaveWorkoutAsyncTask.Callback {
//
//            override fun onSuccess() {
//            }
//
//            override fun onFail(e: Exception) {
//                LogHelper.printExceptionLog(e)
//                view.showToast(e.message)
//            }
//
//        }).execute()
    }

    private val context: Context get() = view.activityContext

    val drugs: MutableList<Drug> get() = viewModel.drugList


    interface View {
        val activityContext: Context
        fun updateContentViews()
        fun showToast(message: String)
    }

}
