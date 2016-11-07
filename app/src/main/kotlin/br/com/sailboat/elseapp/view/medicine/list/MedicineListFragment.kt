package br.com.sailboat.elseapp.view.medicine.list

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.view.adapter.MedicineListAdapter
import br.com.sailboat.elseapp.view.medicine.detail.MedicineDetailActivity
import br.com.sailboat.elseapp.view.medicine.insert.InsertMedicineActivity
import br.com.sailboat.elseapp.view.medicine.list.presenter.MedicineListPresenter
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.fab.*
import kotlinx.android.synthetic.main.recyclerview.*
import kotlinx.android.synthetic.main.toolbar.*

class MedicineListFragment : BaseFragment<MedicineListPresenter>(), MedicineListPresenter.View, MedicineListAdapter.Callback {

    private val REQUEST_NEW_DRUG = 0
    private val REQUEST_DETAILS = 1

    override val layoutId: Int get() = R.layout.frag_medicine_list
    override val activityContext: Context get() = activity

    override fun newPresenterInstance(): MedicineListPresenter {
        return MedicineListPresenter(this)
    }

    override fun onActivityResultOk(requestCode: Int, data: Intent?) {
        presenter.onActivityResultOk(data)
    }

    override fun onActivityResultCanceled(requestCode: Int, data: Intent?) {
        presenter.onActivityResultCanceled(data)
    }

    override fun onClickDrug(position: Int) {
        presenter.onClickDrug(position)
    }

    override fun initViews() {
        initRecyclerView()
        initEmptyListView()
        initToolbar()
        initFab()
    }

    private fun initFab() {
        fab.setOnClickListener {
            presenter.onClickNewDrug()
        }
    }

    override fun updateContentViews() {
        recyclerView.adapter.notifyDataSetChanged()
        updateVisibilityOfViews()
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun startInsertOrEditDrugActivity() {
        InsertMedicineActivity.start(this, REQUEST_NEW_DRUG)
    }

    override fun startDrugDetailActivity(medicine: MedicineVHModel) {
        MedicineDetailActivity.start(this, medicine, REQUEST_DETAILS)
    }

    override fun updateWorkoutRemoved(position: Int) {
        recyclerView.adapter.notifyItemRemoved(position)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = MedicineListAdapter(presenter.medicines, this)
        recyclerView.adapter = adapter
    }

    private fun initEmptyListView() {
        imgEmptyList.setColorFilter(ContextCompat.getColor(activity, R.color.teal_300), PorterDuff.Mode.SRC_ATOP)
        tvEmptyListTitle.text = "No drugs"
        tvEmptyListMessage.text = "Add a new drug  by tapping the + button"
        emptyList.visibility = View.GONE
    }

    private fun initToolbar() {
        toolbar.setTitle("Pill Time")
    }

    private fun updateVisibilityOfViews() {
        val isEmpty = presenter.medicines.isEmpty()

        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        emptyList.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

}
