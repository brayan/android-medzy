package br.com.sailboat.elseapp.view.ui.presenter

import android.content.Context
import android.content.Intent
import android.os.Build
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.helper.ApiLevelHelper
import br.com.sailboat.elseapp.helper.LogHelper
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.async_tasks.LoadDrugsAsyncTask
import br.com.sailboat.elseapp.view.ui.view_model.DrugListViewModel


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

    fun onClickNewWorkout() {
        view.startNewWorkoutActivity()
    }

    fun onClickWorkout(position: Int) {
        val workout = drugs[position]

        if (ApiLevelHelper.isLowerThan(Build.VERSION_CODES.LOLLIPOP)) {
            view.startWorkoutDetailsActivity(workout)
        } else {
            view.startWorkoutDetailsActivityWithAnimation(workout)
        }
    }

    fun onResultCanceledWorkoutDetails() {
        loadDrugs()
    }

    fun onActivityResultOkInsertOrEditWorkout(data: Intent) {
//        val workout = ExtrasHelper.getWorkout(data)
//        val exercises = ExtrasHelper.getExercises(data)
//
//        viewModel.getWorkoutList().add(workout)
//        view.updateContentViews()
//        saveWorkout(workout, exercises)
    }

    fun onActivityResultOkWorkoutDetails(data: Intent) {
//        if (ExtrasHelper.hasWorkoutToDelete(data)) {
//            val workoutToDelete = ExtrasHelper.getWorkout(data)
//            removeWorkoutFromListAndDeleteFromDatabase(workoutToDelete)
//        }
    }

    private fun removeWorkoutFromListAndDeleteFromDatabase(drugToDelete: Drug) {
        val workoutList = viewModel.getDrugList()

        var position = -1

        for (i in workoutList.indices) {
            if (workoutList[i].id == drugToDelete.id) {
                position = i
                break
            }
        }

        if (position != -1) {
            workoutList.removeAt(position)
            view.updateWorkoutRemoved(position)
            deleteWorkout(drugToDelete)
        } else {
            view.showToast("Workout not found to delete")
        }
    }

    private fun loadDrugs() {

        LoadDrugsAsyncTask(object : LoadDrugsAsyncTask.Callback {

            override fun onSucess(list: List<Drug>) {
                viewModel.drugList.clear()
                viewModel.drugList.addAll(list)

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

    private val context: Context
        get() = view.activityContext

    val drugs: List<Drug>
        get() = viewModel.getDrugList()


    interface View {
        val activityContext: Context
        fun updateContentViews()
        fun showToast(message: String)
        fun startNewWorkoutActivity()
        fun startWorkoutDetailsActivity(drug: Drug)
        fun updateWorkoutRemoved(position: Int)
        fun startWorkoutDetailsActivityWithAnimation(drug: Drug)
    }

}
