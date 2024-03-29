package br.com.sailboat.medzy.view.medication.list

import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import br.com.sailboat.canoe.base.BaseFragment
import br.com.sailboat.canoe.dialog.MessageDialog
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.view.adapter.MedicationListAdapter
import br.com.sailboat.medzy.view.adapter.SwipeLeftRightMedication
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import br.com.sailboat.medzy.view.medication.detail.MedicationDetailActivity
import br.com.sailboat.medzy.view.medication.insert.InsertMedicationActivity
import br.com.sailboat.medzy.view.medication.list.presenter.MedicationListPresenter
import kotlinx.android.synthetic.main.empty_meds.*

class MedicationListFragment : BaseFragment<MedicationListPresenter>(), MedicationListPresenter.View {

    private val REQUEST_NEW_MEDICINE = 0
    private val REQUEST_DETAILS = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar

    override fun getLayoutId(): Int {
        return R.layout.frg_medication_list
    }

    override fun newPresenterInstance(): MedicationListPresenter {
        return MedicationListPresenter(this)
    }

    override fun initViews(view: View) {
        initRecyclerView(view)
        initEmptyMedsView()
        initToolbar(view)
        initFab(view)
    }

    override fun updateMeds() {
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun showDialog(message: String) {
        MessageDialog.showMessage(fragmentManager, message, null)
    }

    override fun startInsertMedicationActivity() {
        InsertMedicationActivity.start(this, REQUEST_NEW_MEDICINE)
    }

    override fun startMedicationDetailActivity(medication: MedicationRecyclerItem) {
        MedicationDetailActivity.start(this, medication.medId, REQUEST_DETAILS)
    }

    override fun updateMedicationRemoved(position: Int) {
        recyclerView.adapter.notifyItemRemoved(position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult()
    }

    override fun hideEmptyList() {
        emptyMeds.visibility = View.GONE
    }

    override fun showEmptyList() {
        emptyMeds.visibility = View.VISIBLE
    }

    override fun hideMeds() {
        recyclerView.visibility = View.GONE
    }

    override fun showMeds() {
        recyclerView.visibility = View.VISIBLE
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MedicationListAdapter(presenter)




        val itemTouchHelper = ItemTouchHelper(SwipeLeftRightMedication(activity, object : SwipeLeftRightMedication.Callback {
            override fun onSwiped(adapterPosition: Int, direction: Int) {
                presenter.onSwipedMedication(adapterPosition)
            }
        }))

        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun initEmptyMedsView() {
        emptyMeds.visibility = View.GONE
    }

    private fun initToolbar(view: View) {
        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(R.string.app_name)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

    }

    private fun initFab(view: View) {
        val fab = view.findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            presenter.onClickNewMed()
        }
    }

}
