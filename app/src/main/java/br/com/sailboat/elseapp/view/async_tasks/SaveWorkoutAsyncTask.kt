package br.com.sailboat.elseapp.view.async_tasks

import android.content.Context

import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.model.Drug


class SaveWorkoutAsyncTask(context: Context, drug: Drug, callback: SaveWorkoutAsyncTask.Callback) : BaseAsyncTask() {

    var drug: Drug
    var context: Context

    var callback: Callback

    init {
        this.context = context.applicationContext
        this.drug = drug
        this.callback = callback
    }

    @Throws(Exception::class)
    override fun onDoInBackground() {
        saveEntities()
    }

    override fun onSuccess() {
        callback.onSuccess()
    }

    override fun onFail(e: Exception) {
        callback.onFail(e)
    }

    @Throws(Exception::class)
    private fun saveEntities() {
        if (isWorkoutNew) {
            saveNewWorkout()
        } else {
            updateWorkout()
            deleteRelationshipWorkoutExercise()
        }
        saveRalationshipWorkoutExercise()
    }

    @Throws(Exception::class)
    private fun updateWorkout() {
        //        new WorkoutSQLite(getContext()).update(getWorkout());
    }

    @Throws(Exception::class)
    private fun saveNewWorkout() {
        //        long workoutId = new WorkoutSQLite(getContext()).saveAndGetId(getWorkout());
        //        getWorkout().setId(workoutId);
    }

    @Throws(Exception::class)
    private fun deleteRelationshipWorkoutExercise() {
        //        WorkoutExerciseSQLite dao = new WorkoutExerciseSQLite(getContext());
        //        dao.deleteFromWorkout(getWorkout().getId());
    }

    @Throws(Exception::class)
    private fun saveRalationshipWorkoutExercise() {
        //        WorkoutExerciseSQLite dao = new WorkoutExerciseSQLite(getContext());
        //        for (Exercise exercise : getExercises()) {
        //            dao.saveAndGetId(getWorkout().getId(), exercise.getId());
        //        }
    }

    private val isWorkoutNew: Boolean
        get() = drug.id.equals(-1)


    interface Callback {
        fun onSuccess()
        fun onFail(e: Exception)
    }

}
