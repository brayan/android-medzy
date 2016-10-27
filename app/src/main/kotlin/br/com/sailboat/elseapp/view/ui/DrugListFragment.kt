package br.com.sailboat.elseapp.view.ui

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.adapter.DrugListAdapter
import br.com.sailboat.elseapp.view.ui.presenter.DrugListPresenter
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.recyclerview.*
import kotlinx.android.synthetic.main.toolbar.*

class DrugListFragment : BaseFragment<DrugListPresenter>(), DrugListPresenter.View, DrugListAdapter.Callback {

    private val REQUEST_NEW_WORKOUT = 0
    private val REQUEST_DETAILS = 1

    override val layoutId: Int get() = R.layout.frag_drug_list
    override val activityContext: Context get() = activity

    override fun newPresenterInstance(): DrugListPresenter {
        return DrugListPresenter(this)
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

    override fun onClickDrug(position: Int) {
        presenter.onClickWorkout(position)
    }

    override fun initViews() {
        initRecyclerView()
        initEmptyListView()
        initToolbar()
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

    override fun startWorkoutDetailsActivity(drug: Drug) {
        //        WorkoutDetailsActivity.start(this, workout, REQUEST_DETAILS);
    }

    override fun startWorkoutDetailsActivityWithAnimation(drug: Drug) {
        startWorkoutDetailsActivity(drug)
    }

    override fun updateWorkoutRemoved(position: Int) {
        recyclerView!!.adapter.notifyItemRemoved(position)
    }

    fun onClickFab() {
        presenter.onClickNewWorkout()
    }

    private fun initRecyclerView() {
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        val adapter = DrugListAdapter(presenter.drugs, this)
        recyclerView!!.adapter = adapter
    }

    private fun initEmptyListView() {
        imgEmptyList.setColorFilter(ContextCompat.getColor(activity, R.color.teal_300), PorterDuff.Mode.SRC_ATOP)
        tvEmptyListTitle.text = "No drugs"
        tvEmptyListMessage.text = "Add a new drug  by tapping the + button"
        emptyList.visibility = View.GONE
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
    }

    private fun updateVisibilityOfViews() {
        val emptyList = presenter.drugs.isEmpty()

        if (emptyList) {
            recyclerView!!.visibility = View.GONE
            this.emptyList!!.visibility = View.VISIBLE

        } else {
            this.emptyList!!.visibility = View.GONE
            recyclerView!!.visibility = View.VISIBLE
        }

    }

}