package br.com.sailboat.elseapp.view.adapter.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.sailboat.elseapp.R;
import br.com.sailboat.elseapp.model.Workout;


public class WorkoutViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT_ID = R.layout.holder_workout;

    private TextView tvName;

    private WorkoutViewHolder.Callback callback;

    public WorkoutViewHolder(View itemView, WorkoutViewHolder.Callback callback) {
        super(itemView);
        setCallback(callback);
        initViews(itemView);
    }

    private void initViews(View view) {
        inflateViews(view);
        checkCallbackAndBindListeners(view);
    }

    public void onBindViewHolder(Workout workout) {
        tvName.setText(workout.getName());
    }

    private void inflateViews(View view) {
        tvName = (TextView) view.findViewById(R.id.holder_workout__tv__name);
    }

    private void checkCallbackAndBindListeners(View view) {
        if (getCallback() != null) {
            bindListeners(view);
        }
    }

    private void bindListeners(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallback().onClickWorkout(getAdapterPosition());
            }
        });
    }



    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }



    public interface Callback {
        void onClickWorkout(int position);
    }

}
