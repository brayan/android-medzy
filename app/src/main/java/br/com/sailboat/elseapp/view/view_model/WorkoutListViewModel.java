package br.com.sailboat.elseapp.view.view_model;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.elseapp.base.BaseViewModel;
import br.com.sailboat.elseapp.model.Workout;

public class WorkoutListViewModel extends BaseViewModel {

    private transient final List<Workout> workoutList;

    public WorkoutListViewModel() {
        this.workoutList = new ArrayList<>();
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

}
