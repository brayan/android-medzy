package br.com.sailboat.elseapp.view.async_tasks;

import android.content.Context;

import br.com.sailboat.elseapp.base.BaseAsyncTask;
import br.com.sailboat.elseapp.model.Drug;


public class SaveWorkoutAsyncTask extends BaseAsyncTask {

    private Drug drug;
    private Context context;

    private SaveWorkoutAsyncTask.Callback callback;

    public SaveWorkoutAsyncTask(Context context, Drug drug, Callback callback) {
        setContext(context.getApplicationContext());
        setDrug(drug);
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
        return getDrug().getId() == -1;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
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
