package br.com.sailboat.medzy.view.medicine.list

import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import br.com.sailboat.canoe.base.BaseFragment
import br.com.sailboat.canoe.helper.DialogHelper
import br.com.sailboat.medzy.R

import br.com.sailboat.medzy.view.adapter.MedicineListAdapter
import br.com.sailboat.medzy.view.adapter.MedicineViewHolderItemTouchHelper
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel
import br.com.sailboat.medzy.view.medicine.detail.MedicineDetailActivity
import br.com.sailboat.medzy.view.medicine.insert.InsertMedicineActivity
import br.com.sailboat.medzy.view.medicine.list.presenter.MedicineListPresenter
import kotlinx.android.synthetic.main.empty_meds.*

class MedicineListFragment : BaseFragment<MedicineListPresenter>(), MedicineListPresenter.View {

    private val REQUEST_NEW_MEDICINE = 0
    private val REQUEST_DETAILS = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar

    override fun getLayoutId(): Int {
        return R.layout.frag_medicine_list
    }

    override fun newPresenterInstance(): MedicineListPresenter {
        return MedicineListPresenter(this)
    }

    override fun initViews(view: View) {
        initRecyclerView(view)
        initEmptyMedsView()
        initToolbar(view)
        initFab(view)
    }

    override fun updateMedicines() {
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun showDialog(message: String) {
        DialogHelper.showMessage(activity, message, null)
    }

    override fun startInsertMedicineActivity() {
        InsertMedicineActivity.start(this, REQUEST_NEW_MEDICINE)
    }

    override fun startMedicineDetailActivity(medicine: MedicineVHModel) {
        MedicineDetailActivity.start(this, medicine, REQUEST_DETAILS)
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

    override fun hideMedicines() {
        recyclerView.visibility = View.GONE
    }

    override fun showMedicines() {
        recyclerView.visibility = View.VISIBLE
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MedicineListAdapter(presenter)




        val itemTouchHelper = ItemTouchHelper(MedicineViewHolderItemTouchHelper(activity, object : MedicineViewHolderItemTouchHelper.Callback {
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
            presenter.onClickNewMedicine()
        }
    }

}
