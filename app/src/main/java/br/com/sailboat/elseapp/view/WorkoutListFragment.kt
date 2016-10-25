package br.com.sailboat.elseapp.view

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.model.Workout
import br.com.sailboat.elseapp.view.adapter.WorkoutListAdapter
import br.com.sailboat.elseapp.view.presenter.WorkoutListPresenter
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.recyclerview.*


class WorkoutListFragment : BaseFragment<WorkoutListPresenter>(), WorkoutListPresenter.View, WorkoutListAdapter.Callback {

    private val REQUEST_NEW_WORKOUT = 0
    private val REQUEST_DETAILS = 1

    override val layoutId: Int get() = R.layout.frame_recycler
    override val activityContext: Context get() = activity

    override fun newPresenterInstance(): WorkoutListPresenter {
        return WorkoutListPresenter(this)
    }

    override fun onActivityResultOk(requestCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_NEW_WORKOUT -> {
                presenter.onActivityResultOkInsertOrEditWorkout(data)
                return
            }
            REQUEST_DETAILS -> {
                presenter.onActivityResultOkWorkoutDetails(data)
                return
            }
        }
    }

    override fun onActivityResultCanceled(requestCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_DETAILS -> {
                presenter.onResultCanceledWorkoutDetails()
                return
            }
        }
    }

    override fun initViews() {
        initRecyclerView()
        initEmptyView()
    }

    override fun updateContentViews() {
        recyclerView!!.adapter.notifyDataSetChanged()
        updateVisibilityOfViews()
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun startNewWorkoutActivity() {
        //        InsertOrEditWorkoutActivity.start(this, REQUEST_NEW_WORKOUT);
    }

    override fun startWorkoutDetailsActivity(workout: Workout) {
        //        WorkoutDetailsActivity.start(this, workout, REQUEST_DETAILS);
    }

    override fun startWorkoutDetailsActivityWithAnimation(workout: Workout) {
        startWorkoutDetailsActivity(workout)
    }

    override fun updateWorkoutRemoved(position: Int) {
        recyclerView!!.adapter.notifyItemRemoved(position)
    }

    override fun onClickWorkout(position: Int) {
        presenter.onClickWorkout(position)
    }

    fun onClickFab() {
        presenter.onClickNewWorkout()
    }

    private fun initRecyclerView() {
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        val adapter = WorkoutListAdapter(presenter.workouts, this)
        recyclerView!!.adapter = adapter
    }

    private fun initEmptyView() {
        imgEmpty.setColorFilter(ContextCompat.getColor(activity, R.color.orange_300), PorterDuff.Mode.SRC_ATOP)
        tvTitle.text = "No workouts"
        tvMessage.text = "Create a new workout plan by tapping the + button"
        emptyList.visibility = View.GONE
    }

    private fun updateVisibilityOfViews() {
        val emptyList = presenter.workouts.isEmpty()

        if (emptyList) {
            recyclerView!!.visibility = View.GONE
            this.emptyList!!.visibility = View.VISIBLE

        } else {
            this.emptyList!!.visibility = View.GONE
            recyclerView!!.visibility = View.VISIBLE
        }

    }

}
