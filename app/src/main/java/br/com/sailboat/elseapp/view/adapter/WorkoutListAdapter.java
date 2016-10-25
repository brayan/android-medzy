package br.com.sailboat.elseapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.sailboat.elseapp.model.Workout;
import br.com.sailboat.elseapp.view.adapter.view_holder.WorkoutViewHolder;


public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutViewHolder> {

    private List<Workout> workoutList;
    private WorkoutListAdapter.Callback callback;

    public WorkoutListAdapter(List<Workout> items, WorkoutListAdapter.Callback callback) {
        setWorkoutList(items);
        setCallback(callback);
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflateLayout(parent, WorkoutViewHolder.LAYOUT_ID);
        return new WorkoutViewHolder(view, getCallback());
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        Workout workout = getWorkoutList().get(position);
        holder.onBindViewHolder(workout);
    }

    @Override
    public int getItemCount() {
        return getWorkoutList().size();
    }

    private View inflateLayout(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }



    public interface Callback extends WorkoutViewHolder.Callback {

    }
}
