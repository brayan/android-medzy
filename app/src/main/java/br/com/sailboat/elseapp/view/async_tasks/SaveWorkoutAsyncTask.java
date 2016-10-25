package br.com.sailboat.elseapp.view.async_tasks;

import android.content.Context;

import java.util.List;

import br.com.sailboat.elseapp.base.BaseAsyncTask;
import br.com.sailboat.elseapp.model.Exercise;
import br.com.sailboat.elseapp.model.Workout;


public class SaveWorkoutAsyncTask extends BaseAsyncTask {

    private Workout workout;
    private List<Exercise> exercises;
    private Context context;

    private SaveWorkoutAsyncTask.Callback callback;

    public SaveWorkoutAsyncTask(Context context, Workout workout, List<Exercise> exercises, Callback callback) {
        setContext(context.getApplicationContext());
        setWorkout(workout);
        setExercises(exercises);
        setCallback(callback);
    }

    @Override
    protected void onDoInBackground() throws Exception {
        saveEntities();
    }

    @Override
    protected void onSuccess() {
        getCallback().onSuccess();
    }

    @Override
    protected void onFail(Exception e) {
        getCallback().onFail(e);
    }

    private void saveEntities() throws Exception {
        if (isWorkoutNew()) {
            saveNewWorkout();
        } else {
            updateWorkout();
            deleteRelationshipWorkoutExercise();
        }
        saveRalationshipWorkoutExercise();
    }

    private void updateWorkout() throws Exception {
//        new WorkoutSQLite(getContext()).update(getWorkout());
    }

    private void saveNewWorkout() throws Exception {
//        long workoutId = new WorkoutSQLite(getContext()).saveAndGetId(getWorkout());
//        getWorkout().setId(workoutId);
    }

    private void deleteRelationshipWorkoutExercise() throws Exception {
//        WorkoutExerciseSQLite dao = new WorkoutExerciseSQLite(getContext());
//        dao.deleteFromWorkout(getWorkout().getId());
    }

    private void saveRalationshipWorkoutExercise() throws Exception {
//        WorkoutExerciseSQLite dao = new WorkoutExerciseSQLite(getContext());
//        for (Exercise exercise : getExercises()) {
//            dao.saveAndGetId(getWorkout().getId(), exercise.getId());
//        }
    }

    private boolean isWorkoutNew() {
        return getWorkout().getId() == -1;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public interface Callback {
        void onSuccess();
        void onFail(Exception e);
    }

}
