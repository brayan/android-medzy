package br.com.sailboat.elseapp.view.presenter

import android.content.Context
import android.content.Intent
import android.os.Build
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.helper.ApiLevelHelper
import br.com.sailboat.elseapp.model.Exercise
import br.com.sailboat.elseapp.model.Workout
import br.com.sailboat.elseapp.view.view_model.WorkoutListViewModel


class WorkoutListPresenter(view: WorkoutListPresenter.View) : BasePresenter() {

    var view: View
    var viewModel: WorkoutListViewModel

    init {
        this.view = view
        this.viewModel = WorkoutListViewModel()
    }

    override fun onResumeFirstSession() {
        loadWorkouts()
    }

    override fun onResumeAfterRestart() {
        view.updateContentViews()
    }

    fun onClickNewWorkout() {
        view.startNewWorkoutActivity()
    }

    fun onClickWorkout(position: Int) {
        val workout = workouts[position]

        if (ApiLevelHelper.isLowerThan(Build.VERSION_CODES.LOLLIPOP)) {
            view.startWorkoutDetailsActivity(workout)
        } else {
            view.startWorkoutDetailsActivityWithAnimation(workout)
        }
    }

    fun onResultCanceledWorkoutDetails() {
        loadWorkouts()
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

    private fun removeWorkoutFromListAndDeleteFromDatabase(workoutToDelete: Workout) {
        val workoutList = viewModel.getWorkoutList()

        var position = -1

        for (i in workoutList.indices) {
            if (workoutList[i].getId() == workoutToDelete.id) {
                position = i
                break
            }
        }

        if (position != -1) {
            workoutList.removeAt(position)
            view.updateWorkoutRemoved(position)
            deleteWorkout(workoutToDelete)
        } else {
            view.showToast("Workout not found to delete")
        }
    }

    private fun loadWorkouts() {
//        LoadWorkoutsAsyncTask(context, object : LoadWorkoutsAsyncTask.Callback {
//
//            fun onSuccess(workoutList: List<Workout>) {
//                ListHelper.clearAndAdd(workoutList, viewModel.getWorkoutList())
//                view.updateContentViews()
//            }
//
//            override fun onFail(e: Exception) {
//                LogHelper.printExceptionLog(e)
//            }
//
//        }).execute()
    }

    private fun deleteWorkout(workoutToDelete: Workout) {
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

    private fun saveWorkout(workout: Workout, exercises: List<Exercise>) {
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

    val workouts: List<Workout>
        get() = viewModel.getWorkoutList()


    interface View {
        val activityContext: Context
        fun updateContentViews()
        fun showToast(message: String)
        fun startNewWorkoutActivity()
        fun startWorkoutDetailsActivity(workout: Workout)
        fun updateWorkoutRemoved(position: Int)
        fun startWorkoutDetailsActivityWithAnimation(workout: Workout)
    }

}
