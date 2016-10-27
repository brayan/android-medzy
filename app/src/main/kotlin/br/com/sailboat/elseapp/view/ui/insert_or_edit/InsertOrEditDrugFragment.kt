package br.com.sailboat.elseapp.view.ui.insert_or_edit

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.ui.insert_or_edit.presenter.InsertOrEditDrugPresenter
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.recyclerview.*
import kotlinx.android.synthetic.main.toolbar.*

class InsertOrEditDrugFragment : BaseFragment<InsertOrEditDrugPresenter>(), InsertOrEditDrugPresenter.View {

    private val REQUEST_NEW_WORKOUT = 0
    private val REQUEST_DETAILS = 1

    override val layoutId: Int get() = R.layout.frag_drug_list
    override val activityContext: Context get() = activity

    override fun newPresenterInstance(): InsertOrEditDrugPresenter {
        return InsertOrEditDrugPresenter(this)
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
        initToolbar()
    }

    override fun updateContentViews() {
        recyclerView.adapter.notifyDataSetChanged()
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
        recyclerView.adapter.notifyItemRemoved(position)
    }

    fun onClickFab() {
        presenter.onClickNewWorkout()
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
    }

    private fun updateVisibilityOfViews() {
        val isEmpty = presenter.drugs.isEmpty()

        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        emptyList.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

}
